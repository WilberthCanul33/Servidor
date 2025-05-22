import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5050);
        ExecutorService pool = Executors.newCachedThreadPool();
        System.out.println("Servidor escuchando en el puerto 5050...");
        DatabaseManager.init();

        while (true) {
            Socket client = server.accept();
            pool.execute(() -> handleClient(client));
        }
    }

    private static void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request = in.readLine();
            if (request != null) {
                String[] parts = request.split(";");
                String cmd = parts[0];
                String user = parts[1];
                String pass = parts[2];

                if (cmd.equals("REGISTER")) {
                    boolean success = DatabaseManager.register(user, pass);
                    out.println(success ? "REGISTERED" : "ERROR:USER_EXISTS");
                } else if (cmd.equals("LOGIN")) {
                    boolean valid = DatabaseManager.login(user, pass);
                    out.println(valid ? "LOGIN_OK" : "ERROR:INVALID_CREDENTIALS");
                } else {
                    out.println("ERROR:UNKNOWN_COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}