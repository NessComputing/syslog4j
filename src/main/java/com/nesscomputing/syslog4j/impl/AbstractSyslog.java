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
package com.nesscomputing.syslog4j.impl;

import static com.nesscomputing.syslog4j.SyslogConstants.IDENT_SUFFIX_DEFAULT;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.nesscomputing.syslog4j.SyslogBackLogHandlerIF;
import com.nesscomputing.syslog4j.SyslogConfigIF;
import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.SyslogLevel;
import com.nesscomputing.syslog4j.SyslogMessageIF;
import com.nesscomputing.syslog4j.SyslogMessageModifierIF;
import com.nesscomputing.syslog4j.SyslogMessageProcessorIF;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.message.processor.SyslogMessageProcessor;
import com.nesscomputing.syslog4j.impl.message.processor.structured.StructuredSyslogMessageProcessor;
import com.nesscomputing.syslog4j.impl.message.structured.StructuredSyslogMessage;
import com.nesscomputing.syslog4j.impl.message.structured.StructuredSyslogMessageIF;
import com.nesscomputing.syslog4j.util.SyslogUtility;
/**
* AbstractSyslog provides a base abstract implementation of the SyslogIF.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslog.java,v 1.29 2011/01/11 04:58:52 cvs Exp $
*/
public abstract class AbstractSyslog implements SyslogIF {
    private static final Logger LOG = Logger.getLogger(AbstractSyslog.class);

    protected String syslogProtocol = null;

    protected AbstractSyslogConfigIF syslogConfig = null;

    protected SyslogMessageProcessorIF syslogMessageProcessor = null;
    protected SyslogMessageProcessorIF structuredSyslogMessageProcessor = null;

    protected Object backLogStatusSyncObject = new Object();

    protected boolean backLogStatus = false;
    protected List<SyslogBackLogHandlerIF> notifiedBackLogHandlers = Lists.newArrayList();

    protected boolean getBackLogStatus() {
        synchronized(this.backLogStatusSyncObject) {
            return this.backLogStatus;
        }
    }

    /**
     * @param backLogStatus - true if in a "down" backLog state, false if in an "up" (operational) non-backLog state
     */
    public void setBackLogStatus(boolean backLogStatus) {
        if (this.backLogStatus != backLogStatus) {
            synchronized(this.backLogStatusSyncObject) {
                if (!backLogStatus) {
                    for(int i=0; i<this.notifiedBackLogHandlers.size(); i++) {
                        SyslogBackLogHandlerIF backLogHandler = this.notifiedBackLogHandlers.get(i);

                        backLogHandler.up(this);
                    }

                    this.notifiedBackLogHandlers.clear();
                }

                this.backLogStatus = backLogStatus;
            }
        }
    }

    public void initialize(String protocol, SyslogConfigIF config) throws SyslogRuntimeException {
        this.syslogProtocol = protocol;

        try {
            this.syslogConfig = (AbstractSyslogConfigIF) config;

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("provided config must implement AbstractSyslogConfigIF");
        }

        initialize();
    }

    public SyslogMessageProcessorIF getMessageProcessor() {
        if (this.syslogMessageProcessor == null) {
            this.syslogMessageProcessor = SyslogMessageProcessor.getDefault();
        }

        return this.syslogMessageProcessor;
    }

    public SyslogMessageProcessorIF getStructuredMessageProcessor() {
        if (this.structuredSyslogMessageProcessor == null) {
            this.structuredSyslogMessageProcessor = StructuredSyslogMessageProcessor.getDefault();
        }

        return this.structuredSyslogMessageProcessor;
    }

    public void setMessageProcessor(SyslogMessageProcessorIF messageProcessor) {
        this.syslogMessageProcessor = messageProcessor;
    }

    public void setStructuredMessageProcessor(SyslogMessageProcessorIF messageProcessor) {
        this.structuredSyslogMessageProcessor = messageProcessor;
    }

    public String getProtocol() {
        return this.syslogProtocol;
    }

    public SyslogConfigIF getConfig() {
        return this.syslogConfig;
    }

    public void log(SyslogLevel level, String message) {
        if (this.syslogConfig.isUseStructuredData()) {
            StructuredSyslogMessageIF structuredMessage = new StructuredSyslogMessage(null,null,message);

            log(getStructuredMessageProcessor(),level,structuredMessage.createMessage());

        } else {
            log(getMessageProcessor(),level,message);
        }
    }

    public void log(SyslogLevel level, SyslogMessageIF message) {
        if (message instanceof StructuredSyslogMessageIF) {
            if (getMessageProcessor() instanceof StructuredSyslogMessageProcessor) {
                log(getMessageProcessor(),level,message.createMessage());

            } else {
                log(getStructuredMessageProcessor(),level,message.createMessage());
            }

        } else {
            log(getMessageProcessor(),level,message.createMessage());
        }
    }

    public void debug(String message) {
        log(SyslogLevel.DEBUG, message);
    }

    public void notice(String message) {
        log(SyslogLevel.NOTICE,message);
    }

    public void info(String message) {
        log(SyslogLevel.INFO,message);
    }

    public void warn(String message) {
        log(SyslogLevel.WARN,message);
    }

    public void error(String message) {
        log(SyslogLevel.ERROR,message);
    }

    public void critical(String message) {
        log(SyslogLevel.CRITICAL,message);
    }

    public void alert(String message) {
        log(SyslogLevel.ALERT,message);
    }

    public void emergency(String message) {
        log(SyslogLevel.EMERGENCY,message);
    }

    public void debug(SyslogMessageIF message) {
        log(SyslogLevel.DEBUG,message);
    }

    public void notice(SyslogMessageIF message) {
        log(SyslogLevel.NOTICE,message);
    }

    public void info(SyslogMessageIF message) {
        log(SyslogLevel.INFO,message);
    }

    public void warn(SyslogMessageIF message) {
        log(SyslogLevel.WARN,message);
    }

    public void error(SyslogMessageIF message) {
        log(SyslogLevel.ERROR,message);
    }

    public void critical(SyslogMessageIF message) {
        log(SyslogLevel.CRITICAL,message);
    }

    public void alert(SyslogMessageIF message) {
        log(SyslogLevel.ALERT,message);
    }

    public void emergency(SyslogMessageIF message) {
        log(SyslogLevel.EMERGENCY,message);
    }

    protected String prefixMessage(String message, String suffix) {
        String ident = this.syslogConfig.getIdent();

        String _message = (StringUtils.isBlank(ident) ? "" : (ident + suffix)) + message;

        return _message;
    }

    public void log(SyslogMessageProcessorIF messageProcessor, SyslogLevel level, String message) {
        String _message = null;

        if (this.syslogConfig.isIncludeIdentInMessageModifier()) {
            _message = prefixMessage(message,IDENT_SUFFIX_DEFAULT);
            _message = modifyMessage(level,_message);

        } else {
            _message = modifyMessage(level,message);
            _message = prefixMessage(_message,IDENT_SUFFIX_DEFAULT);
        }

        try {
            write(messageProcessor, level,_message);

        } catch (SyslogRuntimeException sre) {
            if (sre.getCause() != null) {
                backLog(level,_message,sre.getCause());

            } else {
                backLog(level,_message,sre);
            }

            if (this.syslogConfig.isThrowExceptionOnWrite()) {
                throw sre;
            }
        }
    }

    protected void write(SyslogMessageProcessorIF messageProcessor, SyslogLevel level, String message) throws SyslogRuntimeException {
        String header = messageProcessor.createSyslogHeader(this.syslogConfig.getFacility(),level,this.syslogConfig.getLocalName(),this.syslogConfig.isSendLocalTimestamp(),this.syslogConfig.isSendLocalName());

        byte[] h = SyslogUtility.getBytes(this.syslogConfig,header);
        byte[] m = SyslogUtility.getBytes(this.syslogConfig,message);

        int mLength = m.length;

        int availableLen = this.syslogConfig.getMaxMessageLength() - h.length;

        if (this.syslogConfig.isTruncateMessage() && (availableLen > 0 && mLength > availableLen)) {
            mLength = availableLen;
        }

        if (mLength <= availableLen) {
            byte[] data = messageProcessor.createPacketData(h,m,0,mLength);

            write(level,data);

        } else {
            byte[] splitBeginText = this.syslogConfig.getSplitMessageBeginText();
            byte[] splitEndText = this.syslogConfig.getSplitMessageEndText();

            int pos = 0;
            int left = mLength;

            while(left > 0) {
                boolean firstTime = (pos == 0);

                boolean doSplitBeginText = splitBeginText != null && !firstTime;
                boolean doSplitEndText = splitBeginText != null && (firstTime || (left > (availableLen - splitBeginText.length)));

                int actualAvailableLen = availableLen;

                actualAvailableLen -= (splitBeginText != null && doSplitBeginText) ? splitBeginText.length : 0;
                actualAvailableLen -= (splitEndText != null && doSplitEndText) ? splitEndText.length : 0;

                if (actualAvailableLen > left) {
                    actualAvailableLen = left;
                }

                if (actualAvailableLen < 0) {
                    throw new SyslogRuntimeException("Message length < 0; recommendation: increase the size of maxMessageLength");
                }

                byte[] data = messageProcessor.createPacketData(h,m,pos,actualAvailableLen,doSplitBeginText ? splitBeginText : null,doSplitEndText ? splitEndText : null);

                write(level,data);

                pos += actualAvailableLen;
                left -= actualAvailableLen;
            }
        }
    }

    protected abstract void initialize() throws SyslogRuntimeException;

    protected abstract void write(SyslogLevel level, byte[] message) throws SyslogRuntimeException;

    protected String modifyMessage(SyslogLevel level, String message) {
        List<? extends SyslogMessageModifierIF> _messageModifiers = this.syslogConfig.getMessageModifiers();

        if (_messageModifiers == null || _messageModifiers.size() < 1) {
            return message;
        }

        String _message = message;

        SyslogFacility facility = this.syslogConfig.getFacility();

        for(int i=0; i<_messageModifiers.size(); i++) {
            SyslogMessageModifierIF messageModifier = _messageModifiers.get(i);

            _message = messageModifier.modify(this, facility, level, _message);
        }

        return _message;
    }

    public void backLog(SyslogLevel level, String message, Throwable reasonThrowable) {
        backLog(level,message,reasonThrowable != null ? reasonThrowable.toString() : "UNKNOWN");
    }

    public void backLog(SyslogLevel level, String message, String reason) {
        boolean status = getBackLogStatus();

        if (!status) {
            setBackLogStatus(true);
        }

        List<? extends SyslogBackLogHandlerIF> backLogHandlers = this.syslogConfig.getBackLogHandlers();

        for(int i=0; i<backLogHandlers.size(); i++) {
            SyslogBackLogHandlerIF backLogHandler = backLogHandlers.get(i);

            try {
                if (!status) {
                    backLogHandler.down(this, reason);
                    this.notifiedBackLogHandlers.add(backLogHandler);
                }

                backLogHandler.log(this,level,message,reason);
                break;

            } catch (Exception e) {
                LOG.trace("Ignoring Exception, next logger", e);
            }
        }
    }

    public abstract AbstractSyslogWriter getWriter();

    public abstract void returnWriter(AbstractSyslogWriter syslogWriter);

    public Thread createWriterThread(AbstractSyslogWriter syslogWriter) {
        Thread newWriterThread = new Thread(syslogWriter);
        newWriterThread.setName("SyslogWriter: " + getProtocol());
        newWriterThread.setDaemon(syslogConfig.isUseDaemonThread());
        if (syslogConfig.getThreadPriority() > -1) {
            newWriterThread.setPriority(syslogConfig.getThreadPriority());
        }
        syslogWriter.setThread(newWriterThread);
        newWriterThread.start();

        return newWriterThread;
    }


    public AbstractSyslogWriter createWriter(){
        Class<? extends AbstractSyslogWriter> clazz = this.syslogConfig.getSyslogWriterClass();

        AbstractSyslogWriter newWriter = null;

        try {
            newWriter = clazz.newInstance();
            newWriter.initialize(this);

        } catch (InstantiationException ie) {
            if (this.syslogConfig.isThrowExceptionOnInitialize()) {
                throw new SyslogRuntimeException(ie);
            }

        } catch (IllegalAccessException iae) {
            if (this.syslogConfig.isThrowExceptionOnInitialize()) {
                throw new SyslogRuntimeException(iae);
            }
        }

        return newWriter;
    }
}
