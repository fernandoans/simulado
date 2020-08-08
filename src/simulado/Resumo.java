package simulado;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import simulado.acessor.Questao;
import simulado.acessor.Tempo;

public class Resumo extends JDialog implements Runnable {

  private static final long serialVersionUID = 3L;
  private JLabel objeto0;
  private JLabel objeto1;
  private JLabel objeto2;
  private JLabel objeto3;
  private JList<Questao> lista;
  private JLabel labTempo;
  private JButton butResumo;
  private JButton butFinalizar;
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
    mostrar();
  }

  public final void mostrar() {
    this.setTitle("Resumo");
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setModal(true);

    getContentPane().setLayout(null);
    getContentPane().setBackground(new Color(238, 238, 238));
    setSize(810, 440);
    butResumo = new JButton("Voltar");
    butResumo.setBounds(new Rectangle(440, 40, 100, 30));
    getContentPane().add(butResumo, null);
    butResumo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aoFechar();
      }
    });
    butFinalizar = new JButton("Finalizar");
    butFinalizar.setBounds(new Rectangle(687, 40, 100, 30));
    getContentPane().add(butFinalizar, null);
    butFinalizar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        finalizar = true;
        aoFechar();
      }
    });
    labTempo = new JLabel("Tempo Transcorrido: HH:MM:SS");
    labTempo.setBounds(new Rectangle(560, 10, 220, 21));
    getContentPane().add(labTempo, null);
    objeto0 = new JLabel("Quest\343o");
    objeto0.setBounds(new Rectangle(5, 80, 65, 13));
    getContentPane().add(objeto0, null);
    objeto1 = new JLabel("Pergunta");
    objeto1.setBounds(new Rectangle(75, 80, 77, 13));
    getContentPane().add(objeto1, null);
    objeto2 = new JLabel("Op\347\343o Escolhida");
    objeto2.setBounds(new Rectangle(375, 80, 130, 13));
    getContentPane().add(objeto2, null);
    objeto3 = new JLabel("Revis\343o");
    objeto3.setBounds(new Rectangle(720, 80, 60, 13));
    getContentPane().add(objeto3, null);
    lista.setFont(new Font("Courier New", 0, 12));
    JScrollPane sp = new JScrollPane(lista);
    sp.setBounds(new Rectangle(10, 100, 780, 300));
    getContentPane().add(sp, null);
    lista.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting()) {
          return;
        }
        qstAtual = evt.getFirstIndex() + 1;
      }
    });
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        aoFechar();
      }
    });
    th.start();
    setVisible(true);
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
      }
    }
  }

  private void aoFechar() {
    parar = false;
    dispose();
  }
}
