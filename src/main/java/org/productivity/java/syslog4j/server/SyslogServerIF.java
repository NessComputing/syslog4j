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
package org.productivity.java.syslog4j.server;

import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* SyslogServerIF provides a common interface for all Syslog4j server implementations.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerIF.java,v 1.5 2008/11/07 15:15:41 cvs Exp $
*/
public interface SyslogServerIF extends Runnable {
    public void initialize(String protocol, SyslogServerConfigIF config) throws SyslogRuntimeException;

    public String getProtocol();
    public SyslogServerConfigIF getConfig();

    public void run();

    public Thread getThread();
    public void setThread(Thread thread);

    public void shutdown();
}
