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
package com.nesscomputing.syslog4j.impl.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;

import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.AbstractSyslog;
import com.nesscomputing.syslog4j.impl.AbstractSyslogConfigIF;
import com.nesscomputing.syslog4j.impl.AbstractSyslogWriter;

/**
* AbstractSyslogPoolFactory is an abstract implementation of the Apache Commons Pool
* BasePoolableObjectFactory.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogPoolFactory.java,v 1.5 2008/12/10 04:15:11 cvs Exp $
* @see com.nesscomputing.syslog4j.impl.pool.generic.GenericSyslogPoolFactory
*/
public abstract class AbstractSyslogPoolFactory extends BasePoolableObjectFactory {
    protected AbstractSyslog syslog = null;
    protected AbstractSyslogConfigIF syslogConfig = null;

    protected ObjectPool pool = null;

    public AbstractSyslogPoolFactory() {
        //
    }

    public void initialize(AbstractSyslog abstractSyslog) throws SyslogRuntimeException {
        this.syslog = abstractSyslog;

        try {
            this.syslogConfig = (AbstractSyslogConfigIF) this.syslog.getConfig();

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("config must implement AbstractSyslogConfigIF");
        }

        this.pool = createPool();
    }

    public Object makeObject() throws Exception {
        AbstractSyslogWriter syslogWriter = this.syslog.createWriter();

        if (this.syslogConfig.isThreaded()) {
            this.syslog.createWriterThread(syslogWriter);
        }

        return syslogWriter;
    }

    public void destroyObject(Object obj) throws Exception {
        AbstractSyslogWriter writer = (AbstractSyslogWriter) obj;

        writer.shutdown();

        super.destroyObject(writer);
    }

    public abstract ObjectPool createPool() throws SyslogRuntimeException;

    public AbstractSyslogWriter borrowSyslogWriter() throws Exception {
        AbstractSyslogWriter syslogWriter = (AbstractSyslogWriter) this.pool.borrowObject();

        return syslogWriter;
    }

    public void returnSyslogWriter(AbstractSyslogWriter syslogWriter) throws Exception {
        this.pool.returnObject(syslogWriter);
    }

    public void clear() throws Exception {
        this.pool.clear();
    }

    public void close() throws Exception {
        this.pool.close();
    }
}
