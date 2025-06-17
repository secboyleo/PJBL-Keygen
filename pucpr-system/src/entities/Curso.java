package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {
    private int id;
    private String nome;
    private ArrayList<Disciplina> disciplinasCurso;

    public Curso(String nome){
        this.nome = nome;
        this.disciplinasCurso = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome(){return this.nome;}

    public ArrayList<Disciplina> getDisciplinasCurso() {
        return disciplinasCurso;
    }

    public void adicionarDisciplina(Disciplina disciplina){
        if(!disciplinasCurso.contains(disciplina)){
            disciplinasCurso.add(disciplina);
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Curso: " + nome;
    }
}