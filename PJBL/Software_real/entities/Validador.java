package Software_real.entities;


//essa classe vai validar a chave de ativacao do programa que sera inserido, por exemplo:
//caso seja inserido o codigo 000-0IK-90L-PPP
//ele vai ter que validar que esse codigo e realmente uma chave de ativacao

//tem que fazer uma logica que relacione essa validacao com o token gerado pelo usuario
//token do usuario (email + nickname + product) = gera um token para ativar o programa;

//ele vai buscar essas infomacao de token valido dentro de um arquivo txt ou csv?

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ExemploMessageDigest {
    public static void main(String[] args) {
        try {
            // O texto que você quer hashear
            String texto = "Keygen";

            // Obtendo uma instância do algoritmo SHA-256
            MessageDigest tipodehash = MessageDigest.getInstance("SHA-256");

            // Gerando o hash
            byte[] hashBytes = tipodehash.digest(texto.getBytes()); // Corrigido o nome do método

            // Convertendo o hash para uma string hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            // Exibindo o hash
            System.out.println("Hash SHA-256: " + hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            // Capturando a exceção caso o algoritmo não esteja disponível
            System.err.println("Algoritmo de hashing não encontrado: " + e.getMessage());
        }
    }
}




public class Validador {

    
}
