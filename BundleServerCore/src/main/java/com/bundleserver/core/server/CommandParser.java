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

import com.bundleserver.bundleservercommons.core.CommandId;
import com.bundleserver.bundleservercommons.core.RawCommand;

public class CommandParser {

	public static RawCommand parse(String message) {
		if (message == null) {
			return null;
		}

		// Split by spaces
		String[] split = message.split("");

		if (split.length == 0) {
			return null;
		}

		// First index should be a commandId
		String commandId = split[0];

		try {
			return new RawCommand(new CommandId(commandId), split);
		} catch (Exception ex) {
			return null;
		}
	}
}
