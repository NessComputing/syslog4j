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
package org.productivity.java.syslog4j.server;

import static org.productivity.java.syslog4j.SyslogConstants.TCP;
import static org.productivity.java.syslog4j.SyslogConstants.UDP;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.udp.UDPNetSyslogServerConfig;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.google.common.collect.Maps;
/**
 * This class provides a Singleton-based interface for Syslog4j
 * server implementations.
 *
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: SyslogServer.java,v 1.14 2011/01/23 20:49:12 cvs Exp $
 */
public class SyslogServer {
    private static boolean SUPPRESS_RUNTIME_EXCEPTIONS = false;

    protected static final Map<String, SyslogServerIF> instances = Maps.newHashMap();

    static {
        initialize();
    }

    private SyslogServer() {
        //
    }

    /**
     * @param suppress - true to suppress throwing SyslogRuntimeException in many methods of this class, false to throw exceptions (default)
     */
    public static void setSuppressRuntimeExceptions(boolean suppress) {
        SUPPRESS_RUNTIME_EXCEPTIONS = suppress;
    }

    /**
     * @return Returns whether or not to suppress throwing SyslogRuntimeException in many methods of this class
     */
    public static boolean getSuppressRuntimeExceptions() {
        return SUPPRESS_RUNTIME_EXCEPTIONS;
    }

    /**
     * Throws SyslogRuntimeException unless it has been suppressed via setSuppressRuntimeException(boolean).
     *
     * @param message
     * @throws SyslogRuntimeException
     */
    private static void throwRuntimeException(String message) throws SyslogRuntimeException {
        if (SUPPRESS_RUNTIME_EXCEPTIONS) {
            return;

        } else {
            throw new SyslogRuntimeException(message.toString());
        }
    }

    public static final SyslogServerIF getInstance(String protocol) throws SyslogRuntimeException {
        String syslogProtocol = protocol.toLowerCase();

        if (instances.containsKey(syslogProtocol)) {
            return (SyslogServerIF) instances.get(syslogProtocol);

        } else {
            throwRuntimeException("SyslogServer instance \"" + syslogProtocol + "\" not defined; use \"tcp\" or \"udp\" or call SyslogServer.createInstance(protocol,config) first");
            return null;
        }
    }

    public static final SyslogServerIF getThreadedInstance(String protocol) throws SyslogRuntimeException {
        SyslogServerIF server = getInstance(protocol);

        if (server.getThread() == null) {
            Thread thread = new Thread(server);
            thread.setName("SyslogServer: " + protocol);
            thread.setDaemon(server.getConfig().isUseDaemonThread());
            if (server.getConfig().getThreadPriority() > -1) {
                thread.setPriority(server.getConfig().getThreadPriority());
            }

            server.setThread(thread);
            thread.start();
        }

        return server;
    }

    public static final boolean exists(String protocol) {
        if (StringUtils.isBlank(protocol)) {
            return false;
        }

        return instances.containsKey(protocol.toLowerCase());
    }

    public static final SyslogServerIF createInstance(String protocol, SyslogServerConfigIF config) throws SyslogRuntimeException {
        if (StringUtils.isBlank(protocol)) {
            throwRuntimeException("Instance protocol cannot be null or empty");
            return null;
        }

        if (config == null) {
            throwRuntimeException("SyslogServerConfig cannot be null");
            return null;
        }

        String syslogProtocol = protocol.toLowerCase();

        SyslogServerIF syslogServer = null;

        synchronized(instances) {
            if (instances.containsKey(syslogProtocol)) {
                throwRuntimeException("SyslogServer instance \"" + syslogProtocol + "\" already defined.");
                return null;
            }

            try {
                Class<? extends SyslogServerIF> syslogClass = config.getSyslogServerClass();

                syslogServer = syslogClass.newInstance();

            } catch (ClassCastException cse) {
                throw new SyslogRuntimeException(cse);

            } catch (IllegalAccessException iae) {
                throw new SyslogRuntimeException(iae);

            } catch (InstantiationException ie) {
                throw new SyslogRuntimeException(ie);
            }

            syslogServer.initialize(syslogProtocol,config);

            instances.put(syslogProtocol,syslogServer);
        }

        return syslogServer;
    }

    public static final SyslogServerIF createThreadedInstance(String protocol, SyslogServerConfigIF config) throws SyslogRuntimeException {
        createInstance(protocol,config);

        SyslogServerIF server = getThreadedInstance(protocol);

        return server;
    }

    public synchronized static final void destroyInstance(String protocol) {
        if (StringUtils.isBlank(protocol)) {
            return;
        }

        String _protocol = protocol.toLowerCase();

        if (instances.containsKey(_protocol)) {
            SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);

            SyslogServerIF syslogServer = (SyslogServerIF) instances.get(_protocol);

            try {
                syslogServer.shutdown();

            } finally {
                instances.remove(_protocol);
            }

        } else {
            throwRuntimeException("Cannot destroy server protocol \"" + protocol + "\" instance; call shutdown instead");
            return;
        }
    }

    public synchronized static final void destroyInstance(SyslogServerIF syslogServer) {
        if (syslogServer == null) {
            return;
        }

        String protocol = syslogServer.getProtocol().toLowerCase();

        if (instances.containsKey(protocol)) {
            SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);

            try {
                syslogServer.shutdown();

            } finally {
                instances.remove(protocol);
            }

        } else {
            throwRuntimeException("Cannot destroy server protocol \"" + protocol + "\" instance; call shutdown instead");
        }
    }

    public synchronized static void initialize() {
        createInstance(UDP,new UDPNetSyslogServerConfig());
        createInstance(TCP,new TCPNetSyslogServerConfig());
    }

    public synchronized static final void shutdown() throws SyslogRuntimeException {
        Set<String> protocols = instances.keySet();

        Iterator<String> i = protocols.iterator();

        while(i.hasNext()) {
            String protocol = i.next();

            SyslogServerIF syslogServer = instances.get(protocol);

            syslogServer.shutdown();
        }

        instances.clear();
    }

    public static void main(String[] args) throws Exception {
        SyslogServerMain.main(args);
    }
}
