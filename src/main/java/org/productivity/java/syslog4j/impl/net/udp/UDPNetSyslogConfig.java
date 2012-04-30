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
package org.productivity.java.syslog4j.impl.net.udp;

import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.net.AbstractNetSyslogConfig;

/**
* UDPNetSyslogConfig is an extension of AbstractNetSyslogConfig that provides
* configuration support for UDP/IP-based syslog clients.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UDPNetSyslogConfig.java,v 1.6 2008/11/14 04:32:00 cvs Exp $
*/
public class UDPNetSyslogConfig extends AbstractNetSyslogConfig {
    public UDPNetSyslogConfig() {
        super();
    }

    public UDPNetSyslogConfig(SyslogFacility facility, String host, int port) {
        super(facility,host,port);
    }

    public UDPNetSyslogConfig(SyslogFacility facility, String host) {
        super(facility,host);
    }

    public UDPNetSyslogConfig(SyslogFacility facility) {
        super(facility);
    }

    public UDPNetSyslogConfig(String host, int port) {
        super(host,port);
    }

    public UDPNetSyslogConfig(String host) {
        super(host);
    }

    public Class<? extends SyslogIF> getSyslogClass() {
        return UDPNetSyslog.class;
    }
}
