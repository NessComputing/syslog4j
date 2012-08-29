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
package com.nesscomputing.syslog4j.server.impl.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.nesscomputing.syslog4j.SyslogConstants;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.server.SyslogServerEventIF;
import com.nesscomputing.syslog4j.server.impl.AbstractSyslogServer;
import com.nesscomputing.syslog4j.util.SyslogUtility;

/**
* UDPNetSyslogServer provides a simple non-threaded UDP/IP server implementation.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: UDPNetSyslogServer.java,v 1.16 2010/11/12 03:43:15 cvs Exp $
*/
public class UDPNetSyslogServer extends AbstractSyslogServer {
    private static final Logger LOG = Logger.getLogger(UDPNetSyslogServer.class);

    protected DatagramSocket ds = null;

    private final AtomicBoolean started = new AtomicBoolean(false);
    
    public void initialize() throws SyslogRuntimeException {
        //
    }

    public void shutdown() {
        started.set(false);
        super.shutdown();

        if (this.syslogServerConfig.getShutdownWait() > 0) {
            SyslogUtility.sleep(this.syslogServerConfig.getShutdownWait());
        }

        if (this.ds != null && !this.ds.isClosed()) {
            this.ds.close();
        }
    }

    protected DatagramSocket createDatagramSocket() throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = null;

        if (this.syslogServerConfig.getHost() != null) {
            InetAddress inetAddress = InetAddress.getByName(this.syslogServerConfig.getHost());

            datagramSocket = new DatagramSocket(this.syslogServerConfig.getPort(),inetAddress);

        } else {
            datagramSocket = new DatagramSocket(this.syslogServerConfig.getPort());
        }

        return datagramSocket;
    }

    @Override
    public boolean isStarted(){
        return started.get();
    }

    public void run() {
        try {
            this.ds = createDatagramSocket();
            this.shutdown = false;

        } catch (SocketException se) {
            LOG.warn("While creating datagram socket", se);
            return;

        } catch (UnknownHostException uhe) {
            LOG.warn("While creating datagram socket", uhe);
            return;
        }

        byte[] receiveData = new byte[SyslogConstants.SYSLOG_BUFFER_SIZE];

        handleInitialize(this);

        started.set(true);
        while(!this.shutdown) {
            DatagramPacket dp = null;

            try {
                dp = new DatagramPacket(receiveData,receiveData.length);

                this.ds.receive(dp);

                SyslogServerEventIF event = createEvent(this.getConfig(),receiveData,dp.getLength(),dp.getAddress());

                handleEvent(null,this,dp,event);

            } catch (SocketException se) {
                int i = se.getMessage() == null ? -1 : se.getMessage().toLowerCase().indexOf("socket closed");

                if (i == -1) {
                    handleException(null,this,dp.getSocketAddress(),se);
                }

            } catch (IOException ioe) {
                handleException(null,this,dp.getSocketAddress(),ioe);
            }
        }
        started.set(false);
        handleDestroy(this);
    }

    @Override
    public int getActualPort(){
        return this.ds.getLocalPort();
    }
}
