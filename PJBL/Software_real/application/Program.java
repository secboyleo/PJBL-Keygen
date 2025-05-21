package Software_real.application;

import Software_real.entities.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Program {
    public static void main(String[] args) throws IOException {
        User user = new User("leo@gmail.com", "secboyleo", "photoshop");
        FileOutputStream fileOut = new FileOutputStream("teste.arquivo");
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        objOut.writeObject(user);
        objOut.close();
        fileOut.close();


    }
}
