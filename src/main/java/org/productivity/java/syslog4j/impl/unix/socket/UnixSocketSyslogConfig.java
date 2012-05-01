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
package org.productivity.java.syslog4j.impl.unix.socket;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslogConfig;
/**
* UnixSocketSyslogConfig is an extension of AbstractNetSyslogConfig that provides
* configuration support for Unix socket-based syslog clients.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UnixSocketSyslogConfig.java,v 1.8 2010/11/12 03:43:12 cvs Exp $
*/
public class UnixSocketSyslogConfig extends AbstractSyslogConfig {
    protected int type = SyslogConstants.SYSLOG_SOCKET_TYPE_DEFAULT;
    protected short family = SyslogConstants.SYSLOG_SOCKET_FAMILY_DEFAULT;
    protected int protocol = SyslogConstants.SYSLOG_SOCKET_PROTOCOL_DEFAULT;
    protected String library = SyslogConstants.SYSLOG_SOCKET_LIBRARY_DEFAULT;
    protected String path = SyslogConstants.SYSLOG_SOCKET_PATH_DEFAULT;

    public UnixSocketSyslogConfig() {
        // Unix-based socket does not need localName sent
        this.setSendLocalName(false);
        this.setIdent("java");
    }

    public Class<? extends SyslogIF> getSyslogClass() {
        return UnixSocketSyslog.class;
    }

    public UnixSocketSyslogConfig(SyslogFacility facility) {
        this.facility = facility;
    }

    public UnixSocketSyslogConfig(SyslogFacility facility, String path) {
        this.facility = facility;
        this.path = path;
    }

    public UnixSocketSyslogConfig(String path) {
        this.path = path;
    }

    public String getHost() {
        return null;
    }

    public int getPort() {
        return -1;
    }

    public void setHost(String host) throws SyslogRuntimeException {
        throw new SyslogRuntimeException("Host not appropriate for class \"%s\"", this.getClass().getName());
    }

    public void setPort(int port) throws SyslogRuntimeException {
        throw new SyslogRuntimeException("Port not appropriate for class \"%s\"", this.getClass().getName());
    }

    public String getLibrary() {
        return this.library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setType(String type) {
        if (type == null) {
            throw new SyslogRuntimeException("Type cannot be null for class \"%s\"", this.getClass().getName());
        }

        if ("SOCK_STREAM".equalsIgnoreCase(StringUtils.trimToEmpty(type))) {
            this.type = SyslogConstants.SOCK_STREAM;

        } else if ("SOCK_DGRAM".equalsIgnoreCase(StringUtils.trimToEmpty(type))) {
            this.type = SyslogConstants.SOCK_DGRAM;

        } else {
            throw new SyslogRuntimeException("Type must be \"SOCK_STREAM\" or \"SOCK_DGRAM\" for class \"%s\"", this.getClass().getName());
        }
    }

    public short getFamily() {
        return this.family;
    }

    public void setFamily(short family) {
        this.family = family;
    }

    public void setFamily(String family) {
        if (family == null) {
            throw new SyslogRuntimeException("Family cannot be null for class \"%s\"", this.getClass().getName());
        }

        if ("AF_UNIX".equalsIgnoreCase(StringUtils.trimToEmpty(family))) {
            this.family = SyslogConstants.AF_UNIX;

        } else {
            throw new SyslogRuntimeException("Family must be \"AF_UNIX\" for class \"%s\"", this.getClass().getName());
        }
    }

    public int getProtocol() {
        return this.protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getMaxQueueSize() {
        return -1;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        throw new SyslogRuntimeException("UnixSyslog protocol does not uses a queueing mechanism");
    }
}
