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
package com.nesscomputing.syslog4j.impl.pool.generic;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.nesscomputing.syslog4j.SyslogPoolConfigIF;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.AbstractSyslogWriter;
import com.nesscomputing.syslog4j.impl.pool.AbstractSyslogPoolFactory;

/**
* GenericSyslogPoolFactory is an implementation of the Apache Commons Pool
* BasePoolableObjectFactory using a GenericObjectPool.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: GenericSyslogPoolFactory.java,v 1.5 2008/12/10 04:15:10 cvs Exp $
*/
public class GenericSyslogPoolFactory extends AbstractSyslogPoolFactory
{
    protected void configureGenericObjectPool(GenericObjectPool<AbstractSyslogWriter> genericObjectPool) throws SyslogRuntimeException {
        SyslogPoolConfigIF poolConfig = null;

        try {
            poolConfig = (SyslogPoolConfigIF) this.syslog.getConfig();

        } catch (ClassCastException cce) {
            throw new SyslogRuntimeException("config must implement interface SyslogPoolConfigIF");
        }

        genericObjectPool.setMaxActive(poolConfig.getMaxActive());
        genericObjectPool.setMaxIdle(poolConfig.getMaxIdle());
        genericObjectPool.setMaxWait(poolConfig.getMaxWait());
        genericObjectPool.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTimeMillis());
        genericObjectPool.setMinIdle(poolConfig.getMinIdle());
        genericObjectPool.setNumTestsPerEvictionRun(poolConfig.getNumTestsPerEvictionRun());
        genericObjectPool.setSoftMinEvictableIdleTimeMillis(poolConfig.getSoftMinEvictableIdleTimeMillis());
        genericObjectPool.setTestOnBorrow(poolConfig.isTestOnBorrow());
        genericObjectPool.setTestOnReturn(poolConfig.isTestOnReturn());
        genericObjectPool.setTestWhileIdle(poolConfig.isTestWhileIdle());
        genericObjectPool.setTimeBetweenEvictionRunsMillis(poolConfig.getTimeBetweenEvictionRunsMillis());
        genericObjectPool.setWhenExhaustedAction(poolConfig.getWhenExhaustedAction());
    }

    public ObjectPool<AbstractSyslogWriter> createPool() throws SyslogRuntimeException {
        GenericObjectPool<AbstractSyslogWriter> genericPool = new GenericObjectPool<AbstractSyslogWriter>(this);

        configureGenericObjectPool(genericPool);

        return genericPool;
    }
}
