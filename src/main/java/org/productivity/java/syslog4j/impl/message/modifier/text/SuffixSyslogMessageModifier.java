package org.productivity.java.syslog4j.impl.message.modifier.text;

import org.apache.commons.lang3.StringUtils;
import org.productivity.java.syslog4j.SyslogFacility;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogLevel;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* SuffixSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for adding static text to the end of a Syslog message.
*
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
*
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SuffixSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class SuffixSyslogMessageModifier implements SyslogMessageModifierIF {
    protected String suffix = null;
    protected String delimiter = " ";

    public SuffixSyslogMessageModifier() {
        //
    }

    public SuffixSyslogMessageModifier(String suffix) {
        this.suffix = suffix;
    }

    public SuffixSyslogMessageModifier(String suffix, String delimiter) {
        this.suffix = suffix;
        if (delimiter != null) {
            this.delimiter = delimiter;
        }
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String modify(SyslogIF syslog, SyslogFacility facility, SyslogLevel level, String message) {
        if (StringUtils.isBlank(this.suffix)) {
            return message;
        }

        return message + this.delimiter + this.suffix;
    }

    public boolean verify(String message) {
        // NO-OP

        return true;
    }
}
