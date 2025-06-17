package ui;

import entities.Aluno;
import entities.Curso;
import excessoes.AlunoNaoEncontrado;
import job.GerenciadorSistema;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AlunoPanel extends JPanel {
    private GerenciadorSistema gerenciadorSistema;
    private JTextArea displayArea;

    public AlunoPanel(GerenciadorSistema gerenciadorSistema) {
        this.gerenciadorSistema = gerenciadorSistema;
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        JButton btnMostrarAlunos = new JButton("Mostrar Alunos");
        btnMostrarAlunos.addActionListener(e -> mostrarAlunos());
        buttonPanel.add(btnMostrarAlunos);

        JButton btnCadastrarAluno = new JButton("Cadastrar Aluno");
        btnCadastrarAluno.addActionListener(e -> cadastrarAluno());
        buttonPanel.add(btnCadastrarAluno);

        JButton btnBuscarAluno = new JButton("Buscar Aluno");
        btnBuscarAluno.addActionListener(e -> buscarAluno());
        buttonPanel.add(btnBuscarAluno);

        JButton btnMatricularAlunoEmCurso = new JButton("Matricular Aluno em Curso");
        btnMatricularAlunoEmCurso.addActionListener(e -> matricularAlunoEmCurso());
        buttonPanel.add(btnMatricularAlunoEmCurso);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void mostrarAlunos() {
        displayArea.setText("");
        if (gerenciadorSistema.getAlunos().isEmpty()) {
            displayArea.append("Nenhum aluno cadastrado.\n");
        } else {
            displayArea.append("-----------ALUNOS------------\n");
            for (Aluno aluno : gerenciadorSistema.getAlunos()) {
                displayArea.append(aluno.exibirInformacoes() + "\n\n");
            }
            displayArea.append("----------------------------------\n");
        }
    }

    private void cadastrarAluno() {
        while (true) {
            JTextField nomeField = new JTextField();
            JTextField sobrenomeField = new JTextField();
            JTextField cpfField = new JTextField();
            JTextField dataNascimentoField = new JTextField();

            Object[] message = {
                    "Nome:", nomeField,
                    "Sobrenome:", sobrenomeField,
                    "CPF (xxx.xxx.xxx-xx):", cpfField,
                    "Data Nascimento (dd/mm/yyyy):", dataNascimentoField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Cadastrar Aluno", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            }

            if (option == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText().trim();
                String sobrenome = sobrenomeField.getText().trim();
                String cpf = cpfField.getText().trim();
                String dataNascimento = dataNascimentoField.getText().trim();

                if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (!isCpfValido(cpf)) {
                    JOptionPane.showMessageDialog(this, "Formato de CPF inválido! Use xxx.xxx.xxx-xx", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (!isDataValida(dataNascimento)) {
                    JOptionPane.showMessageDialog(this, "Formato de data inválido! Use dd/mm/yyyy.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Aluno aluno = new Aluno(nome, sobrenome, cpf, dataNascimento);
                boolean sucesso = gerenciadorSistema.cadastrarAluno(aluno);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "ALUNO CADASTRADO COM MATRICULA: " + aluno.getMatricula() + "!\n" + aluno.exibirInformacoes());
                    mostrarAlunos();
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: Já existe um aluno com este CPF.", "Erro de Duplicidade", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void buscarAluno() {
        String matriculaStr = JOptionPane.showInputDialog(this, "Digite a matrícula do aluno para buscar:");
        if (matriculaStr != null && !matriculaStr.trim().isEmpty()) {
            try {
                int matriculaBusca = Integer.parseInt(matriculaStr);
                String infoAluno = gerenciadorSistema.buscarAluno(matriculaBusca);
                JOptionPane.showMessageDialog(this, infoAluno);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Matrícula inválida. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (AlunoNaoEncontrado e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Aluno Não Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void matricularAlunoEmCurso() {
        if (gerenciadorSistema.getAlunos().isEmpty() || gerenciadorSistema.getCursos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É necessário ter alunos e cursos cadastrados para realizar esta operação.", "Operação Não Disponível", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matriculaAlunoStr = JOptionPane.showInputDialog(this, "Digite a matrícula do aluno:");
        if (matriculaAlunoStr == null || matriculaAlunoStr.trim().isEmpty()) return;

        String idCursoStr = JOptionPane.showInputDialog(this, "Digite o ID do curso para matricular:");
        if (idCursoStr == null || idCursoStr.trim().isEmpty()) return;

        try {
            int matriculaAluno = Integer.parseInt(matriculaAlunoStr);
            int idCurso = Integer.parseInt(idCursoStr);

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
                JOptionPane.showMessageDialog(this, "Aluno " + alunoSelecionado.getNome() + " matriculado no curso " + cursoSelecionado.getNome() + "!");
                mostrarAlunos();
            } else {
                JOptionPane.showMessageDialog(this, "Aluno ou curso não encontrados. Verifique os dados informados.", "Erro de Matrícula", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Matrícula ou ID do curso inválido. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        String cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        return cpf.matches(cpfRegex);
    }

    private boolean isDataValida(String data) {
        if (data == null) return false;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(data, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}