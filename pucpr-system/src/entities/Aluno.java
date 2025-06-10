package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Aluno extends Pessoa implements Serializable {
    private String matricula;
    private ArrayList<Curso> cursos;

    public Aluno(String nome, String sobrenome, String cpf, String dataNascimento, String matricula){
        super(nome, sobrenome, cpf, dataNascimento);
        this.matricula = matricula;
        this.cursos = new ArrayList<>();
    }

    public String getMatricula(){ return this.matricula; }
    public ArrayList<Curso> getCursosMatriculados() { return this.cursos; }

    //adiciona no curso somente se ja nao estiver
    public void matricularEmCurso(Curso curso){
        if(!cursos.contains(curso)){
            cursos.add(curso);
        }
    }

    @Override
    public String exibirInformacoes() {
        return "Nome Aluno: " + nome + " " + sobrenome + "\nCPF: " + cpf + "\nData Nascimento:" + dataNascimento + "\nMatricula: " + matricula + "\nCursos:\n" + cursos;
    }
}
