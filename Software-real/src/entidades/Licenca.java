package entidades;
import java.time.LocalDate;

public class Licenca {
    private String codigo;
    private Usuario usuario;
    private LocalDate dataEmissao;
    private LocalDate dataExpiracao;
    private boolean ativa;

    public Licenca(String codigo, Usuario usuario, LocalDate dataEmissao, LocalDate dataExpiracao) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.dataEmissao = dataEmissao;
        this.dataExpiracao = dataExpiracao;
        this.ativa = true; //por padrão, uma licença nova é ativa
    }

    public String getCodigo() {
        return codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public LocalDate getDataExpiracao() {
        return dataExpiracao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    //metodo que muda o estado da licenca
    public void setAtiva(boolean ativa){
        this.ativa = ativa;
    }

    /**
     * Verifica se a licença é válida no momento atual.
     * Uma licença é válida se estiver ativa e não tiver expirado.
     * @return true se a licença for válida, false caso contrário.
     */
    public boolean verificarValidade() {
        if (!ativa) {
            return false; // Se a licença não estiver ativa, não é válida
        }
        LocalDate hoje = LocalDate.now();
        // Verifica se a data de expiração é posterior ou igual à data de hoje
        return !dataExpiracao.isBefore(hoje);
    }

    /**
     * Desativa a licença.
     */
    public void desativarLicenca() {
        this.ativa = false;
        System.out.println("Licença " + this.codigo + " desativada.");
    }

    /**
     * Ativa a licença, se ela não estiver expirada.
     * @return true se a licença foi ativada, false caso contrário (se estiver expirada).
     */
    public boolean ativarLicenca() {
        if (verificarValidade()) { // Só pode ativar se ainda for válida (não expirada)
            this.ativa = true;
            System.out.println("Licença " + this.codigo + " ativada.");
            return true;
        } else {
            System.out.println("Não foi possível ativar a licença " + this.codigo + ". Ela está expirada ou inválida.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Licenca [Código=" + codigo + ", Usuário=" + usuario.getIdentificador() +
                ", Emissão=" + dataEmissao + ", Expiração=" + dataExpiracao +
                ", Ativa=" + ativa + "]";
    }

}
