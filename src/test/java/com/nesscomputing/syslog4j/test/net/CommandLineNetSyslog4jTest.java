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

import java.net.SocketAddress;

import junit.framework.TestCase;

import com.nesscomputing.syslog4j.Syslog;
import com.nesscomputing.syslog4j.SyslogMain;
import com.nesscomputing.syslog4j.server.SyslogServer;
import com.nesscomputing.syslog4j.server.SyslogServerEventIF;
import com.nesscomputing.syslog4j.server.SyslogServerIF;
import com.nesscomputing.syslog4j.server.SyslogServerSessionEventHandlerIF;
import com.nesscomputing.syslog4j.util.SyslogUtility;

public class CommandLineNetSyslog4jTest extends TestCase {
    public static class CaptureHandler implements SyslogServerSessionEventHandlerIF {
        public SyslogServerEventIF capturedEvent = null;

        public void destroy(SyslogServerIF syslogServer) {
            // TODO Auto-generated method stub

        }

        public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress) {
            return null;
        }

        public void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event) {
            this.capturedEvent = event;
        }

        public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
            fail(exception.getMessage());
        }

        public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout) {
            //
        }

        public void initialize(SyslogServerIF syslogServer) {
            // TODO Auto-generated method stub

        }
    }

    public void testUDP() {
        testSendReceive("udp",false);
    }

    public void testTCP() {
        testSendReceive("tcp",true);
    }

    public void testSendReceive(String protocol, boolean useSyslogClass) {
        SyslogServer.getInstance(protocol).getConfig().setPort(1514);
        SyslogServerIF syslogServer = SyslogServer.getThreadedInstance(protocol);

        SyslogUtility.sleep(100);

        CaptureHandler captureHandler = new CaptureHandler();
        syslogServer.getConfig().addEventHandler(captureHandler);

        String message = "test message";

        try {
            if (useSyslogClass) {
                Syslog.main(new String[] { "-p", "1514", protocol, message }, false);

            } else {
                SyslogMain.main(new String[] { "-p", "1514", protocol, message }, false);
            }

        } catch (Exception e) {
            //
        }

        SyslogUtility.sleep(250);

        assertTrue(captureHandler.capturedEvent.getMessage().endsWith(message));

        syslogServer.shutdown();

        SyslogUtility.sleep(100);
    }
}
