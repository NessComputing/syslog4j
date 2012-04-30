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
package org.productivity.java.syslog4j.server.impl.net;

import static org.productivity.java.syslog4j.SyslogConstants.SYSLOG_PORT_DEFAULT;

import org.productivity.java.syslog4j.server.impl.AbstractSyslogServerConfig;
/**
* AbstractNetSyslogServerConfig provides a base abstract implementation of the AbstractSyslogServerConfig
* configuration interface.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractNetSyslogServerConfig.java,v 1.4 2008/11/07 15:15:41 cvs Exp $
*/
public abstract class AbstractNetSyslogServerConfig extends AbstractSyslogServerConfig {
    protected String host = null;
    protected int port = SYSLOG_PORT_DEFAULT;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
