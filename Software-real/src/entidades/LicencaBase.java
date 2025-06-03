package entidades;

import java.time.LocalDate;

public abstract class LicencaBase {
    private String codigo;
    private Usuario usuario;
    private LocalDate dataEmissao;
    private LocalDate dataExpiracao;
    private boolean ativa; //premium ou trial

    public LicencaBase(String codigo, Usuario usuario, LocalDate dataEmissao, LocalDate dataExpiracao) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.dataEmissao = dataEmissao;
        this.dataExpiracao = dataExpiracao;
        this.ativa = true;
    }

    public abstract boolean verificarValidade();
    public abstract boolean ativarLicenca();
    public abstract void desativarLicenca();
}
