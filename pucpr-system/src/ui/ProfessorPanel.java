package ui;

import entities.Disciplina;
import entities.Professor;
import excessoes.ProfessorNaoEncontrado;
import job.GerenciadorSistema;

import javax.swing.*;
import java.awt.*;

public class ProfessorPanel extends JPanel {
    private GerenciadorSistema gerenciadorSistema;
    private JTextArea displayArea;

    public ProfessorPanel(GerenciadorSistema gerenciadorSistema) {
        this.gerenciadorSistema = gerenciadorSistema;
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        JButton btnMostrarProfessores = new JButton("Mostrar Professores");
        btnMostrarProfessores.addActionListener(e -> mostrarProfessores());
        buttonPanel.add(btnMostrarProfessores);

        JButton btnCadastrarProfessor = new JButton("Cadastrar Professor");
        btnCadastrarProfessor.addActionListener(e -> cadastrarProfessor());
        buttonPanel.add(btnCadastrarProfessor);

        JButton btnBuscarProfessor = new JButton("Buscar Professor");
        btnBuscarProfessor.addActionListener(e -> buscarProfessor());
        buttonPanel.add(btnBuscarProfessor);

        JButton btnAdicionarDisciplina = new JButton("Add Disciplina a Professor");
        btnAdicionarDisciplina.addActionListener(e -> adicionarDisciplinaAProfessor());
        buttonPanel.add(btnAdicionarDisciplina);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void mostrarProfessores() {
        displayArea.setText("");
        if (gerenciadorSistema.getProfessores().isEmpty()) {
            displayArea.append("Nenhum professor cadastrado.\n");
        } else {
            displayArea.append("-----------PROFESSORES------------\n");
            for (Professor professor : gerenciadorSistema.getProfessores()) {
                displayArea.append(professor.exibirInformacoes() + "\n\n");
            }
            displayArea.append("----------------------------------\n");
        }
    }

    private void cadastrarProfessor() {
        JTextField nomeField = new JTextField();
        JTextField sobrenomeField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField dataNascimentoField = new JTextField();
        JTextField salarioField = new JTextField();

        Object[] message = {
                "Nome:", nomeField,
                "Sobrenome:", sobrenomeField,
                "CPF (xxx.xxx.xxx-xx):", cpfField,
                "Data Nascimento (dd/mm/yyyy):", dataNascimentoField,
                "Salário:", salarioField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Cadastrar Professor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String sobrenome = sobrenomeField.getText();
            String cpf = cpfField.getText();
            String dataNascimento = dataNascimentoField.getText();
            String salarioStr = salarioField.getText();

            if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || salarioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double salario = Double.parseDouble(salarioStr);
                Professor professor = new Professor(nome, sobrenome, cpf, dataNascimento, salario);
                gerenciadorSistema.cadastrarProfessor(professor);
                JOptionPane.showMessageDialog(this, "PROFESSOR CADASTRADO!\n" + professor.exibirInformacoes());
                mostrarProfessores(); // Atualiza a lista
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Salário inválido. Digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarProfessor() {
        String cpfBusca = JOptionPane.showInputDialog(this, "Digite o CPF do professor para buscar:");
        if (cpfBusca != null && !cpfBusca.trim().isEmpty()) {
            try {
                String infoProfessor = gerenciadorSistema.buscarProfessor(cpfBusca);
                JOptionPane.showMessageDialog(this, infoProfessor);
            } catch (ProfessorNaoEncontrado e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Professor Não Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void adicionarDisciplinaAProfessor() {
        if (gerenciadorSistema.getProfessores().isEmpty() || gerenciadorSistema.getDisciplinas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É necessário ter professores e disciplinas cadastradas para realizar esta operação.", "Operação Não Disponível", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpfProfessor = JOptionPane.showInputDialog(this, "Digite o CPF do professor:");
        if (cpfProfessor == null || cpfProfessor.trim().isEmpty()) return;

        String codigoDisciplina = JOptionPane.showInputDialog(this, "Digite o código da disciplina a ser atribuída:");
        if (codigoDisciplina == null || codigoDisciplina.trim().isEmpty()) return;

        Professor professorSelecionado = null;
        for (Professor p : gerenciadorSistema.getProfessores()) {
            if (p.getCpf().equals(cpfProfessor)) {
                professorSelecionado = p;
                break;
            }
        }

        Disciplina disciplinaSelecionada = null;
        for (Disciplina d : gerenciadorSistema.getDisciplinas()) {
            if (d.getCodigo().equals(codigoDisciplina)) {
                disciplinaSelecionada = d;
                break;
            }
        }

        if (professorSelecionado != null && disciplinaSelecionada != null) {
            professorSelecionado.adicionarDisciplina(disciplinaSelecionada);
            JOptionPane.showMessageDialog(this, "Disciplina " + disciplinaSelecionada.getNome() + " atribuída ao professor " + professorSelecionado.getNome() + "!");
            mostrarProfessores(); // atualiza a exibição do professor
        } else {
            JOptionPane.showMessageDialog(this, "Professor ou disciplina não encontrados. Verifique os dados informados.", "Erro de Atribuição", JOptionPane.WARNING_MESSAGE);
        }
    }
}