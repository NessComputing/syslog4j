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
package org.productivity.java.syslog4j.impl.net.tcp;

import static org.productivity.java.syslog4j.SyslogConstants.TCP_FRESH_CONNECTION_INTERVAL_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_KEEP_ALIVE_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_PERSISTENT_CONNECTION_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_REUSE_ADDRESS_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_SET_BUFFER_SIZE_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_SO_LINGER_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TCP_SO_LINGER_SECONDS_DEFAULT;

import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.impl.net.AbstractNetSyslogConfig;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.base.Charsets;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
/**
* TCPNetSyslogConfig is an extension of AbstractNetSyslogConfig that provides
* configuration support for TCP/IP-based syslog clients.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: TCPNetSyslogConfig.java,v 1.18 2010/10/29 03:14:12 cvs Exp $
*/
public class TCPNetSyslogConfig extends AbstractNetSyslogConfig implements TCPNetSyslogConfigIF {
    static final byte[] SYSTEM_DELIMITER_SEQUENCE;

    static {
        String delimiterSequence = System.getProperty("line.separator");

        byte [] systemDelimiterSequence  = delimiterSequence.getBytes(Charsets.UTF_8);

        if (systemDelimiterSequence == null || systemDelimiterSequence.length < 1) {
            systemDelimiterSequence = SyslogConstants.TCP_DELIMITER_SEQUENCE_DEFAULT;
        }

        SYSTEM_DELIMITER_SEQUENCE = systemDelimiterSequence;
    }

    protected byte[] delimiterSequence = SYSTEM_DELIMITER_SEQUENCE;

    protected boolean persistentConnection = TCP_PERSISTENT_CONNECTION_DEFAULT;

    protected boolean soLinger = TCP_SO_LINGER_DEFAULT;
    protected int soLingerSeconds = TCP_SO_LINGER_SECONDS_DEFAULT;

    protected boolean keepAlive = TCP_KEEP_ALIVE_DEFAULT;

    protected boolean reuseAddress = TCP_REUSE_ADDRESS_DEFAULT;

    protected boolean setBufferSize = TCP_SET_BUFFER_SIZE_DEFAULT;

    protected int freshConnectionInterval = TCP_FRESH_CONNECTION_INTERVAL_DEFAULT;

    public TCPNetSyslogConfig() {
        initialize();
    }

    protected void initialize() {
        //
    }

    public TCPNetSyslogConfig(SyslogFacility facility, String host, int port) {
        super(facility, host, port);
        initialize();
    }

    public TCPNetSyslogConfig(SyslogFacility facility, String host) {
        super(facility, host);
        initialize();
    }

    public TCPNetSyslogConfig(SyslogFacility facility) {
        super(facility);
        initialize();
    }

    public TCPNetSyslogConfig(String host, int port) {
        super(host, port);
        initialize();
    }

    public TCPNetSyslogConfig(String host) {
        super(host);
        initialize();
    }

    public Class<? extends SyslogIF> getSyslogClass() {
        return TCPNetSyslog.class;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    public byte[] getDelimiterSequence() {
        return this.delimiterSequence;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public void setDelimiterSequence(byte[] delimiterSequence) {
        this.delimiterSequence = delimiterSequence;
    }

    public void setDelimiterSequence(String delimiterSequence) {
        this.delimiterSequence = SyslogUtility.getBytes(this,delimiterSequence);
    }

    public boolean isPersistentConnection() {
        return this.persistentConnection;
    }

    public void setPersistentConnection(boolean persistentConnection) {
        this.persistentConnection = persistentConnection;
    }

    public boolean isSoLinger() {
        return this.soLinger;
    }

    public void setSoLinger(boolean soLinger) {
        this.soLinger = soLinger;
    }

    public int getSoLingerSeconds() {
        return this.soLingerSeconds;
    }

    public void setSoLingerSeconds(int soLingerSeconds) {
        this.soLingerSeconds = soLingerSeconds;
    }

    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isReuseAddress() {
        return this.reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public boolean isSetBufferSize() {
        return this.setBufferSize;
    }

    public void setSetBufferSize(boolean setBufferSize) {
        this.setBufferSize = setBufferSize;
    }

    public int getFreshConnectionInterval() {
        return freshConnectionInterval;
    }

    public void setFreshConnectionInterval(int freshConnectionInterval) {
        this.freshConnectionInterval = freshConnectionInterval;
    }

    public Class<? extends AbstractSyslogWriter> getSyslogWriterClass() {
        return TCPNetSyslogWriter.class;
    }
}
