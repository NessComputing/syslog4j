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
import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogConfigIF;
import com.nesscomputing.syslog4j.server.SyslogServer;
import com.nesscomputing.syslog4j.server.SyslogServerEventIF;
import com.nesscomputing.syslog4j.server.SyslogServerIF;
import com.nesscomputing.syslog4j.server.SyslogServerSessionlessEventHandlerIF;
import com.nesscomputing.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;
import com.nesscomputing.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfigIF;
import com.nesscomputing.syslog4j.util.SyslogUtility;

public class MaxActiveSocketsTest extends TestCase {
    public class Counter implements SyslogServerSessionlessEventHandlerIF {
        public int counter = 0;

        public void initialize(SyslogServerIF syslogServer) {
            //
        }

        public void event(SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event) {
            counter++;
        }

        public void exception(SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
            //
        }

        public void destroy(SyslogServerIF syslogServer) {
            //
        }
    }

    protected int _testMaxActiveSockets(TCPNetSyslogServerConfigIF serverConfig) {
        Counter c = new Counter();

        serverConfig.setMaxActiveSockets(2);
        serverConfig.addEventHandler(c);

        SyslogServer.createThreadedInstance("tcp_maxactivesockets",serverConfig);

        TCPNetSyslogConfigIF config = new TCPNetSyslogConfig();
        config.setPort(8888);

        SyslogIF syslog1 = Syslog.createInstance("tcp_maxactivesockets1",config);
        syslog1.info("test1");
        syslog1.flush();
        SyslogUtility.sleep(200);

        SyslogIF syslog2 = Syslog.createInstance("tcp_maxactivesockets2",config);
        syslog2.info("test2");
        syslog2.flush();
        SyslogUtility.sleep(200);

        SyslogIF syslog3 = Syslog.createInstance("tcp_maxactivesockets3",config);
        syslog3.info("test3");
        syslog3.flush();
        SyslogUtility.sleep(200);

        syslog1.shutdown();
        SyslogUtility.sleep(200);

        Syslog.destroyInstance("tcp_maxactivesockets1");
        Syslog.destroyInstance("tcp_maxactivesockets2");
        Syslog.destroyInstance("tcp_maxactivesockets3");

        SyslogServer.destroyInstance("tcp_maxactivesockets");

        SyslogUtility.sleep(200);

        return c.counter;
    }

    public void testMaxActiveSocketsWithBlock() {
        TCPNetSyslogServerConfigIF serverConfig = new TCPNetSyslogServerConfig(8888);
        serverConfig.setMaxActiveSocketsBehavior(TCPNetSyslogServerConfigIF.MAX_ACTIVE_SOCKETS_BEHAVIOR_BLOCK);
        int count = _testMaxActiveSockets(serverConfig);

        assertEquals(3,count);
    }

    public void testMaxActiveSocketsWithReject() {
        TCPNetSyslogServerConfigIF serverConfig = new TCPNetSyslogServerConfig(8888);
        serverConfig.setMaxActiveSocketsBehavior(TCPNetSyslogServerConfigIF.MAX_ACTIVE_SOCKETS_BEHAVIOR_REJECT);
        int count = _testMaxActiveSockets(serverConfig);

        assertEquals(2,count);
    }
}
