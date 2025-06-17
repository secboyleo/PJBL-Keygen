package application;

import entities.Aluno;
import entities.Professor;
import entities.Curso;
import entities.Disciplina;
import excessoes.AlunoNaoEncontrado;
import excessoes.ProfessorNaoEncontrado;
import excessoes.CursoNaoEncontrado;
import excessoes.DiscplinaNaoEncontrada;
import job.GerenciadorSistema;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String DADOS_ARQUIVO = "escola.dat";

    public static void main(String[] args) {
        System.out.println("SISTEMA GERENCIAMENTO DE ESCOLA");
        Scanner sc = new Scanner(System.in);

        GerenciadorSistema gerenciadorSistema = new GerenciadorSistema();
        gerenciadorSistema.carregarDados(DADOS_ARQUIVO);

        boolean flag = true;
        while (flag){
            System.out.println("Selecione uma opçao:");
            System.out.println("[1] Alunos");
            System.out.println("[2] Professores");
            System.out.println("[3] Cursos");
            System.out.println("[4] Disciplinas");
            System.out.println("[5] Sair");
            System.out.print(">>>");
            int opcao = -1;
            try {
                opcao = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.next();
                System.out.println();
                continue;
            }


            System.out.println();
            if(opcao == 1){
                System.out.println("[1] Mostrar alunos");
                System.out.println("[2] Cadastrar aluno");
                System.out.println("[3] Buscar aluno por matrícula");
                System.out.println("[4] Matricular aluno em curso");
                System.out.print(">>>");
                try {
                    opcao = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    sc.next();
                    System.out.println();
                    continue;
                }


                if(opcao == 1){
                    if (gerenciadorSistema.getAlunos().isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado.");
                    } else {
                        gerenciadorSistema.mostrarAlunos();
                    }
                    System.out.println();

                } else if (opcao == 2) {
                    System.out.println("Cadastre o aluno");
                    System.out.print("Nome: ");
                    sc.nextLine();
                    String nome = sc.nextLine();

                    System.out.print("Sobrenome: ");
                    String sobrenome = sc.nextLine();

                    System.out.print("CPF (xxx.xxx.xxx-xx): ");
                    String cpf = sc.next();

                    System.out.print("Data Nascimento (dd/mm/yyyy): ");
                    String dataNascimento = sc.next();


                    Aluno aluno = new Aluno(nome, sobrenome, cpf, dataNascimento);
                    if (gerenciadorSistema.cadastrarAluno(aluno)) {
                        System.out.println("ALUNO CADASTRADO COM MATRICULA: " + aluno.getMatricula() + "!");
                        System.out.println(aluno.exibirInformacoes());
                    } else {
                        System.out.println("Erro: Já existe um aluno com este CPF.");
                    }
                    System.out.println();
                } else if (opcao == 3) {
                    System.out.print("Digite a matrícula do aluno para buscar: ");
                    int matriculaBusca = -1;
                    try {
                        matriculaBusca = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para matrícula. Por favor, digite um número inteiro.");
                        sc.next();
                        System.out.println();
                        continue;
                    }
                    try {
                        String infoAluno = gerenciadorSistema.buscarAluno(matriculaBusca);
                        System.out.println(infoAluno);
                    } catch (AlunoNaoEncontrado e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                } else if (opcao == 4) {
                    if (gerenciadorSistema.getAlunos().isEmpty() || gerenciadorSistema.getCursos().isEmpty()) {
                        System.out.println("É necessário ter alunos e cursos cadastrados para realizar esta operação.");
                        System.out.println();
                    } else {
                        System.out.print("Digite a matrícula do aluno: ");
                        int matriculaAluno = -1;
                        try {
                            matriculaAluno = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida para matrícula. Por favor, digite um número inteiro.");
                            sc.next();
                            System.out.println();
                            continue;
                        }
                        System.out.print("Digite o ID do curso para matricular: ");
                        int idCurso = -1;
                        try {
                            idCurso = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida para ID do curso. Por favor, digite um número inteiro.");
                            sc.next();
                            System.out.println();
                            continue;
                        }

                        Aluno alunoSelecionado = null;
                        for (Aluno a : gerenciadorSistema.getAlunos()) {
                            if (a.getMatricula() == matriculaAluno) {
                                alunoSelecionado = a;
                                break;
                            }
                        }

                        Curso cursoSelecionado = null;
                        for (Curso c : gerenciadorSistema.getCursos()) {
                            if (c.getId() == idCurso) {
                                cursoSelecionado = c;
                                break;
                            }
                        }

                        if (alunoSelecionado != null && cursoSelecionado != null) {
                            alunoSelecionado.matricularEmCurso(cursoSelecionado);
                            System.out.println("Aluno " + alunoSelecionado.getNome() + " matriculado no curso " + cursoSelecionado.getNome() + "!");
                        } else {
                            System.out.println("Aluno ou curso não encontrados. Verifique os dados informados.");
                        }
                        System.out.println();
                    }
                }
                else {
                    System.out.println("OPCAO INVALIDA");
                    System.out.println();
                }
            } else if(opcao == 2){
                System.out.println("[1] Mostrar professores");
                System.out.println("[2] Cadastrar professor");
                System.out.println("[3] Buscar professor por CPF");
                System.out.println("[4] Adicionar disciplina a um professor");
                System.out.print(">>>");
                try {
                    opcao = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    sc.next();
                    System.out.println();
                    continue;
                }

                if(opcao == 1){
                    if (gerenciadorSistema.getProfessores().isEmpty()) {
                        System.out.println("Nenhum professor cadastrado.");
                    } else {
                        gerenciadorSistema.mostrarProfessores();
                    }
                    System.out.println();
                } else if (opcao == 2) {
                    System.out.println("Cadastre o professor");
                    System.out.print("Nome: ");
                    sc.nextLine();
                    String nome = sc.nextLine();

                    System.out.print("Sobrenome: ");
                    String sobrenome = sc.nextLine();

                    System.out.print("CPF (xxx.xxx.xxx-xx): ");
                    String cpf = sc.next();

                    System.out.print("Data Nascimento (dd/mm/yyyy): ");
                    String dataNascimento = sc.next();

                    System.out.print("Salario: ");
                    double salario = -1;
                    try {
                        salario = sc.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para salário. Por favor, digite um número.");
                        sc.next();
                        System.out.println();
                        continue;
                    }


                    Professor professor = new Professor(nome, sobrenome, cpf, dataNascimento, salario);
                    if(gerenciadorSistema.cadastrarProfessor(professor)){
                        System.out.println("PROFESSOR CADASTRADO COM ID: " + professor.getId() + "!");
                        System.out.println(professor.exibirInformacoes());
                    } else {
                        System.out.println("Erro: Já existe um professor com este CPF.");
                    }
                    System.out.println();
                } else if (opcao == 3) {
                    System.out.print("Digite o CPF do professor para buscar: ");
                    String cpfBusca = sc.next();
                    try {
                        String infoProfessor = gerenciadorSistema.buscarProfessor(cpfBusca);
                        System.out.println(infoProfessor);
                    } catch (ProfessorNaoEncontrado e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                } else if (opcao == 4) {
                    if (gerenciadorSistema.getProfessores().isEmpty() || gerenciadorSistema.getDisciplinas().isEmpty()) {
                        System.out.println("É necessário ter professores e disciplinas cadastradas para realizar esta operação.");
                        System.out.println();
                    } else {
                        System.out.print("Digite o CPF do professor: ");
                        String cpfProfessor = sc.next();
                        System.out.print("Digite o ID da disciplina a ser atribuída: ");
                        int idDisciplina = -1;

                        try {
                            idDisciplina = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida para ID da disciplina. Por favor, digite um número inteiro.");
                            sc.next();
                            System.out.println();
                            continue;
                        }

                        Professor professorSelecionado = null;
                        for (Professor p : gerenciadorSistema.getProfessores()) {
                            if (p.getCpf().equals(cpfProfessor)) {
                                professorSelecionado = p;
                                break;
                            }
                        }

                        Disciplina disciplinaSelecionada = null;
                        for (Disciplina d : gerenciadorSistema.getDisciplinas()) {
                            if (d.getId() == idDisciplina) {
                                disciplinaSelecionada = d;
                                break;
                            }
                        }

                        if (professorSelecionado != null && disciplinaSelecionada != null) {
                            professorSelecionado.adicionarDisciplina(disciplinaSelecionada);
                            System.out.println("Disciplina " + disciplinaSelecionada.getNome() + " atribuída ao professor " + professorSelecionado.getNome() + "!");
                        } else {
                            System.out.println("Professor ou disciplina não encontrados. Verifique os dados informados.");
                        }
                        System.out.println();
                    }
                }
                else {
                    System.out.println("OPCAO INVALIDA");
                    System.out.println();
                }
            } else if(opcao == 3){
                System.out.println("[1] Mostrar cursos");
                System.out.println("[2] Criar curso");
                System.out.println("[3] Buscar curso por ID");
                System.out.println("[4] Adicionar disciplina a um curso");
                System.out.print(">>>");
                try {
                    opcao = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    sc.next();
                    System.out.println();
                    continue;
                }


                if(opcao == 1){
                    if (gerenciadorSistema.getCursos().isEmpty()) {
                        System.out.println("Nenhum curso cadastrado.");
                    } else {
                        gerenciadorSistema.mostrarCursos();
                    }
                    System.out.println();
                } else if (opcao == 2) {
                    System.out.println("Crie o curso");
                    System.out.print("Nome do Curso: ");
                    sc.nextLine();
                    String nomeCurso = sc.nextLine();

                    Curso curso = new Curso(nomeCurso);
                    gerenciadorSistema.criarCurso(curso);
                    System.out.println("CURSO CRIADO!");
                    System.out.println(curso.toString());
                    System.out.println();
                } else if (opcao == 3) {
                    System.out.print("Digite o ID do curso para buscar: ");
                    int idBusca = -1;
                    try {
                        idBusca = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para o ID. Por favor, digite um número inteiro.");
                        sc.next();
                        System.out.println();
                        continue;
                    }
                    try {
                        String infoCurso = gerenciadorSistema.buscaCurso(idBusca);
                        System.out.println(infoCurso);
                    } catch (CursoNaoEncontrado e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                } else if (opcao == 4) {
                    if (gerenciadorSistema.getCursos().isEmpty() || gerenciadorSistema.getDisciplinas().isEmpty()) {
                        System.out.println("É necessário ter cursos e disciplinas cadastradas para realizar esta operação.");
                        System.out.println();
                    } else {
                        System.out.print("Digite o ID do curso para adicionar a disciplina: ");
                        int idCurso = -1;
                        try {
                            idCurso = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida para ID do curso. Por favor, digite um número inteiro.");
                            sc.next();
                            System.out.println();
                            continue;
                        }

                        System.out.print("Digite o ID da disciplina a ser adicionada: ");
                        int idDisciplina = -1;
                        try {
                            idDisciplina = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada inválida para ID da disciplina. Por favor, digite um número inteiro.");
                            sc.next();
                            System.out.println();
                            continue;
                        }

                        Curso cursoSelecionado = null;
                        for (Curso c : gerenciadorSistema.getCursos()) {
                            if (c.getId() == idCurso) {
                                cursoSelecionado = c;
                                break;
                            }
                        }

                        Disciplina disciplinaSelecionada = null;
                        for (Disciplina d : gerenciadorSistema.getDisciplinas()) {
                            if (d.getId() == idDisciplina) {
                                disciplinaSelecionada = d;
                                break;
                            }
                        }

                        if (cursoSelecionado != null && disciplinaSelecionada != null) {
                            cursoSelecionado.adicionarDisciplina(disciplinaSelecionada);
                            System.out.println("Disciplina " + disciplinaSelecionada.getNome() + " adicionada ao curso " + cursoSelecionado.getNome() + "!");
                        } else {
                            System.out.println("Curso ou disciplina não encontrados. Verifique os IDs informados.");
                        }
                        System.out.println();
                    }
                }
                else {
                    System.out.println("OPCAO INVALIDA");
                    System.out.println();
                }
            } else if(opcao == 4){
                System.out.println("[1] Mostrar disciplinas");
                System.out.println("[2] Criar disciplina");
                System.out.println("[3] Buscar disciplina por ID");
                System.out.print(">>>");
                try {
                    opcao = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    sc.next();
                    System.out.println();
                    continue;
                }

                if(opcao == 1){
                    if (gerenciadorSistema.getDisciplinas().isEmpty()) {
                        System.out.println("Nenhuma disciplina cadastrada.");
                    } else {
                        gerenciadorSistema.mostrarDisciplinas();
                    }
                    System.out.println();
                } else if (opcao == 2) {
                    System.out.println("Crie a disciplina");
                    System.out.print("Nome da Disciplina: ");
                    sc.nextLine();
                    String nomeDisciplina = sc.nextLine();

                    System.out.print("Carga Horaria: ");
                    int cargaHoraria = -1;
                    try {
                        cargaHoraria = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para carga horária. Por favor, digite um número inteiro.");
                        sc.next();
                        System.out.println();
                        continue;
                    }


                    Disciplina disciplina = new Disciplina(nomeDisciplina, cargaHoraria);
                    gerenciadorSistema.criarDisciplina(disciplina);
                    System.out.println("DISCIPLINA CRIADA!");
                    System.out.println(disciplina.toString() + " | Carga Horária: " + disciplina.getCargaHoraria() + "h");
                    System.out.println();
                } else if (opcao == 3) {
                    System.out.print("Digite o ID da disciplina para buscar: ");
                    int idBusca = -1;
                    try {
                        idBusca = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para o ID. Por favor, digite um número inteiro.");
                        sc.next();
                        System.out.println();
                        continue;
                    }
                    try {
                        String infoDisciplina = gerenciadorSistema.buscaDiscplina(idBusca);
                        System.out.println(infoDisciplina);
                    } catch (DiscplinaNaoEncontrada e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                }
                else {
                    System.out.println("OPCAO INVALIDA");
                    System.out.println();
                }
            } else if (opcao == 5) {
                gerenciadorSistema.salvarDados(DADOS_ARQUIVO);
                flag = false;
                System.out.println("Saindo do sistema. Até mais!");
            } else {
                System.out.println("OPCAO INVALIDA");
                System.out.println();
            }
        }
        sc.close();
    }
}