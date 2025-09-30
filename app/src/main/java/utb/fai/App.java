package utb.fai;

import java.io.IOException;

public class App {

    public static void main(String[] args) {

        // Must have at least 5 arguments to continue
        if (args.length < 5) {
            System.err.println("Usage: java -jar app.jar <host> <port> <from> <to> <subject> <text>");
            return;
        }
        
        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String from = args[2];
            String to = args[3];
            String subject = args[4];

            StringBuilder textBuilder = new StringBuilder();

            for (int i = 5; i < args.length; i++) {
                textBuilder.append(args[i]);
                if (i != args.length - 1) textBuilder.append(" ");
            }
            
            String text = textBuilder.toString();

            EmailSender sender = new EmailSender(host, port);
            sender.send(from, to, subject, text);
            sender.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
