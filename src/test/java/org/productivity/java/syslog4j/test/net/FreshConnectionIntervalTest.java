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
package org.productivity.java.syslog4j.test.net;

import java.net.SocketAddress;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslogConfigIF;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfigIF;
import org.productivity.java.syslog4j.util.SyslogUtility;

public class FreshConnectionIntervalTest extends TestCase {
    protected static final Logger LOG = Logger.getLogger("test");

    public class SocketCounter implements SyslogServerSessionEventHandlerIF {
        public int openCounter = 0;
        public int eventCounter = 0;
        public int closeCounter = 0;

        public void initialize(SyslogServerIF syslogServer) {
            //
        }

        public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress) {
            openCounter++;
            return null;
        }

        public void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event) {
            eventCounter++;
            LOG.info(openCounter + "/" + eventCounter + "/" + closeCounter + " " + event.getMessage() + " " + (event.isHostStrippedFromMessage() ? "host_stripped" : "host_not_stripped"));
        }

        public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
            //
        }

        public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout) {
            closeCounter++;
        }

        public void destroy(SyslogServerIF syslogServer) {
            //
        }
    }

    public void testFreshConnectionInterval() {
        TCPNetSyslogServerConfigIF serverConfig = new TCPNetSyslogServerConfig();
        serverConfig.setPort(8888);

        SocketCounter counter = new SocketCounter();
        serverConfig.addEventHandler(counter);

        SyslogServerIF server = SyslogServer.createThreadedInstance("tcp_8888",serverConfig);

        SyslogUtility.sleep(100);

        TCPNetSyslogConfigIF config = new TCPNetSyslogConfig();
        config.setPort(8888);
        config.setFreshConnectionInterval(300);

        SyslogIF syslog = Syslog.createInstance("tcp_8888",config);

        for(int i=0; i<10; i++) {
            syslog.info("message " + i);
            SyslogUtility.sleep(100);
        }

        SyslogUtility.sleep(100);

        SyslogServer.destroyInstance(server);
        Syslog.destroyInstance(syslog);

        SyslogUtility.sleep(100);

        assertEquals("OpenCounter",3,counter.openCounter);
        assertEquals("EventCounter",10,counter.eventCounter);
        assertEquals("CloseCounter",3,counter.closeCounter);
    }
}
