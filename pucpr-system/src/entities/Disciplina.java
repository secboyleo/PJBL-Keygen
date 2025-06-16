package entities;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private int id;
    private String nome;
    private String codigo; // O código ainda existe, mas será gerenciado internamente
    private int cargaHoraria;

    // CONSTRUTOR MODIFICADO: não recebe mais o código
    public Disciplina(String nome, int cargaHoraria){
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome(){return this.nome;}

    public String getCodigo(){return this.codigo;}
    public void setCodigo(String codigo) { this.codigo = codigo; } // Setter para o código

    public int getCargaHoraria() {return cargaHoraria;}

    @Override
    public String toString() {
        return "ID: " + id + " | Disciplina: " + nome;
    }
}