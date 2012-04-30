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
package org.productivity.java.syslog4j.impl.unix;

import static org.productivity.java.syslog4j.SyslogConstants.OPTION_NONE;
import static org.productivity.java.syslog4j.SyslogConstants.SYSLOG_LIBRARY_DEFAULT;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslogConfig;
/**
* UnixSyslogConfig is an extension of AbstractNetSyslogConfig that provides
* configuration support for Unix-based syslog clients.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UnixSyslogConfig.java,v 1.13 2010/10/25 03:50:25 cvs Exp $
*/
public class UnixSyslogConfig extends AbstractSyslogConfig {
    protected String library = SYSLOG_LIBRARY_DEFAULT;
    protected int option = OPTION_NONE;

    public UnixSyslogConfig() {
        // Unix-based syslog does not need localName sent
        this.setSendLocalName(false);
    }

    public Class<? extends SyslogIF> getSyslogClass() {
        return UnixSyslog.class;
    }

    public String getHost() {
        return null;
    }

    public int getPort() {
        return 0;
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

    public int getOption() {
        return this.option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getMaxQueueSize() {
        throw new SyslogRuntimeException("UnixSyslog protocol does not uses a queueing mechanism");
    }

    public void setMaxQueueSize(int maxQueueSize) {
        throw new SyslogRuntimeException("UnixSyslog protocol does not uses a queueing mechanism");
    }
}
