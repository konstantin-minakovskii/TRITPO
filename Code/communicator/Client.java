package communicator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Constants;

public class Client implements Communicator {

	private final String HOST;
	private Socket socket = null;
	private ObjectOutputStream objOutput = null;
	private ObjectInputStream objInput = null;

	private int writeCount = 0;

	public Client(String ip) {
		HOST = ip;

		try {
			socket = new Socket(HOST, Constants.PORT);
			objOutput = new ObjectOutputStream(socket.getOutputStream());
			objInput = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Host " + HOST
					+ " not found.\nError code: There are no servers.");
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: "
					+ HOST + ".");
			System.exit(0);
		}
	}

	public Object getNextObject() {
		Object ret = null;
		try {
			ret = objInput.readUnshared();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Connection lost: client.");
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("object get");
		return ret;
	}

	public synchronized void sendObject(Object o) {
		try {
			objOutput.writeUnshared(o);
			if (writeCount > 1000)
				objOutput.reset();
			writeCount++;
		} catch (IOException e) {
			System.err.println("Connection lost: client.");
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			objOutput.close();
			objInput.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
