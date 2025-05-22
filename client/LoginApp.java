import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class LoginApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginApp::createUI);
    }

    static void createUI() {
        JFrame frame = new JFrame("Login Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        panel.add(new JLabel("Usuario:"));
        panel.add(userField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passField);

        JPanel buttons = new JPanel();
        buttons.add(login);
        buttons.add(register);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);

        login.addActionListener(e -> sendRequest("LOGIN", userField.getText(), new String(passField.getPassword())));
        register.addActionListener(e -> sendRequest("REGISTER", userField.getText(), new String(passField.getPassword())));

        frame.setVisible(true);
    }

    static void sendRequest(String command, String user, String pass) {
        try (Socket socket = new Socket("TU_IP_PUBLICA", 5050);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(command + ";" + user + ";" + pass);
            String response = in.readLine();
            JOptionPane.showMessageDialog(null, response);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor.");
        }
    }
}