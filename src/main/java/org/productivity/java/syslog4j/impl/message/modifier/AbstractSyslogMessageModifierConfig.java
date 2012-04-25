package org.productivity.java.syslog4j.impl.message.modifier;

import static org.productivity.java.syslog4j.SyslogConstants.SYSLOG_MESSAGE_MODIFIER_PREFIX_DEFAULT;
import static org.productivity.java.syslog4j.SyslogConstants.SYSLOG_MESSAGE_MODIFIER_SUFFIX_DEFAULT;

import java.nio.charset.Charset;

import org.productivity.java.syslog4j.SyslogMessageModifierConfigIF;

import com.google.common.base.Charsets;
/**
* AbstractSyslogMessageModifierConfig provides a base abstract implementation of the
* SyslogMessageModifierConfigIF.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogMessageModifierConfig.java,v 1.4 2010/10/28 05:10:57 cvs Exp $
*/
public abstract class AbstractSyslogMessageModifierConfig implements SyslogMessageModifierConfigIF
{
    private static final long serialVersionUID = 5036574188079124884L;

    protected String prefix = SYSLOG_MESSAGE_MODIFIER_PREFIX_DEFAULT;
    protected String suffix = SYSLOG_MESSAGE_MODIFIER_SUFFIX_DEFAULT;
    protected Charset charSet = Charsets.UTF_8;

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null) {
            this.prefix = "";

        } else {
            this.prefix = prefix;
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            this.suffix = "";

        } else {
            this.suffix = suffix;
        }
    }

    public Charset getCharSet() {
        return charSet;
    }

    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }
}
