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
package com.nesscomputing.syslog4j.impl.net.tcp.pool;

import org.apache.log4j.Logger;

import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.AbstractSyslogWriter;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslog;
import com.nesscomputing.syslog4j.impl.pool.AbstractSyslogPoolFactory;
import com.nesscomputing.syslog4j.impl.pool.generic.GenericSyslogPoolFactory;

/**
* PooledTCPNetSyslog is an extension of TCPNetSyslog which provides support
* for Apache Commons Pool.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PooledTCPNetSyslog.java,v 1.5 2008/12/10 04:30:15 cvs Exp $
*/
public class PooledTCPNetSyslog extends TCPNetSyslog {
    private final Logger LOG = Logger.getLogger(PooledTCPNetSyslog.class);

    protected AbstractSyslogPoolFactory poolFactory = null;

    public void initialize() throws SyslogRuntimeException {
        super.initialize();

        this.poolFactory = createSyslogPoolFactory();

        this.poolFactory.initialize(this);
    }

    protected AbstractSyslogPoolFactory createSyslogPoolFactory() {
        AbstractSyslogPoolFactory syslogPoolFactory = new GenericSyslogPoolFactory();

        return syslogPoolFactory;
    }

    public AbstractSyslogWriter getWriter() {
        try {
            AbstractSyslogWriter syslogWriter = this.poolFactory.borrowSyslogWriter();

            return syslogWriter;

        } catch (Exception e) {
            throw new SyslogRuntimeException(e);
        }
    }

    public void returnWriter(AbstractSyslogWriter syslogWriter) {
        try {
            this.poolFactory.returnSyslogWriter(syslogWriter);

        } catch (Exception e) {
            throw new SyslogRuntimeException(e);
        }
    }

    public void flush() throws SyslogRuntimeException {
        try {
            this.poolFactory.clear();

        } catch (Exception e) {
            LOG.trace("While flushing", e);
        }
    }

    public void shutdown() throws SyslogRuntimeException {
        try {
            this.poolFactory.close();

        } catch (Exception e) {
            LOG.trace("While shutdown", e);
        }
    }
}
