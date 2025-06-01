package application;
import entidades.CodigoUnicoGenerator;
import entidades.Usuario;

public class Main {
    public static void main(String[] args){
        Usuario usuario = new Usuario("Leo", "leo@gmail.com" , "1");
        String codigo = CodigoUnicoGenerator.gerarCodigo(usuario.getIdentificador());
        System.out.println("CODIGO:" + codigo);
    }
}

