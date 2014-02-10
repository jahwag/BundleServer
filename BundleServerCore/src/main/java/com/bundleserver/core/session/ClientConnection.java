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
package com.bundleserver.core.session;

import com.bundleserver.core.server.ClientCommandProcessorRepository;
import com.bundleserver.bundleservercommons.core.CommandId;
import com.bundleserver.bundleservercommons.core.RawCommand;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.service.log.LogService;

/**
 *
 */
public class ClientConnection implements Runnable {

	private final Socket socket;
	private final BufferedWriter out;
	private final DataInputStream in;
	private final ClientCommandProcessorRepository clientCommandProcessors;
	private final LogService logging;

	public ClientConnection(ClientCommandProcessorRepository clientCommandProcessors, Socket clientSocket, LogService logging) throws IOException {
		this.clientCommandProcessors = clientCommandProcessors;
		this.socket = clientSocket;
		out = new BufferedWriter(new OutputStreamWriter(socket.
			   getOutputStream()));
		in = new DataInputStream(socket.getInputStream());
		this.logging = logging;
		socket.setSoTimeout(30000); // 30 second timeout
	}

	public void run() {
		logging
			   .log(LogService.LOG_INFO, "Client " + getClientIP() + " connected.");

		try {
			if (clientCommandProcessors.isEmpty()) {
				logging
					   .log(LogService.LOG_INFO, "Rejecting connection attempt from " + getClientIP() + " because no Client Command Processors are registered.");
				socket.close();
				return;
			}
		} catch (IOException ex) {
			Logger.getLogger(ClientConnection.class.getName()).
				   log(Level.SEVERE, null, ex);
		}


		try {
			while (!socket.isClosed()) {
				int commandId = in.readByte();//in.readUnsignedShort();

				String message = "";
				byte c;
				while ((c = in.readByte()) != '\n' && c != '\n') {
					message += (char) c;
				}

				RawCommand parsedCommand = new RawCommand(new CommandId(commandId), message
					   .split("\t"));

				// Process command
				if (parsedCommand != null) {
					clientCommandProcessors.
						   processCommand(parsedCommand, out);
					logging
						   .log(LogService.LOG_DEBUG, "Got message: " + parsedCommand.getCommand().getClass().getSimpleName() + " ("+parsedCommand.getClass().getSimpleName()+").");
				} else {
					logging
						   .log(LogService.LOG_INFO, "Invalid data received. ");
				}
			}
		} catch (IOException ex) {
			logging
				   .log(LogService.LOG_INFO, "Client " + getClientIP() + " disconnected. Freeing thread " + Thread
				   .currentThread().getId()+".");
		}
	}

	private String getClientIP() {
		return socket.getInetAddress().getHostAddress()+":"+getClientPort();
	}

	private int getClientPort() {
		return socket.getLocalPort();
	}
}
