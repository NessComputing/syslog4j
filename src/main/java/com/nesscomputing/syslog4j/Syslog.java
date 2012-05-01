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
package com.nesscomputing.syslog4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
import com.nesscomputing.syslog4j.impl.net.udp.UDPNetSyslogConfig;
import com.nesscomputing.syslog4j.impl.unix.UnixSyslogConfig;
import com.nesscomputing.syslog4j.impl.unix.socket.UnixSocketSyslogConfig;
import com.nesscomputing.syslog4j.util.OSDetectUtility;
import com.nesscomputing.syslog4j.util.SyslogUtility;

/**
 * This class provides a Singleton interface for Syslog4j client implementations.
 *
 * <p>Usage examples:</p>
 *
 * <b>Direct</b>
 * <pre>
 * Syslog.getInstance("udp").info("log message");
 * </pre>
 *
 * <b>Via Instance</b>
 * <pre>
 * SyslogIF syslog = Syslog.getInstance("udp");
 * syslog.info();
 * </pre>
 *
 * <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
 * of the LGPL license is available in the META-INF folder in all
 * distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
 *
 * @author &lt;syslog4j@productivity.org&gt;
 * @version $Id: Syslog.java,v 1.23 2011/01/23 20:49:12 cvs Exp $
 */
public final class Syslog
{
    protected static final Map<String, SyslogIF> instances = Maps.newHashMap();

    /**
     * Set up the default TCP and UDP Syslog protocols, as
     * well as UNIX_SYSLOG and UNIX_SOCKET (if running on a Unix-based system).
     */
    static {
        initialize();
    }

    /**
     * Syslog is a singleton.
     */
    private Syslog() {
        //
    }

    /**
     * Use getInstance(protocol) as the starting point for Syslog4j.
     *
     * @param protocol - the Syslog protocol to use, e.g. "udp", "tcp", "unix_syslog", "unix_socket", or a custom protocol
     * @return Returns an instance of SyslogIF.
     * @throws SyslogRuntimeException
     */
    public static SyslogIF getInstance(String protocol) throws SyslogRuntimeException {
        String _protocol = protocol.toLowerCase();

        if (instances.containsKey(_protocol)) {
            return instances.get(_protocol);

        } else {
            StringBuffer message = new StringBuffer("Syslog protocol \"" + protocol + "\" not defined; call Syslogger.createSyslogInstance(protocol,config) first");

            if (instances.size() > 0) {
                message.append(" or use one of the following instances: ");

                Iterator<String> i = instances.keySet().iterator();
                while (i.hasNext()) {
                    String k = (String) i.next();

                    message.append(k);
                    if (i.hasNext()) {
                        message.append(' ');
                    }
                }
            }

            throw new SyslogRuntimeException(message.toString());
        }
    }

    /**
     * Use createInstance(protocol,config) to create your own Syslog instance.
     *
     * <p>First, create an implementation of SyslogConfigIF, such as UdpNetSyslogConfig.</p>
     *
     * <p>Second, configure that configuration instance.</p>
     *
     * <p>Third, call createInstance(protocol,config) using a short &amp; simple
     * String for the protocol argument.</p>
     *
     * <p>Fourth, either use the returned instance of SyslogIF, or in later code
     * call getInstance(protocol) with the protocol chosen in the previous step.</p>
     *
     * @param protocol
     * @param config
     * @return Returns an instance of SyslogIF.
     * @throws SyslogRuntimeException
     */
    public static SyslogIF createInstance(String protocol, SyslogConfigIF config) throws SyslogRuntimeException
    {
        Preconditions.checkArgument(!StringUtils.isBlank(protocol), "Instance protocol cannot be null or empty");
        Preconditions.checkArgument(config != null, "SyslogConfig cannot be null");

        String syslogProtocol = protocol.toLowerCase();

        SyslogIF syslog = null;

        synchronized(instances) {
            Preconditions.checkState(!instances.containsKey(syslogProtocol), "Syslog protocol \"%s\" already defined", protocol);

            try {
                Class<? extends SyslogIF> syslogClass = config.getSyslogClass();
                syslog = syslogClass.newInstance();

            } catch (ClassCastException cse) {
                if (!config.isThrowExceptionOnInitialize()) {
                    throw new SyslogRuntimeException(cse);

                } else {
                    return null;
                }

            } catch (IllegalAccessException iae) {
                if (!config.isThrowExceptionOnInitialize()) {
                    throw new SyslogRuntimeException(iae);

                } else {
                    return null;
                }

            } catch (InstantiationException ie) {
                if (!config.isThrowExceptionOnInitialize()) {
                    throw new SyslogRuntimeException(ie);

                } else {
                    return null;
                }
            }

            syslog.initialize(syslogProtocol,config);

            instances.put(syslogProtocol,syslog);
        }

        return syslog;
    }


    /**
     * @param protocol - Syslog protocol
     * @return Returns whether the protocol has been previously defined.
     */
    public static boolean exists(String protocol) {
        if (StringUtils.isBlank(protocol)) {
            return false;
        }

        return instances.containsKey(protocol.toLowerCase());
    }

    /**
     * shutdown() gracefully shuts down all defined Syslog protocols,
     * which includes flushing all queues and connections and finally
     * clearing all instances (including those initialized by default).
     */
    public synchronized static void shutdown() {
        Set<String> protocols = instances.keySet();

        if (protocols.size() > 0) {
            Iterator<String> i = protocols.iterator();

            SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);

            while(i.hasNext()) {
                String protocol = i.next();

                SyslogIF syslog = instances.get(protocol);

                syslog.shutdown();
            }

            instances.clear();
        }
    }

    private synchronized static void initialize() {
        createInstance(SyslogConstants.UDP,new UDPNetSyslogConfig());
        createInstance(SyslogConstants.TCP,new TCPNetSyslogConfig());

        if (OSDetectUtility.isUnix() && SyslogUtility.isClassExists(SyslogConstants.JNA_NATIVE_CLASS)) {
            createInstance(SyslogConstants.UNIX_SYSLOG,new UnixSyslogConfig());
            createInstance(SyslogConstants.UNIX_SOCKET,new UnixSocketSyslogConfig());
        }
    }

    static void reset() throws InterruptedException
    {
        shutdown();
        Thread.sleep(200L);
        initialize();
    }


    /**
     * destroyInstance() gracefully shuts down the specified Syslog protocol and
     * removes the instance from Syslog4j.
     *
     * @param protocol - the Syslog protocol to destroy
     * @throws SyslogRuntimeException
     */
    public synchronized static void destroyInstance(String protocol) throws SyslogRuntimeException {
        if (StringUtils.isBlank(protocol)) {
            return;
        }

        String _protocol = protocol.toLowerCase();

        if (instances.containsKey(_protocol)) {
            SyslogUtility.sleep(SyslogConstants.THREAD_LOOP_INTERVAL_DEFAULT);

            SyslogIF syslog = instances.get(_protocol);

            try {
                syslog.shutdown();

            } finally {
                instances.remove(_protocol);
            }

        } else {
            throw new SyslogRuntimeException("Cannot destroy protocol \"%s\" instance; call shutdown instead", protocol);
        }
    }

    /**
     * destroyInstance() gracefully shuts down the specified Syslog instance and
     * removes it from Syslog4j.
     *
     * @param syslog - the Syslog instance to destroy
     * @throws SyslogRuntimeException
     */
    public synchronized static void destroyInstance(SyslogIF syslog) throws SyslogRuntimeException {
        if (syslog == null) {
            return;
        }

        String protocol = syslog.getProtocol().toLowerCase();

        if (instances.containsKey(protocol)) {
            try {
                syslog.shutdown();

            } finally {
                instances.remove(protocol);
            }

        } else {
            throw new SyslogRuntimeException("Cannot destroy protocol \"%s\" instance; call shutdown instead", protocol);
        }
    }

    public static void main(String[] args) throws Exception {
        SyslogMain.main(args);
    }
}
