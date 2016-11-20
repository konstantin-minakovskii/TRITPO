package cs;

public class RunServer implements Runnable {

	private Server server = null;
	private Game game = null;
	int client = -1;

	public RunServer(Server server, int client, Game game) {
		this.server = server;
		this.game = game;
		this.client = client;
	}

	public void run() {
		while (server.getObjInputs().get(client) != null) {
			Object o = server.getNextObject(client);
			if (o instanceof Game) {
				this.game = (Game) o;
				server.sendObject(o);
			} else {
				game.resetConnections();
			}
		}
	}
}
