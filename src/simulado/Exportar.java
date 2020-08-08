package simulado;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulado.acessor.TratarArquivo;

public class Exportar extends JDialog {

  private static final long serialVersionUID = 5L;
  private JLabel arquivo;
  private JLabel registro;

  public Exportar() {
    montarTela();
  }

  private final void montarTela() {
    this.setTitle("Exportar Quest\365es");
    this.setSize(481, 170);
    this.setLocationRelativeTo(null);
    this.setModal(true);

    // Painel Central
    JPanel pnCentral = new JPanel();
    pnCentral.setLayout(new GridLayout(2, 1, 5, 5));
    pnCentral.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    JPanel pnLinha1 = new JPanel();
    pnLinha1.add(new JLabel("Arquivo CSV:"));
    arquivo = new JLabel("");
    pnLinha1.add(arquivo);
    JPanel pnLinha2 = new JPanel();
    pnLinha2.add(new JLabel("Tot.Registros:"));
    registro = new JLabel("");
    pnLinha2.add(registro);
    pnCentral.add(pnLinha1);
    pnCentral.add(pnLinha2);

    // Painel de Botões
    JPanel pnBotoes = new JPanel();
    JButton btSelecionar = new JButton("Selecionar");
    pnBotoes.add(btSelecionar);
    btSelecionar.addActionListener(e -> selecionar());
    JButton btProva = new JButton("Prova");
    pnBotoes.add(btProva);
    btProva.addActionListener(e -> criar());
    JButton btExportar = new JButton("Exportar");
    pnBotoes.add(btExportar);
    btExportar.addActionListener(e -> exportar());

    // Montagem na Tela
    this.add(pnCentral, BorderLayout.CENTER);
    this.add(pnBotoes, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  private void selecionar() {
    FileDialog dig = new FileDialog(this, "Selecionar Arquivo", 0);
    dig.setDirectory("");
    dig.setFile("*.csv");
    dig.setFilenameFilter((dir, name) -> name.endsWith(".csv"));
    dig.setVisible(true);
    String nomArq = (new StringBuilder(String.valueOf(dig.getDirectory()))).append(dig.getFile()).toString();
    if (!(new File(nomArq)).exists()) {
      arquivo.setText(nomArq);
    } else {
      JOptionPane.showMessageDialog(this, "Arquivo existente", "Erro", 0);
    }
  }

  private void criar() {
    if ((new TratarArquivo()).criarDatabase()) {
      JOptionPane.showMessageDialog(this, "Banco de Dados criado sem problemas");
      verificarRegistros();
    }
  }

  private void exportar() {
    if (arquivo.getText().length() > 0) {
      String nomArq = arquivo.getText();
      TratarArquivo banco = new TratarArquivo();
      int linhaImp = banco.exportarDados(nomArq);
      JOptionPane.showMessageDialog(this,
          (new StringBuilder("Foram exportadas ")).append(linhaImp).append(" linhas.").toString());
      verificarRegistros();
    } else {
      JOptionPane.showMessageDialog(this, "Selecionar o Arquivo para Exportar", "Erro", 0);
    }
  }

  private void verificarRegistros() {
    registro.setText((new StringBuilder("Registros: ")).append((new TratarArquivo()).totalRegistro()).toString());
  }
}
