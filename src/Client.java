
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
     Socket socket;

    InputStream is;

    OutputStream os;


     int clientId;

    Scanner in;

    PrintWriter out;
     ChatServer server;
    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        this.clientId = socket.hashCode();
        new Thread(this).start();
    }

    public int getClientId() {
        return clientId;
    }

    void receive(String message) {
        if (out != null) {
            out.println(message);
        }
    }
    public void run() {
        try {
             is = socket.getInputStream();
             os = socket.getOutputStream();

             in = new Scanner(is);
             out = new PrintWriter(os, true);

             out.println("Welcome in chatServer");

             String input;
             while ((input = in.nextLine()) != null){
                 if ("bye".equalsIgnoreCase(input)){
                     break;
                 }
                 server.sendAll(input, this);
             }
             socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.clients.remove(this);
        System.out.println("Client disconnected: " + socket);
    }
}
