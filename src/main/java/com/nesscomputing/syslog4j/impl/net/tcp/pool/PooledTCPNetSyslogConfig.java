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

import org.apache.commons.pool.impl.GenericObjectPool;

import com.nesscomputing.syslog4j.SyslogConstants;
import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.SyslogPoolConfigIF;
import com.nesscomputing.syslog4j.impl.net.tcp.TCPNetSyslogConfig;
/**
* NetSyslogPoolFactory is an implementation of SyslogPoolConfigIF
* which provides configuration support for the Apache Commons Pool.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PooledTCPNetSyslogConfig.java,v 1.3 2008/11/26 15:01:47 cvs Exp $
*/
public class PooledTCPNetSyslogConfig extends TCPNetSyslogConfig implements SyslogPoolConfigIF {
    protected int maxActive							= SyslogConstants.SYSLOG_POOL_CONFIG_MAX_ACTIVE_DEFAULT;
    protected int maxIdle							= SyslogConstants.SYSLOG_POOL_CONFIG_MAX_IDLE_DEFAULT;
    protected long maxWait							= SyslogConstants.SYSLOG_POOL_CONFIG_MAX_WAIT_DEFAULT;
    protected long minEvictableIdleTimeMillis		= SyslogConstants.SYSLOG_POOL_CONFIG_MIN_EVICTABLE_IDLE_TIME_MILLIS_DEFAULT;
    protected int minIdle							= SyslogConstants.SYSLOG_POOL_CONFIG_MIN_IDLE_DEFAULT;
    protected int numTestsPerEvictionRun			= SyslogConstants.SYSLOG_POOL_CONFIG_NUM_TESTS_PER_EVICTION_RUN_DEFAULT;
    protected long softMinEvictableIdleTimeMillis	= SyslogConstants.SYSLOG_POOL_CONFIG_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS_DEFAULT;
    protected long timeBetweenEvictionRunsMillis	= SyslogConstants.SYSLOG_POOL_CONFIG_TIME_BETWEEN_EVICTION_RUNS_MILLIS_DEFAULT;
    protected byte whenExhaustedAction				= GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
    protected boolean testOnBorrow					= SyslogConstants.SYSLOG_POOL_CONFIG_TEST_ON_BORROW_DEFAULT;
    protected boolean testOnReturn					= SyslogConstants.SYSLOG_POOL_CONFIG_TEST_ON_RETURN_DEFAULT;
    protected boolean testWhileIdle					= SyslogConstants.SYSLOG_POOL_CONFIG_TEST_WHILE_IDLE_DEFAULT;

    public PooledTCPNetSyslogConfig() {
        //
    }

    public PooledTCPNetSyslogConfig(SyslogFacility facility, String host, int port) {
        super(facility, host, port);
    }

    public PooledTCPNetSyslogConfig(SyslogFacility facility, String host) {
        super(facility, host);
    }

    public PooledTCPNetSyslogConfig(SyslogFacility facility) {
        super(facility);
    }

    public PooledTCPNetSyslogConfig(String host, int port) {
        super(host, port);
    }

    public PooledTCPNetSyslogConfig(String host) {
        super(host);
    }

    protected void configureThreadedValues(int value) {
        if (isThreaded()) {
            this.minIdle = value;
            this.maxIdle = value;
            this.maxActive = value;
        }
    }

    public int getMaxActive() {
        return this.maxActive;
    }

    public void setMaxActive(int maxActive) {
        configureThreadedValues(maxActive);

        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        configureThreadedValues(maxIdle);

        this.maxIdle = maxIdle;
    }

    public long getMaxWait() {
        return this.maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(int minIdle) {
        configureThreadedValues(minIdle);

        this.minIdle = minIdle;
    }

    public int getNumTestsPerEvictionRun() {
        return this.numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public long getSoftMinEvictableIdleTimeMillis() {
        return this.softMinEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(
            long softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public byte getWhenExhaustedAction() {
        return this.whenExhaustedAction;
    }

    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }

    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return this.testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return this.testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public Class<? extends SyslogIF> getSyslogClass() {
        return PooledTCPNetSyslog.class;
    }
}
