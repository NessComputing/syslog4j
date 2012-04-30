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
package org.productivity.java.syslog4j.test.util;

import org.productivity.java.syslog4j.util.OSDetectUtility;

import junit.framework.TestCase;

public class OSDetectUtilityTest extends TestCase {
    public void testOSDetectUtility() {
        boolean unix = OSDetectUtility.isUnix();
        boolean windows = OSDetectUtility.isWindows();

        assertTrue(unix ^ windows);
    }
}
