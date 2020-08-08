package simulado;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulado.acessor.Atributo;
import simulado.acessor.Questao;
import simulado.acessor.Tempo;

public class Resumo extends JDialog implements Runnable {

  private static final long serialVersionUID = 3L;
  private JList<Questao> lista;
  private JLabel labTempo;
  private boolean finalizar;
  private boolean parar;
  private Tempo tempo;
  private Thread th;
  private int qstAtual;

  public Resumo(JList<Questao> lst, Tempo tempo) {
    finalizar = false;
    parar = true;
    th = new Thread(this);
    qstAtual = -1;
    this.tempo = tempo;
    lista = lst;
    montarTela();
  }

  private final void montarTela() {
    this.setTitle("Resumo");
    this.setSize(810, 450);
    this.setLocationRelativeTo(null);
    this.setModal(true);

    JPanel pnSuperior = new JPanel(new GridLayout(1, 3));
    JButton butResumo = new JButton("Voltar");
    pnSuperior.add(obterCompPanel(butResumo));
    butResumo.addActionListener(e -> aoFechar());
    JButton butFinalizar = new JButton("Finalizar");
    pnSuperior.add(obterCompPanel(butFinalizar));
    butFinalizar.addActionListener(e -> {
      finalizar = true;
      aoFechar();
    });
    labTempo = new JLabel("Tempo Transcorrido: HH:MM:SS");
    labTempo.setBounds(new Rectangle(560, 10, 220, 21));
    pnSuperior.add(labTempo);

    JPanel pnCentral = new JPanel(new BorderLayout());
    JPanel pnTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pnTitulo.add(new JLabel("  Qst      "));
    pnTitulo.add(new JLabel("Pergunta                                                         "));
    pnTitulo.add(new JLabel("Op\347\343o Escolhida                                                           "));
    pnTitulo.add(new JLabel("Revis\343o"));
    pnCentral.add(pnTitulo, BorderLayout.NORTH);
    lista.setFont(new Font("Courier New", 0, 12));
    lista.addListSelectionListener(e -> {
      if (e.getValueIsAdjusting()) {
        return;
      }
      qstAtual = e.getFirstIndex() + 1;
    });
    pnCentral.add(new JScrollPane(lista), BorderLayout.CENTER);

    JPanel pnInferior = new JPanel();
    pnInferior.add(new JLabel(Atributo.COPYRIGHT));
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        aoFechar();
      }
    });

    this.add(pnSuperior, BorderLayout.NORTH);
    this.add(pnCentral, BorderLayout.CENTER);
    this.add(pnInferior, BorderLayout.SOUTH);
    th.start();
    setVisible(true);
  }

  public JPanel obterCompPanel(Component c) {
    JPanel pn = new JPanel();
    pn.add(c);
    return pn;
  }

  public int getQstAtual() {
    return qstAtual;
  }

  public boolean isFinalizar() {
    return finalizar;
  }

  public void run() {
    while (parar && tempo.isMaiorZero()) {
      labTempo.setText((new StringBuilder("Tempo Transcorrido: ")).append(tempo.transHora()).toString());
      try {
        Thread.sleep(1000L);
      } catch (Exception exception) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void aoFechar() {
    parar = false;
    dispose();
  }
}
