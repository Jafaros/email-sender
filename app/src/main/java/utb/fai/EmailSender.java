package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    private static Socket socket;

    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        try {
            socket = new Socket(host, port);
            
            if (socket.isConnected()) {
                System.out.println("Connected to server");
            }
        } catch (UnknownHostException e) {
            System.out.println("Host not found");
        }
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException, InterruptedException {
        byte[] buffer;
        byte[] response = new byte[1024];
        String message;
        int len;

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        
        message = "MAIL FROM:<" + from + ">\r\n";
        buffer = message.getBytes();
        output.write(buffer, 0, buffer.length);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
        
        message = "RCPT TO:<" + to + ">\r\n";
        buffer = message.getBytes();
        output.write(buffer, 0, buffer.length);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() throws IOException, InterruptedException {
        byte[] buffer;
        byte[] response = new byte[1024];
        String message;
        int len;

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        message = "QUIT\r\n";
        buffer = message.getBytes();
        output.write(buffer, 0, buffer.length);
        output.flush();

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }

        socket.close();
    }
}
