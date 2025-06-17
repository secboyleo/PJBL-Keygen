package application;

import job.GerenciadorSistema;
import ui.*; // Importa todos os painéis da UI

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGUI extends JFrame {
    private static final String DADOS_ARQUIVO = "escola.dat";
    private GerenciadorSistema gerenciadorSistema;

    public MainGUI() {
        super("Sistema de Gerenciamento Escolar");
        gerenciadorSistema = new GerenciadorSistema();
        gerenciadorSistema.carregarDados(DADOS_ARQUIVO); // carrge os dados do escola.dat
        gerenciadorSistema.carregarAlunosDeArquivo("alunos.txt"); // carrega os dados do alunos.txt

        // configuração da janela principal
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // gerenciar o fechamento manualmente
        setLocationRelativeTo(null); // centralizar a janela

        // adicionar um WindowListener para salvar os dados ao fechar
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gerenciadorSistema.salvarDados(DADOS_ARQUIVO);
                JOptionPane.showMessageDialog(MainGUI.this, "Dados salvos. Saindo do sistema. Até mais!");
                System.exit(0);
            }
        });

        // criação do JTabbedPane para organizar os painéis
        JTabbedPane tabbedPane = new JTabbedPane();

        // criação e adição dos painéis
        tabbedPane.addTab("Alunos", new AlunoPanel(gerenciadorSistema));
        tabbedPane.addTab("Professores", new ProfessorPanel(gerenciadorSistema));
        tabbedPane.addTab("Cursos", new CursoPanel(gerenciadorSistema));
        tabbedPane.addTab("Disciplinas", new DisciplinaPanel(gerenciadorSistema));

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // garante que a GUI seja executada na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
}