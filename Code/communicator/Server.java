package communicator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import util.Constants;
import board.Game;

public class Server implements Communicator {

	ServerSocket broadcastSocket = null;
	ServerSocket serverSocket = null;

	ArrayList<Socket> clientSockets = null;
	ArrayList<ObjectOutputStream> objOutputs = null;
	ArrayList<ObjectInputStream> objInputs = null;

	private Game game = null;
	private int writeCount = 0;

	public Server() {
		try {
			serverSocket = new ServerSocket(Constants.PORT);
			clientSockets = new ArrayList<Socket>();
			objOutputs = new ArrayList<ObjectOutputStream>();
			objInputs = new ArrayList<ObjectInputStream>();

			new Thread(new Accepter()).start();
			broadcastSocket = new ServerSocket(Constants.BROADCAST_PORT);

			new Thread(new Broadcast()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object getNextObject(int client) {
		Object ret = null;
		try {
			ret = objInputs.get(client).readUnshared();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Connection lost: server.");
			removeFromClients(client);
			e.printStackTrace();
		}
		System.out.println("object get");
		return ret;
	}

	public synchronized void sendObject(Object o) {
		for (int i = 0; i < objOutputs.size(); i++)
			sendObject(o, i);
		if (writeCount > 1000)
			reset();
		writeCount++;
	}

	public synchronized void sendObject(Object o, int client) {
		if (objOutputs.get(client) != null) {
			try {
				objOutputs.get(client).writeUnshared(o);
				if (writeCount > 1000)
					reset();
				writeCount++;
			} catch (IOException e) {
				System.err.println("Connection lost: server.");
				removeFromClients(client);
				e.printStackTrace();
			}
		}
	}

	public synchronized void reset() {
		for (int i = 0; i < objOutputs.size(); i++) {
			try {
				if (objOutputs.get(i) != null) {
					objOutputs.get(i).reset();
					writeCount = 0;
				}
			} catch (IOException e) {
				System.err
						.println("Reset is broken.");
				e.printStackTrace();
			}
		}
	}

	private void removeFromClients(int client) {
		objOutputs.set(client, null);
		objInputs.set(client, null);
		clientSockets.set(client, null);
	}

	public void close() {
		try {
			for (int i = 0; i < clientSockets.size(); i++) {
				objOutputs.get(i).close();
				objInputs.get(i).close();
				clientSockets.get(i).close();
				removeFromClients(i);
			}
			serverSocket.close();
		} catch (IOException e) {
			System.err
					.println("Sockets failed to close.");
			e.printStackTrace();
		}
	}

	public void cleanUp() {
		int size = clientSockets.size();
		for (int i = 0; i < size; i++) {
			if (clientSockets.get(i) == null) {
				objOutputs.remove(i);
				objInputs.remove(i);
				clientSockets.remove(i);
				size--;
				i--;
			}
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getNumClients() {
		return clientSockets.size();
	}

	public ArrayList<ObjectInputStream> getObjInputs() {
		return objInputs;
	}

	class Broadcast implements Runnable {
		public void run() {
			while (true) {
				try {
					broadcastSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Accepter implements Runnable {
		public void run() {
			System.out.println("Starting...");
			int numClients = 0;
			while (true) {
				try {
					System.out.println("\nWaiting for Client!");
					Socket client = serverSocket.accept();
					ObjectInputStream in = new ObjectInputStream(
							client.getInputStream());
					ObjectOutputStream out = new ObjectOutputStream(
							client.getOutputStream());
					clientSockets.add(client);
					objInputs.add(in);
					objOutputs.add(out);

					System.out.println("Client " + numClients + " connected.");
					numClients++;
					// TODO Add prompts for connections
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
