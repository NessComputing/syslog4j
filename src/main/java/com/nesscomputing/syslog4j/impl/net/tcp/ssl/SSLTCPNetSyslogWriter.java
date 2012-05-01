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
package com.nesscomputing.syslog4j.impl.net.tcp.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogWriter;

/**
* SSLTCPNetSyslogWriter is an implementation of Runnable that supports sending
* TCP/IP-based (over SSL/TLS) messages within a separate Thread.
*
* <p>When used in "threaded" mode (see TCPNetSyslogConfig for the option),
* a queuing mechanism is used (via LinkedList).</p>
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogWriter.java,v 1.4 2009/03/29 17:38:58 cvs Exp $
*/
public class SSLTCPNetSyslogWriter extends TCPNetSyslogWriter {
    protected SocketFactory obtainSocketFactory() {
        return SSLSocketFactory.getDefault();
    }
}
