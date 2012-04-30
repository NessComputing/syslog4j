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
import org.productivity.java.syslog4j.impl.net.tcp.ssl.pool.PooledSSLTCPNetSyslogConfig;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.impl.net.tcp.ssl.SSLTCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.tcp.ssl.SSLTCPNetSyslogServerConfigIF;

public class PooledSSLTCPNetSyslog4jTest extends AbstractNetSyslog4jTest {
    protected void setupPoolConfig(boolean threaded, int maxActive, int maxWait) {
        PooledSSLTCPNetSyslogConfig config = new PooledSSLTCPNetSyslogConfig();

        // These next two lines aren't needed, but put here for code coverage
        config.setKeyStore("certs/ssltest.jks");
        config.setKeyStorePassword("ssltest");

        config.setTrustStore("certs/ssltest.jks");
        config.setTrustStorePassword("ssltest");

        config.setThreaded(threaded);
        config.setThrowExceptionOnWrite(true);
        config.setThrowExceptionOnInitialize(true);

        if (maxWait > 0) {
            config.setMaxWait(maxWait);
        }

        if (maxActive > 0) {
            config.setMaxActive(maxActive);
        }

        Syslog.createInstance("pooledSslTcp",config);
    }

    protected int getMessageCount() {
        return 250;
    }

    protected String getClientProtocol() {
        return "pooledSslTcp";
    }

    protected String getServerProtocol() {
        SSLTCPNetSyslogServerConfigIF config = new SSLTCPNetSyslogServerConfig();

        config.setKeyStore("certs/ssltest.jks");
        config.setKeyStorePassword("ssltest");

        config.setTrustStore("certs/ssltest.jks");
        config.setTrustStorePassword("ssltest");

        SyslogServer.createThreadedInstance("pooledSslTcp", config);

        return "pooledSslTcp";
    }

    public void _testOne() {
        setupPoolConfig(false,0,0);

        getSyslog("pooledSslTcp").info("[TEST] test");
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
