package entidades;
import exception.ExcessaoGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//objetivo: gerar um codigo que tenha 16 caracteres e seja separado por hifen para
//usar na validar do software. Exemplo: AER4-3124-IREO-PODW

public class CodigoUnicoGenerator {
    public static String gerarCodigo(String identificadorUsuario) throws RuntimeException, NoSuchAlgorithmException, ExcessaoGenerator {

        try {
            //gera o hash sha256 do identificador
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(identificadorUsuario.getBytes());

            //converte o hash para hexadecimal ou base36(oq for mais curto)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(Integer.toString((hash[i] & 0xFF) | 0x100, 36).substring(1).toUpperCase());
            }

            String base = sb.toString().replaceAll("[^A-Z0-9]", "").substring(0, 16);

            //retorna no formato esperado XXXX-XXXX-XXXX-XXXX
            return base.substring(0, 4) + "-" + sb.substring(4, 8) + "-" + sb.substring(8, 12) + "-" + sb.substring(12, 16);
        } catch (RuntimeException e) {
            throw new ExcessaoGenerator("Erro ao gerar código, RuntimeException");
        } catch (NoSuchAlgorithmException e) {
            throw new ExcessaoGenerator("Erro ao gerar código, NoSuchAlgorithmException");
        }
    }
}

