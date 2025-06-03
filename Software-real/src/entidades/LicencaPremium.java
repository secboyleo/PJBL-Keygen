package entidades;

import java.time.LocalDate;

public class LicencaPremium extends LicencaBase{
    public LicencaPremium(String codigo, Usuario usuario, LocalDate dataEmissao, LocalDate dataExpiracao){
        super(codigo, usuario, dataEmissao, dataExpiracao);
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
