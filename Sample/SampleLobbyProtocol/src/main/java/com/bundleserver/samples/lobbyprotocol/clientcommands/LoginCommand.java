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
package com.bundleserver.samples.lobbyprotocol.clientcommands;

import com.bundleserver.bundleservercommons.actions.PostClientCommandProcessingAction;
import com.bundleserver.bundleservercommons.core.CommandId;
import com.bundleserver.bundleservercommons.core.RawCommand;
import com.bundleserver.bundleservercommons.processors.ClientCommandProcessor;
import java.io.BufferedWriter;
import java.io.IOException;
import org.osgi.service.log.LogService;

public class LoginCommand extends ClientCommandProcessor {

	private final CommandId[] associatedIds = new CommandId[]{new CommandId(3)};

	public PostClientCommandProcessingAction processCommand(RawCommand cmnd) {
		getLogger()
			   .log(LogService.LOG_INFO, "Executing Client Command" + getClass()
			   .getSimpleName());

		return new PostClientCommandProcessingAction(cmnd) {

			@Override
			public void execute(BufferedWriter outputStream) throws IOException {
				outputStream.append("Reply: Success!");
				outputStream.flush();
			}
		};
	}

	@Override
	public CommandId[] getAssociatedIds() {
		return associatedIds;
	}


}
