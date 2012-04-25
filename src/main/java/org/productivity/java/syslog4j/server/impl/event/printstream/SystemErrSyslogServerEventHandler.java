package org.productivity.java.syslog4j.server.impl.event.printstream;

import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;

public class SystemErrSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
    public static SyslogServerSessionEventHandlerIF create() {
        return new SystemErrSyslogServerEventHandler();
    }

    public SystemErrSyslogServerEventHandler() {
        super(System.err);
    }
}
