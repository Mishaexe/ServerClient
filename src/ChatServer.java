import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static final int PORT = 2525;
    static final Set<Client> clients = ConcurrentHashMap.newKeySet();

    ServerSocket serverSocket;
    ChatServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }


    void sendAll(String message, Client sender) {
        for (Client client : clients) {
            if (client != sender) {
                client.receive("Client " + sender.getClientId() + " " + message);
            }
        }
    }
    public void run() {
        while (true) {
            System.out.println("Waiting");
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                clients.add(new Client(socket, this));

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}