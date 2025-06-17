package ui;

import entities.Disciplina;
import excessoes.DiscplinaNaoEncontrada;
import job.GerenciadorSistema;

import javax.swing.*;
import java.awt.*;

public class DisciplinaPanel extends JPanel {
    private GerenciadorSistema gerenciadorSistema;
    private JTextArea displayArea;

    public DisciplinaPanel(GerenciadorSistema gerenciadorSistema) {
        this.gerenciadorSistema = gerenciadorSistema;
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton btnMostrarDisciplinas = new JButton("Mostrar Disciplinas");
        btnMostrarDisciplinas.addActionListener(e -> mostrarDisciplinas());
        buttonPanel.add(btnMostrarDisciplinas);

        JButton btnCriarDisciplina = new JButton("Criar Disciplina");
        btnCriarDisciplina.addActionListener(e -> criarDisciplina());
        buttonPanel.add(btnCriarDisciplina);

        JButton btnBuscarDisciplina = new JButton("Buscar Disciplina por ID");
        btnBuscarDisciplina.addActionListener(e -> buscarDisciplina());
        buttonPanel.add(btnBuscarDisciplina);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void mostrarDisciplinas() {
        displayArea.setText("");
        if (gerenciadorSistema.getDisciplinas().isEmpty()) {
            displayArea.append("Nenhuma disciplina cadastrada.\n");
        } else {
            displayArea.append("-----------DISCIPLINAS------------\n");
            for (Disciplina disciplina : gerenciadorSistema.getDisciplinas()) {
                displayArea.append(disciplina.toString() + " | Carga Horária: " + disciplina.getCargaHoraria() + "h\n");
            }
            displayArea.append("----------------------------------\n");
        }
    }

    private void criarDisciplina() {
        while (true) {
            JTextField nomeDisciplinaField = new JTextField();
            JTextField cargaHorariaField = new JTextField();

            Object[] message = {
                    "Nome da Disciplina:", nomeDisciplinaField,
                    "Carga Horária:", cargaHorariaField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Criar Disciplina", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            }

            if (option == JOptionPane.OK_OPTION) {
                String nomeDisciplina = nomeDisciplinaField.getText().trim();
                String cargaHorariaStr = cargaHorariaField.getText().trim();

                if (nomeDisciplina.isEmpty() || cargaHorariaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    int cargaHoraria = Integer.parseInt(cargaHorariaStr);
                    Disciplina disciplina = new Disciplina(nomeDisciplina, cargaHoraria);
                    gerenciadorSistema.criarDisciplina(disciplina);

                    JOptionPane.showMessageDialog(this, "DISCIPLINA CRIADA COM ID: " + disciplina.getId() + "!\n" + disciplina.toString() + " | Carga Horária: " + disciplina.getCargaHoraria() + "h");
                    mostrarDisciplinas();
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Carga horária inválida. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void buscarDisciplina() {
        String idBuscaStr = JOptionPane.showInputDialog(this, "Digite o ID da disciplina para buscar:");
        if (idBuscaStr != null && !idBuscaStr.trim().isEmpty()) {
            try {
                int idBusca = Integer.parseInt(idBuscaStr);
                String infoDisciplina = gerenciadorSistema.buscaDiscplina(idBusca);
                JOptionPane.showMessageDialog(this, infoDisciplina);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (DiscplinaNaoEncontrada e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Disciplina Não Encontrada", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}