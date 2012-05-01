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
package org.productivity.java.syslog4j.test.message;

import java.util.Date;
import java.util.List;

import org.productivity.java.syslog4j.AbstractNetSyslog4jTest;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.message.pci.PCISyslogMessage;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Lists;

public class UDPPCISyslogMessageTest extends AbstractNetSyslog4jTest {
    protected static final int pause = 100;

    protected int getMessageCount() {
        return -1;
    }

    protected String getClientProtocol() {
        return "udp";
    }

    protected String getServerProtocol() {
        return "udp";
    }

    public void testPCISyslogMessage() {
        // PREPARE

        List<String> events = Lists.newArrayList();
        String message = null;

        String protocol = getClientProtocol();
        SyslogIF syslog = getSyslog(protocol);

        message = new PCISyslogMessage("a","b",new Date(),"c","d","e").createMessage();
        syslog.info("[TEST] " + message);
        events.add("[TEST] " + message);

        // VERIFY

        SyslogUtility.sleep(pause);
        syslog.flush();
        verifySendReceive(events,false,false);
    }
}
