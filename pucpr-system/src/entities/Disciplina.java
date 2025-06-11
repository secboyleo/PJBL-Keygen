package entities;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private String nome;
    private String codigo;
    private int cargaHoraria;

    public Disciplina(String nome, String codigo, int cargaHoraria){
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome(){return this.nome;}
    public String getCodigo(){return this.codigo;}
    public int getCargaHoraria() {return cargaHoraria;}

    @Override
    public String toString() { return "Disciplina: " + nome + " | Código: " + codigo; }
}
