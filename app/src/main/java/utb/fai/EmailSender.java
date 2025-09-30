package utb.fai;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmailSender {
    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(host, port);
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();

        System.out.println("Connected to server");
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException, InterruptedException {
        // EHLO
        sendCommand("EHLO " + from);
        readResponse();

        // MAIL FROM
        sendCommand("MAIL FROM:<" + from + ">");
        readResponse();

        // RCPT TO
        sendCommand("RCPT TO:<" + to + ">");
        readResponse();

        // DATA
        sendCommand("DATA");
        readResponse();

        // Headers + body
        StringBuilder data = new StringBuilder();
        data.append("Date: ").append(java.time.ZonedDateTime.now()).append("\r\n");
        data.append("From: ").append(from).append("\r\n");
        data.append("To: ").append(to).append("\r\n");
        data.append("Subject: ").append(subject).append("\r\n");
        data.append("\r\n");
        data.append(text).append("\r\n");
        data.append(".\r\n");

        byte[] buffer;
        buffer = data.toString().getBytes();
        output.write(buffer, 0, buffer.length);
        output.flush();

        final byte[] response = new byte[1024];
        int len;

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
        sendCommand("QUIT");
        readResponse();

        socket.close();
        System.out.println("Socket closed");
    }

    // Sending command on the server
    private void sendCommand(String command) throws IOException, InterruptedException {
        byte[] buffer;
        buffer = (command + "\r\n").getBytes();
        output.write(buffer, 0, buffer.length);
        output.flush();

        final byte[] response = new byte[1024];
        int len;

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }

    // Reading the response from the server
    private void readResponse() throws IOException, InterruptedException {
        final byte[] response = new byte[1024];
        int len;

        Thread.sleep(500);
        if (input.available() > 0) {
            len = input.read(response);
            System.out.write(response, 0, len);
        }
    }
}
