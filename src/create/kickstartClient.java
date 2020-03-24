package create;

import java.io.IOException;
import java.net.Socket;

public class kickstartClient {
    /*
    v1.0
    This program is to be used to initialize the Server ClientHandler
    Not sure why it needs this but I have observed that the Clients only respond correctly with one "dummy client"
     */
    public static void main(String[] args) throws IOException {
        Socket kick = new Socket("192.168.2.229", 50); //connecting to Server to kickstart ClientHandler
    }
}
