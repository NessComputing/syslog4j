package org.productivity.java.syslog4j.test.split.base;

import java.util.List;

import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslog;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Lists;

public class SplitSyslog extends AbstractSyslog {
    public List<String> lastMessages = Lists.newArrayList();

    protected void initialize() throws SyslogRuntimeException {
        // NO-OP
    }

    public void flush() throws SyslogRuntimeException {
        this.lastMessages.clear();
    }

    protected void write(SyslogLevel level, byte[] message) throws SyslogRuntimeException {
        String lastMessage = SyslogUtility.newString(this.getConfig(),message);

        System.out.println(lastMessage);

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
