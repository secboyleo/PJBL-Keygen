package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;

public class Interface {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Janela
            JFrame frame = new JFrame("Explorador de Arquivos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            // Painel superior com botão de escolha de pasta
            JPanel topPanel = new JPanel(new BorderLayout());
            JButton escolherPasta = new JButton("Escolher Pasta");
            topPanel.add(escolherPasta, BorderLayout.WEST);
            frame.add(topPanel, BorderLayout.NORTH);

            // Modelo da tabela
            String[] colunas = {"Nome", "Tamanho", "Tipo", "Modificado"};
            DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
            JTable tabela = new JTable(modelo);
            JScrollPane scrollPane = new JScrollPane(tabela);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Barra de status
            JLabel status = new JLabel("Nenhuma pasta selecionada");
            status.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            frame.add(status, BorderLayout.SOUTH);

            // Função: Carregar arquivos da pasta
            escolherPasta.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = chooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File pasta = chooser.getSelectedFile();
                    status.setText("Pasta: " + pasta.getAbsolutePath());

                    modelo.setRowCount(0); // limpa a tabela

                    File[] arquivos = pasta.listFiles();
                    if (arquivos != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        for (File arquivo : arquivos) {
                            if (arquivo.isFile()) {
                                String nome = arquivo.getName();
                                String tamanho = arquivo.length() / 1024 + " KB";
                                String tipo = nome.contains(".") ? nome.substring(nome.lastIndexOf('.') + 1).toUpperCase() : "Desconhecido";
                                String modificado = sdf.format(arquivo.lastModified());

                                modelo.addRow(new Object[]{nome, tamanho, tipo, modificado});
                            }
                        }
                    }
                }
            });

            frame.setVisible(true);
        });
    }
}