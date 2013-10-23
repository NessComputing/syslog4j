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
package com.nesscomputing.syslog4j.test.server.event;

import java.net.InetAddress;

import com.nesscomputing.syslog4j.server.impl.event.SyslogServerEvent;

import junit.framework.TestCase;

public class ServerEventTest extends TestCase {
    public void testServerEvent() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();

        int i = hostName.indexOf('.');
        if (i > -1) {
            hostName = hostName.substring(0,i);
        }

        String baseMessage = "<1>Jan  1 00:00:00 ";

        byte[] message = (baseMessage + "test").getBytes();
        SyslogServerEvent event = new SyslogServerEvent(message,message.length,inetAddress);
        assertNull(event.getHost());

        message = (baseMessage + hostName + " test").getBytes();
        event = new SyslogServerEvent(message,message.length,inetAddress);
        assertEquals(hostName,event.getHost());
        assertEquals("test",event.getMessage());
        assertTrue(event.isHostStrippedFromMessage());

        InetAddress mirrorInetAddress = InetAddress.getByAddress("mirror.productivity.org", new byte [] { -72, 22, -86, -77 });
        String mirrorHostName = "mirror";

        message = (baseMessage + mirrorHostName + " test").getBytes();
        event = new SyslogServerEvent(message,message.length,mirrorInetAddress);
        assertEquals(mirrorHostName,event.getHost());
        assertEquals("test",event.getMessage());
        assertTrue(event.isHostStrippedFromMessage());

        String alteredHostName = hostName + "1";

        message = (baseMessage + alteredHostName + " test").getBytes();
        event = new SyslogServerEvent(message,message.length,inetAddress);
        assertEquals(hostName,event.getHost());
        assertEquals(alteredHostName + " test",event.getMessage());
        assertFalse(event.isHostStrippedFromMessage());

        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        message = (baseMessage + hostAddress + " test").getBytes();
        event = new SyslogServerEvent(message,message.length,inetAddress);
        assertEquals(hostAddress,event.getHost());
        assertEquals("test",event.getMessage());
        assertTrue(event.isHostStrippedFromMessage());

        baseMessage = "<1>Xan  1 00:00:00 ";

        message = (baseMessage + "test").getBytes();
        new SyslogServerEvent(message,message.length,inetAddress);

        baseMessage = "<x>Jan  1 00:00:00 ";

        message = (baseMessage + "test").getBytes();
        new SyslogServerEvent(message,message.length,inetAddress);
    }
}
