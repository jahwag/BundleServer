package com.bundleserver.samples.sampleclient.console;

import com.bundleserver.samples.sampleclient.clientinterfaces.AbstractClient;
import com.bundleserver.samples.sampleclient.commands.HelpClientCommand;
import com.bundleserver.samples.sampleclient.commands.LoginClientCommand;
import com.bundleserver.samples.sampleclient.clientinterfaces.ClientCommandWrapper;
import com.bundleserver.samples.sampleclient.clientinterfaces.CommandRegistrationFailureException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkConsole extends AbstractClient {

	private final Socket connection;
	private final BufferedOutputStream os;

	public NetworkConsole() throws NetworkConsoleException {
		try {
			connection = new Socket("localhost", 9181);
			os = new BufferedOutputStream(connection.getOutputStream());
			System.out.println("Type 'help' to list available commands.");
		} catch (Exception ex) {
			throw new NetworkConsoleException("Unable to connect to server.", ex);
		}
	}

	public void execute(String input) throws IOException {
		String cmd = input.split(" ")[0];
		ClientCommandWrapper commandWrapper = getRegisteredCommands().
			   get(cmd.toUpperCase());

		if (commandWrapper != null) {
			commandWrapper.execute(connection, os);
			os.write("\n".getBytes());
			os.flush();
		} else {
			echo("Unknown command");
		}
	}

	public void echo(String text) {
		System.out.println(">"+text);
	}

	@Override
	public Map<String, ClientCommandWrapper> register(Map<String, ClientCommandWrapper> commands) {
		try {
			commands.put("LOGIN", new LoginClientCommand());
			commands.put("HELP", new HelpClientCommand(commands.values()));
		} catch (CommandRegistrationFailureException ex) {
			Logger.getLogger(NetworkConsole.class.getName()).
				   log(Level.SEVERE, null, ex);
		}

		return commands;
	}
}
