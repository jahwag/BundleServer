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
package com.bundleserver.samples.sampleclient.clientinterfaces;

import com.bundleserver.bundleservercommons.processors.ClientCommandProcessor;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class ClientCommandWrapper {

	private final ClientCommandProcessor commandProcessor;

	/**
	 * Dummy Command constructor, for client-side command.
	 * @throws CommandRegistrationFailureException
	 */
	public ClientCommandWrapper() throws CommandRegistrationFailureException {
		commandProcessor = null;
	}

	public ClientCommandWrapper(Class<? extends ClientCommandProcessor> clazz) throws CommandRegistrationFailureException {
		try {
			commandProcessor = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new CommandRegistrationFailureException(ex);
		}
	}

	public ClientCommandProcessor getCommandProcessor() {
		return commandProcessor;
	}

	public abstract void execute(Socket connection, BufferedOutputStream os) throws IOException;


	protected abstract String getDescription();

	@Override
	public abstract String toString();

}
