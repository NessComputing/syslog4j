package org.productivity.java.syslog4j.impl.message.modifier;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogMessageModifierConfigIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

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
