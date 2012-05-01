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
package com.nesscomputing.syslog4j.test.unix;

import junit.framework.TestCase;

import com.nesscomputing.syslog4j.Syslog;
import com.nesscomputing.syslog4j.SyslogConstants;
import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.impl.AbstractSyslog;
import com.nesscomputing.syslog4j.impl.unix.UnixSyslogConfig;
public class UnixSyslogTest extends TestCase {
    public void testUnixSyslogConfig() {
        UnixSyslogConfig config = new UnixSyslogConfig();

        assertNull(config.getHost());

        assertEquals(0,config.getPort());

        try {
            config.setHost("abcdef");
            fail();

        } catch (Exception e) {
            //
        }

        try {
            config.setPort(999);
            fail();

        } catch (Exception e) {
            //
        }

        try {
            config.getMaxQueueSize();
            fail();

        } catch (Exception e) {
            //
        }

        try {
            config.setMaxQueueSize(888);
            fail();

        } catch (Exception e) {
            //
        }

        assertEquals(SyslogConstants.SYSLOG_LIBRARY_DEFAULT,config.getLibrary());
        config.setLibrary("d");
        assertEquals("d",config.getLibrary());

        assertEquals(SyslogConstants.OPTION_NONE,config.getOption());
        config.setOption(1);
        assertEquals(1,config.getOption());
    }

    public void testUnixSyslog() {
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.UNIX_SYSLOG);

        syslog.getConfig().setFacility(SyslogFacility.kern);

        syslog.error(this.getClass().getName() + ": unix_syslog " + System.currentTimeMillis());

        syslog.flush();

        syslog.shutdown();

        AbstractSyslog abstractSyslog = (AbstractSyslog) syslog;

        assertNull(abstractSyslog.getWriter());
        abstractSyslog.returnWriter(null);
    }
}
