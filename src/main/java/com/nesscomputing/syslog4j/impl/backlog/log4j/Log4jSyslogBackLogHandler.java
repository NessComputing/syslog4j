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
package com.nesscomputing.syslog4j.impl.backlog.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.SyslogLevel;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.backlog.AbstractSyslogBackLogHandler;

/**
* Log4jSyslogBackLogHandler is used to send Syslog backLog messages to
* Log4j whenever the Syslog protocol fails.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: Log4jSyslogBackLogHandler.java,v 1.2 2009/07/22 15:54:23 cvs Exp $
*/
public class Log4jSyslogBackLogHandler extends AbstractSyslogBackLogHandler {
    protected Logger logger = null;
    protected Level downLevel = Level.WARN;
    protected Level upLevel = Level.WARN;

    public Log4jSyslogBackLogHandler(Logger logger) throws SyslogRuntimeException {
        this.logger = logger;

        initialize();
    }

    public Log4jSyslogBackLogHandler(Logger logger, boolean appendReason) {
        this.logger = logger;
        this.appendReason = appendReason;

        initialize();
    }

    public Log4jSyslogBackLogHandler(Class<?> loggerClass) {
        if (loggerClass == null) {
            throw new SyslogRuntimeException("loggerClass cannot be null");
        }

        this.logger = Logger.getLogger(loggerClass);

        initialize();
    }

    public Log4jSyslogBackLogHandler(Class<?> loggerClass, boolean appendReason) {
        if (loggerClass == null) {
            throw new SyslogRuntimeException("loggerClass cannot be null");
        }

        this.logger = Logger.getLogger(loggerClass);
        this.appendReason = appendReason;

        initialize();
    }

    public Log4jSyslogBackLogHandler(String loggerName) {
        if (loggerName == null) {
            throw new SyslogRuntimeException("loggerName cannot be null");
        }

        this.logger = Logger.getLogger(loggerName);

        initialize();
    }

    public Log4jSyslogBackLogHandler(String loggerName, boolean appendReason) {
        if (loggerName == null) {
            throw new SyslogRuntimeException("loggerName cannot be null");
        }

        this.logger = Logger.getLogger(loggerName);
        this.appendReason = appendReason;

        initialize();
    }

    public Log4jSyslogBackLogHandler(String loggerName, LoggerFactory loggerFactory) {
        if (loggerName == null) {
            throw new SyslogRuntimeException("loggerName cannot be null");
        }

        if (loggerFactory == null) {
            throw new SyslogRuntimeException("loggerFactory cannot be null");
        }

        this.logger = Logger.getLogger(loggerName,loggerFactory);

        initialize();
    }

    public Log4jSyslogBackLogHandler(String loggerName, LoggerFactory loggerFactory, boolean appendReason) {
        if (loggerName == null) {
            throw new SyslogRuntimeException("loggerName cannot be null");
        }

        if (loggerFactory == null) {
            throw new SyslogRuntimeException("loggerFactory cannot be null");
        }

        this.logger = Logger.getLogger(loggerName,loggerFactory);
        this.appendReason = appendReason;

        initialize();
    }

    public void initialize() throws SyslogRuntimeException {
        if (this.logger == null) {
            throw new SyslogRuntimeException("logger cannot be null");
        }
    }

    protected static Level getLog4jLevel(SyslogLevel level)
    {
        if (level == null) {
            return Level.WARN;
        }

        switch(level) {
            case DEBUG:     return Level.DEBUG;
            case INFO: 		return Level.INFO;
            case NOTICE: 	return Level.INFO;
            case WARN: 		return Level.WARN;
            case ERROR: 	return Level.ERROR;
            case CRITICAL: 	return Level.ERROR;
            case ALERT: 	return Level.ERROR;
            case EMERGENCY: return Level.FATAL;

            default:
                return Level.WARN;
        }
    }

    public void down(SyslogIF syslog, String reason) {
        this.logger.log(this.downLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is down: " + reason);
    }

    public void up(SyslogIF syslog) {
        this.logger.log(this.upLevel,"Syslog protocol \"" + syslog.getProtocol() + "\" is up");
    }

    public void log(SyslogIF syslog, SyslogLevel level, String message, String reason) throws SyslogRuntimeException {
        Level log4jLevel = getLog4jLevel(level);

        String combinedMessage = combine(syslog,level,message,reason);

        this.logger.log(log4jLevel,combinedMessage);
    }
}
