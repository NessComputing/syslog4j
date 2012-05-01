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
package com.nesscomputing.syslog4j.server.impl.net.tcp.ssl;

import com.nesscomputing.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfigIF;

/**
* SSLTCPNetSyslogServerConfigIF provides configuration for SSLTCPNetSyslogServer.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogServerConfigIF.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public interface SSLTCPNetSyslogServerConfigIF extends TCPNetSyslogServerConfigIF {
    public String getKeyStore();
    public void setKeyStore(String keyStore);

    public String getKeyStorePassword();
    public void setKeyStorePassword(String keyStorePassword);

    public String getTrustStore();
    public void setTrustStore(String trustStore);

    public String getTrustStorePassword();
    public void setTrustStorePassword(String trustStorePassword);
}
