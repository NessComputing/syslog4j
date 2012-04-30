package org.productivity.java.syslog4j.test.split.base;

import java.util.List;

import org.apache.log4j.Logger;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslog;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Lists;

public class SplitSyslog extends AbstractSyslog {
    protected static final Logger LOG = Logger.getLogger("test");

    public List<String> lastMessages = Lists.newArrayList();

    protected void initialize() throws SyslogRuntimeException {
        // NO-OP
    }

    public void flush() throws SyslogRuntimeException {
        this.lastMessages.clear();
    }

    protected void write(SyslogLevel level, byte[] message) throws SyslogRuntimeException {
        String lastMessage = SyslogUtility.newString(this.getConfig(),message);

        LOG.info(lastMessage);

        this.lastMessages.add(lastMessage);
    }

    public List<String> getLastMessages() {
        return this.lastMessages;
    }

    public void shutdown() {
        flush();
    }

    public AbstractSyslogWriter getWriter() {
        return null;
    }

    public void returnWriter(AbstractSyslogWriter syslogWriter) {
        //
    }
}
