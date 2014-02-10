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

import com.bundleserver.BundleServer;
import com.bundleserver.core.server.ClientCommandProcessorRepository;
import com.bundleserver.bundleservercommons.processors.ClientCommandProcessor;
import java.io.IOException;
import java.net.ServerSocket;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

public class BundleServerImpl implements BundleServer {

	private volatile LogReaderService logReaderService;
	private volatile LogService logging;
	private ServerSocket listenSocket;
	private ClientCommandProcessorRepository clientCommandProcessors;

	public void init() {
		logReaderService.addLogListener(new LogHandler());
		try {
			listenSocket = new ServerSocket(9181);
			clientCommandProcessors = new ClientCommandProcessorRepository(logging);
		} catch (IOException ex) {
			logging.log(LogService.LOG_ERROR, "Socket already in use.");
			System.exit(1);
		}
	}

	public void start() {
		logging.log(LogService.LOG_INFO, "Attempting to start server.");
		long start = System.currentTimeMillis();

		new Thread(new ListenTask(clientCommandProcessors, listenSocket, logging)).
			   start();

		logging.
			   log(LogService.LOG_INFO, "Started server successfully on " + listenSocket.getInetAddress().getHostAddress() + ":" + listenSocket.getLocalPort() + " in " + (System.
			   currentTimeMillis() - start) + " ms.");
	}

	public void stop() {
		try {
			listenSocket.close();
			logging.log(LogService.LOG_INFO, "Terminating server.");
		} catch (IOException ex) {
			logging.log(LogService.LOG_ERROR, ex.toString());
		}
	}

	public void added(ClientCommandProcessor processor) {
		clientCommandProcessors.register(processor);
	}

	public void removed(ClientCommandProcessor processor) {
		clientCommandProcessors.unregister(processor);
	}
}
