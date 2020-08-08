package simulado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulado.acessor.Atributo;
import simulado.acessor.TratarArquivo;

public class SobreSistema extends JFrame {

  private static final long serialVersionUID = 4L;
  private JButton btIniciar;
  private JButton btImportar;
  private JButton btExportar;
  private JLabel labTitulo;

  public SobreSistema() {
    this.setTitle("Simulado - " + Atributo.CFVERSAO);
    this.setBackground(new Color(255, 255, 255));
    this.setSize(600, 405);
    this.setLocationRelativeTo(null);

    // Painel Central
    JPanel pnCentral = new JPanel();
    pnCentral.add(new JLabel("", Atributo.getImage("simulado.png"), 0));
    labTitulo = new JLabel(Atributo.titulo, null, 0);
    labTitulo.setFont(new Font("Arial", 1, 20));
    pnCentral.add(labTitulo);
    JLabel lab02 = new JLabel(Atributo.COPYRIGHT, null, 0);
    lab02.setFont(new Font("Arial", 2, 12));
    pnCentral.add(lab02);

    // Painel de Botões
    JPanel pnBotoes = new JPanel();
    pnBotoes.setLayout(new GridLayout(12, 1, 5, 5));
    pnBotoes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    btIniciar = new JButton("Iniciar Simulado");
    btIniciar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iniciarSimulado();
      }
    });
    pnBotoes.add(btIniciar);
    JButton btCriar = new JButton("Criar Base");
    btCriar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        criar();
      }
    });
    pnBotoes.add(btCriar);
    btImportar = new JButton("Importar Questões");
    btImportar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        importar();
      }
    });
    pnBotoes.add(btImportar);
    btExportar = new JButton("Exportar Questões");
    btExportar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exportar();
      }
    });
    pnBotoes.add(btExportar);
    addWindowListener(new java.awt.event.WindowAdapter() {
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
        btImportar.setEnabled(Atributo.titulo.length() > 0);
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

  public static void main(String args[]) {
    Atributo.carAtributo();
    new SobreSistema();
  }

  private void aoFechar() {
    System.exit(0);
  }

  private void verificarTitulo() {
    new TratarArquivo().obterTitulo();
    labTitulo.setText(Atributo.titulo);
    btIniciar.setEnabled(Atributo.titulo.length() > 0 && !Atributo.titulo.startsWith("Importe as "));
    btExportar.setEnabled(Atributo.titulo.length() > 0 && !Atributo.titulo.startsWith("Importe as "));
    btImportar.setEnabled(Atributo.titulo.length() > 0);
  }
}
