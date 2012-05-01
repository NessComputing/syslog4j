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
import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.multiple.MultipleSyslogConfig;
import com.nesscomputing.syslog4j.impl.net.AbstractNetSyslogConfig;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogConfig;

public class MultipleSyslog4jTest extends AbstractNetSyslog4jTest {
    protected void setupMultipleConfig() {
        ((AbstractNetSyslogConfig) Syslog.getInstance("tcp").getConfig()).setPort(TEST_PORT);

        MultipleSyslogConfig config = new MultipleSyslogConfig();

        config.addProtocol("tcp");

        Syslog.createInstance("multipleTcp",config);
    }

    protected int getMessageCount() {
        return 100;
    }

    protected String getClientProtocol() {
        return "multipleTcp";
    }

    protected String getServerProtocol() {
        return "tcp";
    }

    public void _testExceptionThrows() {
        SyslogIF s = Syslog.getInstance("multipleTcp");

        // NO-OPs
        s.backLog(null,null,"");
        s.backLog(null,null,new Exception());

        // Exceptions for methods that shouldn't be called
        try { s.setMessageProcessor(null); fail(); } catch (SyslogRuntimeException e) { }
        try { s.getMessageProcessor(); fail(); } catch (SyslogRuntimeException e) { }

        // Exceptions for methods that shouldn't be called
        try { s.setStructuredMessageProcessor(null); fail(); } catch (SyslogRuntimeException e) { }
        try { s.getStructuredMessageProcessor(); fail(); } catch (SyslogRuntimeException e) { }

        // Initialize Exceptions
        try {
            s.initialize(null,new TCPNetSyslogConfig());
            fail();

        } catch (SyslogRuntimeException e) {
            //
        }
    }

    public void testSendReceive() {
        setupMultipleConfig();

        _testExceptionThrows();

        super._testSendReceive(true,true);
    }
}
