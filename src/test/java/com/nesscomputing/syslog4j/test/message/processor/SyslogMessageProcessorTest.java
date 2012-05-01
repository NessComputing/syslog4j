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
package com.nesscomputing.syslog4j.test.message.processor;


import com.nesscomputing.syslog4j.AbstractBaseTest;
import com.nesscomputing.syslog4j.SyslogMessageProcessorIF;
import com.nesscomputing.syslog4j.impl.message.processor.SyslogMessageProcessor;

public class SyslogMessageProcessorTest extends AbstractBaseTest {
    protected static final SyslogMessageProcessorIF syslogMessageProcessor = new SyslogMessageProcessor();

    public void testCreatingDefault() {
        SyslogMessageProcessor origMessageProcessor = SyslogMessageProcessor.getDefault();

        SyslogMessageProcessor newMessageProcessor = new SyslogMessageProcessor();

        SyslogMessageProcessor.setDefault(newMessageProcessor);
        assertEquals(newMessageProcessor,SyslogMessageProcessor.getDefault());

        SyslogMessageProcessor.setDefault(origMessageProcessor);
        assertEquals(origMessageProcessor,SyslogMessageProcessor.getDefault());
    }

    public void testCreatePacketData1() {
        byte[] h = "<15> Oct  5 00:00:00 ".getBytes();
        byte[] m = "[TEST] Test 123".getBytes();

        int s = 0;
        int l = m.length;

        byte[] d = syslogMessageProcessor.createPacketData(h,m,s,l);

        LOG.info(new String(d));
    }

    public void testCreatePacketData2() {
        byte[] h = "<15> Oct  5 00:00:00 ".getBytes();
        byte[] m = "For now is the time".getBytes();

        byte[] d = null;

        d = syslogMessageProcessor.createPacketData(h,m,0,8,null,"..".getBytes());
        LOG.info(new String(d));

        d = syslogMessageProcessor.createPacketData(h,m,8,6,"..".getBytes(),"..".getBytes());
        LOG.info(new String(d));
    }
}
