package entidades;

import java.time.LocalDate;

public class LicencaTrial extends LicencaBase{

    public LicencaTrial(String codigo, Usuario usuario, LocalDate dataEmissao){
        super(codigo, usuario,dataEmissao, dataEmissao.plusDays(3));
    }


    @Override
    public boolean verificarValidade(){
        if (!getAtiva()) {
            return false; // Se a licença não estiver ativa, não é válida
        }
        LocalDate hoje = LocalDate.now();
        // Verifica se a data de expiração é posterior ou igual à data de hoje
        return !getDataExpiracao().isBefore(hoje);
    }

    @Override
    public boolean ativarLicenca(){
        if (verificarValidade()) { // Só pode ativar se ainda for válida (não expirada)
            setAtiva(true);
            System.out.println("Licença " + getCodigo() + " ativada.");
            return true;
        } else {
            System.out.println("Não foi possível ativar a licença " + getCodigo() + ". Ela está expirada ou inválida.");
            return false;
        }
    }

    @Override
    public void desativarLicenca(){
        setAtiva(false);
        System.out.println("Licença " + getCodigo() + " desativada.");
    }
}
