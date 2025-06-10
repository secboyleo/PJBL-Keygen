package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Professor extends Pessoa implements Serializable {
    private double salario;
    private ArrayList<Disciplina> disciplinas;

    public Professor(String nome, String sobrenome, String cpf, String dataNascimento, double salario){
        super(nome, sobrenome, cpf, dataNascimento);
        this.salario = salario;
        this.disciplinas = new ArrayList<>();
    }

    public double getSalario(){return this.salario; }

    public void adicionarDisciplina(Disciplina disciplina){
        if(!disciplinas.contains(disciplina)){
            disciplinas.add(disciplina);
        }
    }

    @Override
    public String exibirInformacoes() {
        return "Nome Professor: " + nome + " " + sobrenome + "\nCPF: " + cpf + "\nData Nascimento:" + dataNascimento + "\nSalario: " + salario + "\nDisciplinas:\n" + disciplinas;
    }

    @Override
    public String toString() { return "Professor: " + nome + " " + sobrenome + " | CPF: " + cpf; }
}
