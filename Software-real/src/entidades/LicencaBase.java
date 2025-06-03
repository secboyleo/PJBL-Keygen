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

    @Override
    public String toString() {
        return "Licenca [Código=" + codigo + ", Usuário=" + usuario.getIdentificador() +
                ", Emissão=" + dataEmissao + ", Expiração=" + dataExpiracao +
                ", Ativa=" + ativa + "]";
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

    public boolean getAtiva(){
        return ativa;
    }

    public boolean isAtiva() {
        return ativa;
    }

    //metodo que muda o estado da licenca
    public void setAtiva(boolean ativa){
        this.ativa = ativa;
    }
}
