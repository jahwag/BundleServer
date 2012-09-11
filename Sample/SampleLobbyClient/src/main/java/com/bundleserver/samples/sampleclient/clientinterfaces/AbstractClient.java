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

import com.bundleserver.samples.sampleclient.clientinterfaces.ClientCommandWrapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractClient {

	private final Map<String, ClientCommandWrapper> registeredCommands = register(new HashMap<String, ClientCommandWrapper>());

	/**
	 * Register supported client commands.
	 * <p/>
	 * @param commands
	 * @return all commands to be made available.
	 */
	public abstract Map<String, ClientCommandWrapper> register(Map<String, ClientCommandWrapper> commands);

	/**
	 * Not available when register() is called.
	 * @return immutable collection of commands available.
	 */
	public Map<String, ClientCommandWrapper> getRegisteredCommands() {
		return Collections.unmodifiableMap(registeredCommands);
	}

}