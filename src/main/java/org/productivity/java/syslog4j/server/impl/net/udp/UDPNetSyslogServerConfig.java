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
package org.productivity.java.syslog4j.server.impl.net.udp;

import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.impl.net.AbstractNetSyslogServerConfig;

/**
* UDPNetSyslogServerConfig provides configuration for UDPNetSyslogServer.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UDPNetSyslogServerConfig.java,v 1.6 2010/10/28 05:10:57 cvs Exp $
*/
public class UDPNetSyslogServerConfig extends AbstractNetSyslogServerConfig {
    public UDPNetSyslogServerConfig() {
        //
    }

    public UDPNetSyslogServerConfig(int port) {
        this.port = port;
    }

    public UDPNetSyslogServerConfig(String host) {
        this.host = host;
    }

    public UDPNetSyslogServerConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Class<? extends SyslogServerIF> getSyslogServerClass() {
        return UDPNetSyslogServer.class;
    }
}
