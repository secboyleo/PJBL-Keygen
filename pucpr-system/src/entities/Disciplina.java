package entities;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private int id;
    private String nome;
    private int cargaHoraria;

    public Disciplina(String nome, int cargaHoraria){
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome(){return this.nome;}

    public int getCargaHoraria() {return cargaHoraria;}

    @Override
    public String toString() {
        return "ID: " + id + " | Disciplina: " + nome;
    }
}