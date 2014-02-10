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

package com.bundleserver.samples.sampleclient.client_api;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * A client-side only command which cannot communicate with the server.
 * @author Jahziah Wagner <jahziah[dot]wagner[at]gmail[dot]com>
 */
public abstract class ClientCommandWrapper implements CommandWrapper {

	@Override
	public final void execute(Socket connection, BufferedOutputStream os) throws IOException {
		execute();
	}

	protected abstract void execute();

	@Override
	public abstract String getDescription();

}
