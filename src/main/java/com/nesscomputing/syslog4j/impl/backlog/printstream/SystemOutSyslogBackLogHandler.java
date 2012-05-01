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
package com.nesscomputing.syslog4j.impl.backlog.printstream;

import com.nesscomputing.syslog4j.SyslogBackLogHandlerIF;

/**
* SystemOutSyslogBackLogHandler provides a last-chance mechanism to log messages that fail
* (for whatever reason) within the rest of Syslog to System.out.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SystemOutSyslogBackLogHandler.java,v 1.2 2009/03/29 17:38:59 cvs Exp $
*/
public class SystemOutSyslogBackLogHandler extends PrintStreamSyslogBackLogHandler {
    public static final SyslogBackLogHandlerIF create() {
        return new SystemOutSyslogBackLogHandler();
    }

    public SystemOutSyslogBackLogHandler() {
        super(System.out,true);
    }
    public SystemOutSyslogBackLogHandler(boolean appendReason) {
        super(System.out,true,appendReason);
    }
}
