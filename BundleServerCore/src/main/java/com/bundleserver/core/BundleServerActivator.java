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
package com.bundleserver.core;

import com.bundleserver.BundleServer;
import com.bundleserver.bundleservercommons.processors.ClientCommandProcessor;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

public class BundleServerActivator extends DependencyActivatorBase {

	public void init(BundleContext context, DependencyManager manager) throws Exception {
		manager.add(createComponent().
			   setInterface(BundleServer.class.getName(), null).
			   setImplementation(BundleServerImpl.class).

			   add(createServiceDependency().
			   setService(LogService.class).
			   setRequired(true)).

			   add(createServiceDependency().
			   setService(LogReaderService.class).
			   setRequired(true)).

			   add(createServiceDependency().
			   setService(ClientCommandProcessor.class).
			   setRequired(false).
			   setCallbacks("added", "removed")));
	}

	public void destroy(BundleContext context, DependencyManager manager) throws Exception {
	}
}