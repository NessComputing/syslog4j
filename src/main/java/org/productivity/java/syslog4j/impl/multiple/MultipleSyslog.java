package org.productivity.java.syslog4j.impl.multiple;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogMessageIF;
import org.productivity.java.syslog4j.SyslogMessageProcessorIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* MultipleSyslog is an aggregator Syslog implementation for allowing a single
* Syslog call to send to multiple Syslog implementations.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: MultipleSyslog.java,v 1.10 2010/02/11 05:00:55 cvs Exp $
*/
public class MultipleSyslog implements SyslogIF {
    protected String syslogProtocol = null;
    protected MultipleSyslogConfig multipleSyslogConfig = null;

    public void initialize(String protocol, SyslogConfigIF config) throws SyslogRuntimeException {
        this.syslogProtocol = protocol;

        try {
            this.multipleSyslogConfig = (MultipleSyslogConfig) config;

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("config must be of type MultipleSyslogConfig");
        }
    }

    public SyslogConfigIF getConfig() {
        return this.multipleSyslogConfig;
    }

    public void debug(String message) {
        log(SyslogLevel.DEBUG,message);
    }

    public void debug(SyslogMessageIF message) {
        log(SyslogLevel.DEBUG,message);
    }

    public void critical(String message) {
        log(SyslogLevel.CRITICAL,message);
    }

    public void critical(SyslogMessageIF message) {
        log(SyslogLevel.CRITICAL,message);
    }

    public void error(String message) {
        log(SyslogLevel.ERROR,message);
    }

    public void error(SyslogMessageIF message) {
        log(SyslogLevel.ERROR,message);
    }

    public void alert(String message) {
        log(SyslogLevel.ALERT,message);
    }

    public void alert(SyslogMessageIF message) {
        log(SyslogLevel.ALERT,message);
    }

    public void notice(String message) {
        log(SyslogLevel.NOTICE,message);
    }

    public void notice(SyslogMessageIF message) {
        log(SyslogLevel.NOTICE,message);
    }

    public void emergency(String message) {
        log(SyslogLevel.EMERGENCY,message);
    }

    public void emergency(SyslogMessageIF message) {
        log(SyslogLevel.EMERGENCY,message);
    }

    public void info(String message) {
        log(SyslogLevel.INFO,message);
    }

    public void info(SyslogMessageIF message) {
        log(SyslogLevel.INFO,message);
    }

    public void warn(String message) {
        log(SyslogLevel.WARN,message);
    }

    public void warn(SyslogMessageIF message) {
        log(SyslogLevel.WARN,message);
    }

    @Override
    public void log(SyslogLevel level, String message) {
        for(int i=0; i<this.multipleSyslogConfig.getProtocols().size(); i++) {
            String protocol = (String) this.multipleSyslogConfig.getProtocols().get(i);

            SyslogIF syslog = Syslog.getInstance(protocol);

            syslog.log(level,message);
        }
    }

    @Override
    public void log(SyslogLevel level, SyslogMessageIF message) {
        for(int i=0; i<this.multipleSyslogConfig.getProtocols().size(); i++) {
            String protocol = (String) this.multipleSyslogConfig.getProtocols().get(i);

            SyslogIF syslog = Syslog.getInstance(protocol);

            syslog.log(level,message);
        }
    }

    public void flush() throws SyslogRuntimeException {
        for(int i=0; i<this.multipleSyslogConfig.getProtocols().size(); i++) {
            String protocol = (String) this.multipleSyslogConfig.getProtocols().get(i);

            SyslogIF syslog = Syslog.getInstance(protocol);

            syslog.flush();
        }
    }

    public void shutdown() throws SyslogRuntimeException {
        for(int i=0; i<this.multipleSyslogConfig.getProtocols().size(); i++) {
            String protocol = (String) this.multipleSyslogConfig.getProtocols().get(i);

            SyslogIF syslog = Syslog.getInstance(protocol);

            syslog.shutdown();
        }
    }

    public void backLog(SyslogLevel level, String message, Throwable reasonThrowable) {
        // MultipleSyslog is an aggregator; backLog state will be handled by individual Syslog protocols
    }

    public void backLog(SyslogLevel level, String message, String reason) {
        // MultipleSyslog is an aggregator; backLog state will be handled by individual Syslog protocols
    }

    public void setMessageProcessor(SyslogMessageProcessorIF messageProcessor) {
        throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
    }

    public SyslogMessageProcessorIF getMessageProcessor() {
        throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
    }

    public void setStructuredMessageProcessor(SyslogMessageProcessorIF messageProcessor) {
        throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
    }

    public SyslogMessageProcessorIF getStructuredMessageProcessor() {
        throw new SyslogRuntimeException("MultipleSyslog is an aggregator; please set the individual protocols");
    }

    public String getProtocol() {
        return this.syslogProtocol;
    }
}
