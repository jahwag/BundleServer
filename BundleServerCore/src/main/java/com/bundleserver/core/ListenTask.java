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

import com.bundleserver.core.server.ClientCommandProcessorRepository;
import com.bundleserver.core.session.ClientConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.osgi.service.log.LogService;

public class ListenTask implements Runnable {

	private LogService logging;
	private final ServerSocket listenSocket;
	private final ClientCommandProcessorRepository clientCommandProcessors;
	private ExecutorService threadPool = Executors.newCachedThreadPool();

	ListenTask(ClientCommandProcessorRepository clientCommandProcessors, ServerSocket listenSocket, LogService logging) {
		this.listenSocket = listenSocket;
		this.logging = logging;
		this.clientCommandProcessors = clientCommandProcessors;
	}

	public void run() {
		logging.
			   log(LogService.LOG_INFO, "Listening for incoming connections.");

		try {
			while (!listenSocket.isClosed()) {
				// Listen for incoming connection
				Socket clientSocket = listenSocket.accept();

				// Spawn new thread to handle this client
				threadPool.execute(new ClientConnection(clientCommandProcessors, clientSocket, logging));
			}
		} catch (IOException ex) {
			logging.log(LogService.LOG_ERROR, ex.toString());
		} // TODO close all lsitenSockets to kill those threads when this one is killed
	}
}
