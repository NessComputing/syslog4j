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
package org.productivity.java.syslog4j.impl.net.tcp;

import org.productivity.java.syslog4j.impl.net.AbstractNetSyslogConfigIF;

/**
* TCPNetSyslogConfigIF is a configuration interface supporting TCP/IP-based
* Syslog implementations.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: TCPNetSyslogConfigIF.java,v 1.6 2010/10/29 03:14:12 cvs Exp $
*/
public interface TCPNetSyslogConfigIF extends AbstractNetSyslogConfigIF {
    public byte[] getDelimiterSequence();
    public void setDelimiterSequence(byte[] delimiterSequence);

    public boolean isPersistentConnection();
    public void setPersistentConnection(boolean persistentConnection);

    public boolean isSoLinger();
    public void setSoLinger(boolean soLinger);

    public int getSoLingerSeconds();
    public void setSoLingerSeconds(int soLingerSeconds);

    public boolean isKeepAlive();
    public void setKeepAlive(boolean keepAlive);

    public boolean isReuseAddress();
    public void setReuseAddress(boolean reuseAddress);

    public boolean isSetBufferSize();
    public void setSetBufferSize(boolean setBufferSize);

    public int getFreshConnectionInterval();
    public void setFreshConnectionInterval(int interval);
}
