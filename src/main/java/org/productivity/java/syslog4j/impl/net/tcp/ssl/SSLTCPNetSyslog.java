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
package org.productivity.java.syslog4j.impl.net.tcp.ssl;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslog;

/**
* SSLTCPNetSyslog is an extension of AbstractSyslog that provides support for
* TCP/IP-based (over SSL/TLS) syslog clients.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslog.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public class SSLTCPNetSyslog extends TCPNetSyslog {
    public void initialize() throws SyslogRuntimeException {
        super.initialize();

        SSLTCPNetSyslogConfigIF sslTcpNetSyslogConfig = (SSLTCPNetSyslogConfigIF) this.tcpNetSyslogConfig;

        String keyStore = sslTcpNetSyslogConfig.getKeyStore();

        if (!StringUtils.isBlank(keyStore)) {
            System.setProperty("javax.net.ssl.keyStore",keyStore);
        }

        String keyStorePassword = sslTcpNetSyslogConfig.getKeyStorePassword();

        if (!StringUtils.isBlank(keyStorePassword)) {
            System.setProperty("javax.net.ssl.keyStorePassword",keyStorePassword);
        }

        String trustStore = sslTcpNetSyslogConfig.getTrustStore();

        if (!StringUtils.isBlank(trustStore)) {
            System.setProperty("javax.net.ssl.trustStore",trustStore);
        }

        String trustStorePassword = sslTcpNetSyslogConfig.getTrustStorePassword();

        if (!StringUtils.isBlank(trustStorePassword)) {
            System.setProperty("javax.net.ssl.trustStorePassword",trustStorePassword);
        }
    }
}
