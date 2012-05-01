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
package com.nesscomputing.syslog4j.impl.message.processor;


/**
* SyslogMessageProcessor wraps AbstractSyslogMessageProcessor.
*
* <p>Those wishing to replace (or improve upon) this implementation
* can write a custom SyslogMessageProcessorIF and set it per
* instance via the SyslogIF.setMessageProcessor(..) method or set it globally
* via the SyslogMessageProcessor.setDefault(..) method.</p>
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogMessageProcessor.java,v 1.7 2010/02/04 03:41:37 cvs Exp $
*/
public class SyslogMessageProcessor extends AbstractSyslogMessageProcessor {
    private static final SyslogMessageProcessor INSTANCE = new SyslogMessageProcessor();

    private static SyslogMessageProcessor defaultInstance = INSTANCE;

    public static void setDefault(SyslogMessageProcessor messageProcessor) {
        if (messageProcessor != null) {
            defaultInstance = messageProcessor;
        }
    }

    public static SyslogMessageProcessor getDefault() {
        return defaultInstance;
    }
}
