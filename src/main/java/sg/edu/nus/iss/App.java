package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public final class App 
{
    private App() {

    }

    public static void main( String[] args ) throws UnknownHostException ,IOException
    {
        // variable to store keyboard input and socket returnvalue
        String keyInput = "", msgRc = "";

        // using console to receive input from keyboard
        Console cons = System.console();

        // open a socket to connect to srver on port 1234
        Socket sock = new Socket("localhost", 1234);

        try (OutputStream os = sock.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            try (InputStream is = sock.getInputStream()) {
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                while (keyInput.equalsIgnoreCase("quit")) {
                    keyInput = cons.readLine("Enter guess number");
                    dos.writeUTF(keyInput);
                    dos.flush();

                    msgRc = dis.readUTF();
                    System.out.println("From server: " + msgRc);
                }

                bos.close();
                dos.close();
                sock.close();

            } catch (EOFException e) {
                e.printStackTrace();
                sock.close();
            }
        } catch (EOFException e) {
            e.printStackTrace();
            sock.close();
        }
    }  
}
