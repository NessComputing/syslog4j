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

import org.productivity.java.syslog4j.AbstractNetSyslog4jTest;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.impl.net.tcp.pool.PooledTCPNetSyslogConfig;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;

public class PooledTCPNetSyslog4jTest extends AbstractNetSyslog4jTest {
    protected void setupPoolConfig(boolean threaded, int maxActive, int maxWait) {
        PooledTCPNetSyslogConfig config = new PooledTCPNetSyslogConfig();

        config.setThreaded(threaded);
        config.setThrowExceptionOnWrite(true);
        config.setThrowExceptionOnInitialize(true);

        if (maxWait > 0) {
            config.setMaxWait(maxWait);
        }

        if (maxActive > 0) {
            config.setMaxActive(maxActive);
        }

        Syslog.createInstance("pooledTcp",config);
    }

    protected int getMessageCount() {
        return 250;
    }

    protected String getClientProtocol() {
        return "pooledTcp";
    }

    protected String getServerProtocol() {
        TCPNetSyslogServerConfig serverConfig = new TCPNetSyslogServerConfig();

        SyslogServer.createThreadedInstance("pooledTcp", serverConfig);

        return "pooledTcp";
    }

    public void _testOne() {
        setupPoolConfig(false,0,0);

        getSyslog("pooledTcp").info("[TEST] test");
    }

    public void testThreadedSendReceive_50_threaded() {
        setupPoolConfig(true,0,8000);

        super._testThreadedSendReceive(50,true,true);
    }

    public void testThreadedSendReceive_50() {
        setupPoolConfig(false,0,8000);

        super._testThreadedSendReceive(50,true,true);
    }
}
