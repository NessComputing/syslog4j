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

public enum SyslogFacility
{
    kern(0), user(1), mail(2), daemon(3), auth(4), syslog(5),
    lpr(6), news(7), uucp(8), cron(9), authpriv(10), ftp(11),
    local0(16), local1(17), local2(18), local3(19), local4(20),
    local5(21), local6(22), local7(23);

    public static final SyslogFacility DEFAULT = user;

    private final int value;

    private SyslogFacility(final int value)
    {
        this.value = value << 3;
    }

    public int getValue()
    {
        return value;
    }

    public static final SyslogFacility forName(final String name)
    {
        for (SyslogFacility facility : values()) {
            if (StringUtils.equalsIgnoreCase(facility.name(), name)) {
                return facility;
            }
        }
        return null;
    }

    public static final SyslogFacility forValue(final int value)
    {
        for (SyslogFacility facility : values()) {
            if (facility.getValue() == value) {
                return facility;
            }
        }
        return null;
    }
}
