package Software_real.application;

import Software_real.entities.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Read {
    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream("teste.arquivo");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Lê o objeto do arquivo e faz cast para User
            User user = (User) objIn.readObject();

            objIn.close();
            fileIn.close();

            // Exibe os dados do objeto
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getNickname());
            System.out.println("Software: " + user.getProduct());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}