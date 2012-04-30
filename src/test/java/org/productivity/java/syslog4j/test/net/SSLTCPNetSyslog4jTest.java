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
import org.productivity.java.syslog4j.impl.net.tcp.ssl.SSLTCPNetSyslogConfig;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.impl.net.tcp.ssl.SSLTCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.tcp.ssl.SSLTCPNetSyslogServerConfigIF;

public class SSLTCPNetSyslog4jTest extends AbstractNetSyslog4jTest {
    protected int getMessageCount() {
        return 100;
    }

    protected String getClientProtocol() {
        return "sslTcp";
    }

    protected String getServerProtocol() {
        return "sslTcp";
    }

    public void setUp() {
        setupSslClient();
        setupSslServer();

        super.setUp();
    }

    protected void setupSslClient() {
        SSLTCPNetSyslogConfig config = new SSLTCPNetSyslogConfig("127.0.0.1",10514);

        // These next two lines aren't needed, but put here for code coverage
        config.setKeyStore("certs/ssltest.jks");
        config.setKeyStorePassword("ssltest");

        config.setTrustStore("certs/ssltest.jks");
        config.setTrustStorePassword("ssltest");

        Syslog.createInstance("sslTcp",config);
    }

    protected void setupSslServer() {
        SSLTCPNetSyslogServerConfigIF config = new SSLTCPNetSyslogServerConfig();

        config.setKeyStore("certs/ssltest.jks");
        config.setKeyStorePassword("ssltest");

        config.setTrustStore("certs/ssltest.jks");
        config.setTrustStorePassword("ssltest");

        SyslogServer.createInstance("sslTcp", config);
    }

    protected boolean isSyslogServerTcpBacklog() {
        return true;
    }

    public void testSendReceive() {
        super._testSendReceive(true,true);
    }

    public void testThreadedSendReceive() {
        super._testThreadedSendReceive(50,true,true);
    }
}
