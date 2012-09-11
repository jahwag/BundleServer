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
package com.bundleserver.samples.sampleclient.commands;

import com.bundleserver.samples.lobbyprotocol.clientcommands.LoginCommand;
import com.bundleserver.samples.sampleclient.clientinterfaces.ClientCommandWrapper;
import com.bundleserver.samples.sampleclient.clientinterfaces.CommandRegistrationFailureException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Sends the LOGIN Command.
 * @author Jahziah Wagner <jahziah[dot]wagner[at]gmail[dot]com>
 */
public class LoginClientCommand extends ClientCommandWrapper {

	public LoginClientCommand() throws CommandRegistrationFailureException {
		super(LoginCommand.class);
	}

	@Override
	public void execute(Socket connection, BufferedOutputStream os) throws IOException {
		os.write(3);
	}

	@Override
	public String getDescription() {
		return "LOGIN [username] - Login to lobby server.";
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
