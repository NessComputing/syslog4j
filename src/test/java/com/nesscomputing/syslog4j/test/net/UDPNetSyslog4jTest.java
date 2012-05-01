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
import com.nesscomputing.syslog4j.server.SyslogServer;

public class UDPNetSyslog4jTest extends AbstractNetSyslog4jTest {
    protected static boolean ONCE = true;

    public void setUp() {
        if (ONCE) {
            ONCE = false;

        } else {
            SyslogServer.getInstance(getServerProtocol()).getConfig().setHost("127.0.0.1");
        }

        super.setUp();
    }

    protected int getMessageCount() {
        return 100;
    }

    protected String getClientProtocol() {
        return "udp";
    }

    protected String getServerProtocol() {
        return "udp";
    }

    public void testSendReceive() {
        super._testSendReceive(true,true);
    }

    public void xtestThreadedSendReceive() {
        super._testThreadedSendReceive(10,true,true);
    }

    public void testPCIMessages() {
        super._testSendReceivePCIMessages(true,true);
    }

    public void testStructuredMessages() {
        super._testSendReceiveStructuredMessages(true,true);
    }
}
