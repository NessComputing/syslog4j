/**
 *
 * (C) Copyright 2008-2011 syslog4j.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package com.nesscomputing.syslog4j.server.impl.event;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogLevel;
import com.nesscomputing.syslog4j.server.SyslogServerEventIF;
import com.nesscomputing.syslog4j.util.SyslogUtility;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
* SyslogServerEvent provides an implementation of the SyslogServerEventIF interface.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerEvent.java,v 1.9 2011/01/11 06:21:15 cvs Exp $
*/
public class SyslogServerEvent implements SyslogServerEventIF {
    private static final Logger LOG = Logger.getLogger(SyslogServerEvent.class);

    public static final String DATE_FORMAT = "MMM dd HH:mm:ss yyyy";

    protected Charset charSet = Charsets.UTF_8;
    protected String rawString = null;
    protected byte[] rawBytes = null;
    protected int rawLength = -1;
    protected Date date = null;
    protected SyslogLevel level = SyslogLevel.INFO;
    protected SyslogFacility facility = SyslogFacility.user;
    protected String host = null;
    protected boolean isHostStrippedFromMessage = false;
    protected String message = null;
    protected InetAddress inetAddress = null;

    protected SyslogServerEvent() { }

    public SyslogServerEvent(final String message, InetAddress inetAddress) {
        initialize(message,inetAddress);

        parse();
    }

    public SyslogServerEvent(final byte[] message, int length, InetAddress inetAddress) {
        initialize(message,length,inetAddress);

        parse();
    }

    protected void initialize(final String message, InetAddress inetAddress) {
        this.rawString = message;
        this.rawLength = message.length();
        this.inetAddress = inetAddress;

        this.message = message;
    }

    protected void initialize(final byte[] message, int length, InetAddress inetAddress) {
        this.rawBytes = message;
        this.rawLength = length;
        this.inetAddress = inetAddress;
    }

    protected void parseHost() {
        int i = this.message.indexOf(' ');

        if (i > -1) {
            String hostAddress = null;
            String hostName = null;

            String providedHost = StringUtils.trimToEmpty(this.message.substring(0,i));

            hostAddress = this.inetAddress.getHostAddress();

            if (providedHost.equalsIgnoreCase(hostAddress)) {
                this.host = hostAddress;
                this.message = this.message.substring(i+1);
                isHostStrippedFromMessage = true;
            }

            if (this.host == null) {
                hostName = this.inetAddress.getHostName();

                if (!hostName.equalsIgnoreCase(hostAddress)) {
                    if (providedHost.equalsIgnoreCase(hostName)) {
                        this.host = hostName;
                        this.message = this.message.substring(i+1);
                        isHostStrippedFromMessage = true;
                    }

                    if (this.host == null) {
                        int j = hostName.indexOf('.');

                        if (j > -1) {
                            hostName = hostName.substring(0,j);
                        }

                        if (providedHost.equalsIgnoreCase(hostName)) {
                            this.host = hostName;
                            this.message = this.message.substring(i+1);
                            isHostStrippedFromMessage = true;
                        }
                    }
                }
            }

            if (this.host == null) {
                this.host = (hostName != null) ? hostName : hostAddress;
            }
        }
    }

    protected void parseDate() {
        if (this.message.length() >= 16 && this.message.charAt(3) == ' ' && this.message.charAt(6) == ' ') {
            String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

            String originalDate = this.message.substring(0,15) + " " + year;

            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            try {
                this.date = dateFormat.parse(originalDate);

                this.message = this.message.substring(16);

            } catch (ParseException pe) {
                this.date = new Date();
            }
        }

        parseHost();
    }

    protected void parsePriority() {
        if (this.message.charAt(0) == '<') {
            int i = this.message.indexOf(">");

            if (i <= 4 && i > -1) {
                String priorityStr = this.message.substring(1,i);

                int priority = 0;
                try {
                    priority = Integer.parseInt(priorityStr);
                    this.facility = SyslogFacility.forValue(priority >> 3);
                    this.level = SyslogLevel.values()[priority & 7];

                    this.message = this.message.substring(i+1);

                    parseDate();

                } catch (NumberFormatException nfe) {
                    LOG.trace("While parsing priority", nfe);
                }

                parseHost();
            }
        }
    }

    protected void parse() {
        if (this.message == null) {
            this.message = SyslogUtility.newString(this,this.rawBytes,this.rawLength);
        }

        parsePriority();
    }

    public SyslogFacility getFacility() {
        return this.facility;
    }

    public void setFacility(SyslogFacility facility) {
        this.facility = facility;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    public byte[] getRaw() {
        if (this.rawString != null) {
            byte[] rawStringBytes = SyslogUtility.getBytes(this,this.rawString);

            return rawStringBytes;

        } else if (this.rawBytes.length == this.rawLength) {
            return this.rawBytes;

        } else {
            byte[] newRawBytes = new byte[this.rawLength];
            System.arraycopy(this.rawBytes,0,newRawBytes,0,this.rawLength);

            return newRawBytes;
        }
    }

    public int getRawLength() {
        return this.rawLength;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    public Date getDate() {
        return this.date;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public void setDate(Date date) {
        this.date = date;
    }

    public SyslogLevel getLevel() {
        return this.level;
    }

    public void setLevel(SyslogLevel level) {
        this.level = level;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isHostStrippedFromMessage() {
        return isHostStrippedFromMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Charset getCharSet() {
        return this.charSet;
    }

    @Override
    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }
}
