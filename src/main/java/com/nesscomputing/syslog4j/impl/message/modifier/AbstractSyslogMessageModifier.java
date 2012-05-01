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
package com.nesscomputing.syslog4j.impl.message.modifier;

import org.apache.commons.lang3.StringUtils;

import com.nesscomputing.syslog4j.SyslogMessageModifierConfigIF;
import com.nesscomputing.syslog4j.SyslogMessageModifierIF;

public abstract class AbstractSyslogMessageModifier implements SyslogMessageModifierIF {
    protected SyslogMessageModifierConfigIF messageModifierConfig = null;

    public AbstractSyslogMessageModifier(SyslogMessageModifierConfigIF messageModifierConfig) {
        this.messageModifierConfig = messageModifierConfig;
    }

    public String[] parseInlineModifier(String message) {
        return parseInlineModifier(message,this.messageModifierConfig.getPrefix(),this.messageModifierConfig.getSuffix());
    }

    public static String[] parseInlineModifier(String message, String prefix, String suffix) {
        String[] messageAndModifier = null;

        if (StringUtils.isBlank(message)) {
            return null;
        }

        if (StringUtils.isBlank(prefix)) {
            prefix = " ";
        }

        if (StringUtils.isBlank(suffix)) {
            int pi = message.lastIndexOf(prefix);

            if (pi > -1) {
                messageAndModifier = new String[] { message.substring(0,pi), message.substring(pi+prefix.length()) };
            }

        } else {
            int si = message.lastIndexOf(suffix);

            if (si > -1) {
                int pi = message.lastIndexOf(prefix,si);

                if (pi > -1) {
                    messageAndModifier = new String[] { message.substring(0,pi), message.substring(pi+prefix.length(),si) };
                }
            }
        }

        return messageAndModifier;
    }

    protected abstract boolean verify(String message, String modifier);

    public boolean verify(String message) {
        String[] messageAndModifier = parseInlineModifier(message);

        if (messageAndModifier == null || messageAndModifier.length != 2) {
            return false;
        }

        return verify(messageAndModifier[0],messageAndModifier[1]);
    }
}
