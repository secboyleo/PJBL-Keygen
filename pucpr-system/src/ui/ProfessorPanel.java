package ui;

import entities.Disciplina;
import entities.Professor;
import excessoes.ProfessorNaoEncontrado;
import job.GerenciadorSistema;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        while (true) {
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

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            }

            if (option == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText().trim();
                String sobrenome = sobrenomeField.getText().trim();
                String cpf = cpfField.getText().trim();
                String dataNascimento = dataNascimentoField.getText().trim();
                String salarioStr = salarioField.getText().trim();

                if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || salarioStr.isEmpty()) {
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

                try {
                    double salario = Double.parseDouble(salarioStr);
                    Professor professor = new Professor(nome, sobrenome, cpf, dataNascimento, salario);

                    boolean sucesso = gerenciadorSistema.cadastrarProfessor(professor);

                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "PROFESSOR CADASTRADO COM ID: " + professor.getId() + "!\n" + professor.exibirInformacoes());
                        mostrarProfessores();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro: Já existe um professor com este CPF.", "Erro de Duplicidade", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Salário inválido. Digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                }
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
            mostrarProfessores();
        } else {
            JOptionPane.showMessageDialog(this, "Professor ou disciplina não encontrados. Verifique os dados informados.", "Erro de Atribuição", JOptionPane.WARNING_MESSAGE);
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