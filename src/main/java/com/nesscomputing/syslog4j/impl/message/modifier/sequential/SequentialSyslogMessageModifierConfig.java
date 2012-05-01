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
package com.nesscomputing.syslog4j.impl.message.modifier.sequential;

import com.nesscomputing.syslog4j.SyslogConstants;
import com.nesscomputing.syslog4j.impl.message.modifier.AbstractSyslogMessageModifierConfig;
/**
* SequentialSyslogMessageModifierConfig is an implementation of AbstractSyslogMessageModifierConfig
* that provides configuration for SequentialSyslogMessageModifier.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SequentialSyslogMessageModifierConfig.java,v 1.4 2009/03/29 17:38:58 cvs Exp $
*/
public class SequentialSyslogMessageModifierConfig extends AbstractSyslogMessageModifierConfig {
    protected long firstNumber = SyslogConstants.SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_FIRST_NUMBER_DEFAULT;
    protected long lastNumber = SyslogConstants.SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_LAST_NUMBER_DEFAULT;
    protected char padChar = SyslogConstants.SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_PAD_CHAR_DEFAULT;
    protected boolean usePadding = SyslogConstants.SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_USE_PADDING_DEFAULT;

    public static final SequentialSyslogMessageModifierConfig createDefault() {
        SequentialSyslogMessageModifierConfig modifierConfig = new SequentialSyslogMessageModifierConfig();

        return modifierConfig;
    }

    public SequentialSyslogMessageModifierConfig() {
        setPrefix(SyslogConstants.SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_PREFIX_DEFAULT);
        setSuffix(SyslogConstants.SYSLOG_SEQUENTIAL_MESSAGE_MODIFIER_SUFFIX_DEFAULT);
    }

    public long getLastNumberDigits() {
        return Long.toString(this.lastNumber).length();
    }

    public long getFirstNumber() {
        return this.firstNumber;
    }

    public void setFirstNumber(long firstNumber) {
        if (firstNumber < this.lastNumber) {
            this.firstNumber = firstNumber;
        }
    }

    public long getLastNumber() {
        return this.lastNumber;
    }

    public void setLastNumber(long lastNumber) {
        if (lastNumber > this.firstNumber) {
            this.lastNumber = lastNumber;
        }
    }

    public boolean isUsePadding() {
        return this.usePadding;
    }

    public void setUsePadding(boolean usePadding) {
        this.usePadding = usePadding;
    }

    public char getPadChar() {
        return this.padChar;
    }

    public void setPadChar(char padChar) {
        this.padChar = padChar;
    }
}
