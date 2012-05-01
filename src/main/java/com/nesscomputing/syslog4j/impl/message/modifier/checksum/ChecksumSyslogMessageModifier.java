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
package com.nesscomputing.syslog4j.impl.message.modifier.checksum;

import com.nesscomputing.syslog4j.SyslogFacility;
import com.nesscomputing.syslog4j.SyslogIF;
import com.nesscomputing.syslog4j.SyslogLevel;
import com.nesscomputing.syslog4j.SyslogRuntimeException;
import com.nesscomputing.syslog4j.impl.message.modifier.AbstractSyslogMessageModifier;
import com.nesscomputing.syslog4j.util.SyslogUtility;

/**
* ChecksumSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for Java Checksum algorithms (java.util.zip.Checksum).
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: ChecksumSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class ChecksumSyslogMessageModifier extends AbstractSyslogMessageModifier {
    protected ChecksumSyslogMessageModifierConfig config = null;

    public static final ChecksumSyslogMessageModifier createCRC32() {
        ChecksumSyslogMessageModifier crc32 = new ChecksumSyslogMessageModifier(ChecksumSyslogMessageModifierConfig.createCRC32());

        return crc32;
    }
    public static final ChecksumSyslogMessageModifier createADLER32() {
        ChecksumSyslogMessageModifier adler32 = new ChecksumSyslogMessageModifier(ChecksumSyslogMessageModifierConfig.createADLER32());

        return adler32;
    }

    public ChecksumSyslogMessageModifier(ChecksumSyslogMessageModifierConfig config) {
        super(config);

        this.config = config;

        if (this.config == null) {
            throw new SyslogRuntimeException("Checksum config object cannot be null");
        }

        if (this.config.getChecksum() == null) {
            throw new SyslogRuntimeException("Checksum object cannot be null");
        }
    }

    public ChecksumSyslogMessageModifierConfig getConfig() {
        return this.config;
    }

    protected void continuousCheckForVerify() {
        if (this.config.isContinuous()) {
            throw new SyslogRuntimeException("%s.verify(..) does not work with isContinuous() returning true", this.getClass().getName());
        }

    }

    public boolean verify(String message, String hexChecksum) {
        continuousCheckForVerify();

        long checksum = Long.parseLong(hexChecksum,16);

        return verify(message,checksum);
    }

    public boolean verify(String message, long checksum) {
        continuousCheckForVerify();

        synchronized(this.config.getChecksum()) {
            this.config.getChecksum().reset();

            byte[] messageBytes = SyslogUtility.getBytes(this.config,message);

            this.config.getChecksum().update(messageBytes,0,message.length());

            return this.config.getChecksum().getValue() == checksum;
        }
    }

    @Override
    public String modify(SyslogIF syslog, SyslogFacility facility, SyslogLevel level, String message) {
        synchronized(this.config.getChecksum()) {
            StringBuffer messageBuffer = new StringBuffer(message);

            byte[] messageBytes = SyslogUtility.getBytes(syslog.getConfig(),message);

            if (!this.config.isContinuous()) {
                this.config.getChecksum().reset();
            }

            this.config.getChecksum().update(messageBytes,0,message.length());

            messageBuffer.append(this.config.getPrefix());
            messageBuffer.append(Long.toHexString(this.config.getChecksum().getValue()).toUpperCase());
            messageBuffer.append(this.config.getSuffix());

            return messageBuffer.toString();
        }
    }
}
