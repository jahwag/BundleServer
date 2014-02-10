package com.bundleserver.samples.sampleclient;

import com.bundleserver.samples.sampleclient.console.ClientConsoleApplication;
import com.bundleserver.samples.sampleclient.console.NetworkConsoleException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

	public static void main(String[] args) {
		boolean DEBUG_MODE_ON = true;//args.length > 1 && args[1] != null ? Boolean.valueOf(args[1]) : false;

		try {
			ClientConsoleApplication clientApplication = new ClientConsoleApplication();
		} catch (NetworkConsoleException | IOException ex) {
			if (DEBUG_MODE_ON) {
				Logger.getLogger(App.class.getName()).
					   log(Level.SEVERE, null, ex);
			} else {
				System.err.println(ex.getMessage());
			}
		}
	}
}
