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

import java.util.Date;

import com.nesscomputing.syslog4j.SyslogCharSetIF;
import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogLevel;

/**
* SyslogServerEventIF provides an extensible interface for Syslog4j
* server events.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerEventIF.java,v 1.4 2010/11/28 01:38:08 cvs Exp $
*/
public interface SyslogServerEventIF extends SyslogCharSetIF {
    /**
     * Note: getRaw() may use System.arraycopy(..) each time it is called; best to call it once and store the result.
     *
     * @return Returns the raw data received from the client.
     */
    public byte[] getRaw();

    public SyslogFacility getFacility();
    public void setFacility(SyslogFacility facility);

    public Date getDate();
    public void setDate(Date date);

    public SyslogLevel getLevel();
    public void setLevel(SyslogLevel level);

    public String getHost();
    public void setHost(String host);
    public boolean isHostStrippedFromMessage();

    public String getMessage();
    public void setMessage(String message);
}
