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
package org.productivity.java.syslog4j.impl;

import static org.productivity.java.syslog4j.SyslogConstants.INCLUDE_IDENT_IN_MESSAGE_MODIFIER_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.MAX_MESSAGE_LENGTH_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.MAX_SHUTDOWN_WAIT_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.SEND_LOCAL_NAME_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.SEND_LOCAL_TIMESTAMP_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.SPLIT_MESSAGE_BEGIN_TEXT_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.SPLIT_MESSAGE_END_TEXT_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.THREADED_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.THREAD_PRIORITY_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.THROW_EXCEPTION_ON_INITIALIZE_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.THROW_EXCEPTION_ON_WRITE_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.TRUNCATE_MESSAGE_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.USE_DAEMON_THREAD_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.USE_STRUCTURED_DATA_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.WRITE_RETRIES_DEFAULT;

import java.nio.charset.Charset;
import java.util.List;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.backlog.printstream.SystemErrSyslogBackLogHandler;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
/**
* AbstractSyslog provides a base abstract implementation of the SyslogConfigIF
* configuration interface.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogConfig.java,v 1.24 2010/11/28 04:43:31 cvs Exp $
*/
public abstract class AbstractSyslogConfig implements AbstractSyslogConfigIF {
    protected final static List<SyslogBackLogHandlerIF> defaultBackLogHandlers = Lists.newArrayList();

    static {
        defaultBackLogHandlers.add(new SystemErrSyslogBackLogHandler());
    }

    protected SyslogFacility facility = SyslogFacility.DEFAULT;

    protected Charset charSet = Charsets.UTF_8;

    protected String ident = "";

    protected String localName = null;

    protected boolean sendLocalTimestamp = SEND_LOCAL_TIMESTAMP_DEFAULT;
    protected boolean sendLocalName = SEND_LOCAL_NAME_DEFAULT;

    protected boolean includeIdentInMessageModifier = INCLUDE_IDENT_IN_MESSAGE_MODIFIER_DEFAULT;
    protected boolean throwExceptionOnWrite = THROW_EXCEPTION_ON_WRITE_DEFAULT;
    protected boolean throwExceptionOnInitialize = THROW_EXCEPTION_ON_INITIALIZE_DEFAULT;

    protected int maxMessageLength = MAX_MESSAGE_LENGTH_DEFAULT;
    protected byte[] splitMessageBeginText = SPLIT_MESSAGE_BEGIN_TEXT_DEFAULT.getBytes();
    protected byte[] splitMessageEndText = SPLIT_MESSAGE_END_TEXT_DEFAULT.getBytes();

    protected List<SyslogMessageModifierIF> messageModifiers = null;
    protected List<SyslogBackLogHandlerIF> backLogHandlers = null;

    protected boolean threaded = THREADED_DEFAULT;
    protected boolean useDaemonThread = USE_DAEMON_THREAD_DEFAULT;
    protected int threadPriority = THREAD_PRIORITY_DEFAULT;
    protected long threadLoopInterval = THREAD_LOOP_INTERVAL_DEFAULT;

    protected int writeRetries = WRITE_RETRIES_DEFAULT;
    protected long maxShutdownWait = MAX_SHUTDOWN_WAIT_DEFAULT;

    protected boolean truncateMessage = TRUNCATE_MESSAGE_DEFAULT;
    protected boolean useStructuredData = USE_STRUCTURED_DATA_DEFAULT;

    public abstract Class<? extends SyslogIF> getSyslogClass();

    @Override
    public Charset getCharSet() {
        return this.charSet;
    }

    @Override
    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public boolean isThrowExceptionOnWrite() {
        return this.throwExceptionOnWrite;
    }

    public void setThrowExceptionOnWrite(boolean throwExceptionOnWrite) {
        this.throwExceptionOnWrite = throwExceptionOnWrite;
    }

    public boolean isThrowExceptionOnInitialize() {
        return this.throwExceptionOnInitialize;
    }

    public void setThrowExceptionOnInitialize(boolean throwExceptionOnInitialize) {
        this.throwExceptionOnInitialize = throwExceptionOnInitialize;
    }

    public byte[] getSplitMessageBeginText() {
        return this.splitMessageBeginText;
    }

    public void setSplitMessageBeginText(byte[] splitMessageBeginText) {
        this.splitMessageBeginText = splitMessageBeginText;
    }

    public void setSplitMessageBeginText(String splitMessageBeginText) throws SyslogRuntimeException {
        this.splitMessageBeginText = SyslogUtility.getBytes(this,splitMessageBeginText);
    }

    public byte[] getSplitMessageEndText() {
        return this.splitMessageEndText;
    }

    public void setSplitMessageEndText(byte[] splitMessageEndText) {
        this.splitMessageEndText = splitMessageEndText;
    }

    public void setSplitMessageEndText(String splitMessageEndText) throws SyslogRuntimeException {
        this.splitMessageEndText = SyslogUtility.getBytes(this,splitMessageEndText);
    }

    public int getMaxMessageLength() {
        return this.maxMessageLength;
    }

    public void setMaxMessageLength(int maxMessageLength) {
        this.maxMessageLength = maxMessageLength;
    }

    public boolean isSendLocalTimestamp() {
        return this.sendLocalTimestamp;
    }

    public void setSendLocalTimestamp(boolean sendLocalTimestamp) {
        this.sendLocalTimestamp = sendLocalTimestamp;
    }

    public boolean isSendLocalName() {
        return this.sendLocalName;
    }

    public void setSendLocalName(boolean sendLocalName) {
        this.sendLocalName = sendLocalName;
    }

    @Override
    public SyslogFacility getFacility() {
        return this.facility;
    }

    @Override
    public void setFacility(SyslogFacility facility) {
        this.facility = facility;
    }

    public String getIdent() {
        return this.ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    protected synchronized List<SyslogMessageModifierIF> _getMessageModifiers() {
        if (this.messageModifiers == null) {
            this.messageModifiers = Lists.newArrayList();
        }

        return this.messageModifiers;
    }

    public void addMessageModifier(SyslogMessageModifierIF messageModifier) {
        if (messageModifier == null) {
            return;
        }

        List<SyslogMessageModifierIF> _messageModifiers = _getMessageModifiers();

        synchronized(_messageModifiers) {
            _messageModifiers.add(messageModifier);
        }
    }

    public void insertMessageModifier(int index, SyslogMessageModifierIF messageModifier) {
        if (messageModifier == null) {
            return;
        }

        List<SyslogMessageModifierIF> _messageModifiers = _getMessageModifiers();

        synchronized(_messageModifiers) {
            try {
                _messageModifiers.add(index, messageModifier);

            } catch (IndexOutOfBoundsException ioobe) {
                throw new SyslogRuntimeException(ioobe);
            }
        }
    }

    public void removeMessageModifier(SyslogMessageModifierIF messageModifier) {
        if (messageModifier == null) {
            return;
        }

        List<SyslogMessageModifierIF> _messageModifiers = _getMessageModifiers();

        synchronized(_messageModifiers) {
            _messageModifiers.remove(messageModifier);
        }
    }

    public List<SyslogMessageModifierIF> getMessageModifiers() {
        return this.messageModifiers;
    }

    public void setMessageModifiers(List<SyslogMessageModifierIF> messageModifiers) {
        this.messageModifiers = messageModifiers;
    }

    public void removeAllMessageModifiers() {
        if (this.messageModifiers == null || this.messageModifiers.isEmpty()) {
            return;
        }

        this.messageModifiers.clear();
    }

    protected synchronized List<SyslogBackLogHandlerIF> _getBackLogHandlers() {
        if (this.backLogHandlers == null) {
            this.backLogHandlers = Lists.newArrayList();
        }

        return this.backLogHandlers;
    }

    public void addBackLogHandler(SyslogBackLogHandlerIF backLogHandler) {
        if (backLogHandler == null) {
            return;
        }

        List<SyslogBackLogHandlerIF> _backLogHandlers = _getBackLogHandlers();

        synchronized(_backLogHandlers) {
            backLogHandler.initialize();
            _backLogHandlers.add(backLogHandler);
        }
    }

    public void insertBackLogHandler(int index, SyslogBackLogHandlerIF backLogHandler) {
        if (backLogHandler == null) {
            return;
        }

        List<SyslogBackLogHandlerIF> _backLogHandlers = _getBackLogHandlers();

        synchronized(_backLogHandlers) {
            try {
                backLogHandler.initialize();
                _backLogHandlers.add(index,backLogHandler);

            } catch (IndexOutOfBoundsException ioobe) {
                throw new SyslogRuntimeException(ioobe);
            }
        }
    }

    public void removeBackLogHandler(SyslogBackLogHandlerIF backLogHandler) {
        if (backLogHandler == null) {
            return;
        }

        List<SyslogBackLogHandlerIF> _backLogHandlers = _getBackLogHandlers();

        synchronized(_backLogHandlers) {
            _backLogHandlers.remove(backLogHandler);
        }
    }

    public List<SyslogBackLogHandlerIF> getBackLogHandlers() {
        if (this.backLogHandlers == null || this.backLogHandlers.size() < 1) {
            return defaultBackLogHandlers;
        }

        return this.backLogHandlers;
    }

    public void setBackLogHandlers(List<SyslogBackLogHandlerIF> backLogHandlers) {
        this.backLogHandlers = backLogHandlers;
    }

    public void removeAllBackLogHandlers() {
        if (this.backLogHandlers == null || this.backLogHandlers.isEmpty()) {
            return;
        }

        this.backLogHandlers.clear();
    }

    public boolean isIncludeIdentInMessageModifier() {
        return this.includeIdentInMessageModifier;
    }

    public void setIncludeIdentInMessageModifier(boolean includeIdentInMessageModifier) {
        this.includeIdentInMessageModifier = includeIdentInMessageModifier;
    }

    public boolean isThreaded() {
        return this.threaded;
    }

    public void setThreaded(boolean threaded) {
        this.threaded = threaded;
    }

    public boolean isUseDaemonThread() {
        return useDaemonThread;
    }

    public void setUseDaemonThread(boolean useDaemonThread) {
        this.useDaemonThread = useDaemonThread;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    public long getThreadLoopInterval() {
        return this.threadLoopInterval;
    }

    public void setThreadLoopInterval(long threadLoopInterval) {
        this.threadLoopInterval = threadLoopInterval;
    }

    public long getMaxShutdownWait() {
        return this.maxShutdownWait;
    }

    public void setMaxShutdownWait(long maxShutdownWait) {
        this.maxShutdownWait = maxShutdownWait;
    }

    public int getWriteRetries() {
        return this.writeRetries;
    }

    public void setWriteRetries(int writeRetries) {
        this.writeRetries = writeRetries;
    }

    public boolean isTruncateMessage() {
        return this.truncateMessage;
    }

    public void setTruncateMessage(boolean truncateMessage) {
        this.truncateMessage = truncateMessage;
    }

    public boolean isUseStructuredData() {
        return this.useStructuredData;
    }

    public void setUseStructuredData(boolean useStructuredData) {
        this.useStructuredData = useStructuredData;
    }

    public Class<? extends AbstractSyslogWriter> getSyslogWriterClass() {
        return null;
    }
}
