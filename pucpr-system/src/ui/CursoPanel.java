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

        JButton btnBuscarCurso = new JButton("Buscar Curso por ID"); // Texto do botão atualizado
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
        while (true) {
            JTextField nomeCursoField = new JTextField();
            // Campo de código removido
            Object[] message = { "Nome do Curso:", nomeCursoField };

            int option = JOptionPane.showConfirmDialog(this, message, "Criar Curso", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            }

            if (option == JOptionPane.OK_OPTION) {
                String nomeCurso = nomeCursoField.getText().trim();
                if (nomeCurso.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome do curso é obrigatório!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Curso curso = new Curso(nomeCurso); // Usa o novo construtor
                gerenciadorSistema.criarCurso(curso);

                JOptionPane.showMessageDialog(this, "CURSO CRIADO COM ID: " + curso.getId() + "!\n" + curso.toString());
                mostrarCursos();
                break;
            }
        }
    }

    private void buscarCurso() {
        String idBusca = JOptionPane.showInputDialog(this, "Digite o ID do curso para buscar:"); // Pede o ID
        if (idBusca != null && !idBusca.trim().isEmpty()) {
            try {
                // A busca ainda usa o método que recebe uma String
                String infoCurso = gerenciadorSistema.buscaCurso(idBusca.trim());
                JOptionPane.showMessageDialog(this, infoCurso);
            } catch (CursoNaoEncontrado e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Curso Não Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void adicionarDisciplinaACurso() {
        if (gerenciadorSistema.getCursos().isEmpty() || gerenciadorSistema.getDisciplinas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É necessário ter cursos e disciplinas cadastradas.", "Operação Não Disponível", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idCurso = JOptionPane.showInputDialog(this, "Digite o ID do curso:"); // Pede o ID
        if (idCurso == null || idCurso.trim().isEmpty()) return;

        String idDisciplina = JOptionPane.showInputDialog(this, "Digite o ID da disciplina a ser adicionada:"); // Pede o ID
        if (idDisciplina == null || idDisciplina.trim().isEmpty()) return;

        Curso cursoSelecionado = null;
        for (Curso c : gerenciadorSistema.getCursos()) {
            if (c.getCodigo().equals(idCurso.trim())) {
                cursoSelecionado = c;
                break;
            }
        }

        Disciplina disciplinaSelecionada = null;
        for (Disciplina d : gerenciadorSistema.getDisciplinas()) {
            if (d.getCodigo().equals(idDisciplina.trim())) {
                disciplinaSelecionada = d;
                break;
            }
        }

        if (cursoSelecionado != null && disciplinaSelecionada != null) {
            cursoSelecionado.adicionarDisciplina(disciplinaSelecionada);
            JOptionPane.showMessageDialog(this, "Disciplina " + disciplinaSelecionada.getNome() + " adicionada ao curso " + cursoSelecionado.getNome() + "!");
            mostrarCursos();
        } else {
            JOptionPane.showMessageDialog(this, "Curso ou disciplina não encontrados. Verifique os IDs informados.", "Erro de Adição", JOptionPane.WARNING_MESSAGE);
        }
    }
}