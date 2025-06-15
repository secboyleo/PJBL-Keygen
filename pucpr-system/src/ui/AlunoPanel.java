package ui;

import entities.Aluno;
import entities.Curso;
import excessoes.AlunoNaoEncontrado;
import job.GerenciadorSistema;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AlunoPanel extends JPanel {
    private GerenciadorSistema gerenciadorSistema;
    private JTextArea displayArea;

    public AlunoPanel(GerenciadorSistema gerenciadorSistema) {
        this.gerenciadorSistema = gerenciadorSistema;
        setLayout(new BorderLayout());

        // Área de exibição
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 linha, 4 colunas, 5px de espaçamento

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


        add(buttonPanel, BorderLayout.NORTH); // adiciona na parte superior
    }

    private void mostrarAlunos() {
        displayArea.setText(""); // Limpa a área de exibição
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
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String sobrenome = sobrenomeField.getText();
            String cpf = cpfField.getText();
            String dataNascimento = dataNascimentoField.getText();

            if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Aluno aluno = new Aluno(nome, sobrenome, cpf, dataNascimento);
            gerenciadorSistema.cadastrarAluno(aluno);
            JOptionPane.showMessageDialog(this, "ALUNO CADASTRADO COM MATRICULA: " + aluno.getMatricula() + "!\n" + aluno.exibirInformacoes());
            mostrarAlunos(); // Atualiza a lista
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

        int matriculaAluno;
        try {
            matriculaAluno = Integer.parseInt(matriculaAlunoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Matrícula inválida. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigoCurso = JOptionPane.showInputDialog(this, "Digite o código do curso para matricular:");
        if (codigoCurso == null || codigoCurso.trim().isEmpty()) return;

        Aluno alunoSelecionado = null;
        for (Aluno a : gerenciadorSistema.getAlunos()) {
            if (a.getMatricula() == matriculaAluno) {
                alunoSelecionado = a;
                break;
            }
        }

        Curso cursoSelecionado = null;
        for (Curso c : gerenciadorSistema.getCursos()) {
            if (c.getCodigo().equals(codigoCurso)) {
                cursoSelecionado = c;
                break;
            }
        }

        if (alunoSelecionado != null && cursoSelecionado != null) {
            alunoSelecionado.matricularEmCurso(cursoSelecionado);
            JOptionPane.showMessageDialog(this, "Aluno " + alunoSelecionado.getNome() + " matriculado no curso " + cursoSelecionado.getNome() + "!");
            mostrarAlunos(); // Atualiza a exibição do aluno
        } else {
            JOptionPane.showMessageDialog(this, "Aluno ou curso não encontrados. Verifique os dados informados.", "Erro de Matrícula", JOptionPane.WARNING_MESSAGE);
        }
    }
}