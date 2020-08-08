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

import simulado.acessor.Comuns;
import simulado.acessor.TratarArquivo;

public class Importar extends JDialog {

  private static final long serialVersionUID = 6L;
  private JLabel arquivo;
  private JLabel registro;

  public Importar() {
    montarTela();
  }

  private final void montarTela() {
    this.setTitle("Importar Quest\365es para " + Comuns.atributo.getProva());
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
    JButton btImportar = new JButton("Importar");
    pnBotoes.add(btImportar);
    btImportar.addActionListener(e -> importar());
    JButton btLimpar = new JButton("Limpar");
    pnBotoes.add(btLimpar);
    btLimpar.addActionListener(e -> limpar());

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
    if ((new File(nomArq)).exists()) {
      arquivo.setText(nomArq);
    } else {
      JOptionPane.showMessageDialog(this, "Arquivo n\343o Encontrado", "Erro", 0);
    }
  }

  private void importar() {
    if (arquivo.getText().length() > 0) {
      String nomArq = arquivo.getText();
      TratarArquivo banco = new TratarArquivo();
      int linhaImp = banco.importarDados(nomArq);
      JOptionPane.showMessageDialog(this,
          (new StringBuilder("Foram importadas ")).append(linhaImp).append(" linhas.").toString());
      verificarRegistros();
    } else {
      JOptionPane.showMessageDialog(this, "Selecionar o Arquivo para Importar", "Erro", 0);
    }
  }

  private void limpar() {
    if (JOptionPane.showConfirmDialog(this, "Confirmar Remoção das Questões da Prova?", "Ação IRREVERSÍVEL", 0) == 0) {
      if ((new TratarArquivo()).limparQuestoes()) {
        JOptionPane.showMessageDialog(this, "Remoção ocorrida sem problemas, realize a Importação");
      }
    }
  }

  private void verificarRegistros() {
    registro.setText((new StringBuilder("Registros: ")).append((new TratarArquivo()).totalRegistro()).toString());
  }
}
