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
package com.nesscomputing.syslog4j.impl;

import java.util.List;

import com.nesscomputing.syslog4j.SyslogBackLogHandlerIF;
import com.nesscomputing.syslog4j.SyslogConfigIF;
import com.nesscomputing.syslog4j.SyslogMessageModifierIF;

/**
* AbstractSyslogConfigIF provides an interface for all Abstract Syslog
* configuration implementations.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogConfigIF.java,v 1.7 2010/10/29 03:14:20 cvs Exp $
*/
public interface AbstractSyslogConfigIF extends SyslogConfigIF {
    public Class<? extends AbstractSyslogWriter> getSyslogWriterClass();

    public List<? extends SyslogBackLogHandlerIF> getBackLogHandlers();

    public List<? extends SyslogMessageModifierIF> getMessageModifiers();

    public byte[] getSplitMessageBeginText();
    public void setSplitMessageBeginText(byte[] beginText);

    public byte[] getSplitMessageEndText();
    public void setSplitMessageEndText(byte[] endText);

    public boolean isThreaded();
    public void setThreaded(boolean threaded);

    public boolean isUseDaemonThread();
    public void setUseDaemonThread(boolean useDaemonThread);

    public int getThreadPriority();
    public void setThreadPriority(int threadPriority);

    public long getThreadLoopInterval();
    public void setThreadLoopInterval(long threadLoopInterval);

    public long getMaxShutdownWait();
    public void setMaxShutdownWait(long maxShutdownWait);

    public int getWriteRetries();
    public void setWriteRetries(int writeRetries);

    public int getMaxQueueSize();
    /**
     * Use the (default) value of -1 to allow for a queue of indefinite depth (size).
     *
     * @param maxQueueSize
     */
    public void setMaxQueueSize(int maxQueueSize);
}
