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
package org.productivity.java.syslog4j.impl.message.modifier.escape;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* HTMLEntityEscapeSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that safely escapes HTML entity characters.
*
* <p>This modifier is useful for applications that display log content in browsers without
* properly escaping HTML characters.</p>
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: HTMLEntityEscapeSyslogMessageModifier.java,v 1.4 2010/10/28 05:10:57 cvs Exp $
*/
public class HTMLEntityEscapeSyslogMessageModifier implements SyslogMessageModifierIF {
    public static final SyslogMessageModifierIF createDefault() {
        return new HTMLEntityEscapeSyslogMessageModifier();
    }

    @Override
    public String modify(SyslogIF syslog, SyslogFacility facility, SyslogLevel level, String message) {
        if (StringUtils.isBlank(message)) {
            return message;
        }
        else {
            String escapedMessage = escapeHtml(message);
            return escapedMessage;
        }
    }

    public boolean verify(String message) {
        // NO-OP

        return true;
    }

    /**
     * escapeHtml(String) is based partly on the article posted here: http://www.owasp.org/index.php/How_to_perform_HTML_entity_encoding_in_Java
     * with the addition of common characters and modifications for Java 1.4 support.
     *
     * @param message
     * @return Returns a message where any HTML entity characters are escaped.
     */
    public static String escapeHtml(String message) {
        StringBuffer b = new StringBuffer(message.length());

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);

            if (ch == '<') {
                b.append("&lt;");
            } else if (ch == '>') {
               b.append("&gt;");
            } else if (ch == '"') {
               b.append("&quot;");
            } else if (ch == '\'') {
                b.append("&#39;");
            } else if (ch == '&') {
                b.append("&amp;");
            } else if (ch >= ' ' && ch <= '~') {
                b.append(ch);
            } else if (Character.isWhitespace(ch)) {
                b.append("&#").append((int) ch).append(";");
            } else if (Character.isISOControl(ch)) {
                continue; // Ignore character
            } else if (Character.isDefined(ch)) {
                b.append("&#").append((int) ch).append(";");
            }
        }

        return b.toString();
     }

}
