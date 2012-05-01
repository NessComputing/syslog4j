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
package org.productivity.java.syslog4j;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;
import org.productivity.java.syslog4j.server.impl.net.AbstractNetSyslogServerConfig;
import org.productivity.java.syslog4j.test.base.AbstractBaseTest;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Lists;

public abstract class AbstractLog4jSyslog4jTest extends AbstractBaseTest {

    protected static final Logger LOG = Logger.getLogger("test");

    protected static class RecorderHandler implements SyslogServerSessionEventHandlerIF {
        protected List<String> recordedEvents = Lists.newArrayList();

        public List<String> getRecordedEvents() {
            return this.recordedEvents;
        }

        public void initialize(SyslogServerIF syslogServer) {
            //
        }

        public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress) {
            return null;
        }

        public void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event) {
            String recordedEvent = SyslogUtility.newString(syslogServer.getConfig(),event.getRaw());

            recordedEvent = recordedEvent.substring(recordedEvent.toUpperCase().indexOf("[TEST] "));

            this.recordedEvents.add(recordedEvent);
        }

        public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
            fail(exception.getMessage());
        }

        public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout) {
            //
        }

        public void destroy(SyslogServerIF syslogServer) {
            //
        }
    }

    public static final int TEST_PORT = 10514;

    protected SyslogServerIF server = null;

    protected abstract String getServerProtocol();

    protected abstract int getMessageCount();

    protected RecorderHandler recorderEventHandler = new RecorderHandler();

    protected void startServerThread(String protocol) {
        this.server = SyslogServer.getInstance(protocol);

        AbstractNetSyslogServerConfig config = (AbstractNetSyslogServerConfig) this.server.getConfig();
        config.setPort(TEST_PORT);
        config.addEventHandler(this.recorderEventHandler);

        this.server = SyslogServer.getThreadedInstance(protocol);
    }

    public void setUp() {
        UDPNetSyslogConfig config = new UDPNetSyslogConfig();

        assertTrue(config.isCacheHostAddress());
        config.setCacheHostAddress(false);
        assertFalse(config.isCacheHostAddress());

        assertTrue(config.isThrowExceptionOnInitialize());
        config.setThrowExceptionOnInitialize(false);
        assertFalse(config.isThrowExceptionOnInitialize());

        assertFalse(config.isThrowExceptionOnWrite());
        config.setThrowExceptionOnWrite(true);
        assertTrue(config.isThrowExceptionOnWrite());

        Syslog.createInstance("log4jUdp",config);

        String protocol = getServerProtocol();

        startServerThread(protocol);
        SyslogUtility.sleep(100);
    }

    protected void verifySendReceive(List<String> events, boolean sort) {
        if (sort) {
            Collections.sort(events);
        }

        List<String> recordedEvents = this.recorderEventHandler.getRecordedEvents();

        if (sort) {
            Collections.sort(recordedEvents);
        }

        for(int i=0; i < events.size(); i++) {
            String sentEvent = (String) events.get(i);

            String recordedEvent = (String) recordedEvents.get(i);

            if (!sentEvent.equals(recordedEvent)) {
                LOG.info("SENT: " + sentEvent);
                LOG.info("RCVD: " + recordedEvent);

                fail("Sent and recorded events do not match");
            }
        }
    }

    public void _testSendReceive(){
        Logger logger = Logger.getLogger(this.getClass());

        List<String> events = Lists.newArrayList();

        for(int i=0; i<getMessageCount(); i++) {
            String message = "[TEST] " + i + " / " + System.currentTimeMillis();

            logger.info(message);
            events.add(message);
        }

        SyslogUtility.sleep(100);

        verifySendReceive(events,true);
    }

    public void tearDown() throws InterruptedException {
        Syslog.reset();

        SyslogServer.shutdown();

        SyslogUtility.sleep(100);

        SyslogServer.initialize();
    }
}
