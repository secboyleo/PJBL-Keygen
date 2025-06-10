package job;

import entities.Aluno;
import entities.Curso;
import entities.Disciplina;
import entities.Professor;
import excessoes.AlunoNaoEncontrado;
import excessoes.CursoNaoEncontrado;
import excessoes.DiscplinaNaoEncontrada;
import excessoes.ProfessorNaoEncontrado;

import java.io.*;
import java.util.ArrayList;

public class GerenciadorSistema {
    private ArrayList<Aluno> alunos;
    private ArrayList<Professor> professores;
    private ArrayList<Curso> cursos;
    private ArrayList<Disciplina> disciplinas;

    public GerenciadorSistema(){
        this.alunos = new ArrayList<>();
        this.professores =  new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
    }

    public void cadastrarAluno(Aluno aluno){
        alunos.add(aluno);
    }

    public void cadastrarProfessor(Professor professor){
        professores.add(professor);
    }

    public void criarCurso(Curso curso){
        cursos.add(curso);
    }

    public void criarDisciplina(Disciplina disciplina){
        disciplinas.add(disciplina);
    }

    //busca aluno, professor curso e disciplinas---------------------------------------------------------------------
    public String buscarAluno(String matricula) throws AlunoNaoEncontrado {
        for(Aluno aluno : alunos){
            if(aluno.getMatricula().equals(matricula)){ return aluno.exibirInformacoes(); }
        }

        throw new AlunoNaoEncontrado("Aluno com matricula: " + matricula + ", nao foi encontrado");
    }

    public String buscarProfessor(String cpf) throws ProfessorNaoEncontrado {
        for(Professor professor : professores){
            if(professor.getCpf().equals(cpf)){ return professor.exibirInformacoes(); }
        }

        throw new ProfessorNaoEncontrado("Professor com cpf: " + cpf + ", nao foi encontrado");
    }

    public String buscaCurso(String codigo) throws CursoNaoEncontrado {
        for(Curso curso : cursos){
            if(curso.getCodigo().equals(codigo)){ return "Curso:" + curso.getNome() + " | Codigo: " + curso.getCodigo(); }
        }

        throw new CursoNaoEncontrado("Curso com codigo: " + codigo + ", nao foi encontrado");
    }

    public String buscaDiscplina(String codigo) throws DiscplinaNaoEncontrada {
        for(Disciplina disciplina : disciplinas){
            if(disciplina.getCodigo().equals(codigo)){ return "Discplina:" + disciplina.getNome() + " | Codigo: " + disciplina.getCodigo();}
        }

        throw new DiscplinaNaoEncontrada("Discplina com o codigo: " + codigo + ", nao encontrada");
    }

    //mostrar arrays-------------------------------------------------------------------------------------------------------------------
    public void mostrarAlunos(){
        System.out.println("-----------ALUNOS------------");
        for(Aluno aluno : alunos){
            System.out.println(aluno.exibirInformacoes());
            System.out.println();
        }

        System.out.println("----------------------------------");
    }

    public void mostrarProfessores(){
        System.out.println("-----------Professores------------");
        for(Professor professor : professores){
            System.out.println(professor.exibirInformacoes());
            System.out.println();
        }

        System.out.println("----------------------------------");
    }

    public void mostrarCursos(){
        System.out.println("-------------Cursos-------------");
        for(Curso curso : cursos){
            System.out.println(curso.toString());
            System.out.println();
        }
    }

    public void mostrarDisciplinas(){
        System.out.println("--------------Disciplinas----------------");
        for(Disciplina disciplina : disciplinas){
            System.out.println(disciplina.toString());
            System.out.println();
        }
    }

    //metodos get dos arrays (pode ser util mais para frente)
    public ArrayList<Aluno> getAlunos() { return this.alunos;}
    public ArrayList<Professor> getProfessores() {return professores;}
    public ArrayList<Curso> getCursos() {return cursos;}
    public ArrayList<Disciplina> getDisciplinas() {return disciplinas;}
}
