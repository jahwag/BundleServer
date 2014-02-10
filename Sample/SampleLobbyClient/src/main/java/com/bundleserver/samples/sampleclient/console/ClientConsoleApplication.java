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
package com.bundleserver.samples.sampleclient.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientConsoleApplication {

	public static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

	public ClientConsoleApplication() throws NetworkConsoleException, IOException {
		NetworkConsole networkConsole = new NetworkConsole();

		String text = null;

		do {
			text = BUFFERED_READER.readLine();

			networkConsole.execute(text);
		} while (!text.toUpperCase().equals("EXIT"));

	}
}
