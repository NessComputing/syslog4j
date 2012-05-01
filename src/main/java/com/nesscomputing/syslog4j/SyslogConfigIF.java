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
package com.nesscomputing.syslog4j;


/**
* SyslogConfigIF provides a common, extensible configuration interface for all
* implementations of SyslogIF.
*
* <p>Syslog4j is licensed under the Lesser GNU License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogConfigIF.java,v 1.19 2010/11/28 04:15:18 cvs Exp $
*/
public interface SyslogConfigIF extends SyslogCharSetIF {
    Class<? extends SyslogIF> getSyslogClass();

    SyslogFacility getFacility();
    void setFacility(SyslogFacility facility);

    int getPort();
    void setPort(int port) throws SyslogRuntimeException;

    String getLocalName();
    void setLocalName(String localName) throws SyslogRuntimeException;

    String getHost();
    void setHost(String host) throws SyslogRuntimeException;

    String getIdent();
    void setIdent(String ident);

    boolean isIncludeIdentInMessageModifier();
    void setIncludeIdentInMessageModifier(boolean throwExceptionOnInitialize);

    boolean isThrowExceptionOnInitialize();
    void setThrowExceptionOnInitialize(boolean throwExceptionOnInitialize);

    boolean isThrowExceptionOnWrite();
    void setThrowExceptionOnWrite(boolean throwExceptionOnWrite);

    boolean isSendLocalTimestamp();
    void setSendLocalTimestamp(boolean sendLocalTimestamp);

    boolean isSendLocalName();
    void setSendLocalName(boolean sendLocalName);

    boolean isTruncateMessage();
    void setTruncateMessage(boolean truncateMessage);

    boolean isUseStructuredData();
    void setUseStructuredData(boolean useStructuredData);

    int getMaxMessageLength();
    void setMaxMessageLength(int maxMessageLength);

    void addMessageModifier(SyslogMessageModifierIF messageModifier);
    void insertMessageModifier(int index, SyslogMessageModifierIF messageModifier);
    void removeMessageModifier(SyslogMessageModifierIF messageModifier);
    void removeAllMessageModifiers();

    void addBackLogHandler(SyslogBackLogHandlerIF backLogHandler);
    void insertBackLogHandler(int index, SyslogBackLogHandlerIF backLogHandler);
    void removeBackLogHandler(SyslogBackLogHandlerIF backLogHandler);
    void removeAllBackLogHandlers();
}
