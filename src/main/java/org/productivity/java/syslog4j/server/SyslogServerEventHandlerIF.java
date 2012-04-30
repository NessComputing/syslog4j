package org.productivity.java.syslog4j.server;


public abstract interface SyslogServerEventHandlerIF {
    public void initialize(SyslogServerIF syslogServer);
    public void destroy(SyslogServerIF syslogServer);
}
