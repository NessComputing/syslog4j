package com.nesscomputing.syslog4j.impl.message.structured;

import org.junit.Assert;
import org.junit.Test;

public class TestMatchChar
{
    @Test
    public void testMatchChar() throws Exception
    {
        Assert.assertEquals(2, StructuredSyslogMessage.matchChar("hello", 0, 'l'));
        Assert.assertEquals(2, StructuredSyslogMessage.matchChar("hello", 2, 'l'));
        Assert.assertEquals(3, StructuredSyslogMessage.matchChar("hello", 3, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("hello", 4, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("hello", 10, 'l'));

        Assert.assertEquals(2, StructuredSyslogMessage.matchChar("\\ll", 0, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("\\l\\l", 0, 'l'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("\\", 0, 'x'));
        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("foo\\", 0, 'x'));

        Assert.assertEquals(-1, StructuredSyslogMessage.matchChar("this\\\"is\\ a\\ test.", 0, ' '));
    }

}
