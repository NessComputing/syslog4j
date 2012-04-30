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

import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* StringCaseSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for shifting a Syslog message to all upper case or all
* lower case.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: StringCaseSyslogMessageModifier.java,v 1.3 2010/10/28 05:10:57 cvs Exp $
*/
public class StringCaseSyslogMessageModifier implements SyslogMessageModifierIF {
    public static final byte LOWER_CASE = 0;
    public static final byte UPPER_CASE = 1;

    public static final StringCaseSyslogMessageModifier LOWER = new StringCaseSyslogMessageModifier(LOWER_CASE);
    public static final StringCaseSyslogMessageModifier UPPER = new StringCaseSyslogMessageModifier(UPPER_CASE);

    protected byte stringCase = LOWER_CASE;

    public StringCaseSyslogMessageModifier(byte stringCase) {
        this.stringCase = stringCase;

        if (stringCase < LOWER_CASE || stringCase > UPPER_CASE) {
            throw new SyslogRuntimeException("stringCase must be LOWER_CASE (0) or UPPER_CASE (1)");
        }
    }

    @Override
    public String modify(SyslogIF syslog, SyslogFacility facility, SyslogLevel level, String message) {
        String _message = message;

        if (message != null) {
            if (this.stringCase == LOWER_CASE) {
                _message = _message.toLowerCase();

            } else if (this.stringCase == UPPER_CASE) {
                _message = _message.toUpperCase();
            }
        }

        return _message;
    }

    public boolean verify(String message) {
        // NO-OP

        return true;
    }
}
