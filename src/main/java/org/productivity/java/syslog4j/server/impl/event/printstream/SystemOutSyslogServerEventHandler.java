package org.productivity.java.syslog4j.server.impl.event.printstream;

import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;

public class SystemOutSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
    public static SyslogServerSessionEventHandlerIF create() {
        return new SystemOutSyslogServerEventHandler();
    }

    public SystemOutSyslogServerEventHandler() {
        super(System.out);
    }
}
