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
package org.productivity.java.syslog4j.impl.message.modifier.text;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* PrefixSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for adding static text to the beginning of a Syslog message.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: PrefixSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class PrefixSyslogMessageModifier implements SyslogMessageModifierIF {
    protected String prefix = null;
    protected String delimiter = " ";

    public PrefixSyslogMessageModifier() {
        //
    }

    public PrefixSyslogMessageModifier(String prefix) {
        this.prefix = prefix;
    }

    public PrefixSyslogMessageModifier(String prefix, String delimiter) {
        this.prefix = prefix;
        if (delimiter != null) {
            this.delimiter = delimiter;
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String modify(SyslogIF syslog, SyslogFacility facility, SyslogLevel level, String message) {
        if (StringUtils.isBlank(prefix)) {
            return message;
        }

        return this.prefix + this.delimiter + message;
    }

    public boolean verify(String message) {
        // NO-OP

        return true;
    }
}
