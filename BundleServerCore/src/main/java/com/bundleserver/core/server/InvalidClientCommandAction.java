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
package com.bundleserver.core.server;

import com.bundleserver.bundleservercommons.actions.PostClientCommandProcessingAction;
import com.bundleserver.bundleservercommons.core.RawCommand;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used when no ClientCommandProcessor was associated with the received
 * commandId.
 * <p/>
 * Warns the client that they sent something that was wrong.
 */
public class InvalidClientCommandAction extends PostClientCommandProcessingAction {

	public InvalidClientCommandAction(RawCommand command) {
		super(command);
	}

	@Override
	public void execute(BufferedWriter outputStream) {
		try {
			outputStream.
				   write("Error: Unknown command '" + getCommand() + "'\n");
			outputStream.
				   flush();

		} catch (IOException ex) {
			// TODO replace with logging service
			Logger.getLogger(InvalidClientCommandAction.class.getName()).
				   log(Level.SEVERE, null, ex);
		}
	}
}
