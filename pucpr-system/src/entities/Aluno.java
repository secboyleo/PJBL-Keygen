package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Aluno extends Pessoa implements Serializable {
    private int matricula;
    private ArrayList<Curso> cursos;

    public Aluno(String nome, String sobrenome, String cpf, String dataNascimento){
        super(nome, sobrenome, cpf, dataNascimento);
        this.cursos = new ArrayList<>();
    }

    public int getMatricula(){ return this.matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }
    public ArrayList<Curso> getCursosMatriculados() { return this.cursos; }

    //adiciona no curso somente se ja nao estiver
    public void matricularEmCurso(Curso curso){
        if(!cursos.contains(curso)){
            cursos.add(curso);
        }
    }

    @Override
    public String exibirInformacoes() {
        return "Nome Aluno: " + nome + " " + getSobrenome() + "\nCPF: " + cpf + "\nData Nascimento:" + getDataNascimento() + "\nMatricula: " + matricula + "\nCursos:\n" + cursos;
    }
}
