package entities;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    protected String nome;
    protected String sobrenome;
    protected String cpf;
    protected String dataNascimento;

    public Pessoa(String nome, String sobrenome, String cpf, String dataNascimento){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNome(){return this.nome; }
    public String getSobrenome(){return this.sobrenome;}
    public String getCpf(){return this.cpf;}
    public String getDataNascimento(){return this.dataNascimento;}

    public abstract String exibirInformacoes();
}
