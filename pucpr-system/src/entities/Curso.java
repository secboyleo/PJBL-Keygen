package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {
    private String nome;
    private String codigo;
    private ArrayList<Disciplina> disciplinasCurso;

    public Curso(String nome, String codigo){
        this.nome = nome;
        this.codigo = codigo;
        this.disciplinasCurso = new ArrayList<>();
    }

    public String getNome(){return this.nome;}
    public String getCodigo(){return this.codigo;}
    public ArrayList<Disciplina> getDisciplinasCurso() {
        return disciplinasCurso;
    }

    public void adicionarDisciplina(Disciplina disciplina){
        if(!disciplinasCurso.contains(disciplina)){
            disciplinasCurso.add(disciplina);
        }
    }

    @Override
    public String toString() { return "Curso: " + nome + " | Código: " + codigo; }

//    public void matricularAluno(Aluno aluno){
//        aluno.matricularEmCurso();
//    }
}
