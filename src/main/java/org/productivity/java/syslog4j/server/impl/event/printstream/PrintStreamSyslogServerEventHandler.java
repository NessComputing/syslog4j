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
package org.productivity.java.syslog4j.server.impl.event.printstream;

import java.io.PrintStream;
import java.net.SocketAddress;
import java.util.Date;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;

/**
* SystemOutSyslogServerEventHandler provides a simple example implementation
* of the SyslogServerEventHandlerIF which writes the events to System.out.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PrintStreamSyslogServerEventHandler.java,v 1.7 2010/11/28 22:07:57 cvs Exp $
*/
public class PrintStreamSyslogServerEventHandler implements SyslogServerSessionEventHandlerIF {
    protected PrintStream stream = null;

    public PrintStreamSyslogServerEventHandler(PrintStream stream) {
        this.stream = stream;
    }

    public void initialize(SyslogServerIF syslogServer) {
        return;
    }

    public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress) {
        return null;
    }

    public void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event) {
        String date = (event.getDate() == null ? new Date() : event.getDate()).toString();
        String facility = event.getFacility().name();
        String level = event.getLevel().name();

        this.stream.println("{" + facility + "} " + date + " " + level + " " + event.getMessage());
    }

    public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
        //
    }

    public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout) {
        //
    }

    public void destroy(SyslogServerIF syslogServer) {
        return;
    }
}
