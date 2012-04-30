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
package org.productivity.java.syslog4j.server.impl.event.printstream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class FileSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
    protected static PrintStream createPrintStream(String fileName, boolean append) throws IOException {
        File file = new File(fileName);

        OutputStream os = new FileOutputStream(file,append);

        PrintStream printStream = new PrintStream(os);

        return printStream;
    }

    public FileSyslogServerEventHandler(String fileName) throws IOException {
        super(createPrintStream(fileName,true));
    }

    public FileSyslogServerEventHandler(String fileName, boolean append) throws IOException {
        super(createPrintStream(fileName,append));
    }
}
