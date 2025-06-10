package application;

import entities.Aluno;
import entities.Curso;
import entities.Disciplina;
import entities.Professor;
import excessoes.AlunoNaoEncontrado;
import excessoes.CursoNaoEncontrado;
import excessoes.DiscplinaNaoEncontrada;
import excessoes.ProfessorNaoEncontrado;
import job.GerenciadorSistema;

public class MainTeste {
    public static void main(String[] args) {
        GerenciadorSistema sistema = new GerenciadorSistema();

        // 1. Cadastrar Alunos
        Aluno aluno1 = new Aluno("João", "Silva", "111.222.333-44", "01/01/2000", "2020001");
        Aluno aluno2 = new Aluno("Maria", "Souza", "555.666.777-88", "05/05/1999", "2019002");
        sistema.cadastrarAluno(aluno1);
        sistema.cadastrarAluno(aluno2);
        System.out.println("--- Alunos Cadastrados ---");

        // 2. Cadastrar Professores
        Professor professor1 = new Professor("Carlos", "Oliveira", "999.888.777-66", "10/10/1975", 5000.00);
        Professor professor2 = new Professor("Ana", "Pereira", "123.456.789-00", "12/12/1980", 6000.00);
        sistema.cadastrarProfessor(professor1);
        sistema.cadastrarProfessor(professor2);
        System.out.println("--- Professores Cadastrados ---");

        // 3. Criar Cursos
        Curso curso1 = new Curso("Engenharia de Software", "ES001");
        Curso curso2 = new Curso("Ciência da Computação", "CC001");
        sistema.criarCurso(curso1);
        sistema.criarCurso(curso2);
        System.out.println("--- Cursos Criados ---");


        // 4. Criar Disciplinas
        Disciplina disciplina1 = new Disciplina("Estruturas de Dados", "ED101", 60);
        Disciplina disciplina2 = new Disciplina("Programação Orientada a Objetos", "POO202", 80);
        Disciplina disciplina3 = new Disciplina("Banco de Dados", "BD303", 75);
        sistema.criarDisciplina(disciplina1);
        sistema.criarDisciplina(disciplina2);
        sistema.criarDisciplina(disciplina3);
        System.out.println("--- Disciplinas Criadas ---");

        System.out.println("\n--- Testando Buscas ---");

        // Teste de busca de Aluno
        try {
            System.out.println(sistema.buscarAluno("2020001"));
            System.out.println(sistema.buscarAluno("9999999")); // esse cabra nao existe
        } catch (AlunoNaoEncontrado e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }

        // Teste de busca de Professor
        try {
            System.out.println(sistema.buscarProfessor("999.888.777-66"));
            System.out.println(sistema.buscarProfessor("000.000.000-00")); // esse cabra nao existe
        } catch (ProfessorNaoEncontrado e) {
            System.out.println("Erro ao buscar professor: " + e.getMessage());
        }

        // Teste de busca de Curso
        try {
            System.out.println(sistema.buscaCurso("ES001"));
            System.out.println(sistema.buscaCurso("ADS001")); // esse cabra nao existe
        } catch (CursoNaoEncontrado e) {
            System.out.println("Erro ao buscar curso: " + e.getMessage());
        }

        // Teste de busca de Disciplina
        try {
            System.out.println(sistema.buscaDiscplina("POO202"));
            System.out.println(sistema.buscaDiscplina("RED404")); // Disciplina não existente
        } catch (DiscplinaNaoEncontrada e) {
            System.out.println("Erro ao buscar disciplina: " + e.getMessage());
        }
    }
}
