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
