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
package com.nesscomputing.syslog4j.test.split.base;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.nesscomputing.syslog4j.SyslogLevel;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.AbstractSyslog;
import com.nesscomputing.syslog4j.impl.AbstractSyslogWriter;
import com.nesscomputing.syslog4j.util.SyslogUtility;

public class SplitSyslog extends AbstractSyslog {
    protected static final Logger LOG = Logger.getLogger("test");

    public List<String> lastMessages = Lists.newArrayList();

    protected void initialize() throws SyslogRuntimeException {
        // NO-OP
    }

    public void flush() throws SyslogRuntimeException {
        this.lastMessages.clear();
    }

    protected void write(SyslogLevel level, byte[] message) throws SyslogRuntimeException {
        String lastMessage = SyslogUtility.newString(this.getConfig(),message);

        LOG.info(lastMessage);

        this.lastMessages.add(lastMessage);
    }

    public List<String> getLastMessages() {
        return this.lastMessages;
    }

    public void shutdown() {
        flush();
    }

    public AbstractSyslogWriter getWriter() {
        return null;
    }

    public void returnWriter(AbstractSyslogWriter syslogWriter) {
        //
    }
}
