package simulado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulado.acessor.Atributo;
import simulado.acessor.Comuns;
import simulado.acessor.TratarArquivo;

public class SobreSistema extends JFrame {

  private static final long serialVersionUID = 4L;
  private JButton btIniciar;
  private JButton btImportar;
  private JButton btExportar;
  private JLabel labTitulo;

  public SobreSistema() {
    montarTela();
  }

  private final void montarTela() {
    this.setTitle("Simulado - " + Atributo.CFVERSAO);
    this.setBackground(new Color(255, 255, 255));
    this.setSize(600, 405);
    this.setLocationRelativeTo(null);

    // Painel Central
    JPanel pnCentral = new JPanel();
    pnCentral.add(new JLabel("", Comuns.getImage("simulado.png"), 0));
    JPanel pnTitulos = new JPanel(new GridLayout(2, 1));
    labTitulo = new JLabel(Comuns.atributo.getTitulo(), null, 0);
    labTitulo.setFont(new Font("Arial", 1, 20));
    pnTitulos.add(labTitulo);
    JLabel lab02 = new JLabel(Atributo.COPYRIGHT, null, 0);
    lab02.setFont(new Font("Arial", 2, 12));
    pnTitulos.add(lab02);
    pnCentral.add(pnTitulos);

    // Painel de Botões
    JPanel pnBotoes = new JPanel();
    pnBotoes.setLayout(new GridLayout(12, 1, 5, 5));
    pnBotoes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    btIniciar = new JButton("Iniciar Simulado");
    btIniciar.addActionListener(e -> iniciarSimulado());
    pnBotoes.add(btIniciar);
    JButton btCriar = new JButton("Criar Base");
    btCriar.addActionListener(e -> criar());
    pnBotoes.add(btCriar);
    btImportar = new JButton("Importar Questões");
    btImportar.addActionListener(e -> importar());
    pnBotoes.add(btImportar);
    btExportar = new JButton("Exportar Questões");
    btExportar.addActionListener(e -> exportar());
    pnBotoes.add(btExportar);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        aoFechar();
      }
    });

    // Montagem na Tela
    this.add(pnCentral, BorderLayout.CENTER);
    this.add(pnBotoes, BorderLayout.EAST);
    this.verificarTitulo();
    this.setVisible(true);
  }

  public void iniciarSimulado() {
    new Inicial();
  }

  private void criar() {
    if (JOptionPane.showConfirmDialog(this, "Confirmar a Geração da Base de Dados?", "Ação IRREVERSÍVEL", 0) == 0) {
      if ((new TratarArquivo()).criarDatabase()) {
        JOptionPane.showMessageDialog(this, "Banco de Dados criado sem problemas, realize a Importação");
        btImportar.setEnabled(Comuns.atributo.getTitulo().length() > 0);
      }
    }
  }

  public void importar() {
    new Importar();
    verificarTitulo();
  }

  public void exportar() {
    new Exportar();
  }

  public static void main(String[] args) {
    Comuns.carAtributo();
    new SobreSistema();
  }

  private void aoFechar() {
    System.exit(0);
  }

  private void verificarTitulo() {
    new TratarArquivo().obterTitulo();
    labTitulo.setText(Comuns.atributo.getTitulo());
    btIniciar.setEnabled(labTitulo.getText().length() > 0 && !labTitulo.getText().startsWith("Importe as "));
    btExportar.setEnabled(labTitulo.getText().length() > 0 && !labTitulo.getText().startsWith("Importe as "));
    btImportar.setEnabled(labTitulo.getText().length() > 0);
  }
}
