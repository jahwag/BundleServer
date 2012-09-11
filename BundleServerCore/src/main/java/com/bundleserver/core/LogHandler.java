/*
 * Copyright 2012 Jahziah Wagner <jahziah[dot]wagner[at]gmail[dot]com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bundleserver.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

public class LogHandler implements LogListener {

	private File logFile;
	private BufferedWriter os;

	public LogHandler() {
		try {
			logFile = new File("." + File.separator + "bundleserver.log");
			os = new BufferedWriter(new FileWriter(logFile));
		} catch (IOException ex) {
			Logger.getLogger(BundleServerImpl.class.getName()).
				   log(Level.SEVERE, null, ex);
		}
	}

	public void logged(LogEntry entry) {
		String time = new SimpleDateFormat("HH:mm:ss zzz").format(entry.
			   getTime());
		String std = "[" + entry.getBundle().
			   getSymbolicName() + "]: " + entry.getMessage();
		String msg = "";

		switch (entry.getLevel()) {
			case LogService.LOG_DEBUG:
				msg = time + " [DEBUG]" + std;
				break;
			case LogService.LOG_INFO:
				msg = time + " [INFO]" + std;
				break;
			case LogService.LOG_ERROR:
				msg = time + " [ERROR]" + std;
				entry.getException().
					   printStackTrace();
				break;
			case LogService.LOG_WARNING:
				msg = time + " [WARNING]" + std;
				entry.getException().
					   printStackTrace();
				break;
		}

		try {
			System.out.println(getClass().getSimpleName()+":" + msg);
			os.write(msg + "\n");
			os.flush();
		} catch (IOException ex) {
			Logger.getLogger(BundleServerImpl.class.getName()).
				   log(Level.SEVERE, null, ex);
		}
	}
}
