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
package org.productivity.java.syslog4j.test.unix.socket;

import static org.productivity.java.syslog4j.SyslogConstants.SOCK_STREAM;
import junit.framework.TestCase;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.unix.socket.UnixSocketSyslogConfig;
import org.productivity.java.syslog4j.util.SyslogUtility;

public class UnixSocketSyslogTest extends TestCase {
    public void testUnixSyslog() {
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.UNIX_SOCKET);

        UnixSocketSyslogConfig config = (UnixSocketSyslogConfig) syslog.getConfig();

        config.setPath("/tmp/syslog4j.sock");
        config.setType(SOCK_STREAM);

        syslog.info(this.getClass().getName() + ": unix_socket " + System.currentTimeMillis());

        syslog.flush();

        SyslogUtility.sleep(1000);
    }
}
