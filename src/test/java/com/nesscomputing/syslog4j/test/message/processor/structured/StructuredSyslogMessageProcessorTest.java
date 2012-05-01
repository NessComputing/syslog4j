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
package com.nesscomputing.syslog4j.test.message.processor.structured;

import junit.framework.TestCase;

import com.nesscomputing.syslog4j.impl.message.processor.structured.StructuredSyslogMessageProcessor;

public class StructuredSyslogMessageProcessorTest extends TestCase {
    public void testCreatingDefaultAndParameters() {
        StructuredSyslogMessageProcessor origMessageProcessor = StructuredSyslogMessageProcessor.getDefault();

        StructuredSyslogMessageProcessor newMessageProcessor = new StructuredSyslogMessageProcessor();

        newMessageProcessor.setApplicationName("app1");
        assertEquals("app1",newMessageProcessor.getApplicationName());

        newMessageProcessor.setProcessId("proc1");
        assertEquals("proc1",newMessageProcessor.getProcessId());

        StructuredSyslogMessageProcessor.setDefault(newMessageProcessor);
        assertEquals(newMessageProcessor,StructuredSyslogMessageProcessor.getDefault());

        StructuredSyslogMessageProcessor.setDefault(origMessageProcessor);
        assertEquals(origMessageProcessor,StructuredSyslogMessageProcessor.getDefault());
    }
}
