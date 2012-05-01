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
package com.nesscomputing.syslog4j.server;

import java.net.SocketAddress;


/**
* SyslogServerEventHandlerIF provides an extensible interface for Syslog4j
* server event handlers.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerSessionlessEventHandlerIF.java,v 1.1 2010/11/12 02:56:44 cvs Exp $
*/
public interface SyslogServerSessionlessEventHandlerIF extends SyslogServerEventHandlerIF {
    public void event(SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event);
    public void exception(SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception);
}
