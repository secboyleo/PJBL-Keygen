package entidades;

import java.time.LocalDate;

public class Usuario {
    private String nome;
    private String email;
    private String identificador;
    private String id;
    private LocalDate dataCadastro;

    public Usuario(String nome, String email, String id){
        this.nome = nome;
        this.email = email;
        this.id = id;
        this.dataCadastro = LocalDate.now();
    }

    //o identificador e gerado pelo tamanho do nome + tamanho do email + email
    public String geraIdentificador(){return String.format("%d", nome.length()) + String.format("%d", email.length()) + email + id;}

    public String getIdentificador(){
        this.identificador = geraIdentificador();
        return this.identificador;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
}

//leo = 3
//leo@gmail.com = 13
//313leo@gmail.com
