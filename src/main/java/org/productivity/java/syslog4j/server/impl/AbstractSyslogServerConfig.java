package org.productivity.java.syslog4j.server.impl;

import static org.productivity.java.syslog4j.SyslogConstants.THREAD_PRIORITY_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.USE_DAEMON_THREAD_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.USE_STRUCTURED_DATA_DEFAULT;

import java.nio.charset.Charset;
import java.util.List;

import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.server.SyslogServerConfigIF;
import org.productivity.java.syslog4j.server.SyslogServerEventHandlerIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
/**
* AbstractSyslogServerConfig provides a base abstract implementation of the SyslogServerConfigIF
* configuration interface.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogServerConfig.java,v 1.9 2011/01/11 05:11:13 cvs Exp $
*/
public abstract class AbstractSyslogServerConfig implements SyslogServerConfigIF {
    private static final long serialVersionUID = 870248648801259856L;

    public abstract Class<? extends SyslogServerIF> getSyslogServerClass();

    protected Charset charSet = Charsets.UTF_8;

    protected long shutdownWait = SyslogConstants.SERVER_SHUTDOWN_WAIT_DEFAULT;

    protected List<SyslogServerEventHandlerIF> eventHandlers = Lists.newArrayList();

    protected boolean useStructuredData = USE_STRUCTURED_DATA_DEFAULT;

    protected Object dateTimeFormatter = null;

    protected boolean useDaemonThread = USE_DAEMON_THREAD_DEFAULT;
    protected int threadPriority = THREAD_PRIORITY_DEFAULT;

    @Override
    public Charset getCharSet() {
        return this.charSet;
    }

    @Override
    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }

    public long getShutdownWait() {
        return this.shutdownWait;
    }

    public void setShutdownWait(long shutdownWait) {
        this.shutdownWait = shutdownWait;
    }

    public List<? extends SyslogServerEventHandlerIF> getEventHandlers() {
        return this.eventHandlers;
    }

    public void addEventHandler(SyslogServerEventHandlerIF eventHandler) {
        this.eventHandlers.add(eventHandler);
    }

    public void insertEventHandler(int pos, SyslogServerEventHandlerIF eventHandler) {
        this.eventHandlers.add(pos, eventHandler);
    }

    public void removeEventHandler(SyslogServerEventHandlerIF eventHandler) {
        this.eventHandlers.remove(eventHandler);
    }

    public void removeAllEventHandlers() {
        this.eventHandlers.clear();
    }

    public boolean isUseStructuredData() {
        return useStructuredData;
    }

    public void setUseStructuredData(boolean useStructuredData) {
        this.useStructuredData = useStructuredData;
    }

    public boolean isUseDaemonThread() {
        return useDaemonThread;
    }

    public Object getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(Object dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
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
}
