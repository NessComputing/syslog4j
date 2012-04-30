package org.productivity.java.syslog4j.test.net;

import java.util.List;

import org.junit.Ignore;
import org.productivity.java.syslog4j.AbstractNetSyslog4jTest;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslogConfigIF;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Lists;

@Ignore
public class BackLogTCPNetSyslog4jTest extends AbstractNetSyslog4jTest {
    public static class TestBackLogHandler implements SyslogBackLogHandlerIF {
        protected List<String> events = null;

        public TestBackLogHandler(List<String> events) {
            this.events = events;
        }

        @Override
        public void initialize() throws SyslogRuntimeException {
            LOG.info(this.getClass().getName() + ": READY");
        }

        @Override
        public void down(SyslogIF syslog, String reason) {
            LOG.info(this.getClass().getName() + ": DOWN");
        }

        @Override
        public void up(SyslogIF syslog) {
            LOG.info(this.getClass().getName() + ": UP");
        }

        @Override
        public void log(SyslogIF syslog, SyslogLevel level, String message, String reason) throws SyslogRuntimeException {
            String _message = message.substring(message.toUpperCase().indexOf("[TEST]"));

            this.events.add(_message);
        }
    }

    public static class ThreadStarter implements Runnable {
        protected long pause = -1;
        protected String protocol = null;

        public ThreadStarter(long pause, String protocol) {
            this.pause = pause;
            this.protocol = protocol;

            SyslogServer.getInstance(this.protocol).shutdown();
        }

        public void run() {
            SyslogUtility.sleep(this.pause);

            SyslogServer.getThreadedInstance(this.protocol);
        }
    }

    protected int getMessageCount() {
        return 1200;
    }

    protected String getClientProtocol() {
        return "tcp";
    }

    protected String getServerProtocol() {
        return "tcp";
    }

    public void testSendReceive() {
        Thread t = new Thread(new ThreadStarter(2500,"tcp"));
        t.start();

        List<String> backLogEvents = Lists.newArrayList();

        TestBackLogHandler bh = new TestBackLogHandler(backLogEvents);
        bh.initialize();

        Syslog.getInstance("tcp").getConfig().addBackLogHandler(bh);
        ((AbstractSyslogConfigIF) Syslog.getInstance("tcp").getConfig()).setThreaded(false);

        SyslogServer.getInstance("tcp").getConfig().setShutdownWait(0);

        _testThreadedSendReceive(1,true,true,backLogEvents);
    }
}
