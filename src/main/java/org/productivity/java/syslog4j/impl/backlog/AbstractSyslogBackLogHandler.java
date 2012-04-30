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
package org.productivity.java.syslog4j.impl.backlog;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;

/**
* AbstractSyslogBackLogHandler is an implementation of SyslogBackLogHandlerIF
* that mainly provides the helpful "combine" method for handling the "reason"
* why a BackLog has occurred.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogBackLogHandler.java,v 1.1 2009/01/24 22:00:18 cvs Exp $
*/
public abstract class AbstractSyslogBackLogHandler implements SyslogBackLogHandlerIF {
    protected boolean appendReason = true;

    protected String combine(SyslogIF syslog, SyslogLevel level, String message, String reason) {
        // Note: syslog is explicitly ignored by default

        String _message = message != null ? message : "UNKNOWN";
        String _reason = reason != null ? reason : "UNKNOWN";
        String _level = level != null ? level.name() : "";

        String combinedMessage = _level + " " + _message;

        if (this.appendReason) {
            combinedMessage += " [" + _reason + "]";
        }

        return combinedMessage;
    }
}
