package ui;

import entities.Curso;
import entities.Disciplina;
import excessoes.CursoNaoEncontrado;
import job.GerenciadorSistema;

import javax.swing.*;
import java.awt.*;

public class CursoPanel extends JPanel {
    private GerenciadorSistema gerenciadorSistema;
    private JTextArea displayArea;

    public CursoPanel(GerenciadorSistema gerenciadorSistema) {
        this.gerenciadorSistema = gerenciadorSistema;
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        JButton btnMostrarCursos = new JButton("Mostrar Cursos");
        btnMostrarCursos.addActionListener(e -> mostrarCursos());
        buttonPanel.add(btnMostrarCursos);

        JButton btnCriarCurso = new JButton("Criar Curso");
        btnCriarCurso.addActionListener(e -> criarCurso());
        buttonPanel.add(btnCriarCurso);

        JButton btnBuscarCurso = new JButton("Buscar Curso");
        btnBuscarCurso.addActionListener(e -> buscarCurso());
        buttonPanel.add(btnBuscarCurso);

        JButton btnAdicionarDisciplina = new JButton("Add Disciplina a Curso");
        btnAdicionarDisciplina.addActionListener(e -> adicionarDisciplinaACurso());
        buttonPanel.add(btnAdicionarDisciplina);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void mostrarCursos() {
        displayArea.setText("");
        if (gerenciadorSistema.getCursos().isEmpty()) {
            displayArea.append("Nenhum curso cadastrado.\n");
        } else {
            displayArea.append("-----------CURSOS------------\n");
            for (Curso curso : gerenciadorSistema.getCursos()) {
                displayArea.append(curso.toString() + "\n");
                displayArea.append("  Disciplinas no Curso:\n");
                if (curso.getDisciplinasCurso().isEmpty()) {
                    displayArea.append("    Nenhuma disciplina adicionada a este curso.\n");
                } else {
                    for (Disciplina disc : curso.getDisciplinasCurso()) {
                        displayArea.append("    - " + disc.toString() + "\n");
                    }
                }
                displayArea.append("\n");
            }
            displayArea.append("----------------------------------\n");
        }
    }

    private void criarCurso() {
        JTextField nomeCursoField = new JTextField();
        JTextField codigoCursoField = new JTextField();

        Object[] message = {
                "Nome do Curso:", nomeCursoField,
                "Código do Curso:", codigoCursoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Criar Curso", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nomeCurso = nomeCursoField.getText();
            String codigoCurso = codigoCursoField.getText();

            if (nomeCurso.isEmpty() || codigoCurso.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso curso = new Curso(nomeCurso, codigoCurso);
            gerenciadorSistema.criarCurso(curso);
            JOptionPane.showMessageDialog(this, "CURSO CRIADO!\n" + curso.toString());
            mostrarCursos(); // Atualiza a lista
        }
    }

    private void buscarCurso() {
        String codigoBusca = JOptionPane.showInputDialog(this, "Digite o código do curso para buscar:");
        if (codigoBusca != null && !codigoBusca.trim().isEmpty()) {
            try {
                String infoCurso = gerenciadorSistema.buscaCurso(codigoBusca);
                JOptionPane.showMessageDialog(this, infoCurso);
            } catch (CursoNaoEncontrado e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Curso Não Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void adicionarDisciplinaACurso() {
        if (gerenciadorSistema.getCursos().isEmpty() || gerenciadorSistema.getDisciplinas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É necessário ter cursos e disciplinas cadastradas para realizar esta operação.", "Operação Não Disponível", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoCurso = JOptionPane.showInputDialog(this, "Digite o código do curso para adicionar a disciplina:");
        if (codigoCurso == null || codigoCurso.trim().isEmpty()) return;

        String codigoDisciplina = JOptionPane.showInputDialog(this, "Digite o código da disciplina a ser adicionada:");
        if (codigoDisciplina == null || codigoDisciplina.trim().isEmpty()) return;

        Curso cursoSelecionado = null;
        for (Curso c : gerenciadorSistema.getCursos()) {
            if (c.getCodigo().equals(codigoCurso)) {
                cursoSelecionado = c;
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

        if (cursoSelecionado != null && disciplinaSelecionada != null) {
            cursoSelecionado.adicionarDisciplina(disciplinaSelecionada);
            JOptionPane.showMessageDialog(this, "Disciplina " + disciplinaSelecionada.getNome() + " adicionada ao curso " + cursoSelecionado.getNome() + "!");
            mostrarCursos(); // Atualiza a exibição do curso
        } else {
            JOptionPane.showMessageDialog(this, "Curso ou disciplina não encontrados. Verifique os códigos informados.", "Erro de Adição", JOptionPane.WARNING_MESSAGE);
        }
    }
}