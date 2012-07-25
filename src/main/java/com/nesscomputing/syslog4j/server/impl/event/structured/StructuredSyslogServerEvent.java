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
package com.nesscomputing.syslog4j.server.impl.event.structured;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.nesscomputing.syslog4j.SyslogConstants;
import com.nesscomputing.syslog4j.impl.message.structured.StructuredSyslogMessage;
import com.nesscomputing.syslog4j.server.impl.event.SyslogServerEvent;

/**
 * SyslogServerStructuredEvent provides an implementation of the
 * SyslogServerEventIF interface that supports receiving of structured syslog
 * messages, as defined in:
 *
 * <p>
 * http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6
 * </p>
 *
 * <p>
 * Syslog4j is licensed under the Lesser GNU Public License v2.1. A copy of the
 * LGPL license is available in the META-INF folder in all distributions of
 * Syslog4j and in the base directory of the "doc" ZIP.
 * </p>
 *
 * @author Manish Motwani
 * @version $Id: StructuredSyslogServerEvent.java,v 1.6 2011/01/11 05:11:13 cvs Exp $
 */
public class StructuredSyslogServerEvent extends SyslogServerEvent {
    private String applicationName;
    private String processId;
    private DateTime dateTime;
    private DateTimeFormatter dateTimeFormatter;

    public StructuredSyslogServerEvent(final byte[] message, int length, InetAddress inetAddress) {
        super(message, length, inetAddress);
    }

    public StructuredSyslogServerEvent(final String message, InetAddress inetAddress) {
        super(message, inetAddress);
    }

    protected void parse()
    {
        applicationName = SyslogConstants.STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE;
        processId = null;
        dateTime = null;
        dateTimeFormatter = null;

        super.parse();
        parseApplicationName();
        parseProcessId();
    }

    public DateTimeFormatter getDateTimeFormatter() {
        if (dateTimeFormatter == null) {
            this.dateTimeFormatter = ISODateTimeFormat.dateTime();
        }

        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(Object dateTimeFormatter) {
        this.dateTimeFormatter = (DateTimeFormatter) dateTimeFormatter;
    }

    protected void parseApplicationName() {
        int i = this.message.indexOf(' ');

        if (i > -1) {
            this.applicationName = StringUtils.trimToEmpty(this.message.substring(0, i));
            this.message = this.message.substring(i + 1);
        }

        if (SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(this.applicationName)) {
            this.applicationName = null;
        }
    }

    protected void parseProcessId() {
        int i = this.message.indexOf(' ');

        if (i > -1) {
            this.processId = StringUtils.trimToEmpty(this.message.substring(0, i));
            this.message = this.message.substring(i + 1);
        }

        if (SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(this.processId)) {
            this.processId = null;
        }
    }

    protected void parseDate() {
        // skip VERSION field
        int i = this.message.indexOf(' ');
        this.message = this.message.substring(i + 1);

        // parse the date
        i = this.message.indexOf(' ');

        if (i > -1) {
            String dateString = StringUtils.trimToEmpty(this.message.substring(0, i));

            try {
                DateTimeFormatter formatter = getDateTimeFormatter();

                this.dateTime = formatter.parseDateTime(dateString);
                this.date = this.dateTime.toDate();

                this.message = this.message.substring(dateString.length() + 1);

            } catch (Exception e) {
                // Not structured date format, try super one
                super.parseDate();
            }
        }
    }

    protected void parseHost() {
        int i = this.message.indexOf(' ');

        if (i > -1) {
            this.host = StringUtils.trimToEmpty(this.message.substring(0,i));
            this.message = this.message.substring(i+1);
        }
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public String getProcessId() {
        return this.processId;
    }

    public DateTime getDateTime() {
        return this.dateTime;
    }

    public StructuredSyslogMessage getStructuredMessage()
    {
        return StructuredSyslogMessage.fromString(getMessage());
    }
}
