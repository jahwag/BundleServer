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
import com.bundleserver.bundleservercommons.core.CommandId;
import com.bundleserver.bundleservercommons.core.RawCommand;
import com.bundleserver.bundleservercommons.processors.ClientCommandProcessor;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.osgi.service.log.LogService;

public class ClientCommandProcessorRepository {

	private HashMap<CommandId, List<ClientCommandProcessor>> processorCollection;
	private final LogService logging;

	public ClientCommandProcessorRepository(LogService logging) {
		this.processorCollection = new HashMap<CommandId, List<ClientCommandProcessor>>();
		this.logging = logging;
	}

	public void register(ClientCommandProcessor processor) {
		for (CommandId commandId : processor.getAssociatedIds()) {
			List<ClientCommandProcessor> targetList;

			if (!processorCollection.containsKey(commandId)) {
				targetList = new LinkedList<ClientCommandProcessor>();
				processorCollection.put(commandId, targetList);
			} else {
				targetList = processorCollection.get(commandId);
			}

			targetList.add(processor);

			logging
				   .log(LogService.LOG_INFO, "Registered ClientCommandProcessor: " + processor
				   .getClass().
				   getName());
		}
	}

	public void unregister(ClientCommandProcessor processor) {
		for (CommandId commandId : processor.getAssociatedIds()) {
			List<ClientCommandProcessor> targetList = processorCollection.
				   remove(commandId);

			// Clean up processor collection to save space
			if (targetList.isEmpty()) {
				processorCollection.remove(commandId);
			}

			logging
				   .log(LogService.LOG_INFO, "Unregistered ClientCommandProcessor: " + processor
				   .getClass().
				   getName());
		}
	}

	public void processCommand(RawCommand commandToProcess, BufferedWriter out) {
		final List<ClientCommandProcessor> commandProcessors = processorCollection
			   .get(commandToProcess.getCommand());

		if (commandProcessors == null) {
			logging
				   .log(LogService.LOG_DEBUG, "Received an invalid command from client.");
			new InvalidClientCommandAction(commandToProcess).execute(out);
			return;
		}

		for (ClientCommandProcessor processor : commandProcessors) {
			PostClientCommandProcessingAction postProcessAction = processor.
				   processCommand(commandToProcess);

			try {
				postProcessAction.execute(out);logging
				   .log(LogService.LOG_DEBUG, "Processed message: " + commandToProcess
				   .getMessage());
			} catch (IOException ex) {
				logging
				   .log(LogService.LOG_ERROR, "Failed to process message: " + commandToProcess
				   .getMessage());
			}


		}
	}

	public boolean isEmpty() {
		return processorCollection.isEmpty();
	}
}
