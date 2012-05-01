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
package com.nesscomputing.syslog4j.test.split.base;

import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.AbstractSyslogConfig;

public class SplitSyslogConfig extends AbstractSyslogConfig {
    public Class<? extends SyslogIF> getSyslogClass() {
        return SplitSyslog.class;
    }

    public String getHost() {
        return null;
    }

    public String getSocketPath() {
        return null;
    }

    public int getPort() {
        return 0;
    }

    public void setHost(String host) throws SyslogRuntimeException {
        //
    }

    public void setSocketPath(String path) throws SyslogRuntimeException {
        //
    }

    public void setPort(int port) throws SyslogRuntimeException {
        //
    }

    public int getMaxQueueSize() {
        return 0;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        //
    }


}
