package job;

import entities.Aluno;
import entities.Curso;
import entities.Disciplina;
import entities.Professor;

import java.util.ArrayList;

public class GerenciadorSistema {
    private ArrayList<Aluno> alunos;
    private ArrayList<Professor> professores;
    private ArrayList<Curso> cursos;
    private ArrayList<Disciplina> disciplinas;

    public GerenciadorSistema() {
        this.alunos = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
    }

    public void cadastrarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void cadastrarProfessor(Professor professor) {
        professores.add(professor);
    }

    public void criarCurso(Curso curso) {
        cursos.add(curso);
    }

    public void criarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public String buscarProfessor(String cpf) {
        for (Professor professor : professores) {
            if (professor.getCpf().equals(cpf)) {
                return professor.exibirInformacoes();
            }
        }

        return cpf;
    }

    public String buscaCurso(String codigo){
        for (Curso curso : cursos) {
            if (curso.getCodigo().equals(codigo)) {
                return "Curso:" + curso.getNome() + " | Codigo: " + curso.getCodigo();
            }
        }

        return codigo;
    }

    public String buscaDiscplina(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equals(codigo)) {
                return "Discplina:" + disciplina.getNome() + " | Codigo: " + disciplina.getCodigo();
            }
        }


        return codigo;
    }

}