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
package org.productivity.java.syslog4j.test.server.event;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;

import junit.framework.TestCase;

import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;
import org.productivity.java.syslog4j.server.impl.event.SyslogServerEvent;
import org.productivity.java.syslog4j.server.impl.event.printstream.FileSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.event.printstream.PrintStreamSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.event.printstream.SystemErrSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.event.printstream.SystemOutSyslogServerEventHandler;

import com.google.common.base.Charsets;

public class PrintStreamServerEventTest extends TestCase {
    public void testPrintStreamEvent() {
        SyslogServerIF server = SyslogServer.getInstance("udp");

        String message = "test message";

        InetAddress inetAddress = null;

        try { inetAddress = InetAddress.getLocalHost(); } catch (UnknownHostException uhe) { }

        SyslogServerEventIF event = new SyslogServerEvent(message.getBytes(),message.length(),inetAddress);

        assertEquals(Charsets.UTF_8, event.getCharSet());
        event.setCharSet(Charset.forName("us-ascii"));
        assertEquals(Charset.forName("us-ascii"), event.getCharSet());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        SyslogServerSessionEventHandlerIF eventHandler = new PrintStreamSyslogServerEventHandler(ps);
        eventHandler.sessionOpened(server,null);
        eventHandler.event(null,server,null,event);
        eventHandler.exception(null,server,null,null);
        eventHandler.sessionClosed(null,server,null,false);
        assertEquals(event.getMessage(),new String("test message"));

        Date date = new Date();
        event.setDate(date);
        assertTrue(date == event.getDate());

        event.setFacility(SyslogFacility.local0);
        assertEquals(SyslogFacility.local0,event.getFacility());

        event.setHost("foo");
        assertEquals("foo",event.getHost());

        event.setLevel(SyslogLevel.DEBUG);
        assertEquals(SyslogLevel.DEBUG,event.getLevel());

        event.setMessage(message);
        assertEquals(message,event.getMessage());

        eventHandler = SystemOutSyslogServerEventHandler.create();
        eventHandler.event(null,server,null,event);

        eventHandler = new SystemErrSyslogServerEventHandler();
        eventHandler.event(null,server,null,event);

        try {
            File f = File.createTempFile("syslog4j-test",".txt");

            eventHandler = new FileSyslogServerEventHandler(f.getPath());
            eventHandler.event(null,server,null,event);

            eventHandler = new FileSyslogServerEventHandler(f.getPath(),true);
            eventHandler.event(null,server,null,event);

        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
