package application;
import entidades.CodigoUnicoGenerator;
import entidades.Usuario;
import entidades.Licenca; // Importe a nova classe
import exception.ExcessaoGenerator;
import exception.ExcessaoGerarArquivo;

import java.io.BufferedWriter;
import java.io.FileWriter;   
import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate; 

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, ExcessaoGenerator, ExcessaoGerarArquivo {
        Usuario usuario = new Usuario("Leo", "leo@gmail.com", "2");
        String codigoGerado = null;
        try {

            codigoGerado = CodigoUnicoGenerator.gerarCodigo(usuario.getIdentificador());
            System.out.println("CODIGO GERADO: " + codigoGerado);

            //salvar em txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("codigo_gerado.txt"))) {
                writer.write(codigoGerado);
                System.out.println("Código gerado salvo em codigo_gerado.txt");
            } catch (IOException e) {
                throw new ExcessaoGerarArquivo("Erro ao passar o hash!");
            }


        } catch (ExcessaoGenerator | ExcessaoGerarArquivo e) {
            System.err.println(e.getMessage());
        }

        // Criando uma licença para o usuário
        // A licença é válida por 1 ano a partir de hoje
        LocalDate dataEmissao = LocalDate.now();
        LocalDate dataExpiracao = dataEmissao.plusYears(1); // Licença válida por 1 ano

        // Somente cria a licença se o código foi gerado com sucesso
        if (codigoGerado != null) {
            Licenca licencaDoLeo = new Licenca(codigoGerado, usuario, dataEmissao, dataExpiracao);

            System.out.println("STATUS DA LICENÇA:");
            System.out.println(licencaDoLeo.toString());
            System.out.println("Licença válida? " + licencaDoLeo.verificarValidade());
            
        } else {
            System.out.println("Não foi possível criar a licença pois o código não foi gerado.");
        }
    }
}
