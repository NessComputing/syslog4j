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
package org.productivity.java.syslog4j;

import org.apache.commons.lang3.StringUtils;

public enum SyslogLevel
{
    EMERGENCY(0), ALERT(1), CRITICAL(2), ERROR(3), WARN(4),
    NOTICE(5), INFO(6), DEBUG(7);

    private final int value;

    private SyslogLevel(final int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static final SyslogLevel forName(final String name)
    {
        for (SyslogLevel level : values()) {
            if (StringUtils.equalsIgnoreCase(level.name(), name)) {
                return level;
            }
        }
        return null;
    }

    public static final SyslogLevel forValue(final int value)
    {
        for (SyslogLevel level : values()) {
            if (level.getValue() == value) {
                return level;
            }
        }
        return null;
    }

}
