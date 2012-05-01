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
package com.nesscomputing.syslog4j.test.util;

import java.nio.charset.Charset;

import junit.framework.TestCase;


import com.google.common.base.Charsets;
import com.nesscomputing.syslog4j.SyslogCharSetIF;
import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.util.SyslogUtility;

public class SyslogUtilityTest extends TestCase {
    public static class CharSet implements SyslogCharSetIF {
        protected Charset charSet = Charsets.UTF_8;

        @Override
        public Charset getCharSet() {
            return this.charSet;
        }

        @Override
        public void setCharSet(Charset charSet) {
            this.charSet = charSet;
        }

    }

    public void testSyslogUtility() {
        assertTrue(SyslogUtility.isClassExists("java.lang.String"));
        assertFalse(SyslogUtility.isClassExists("java.lang.NonexistentClass"));

        assertEquals(SyslogFacility.auth,SyslogFacility.forName("auth"));
        assertEquals(SyslogFacility.authpriv,SyslogFacility.forName("authpriv"));
        assertEquals(SyslogFacility.cron,SyslogFacility.forName("cron"));
        assertEquals(SyslogFacility.daemon,SyslogFacility.forName("daemon"));
        assertEquals(SyslogFacility.ftp,SyslogFacility.forName("ftp"));
        assertEquals(SyslogFacility.kern,SyslogFacility.forName("kern"));
        assertEquals(SyslogFacility.local0,SyslogFacility.forName("local0"));
        assertEquals(SyslogFacility.local1,SyslogFacility.forName("local1"));
        assertEquals(SyslogFacility.local2,SyslogFacility.forName("local2"));
        assertEquals(SyslogFacility.local3,SyslogFacility.forName("local3"));
        assertEquals(SyslogFacility.local4,SyslogFacility.forName("local4"));
        assertEquals(SyslogFacility.local5,SyslogFacility.forName("local5"));
        assertEquals(SyslogFacility.local6,SyslogFacility.forName("local6"));
        assertEquals(SyslogFacility.local7,SyslogFacility.forName("local7"));
        assertEquals(SyslogFacility.lpr,SyslogFacility.forName("lpr"));
        assertEquals(SyslogFacility.mail,SyslogFacility.forName("mail"));
        assertEquals(SyslogFacility.news,SyslogFacility.forName("news"));
        assertEquals(SyslogFacility.syslog,SyslogFacility.forName("syslog"));
        assertEquals(SyslogFacility.user,SyslogFacility.forName("user"));
        assertEquals(SyslogFacility.uucp,SyslogFacility.forName("uucp"));
        assertEquals(null,SyslogFacility.forName(null));
        assertEquals(null,SyslogFacility.forName(""));

        assertEquals("auth",SyslogFacility.auth.name());
        assertEquals("authpriv",SyslogFacility.authpriv.name());
        assertEquals("cron",SyslogFacility.cron.name());
        assertEquals("daemon",SyslogFacility.daemon.name());
        assertEquals("ftp",SyslogFacility.ftp.name());
        assertEquals("kern",SyslogFacility.kern.name());
        assertEquals("local0",SyslogFacility.local0.name());
        assertEquals("local1",SyslogFacility.local1.name());
        assertEquals("local2",SyslogFacility.local2.name());
        assertEquals("local3",SyslogFacility.local3.name());
        assertEquals("local4",SyslogFacility.local4.name());
        assertEquals("local5",SyslogFacility.local5.name());
        assertEquals("local6",SyslogFacility.local6.name());
        assertEquals("local7",SyslogFacility.local7.name());
        assertEquals("lpr",SyslogFacility.lpr.name());
        assertEquals("mail",SyslogFacility.mail.name());
        assertEquals("news",SyslogFacility.news.name());
        assertEquals("syslog",SyslogFacility.syslog.name());
        assertEquals("user",SyslogFacility.user.name());
        assertEquals("uucp",SyslogFacility.uucp.name());

        String message = "foo";

        CharSet cs = new CharSet();
        cs.setCharSet(Charset.forName("us-ascii"));

        assertEquals(message,SyslogUtility.newString(cs,message.getBytes()));
        assertEquals(message,new String(SyslogUtility.getBytes(cs,message)));

        try {
            SyslogUtility.getInetAddress("fake-host-name");
//			fail("Should not return an address on a fake host name: " + address.toString());

        } catch (SyslogRuntimeException sre) {
            assertTrue(true);
        }

        try {
            SyslogUtility.getLocalName();

        } catch (SyslogRuntimeException sre) {
            assertTrue(true);
        }
    }
}
