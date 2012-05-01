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
package com.nesscomputing.syslog4j.test.net;


import com.nesscomputing.syslog4j.AbstractNetSyslog4jTest;
import com.nesscomputing.syslog4j.Syslog;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogConfig;

public class NonPersistentTCPNetSyslog4jTest extends AbstractNetSyslog4jTest {
    public static final String instanceName = "tcp-non-persistent";

    public void setUp() {
        TCPNetSyslogConfig config = new TCPNetSyslogConfig();
        config.setPersistentConnection(false);

        Syslog.createInstance(instanceName,config);

        super.setUp();
    }

    protected int getMessageCount() {
        return 100;
    }

    protected String getClientProtocol() {
        return instanceName;
    }

    protected String getServerProtocol() {
        return "tcp";
    }

    public void testSendReceive() {
        super._testSendReceive(true,true);
    }

    public void testThreadedSendReceive() {
        super._testThreadedSendReceive(10,true,true);
    }
}
