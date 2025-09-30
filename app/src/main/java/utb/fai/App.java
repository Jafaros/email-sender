package utb.fai;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing
        
        try {
            //EmailSender sender = new EmailSender("smtp.utb.cz", 25);
            EmailSender sender = new EmailSender("localhost", 9999);
            sender.send("p_grajciar@utb.cz", "p_grajciar@utb.cz", "Test", "Funguje to?\nSnad...");
            sender.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
