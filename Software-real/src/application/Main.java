package application;
import entidades.CodigoUnicoGenerator;
import entidades.Usuario;
import entidades.Licenca; // Importe a nova classe
import exception.ExcessaoGenerator;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate; // Importe LocalDate

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, ExcessaoGenerator {
        Usuario usuario = new Usuario("Leo", "leo@gmail.com", "1");
        String codigoGerado = null;
        try {
            codigoGerado = CodigoUnicoGenerator.gerarCodigo(usuario.getIdentificador());
            System.out.println("CODIGO GERADO: " + codigoGerado);
        } catch (ExcessaoGenerator e) {
            e.getMessage();
        }

        // Criando uma licença para o usuário
        // A licença é válida por 1 ano a partir de hoje
        LocalDate dataEmissao = LocalDate.now();
        LocalDate dataExpiracao = dataEmissao.plusYears(1); // Licença válida por 1 ano

        Licenca licencaDoLeo = new Licenca(codigoGerado, usuario, dataEmissao, dataExpiracao);

        System.out.println("STATUS DA LICENÇA:");
        System.out.println(licencaDoLeo.toString());
        System.out.println("Licença válida? " + licencaDoLeo.verificarValidade());

        // Exemplo de desativação e reativação
//        licencaDoLeo.desativarLicenca();
//        System.out.println("Licença válida após desativação? " + licencaDoLeo.verificarValidade());
//
//        licencaDoLeo.ativarLicenca();
//        System.out.println("Licença válida após reativação? " + licencaDoLeo.verificarValidade());
    }
}