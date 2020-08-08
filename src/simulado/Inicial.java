package simulado;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import simulado.acessor.Atributo;
import simulado.acessor.Questao;
import simulado.acessor.Tempo;
import simulado.acessor.TratarArquivo;

public class Inicial extends JDialog implements Runnable {

  private static final long serialVersionUID = 1L;
  private JLabel objeto0;
  private JLabel objeto1;
  private JLabel objeto2;
  private JLabel labRef;
  private JLabel objeto5;
  private JTextField edtIrQuestao;
  private JTextArea txaPerg;
  private JTextArea txaOpcA;
  private JTextArea txaOpcB;
  private JTextArea txaOpcC;
  private JTextArea txaOpcD;
  private ButtonGroup opcoes;
  private JRadioButton radOpcA;
  private JRadioButton radOpcB;
  private JRadioButton radOpcC;
  private JRadioButton radOpcD;
  private JLabel labQuestao;
  private JButton imgAnt;
  private JButton imgProx;
  private JCheckBox chkMarcar;
  private JLabel labTempo;
  private JButton butResumo;
  private JButton butFinalizar;
  private JLabel labDetalhe;
  private Tempo tempo;
  private Thread th;
  private int qstAtual;
  private final int qstTotal;
  private List<Questao> questoes;
  private JList lista;
  private boolean parar;

  public Inicial() {
    this.setTitle((new StringBuilder(String.valueOf(Atributo.titulo))).append(" - ").append("Versao 1.12").toString());
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setModal(true);

    th = new Thread(this);
    qstAtual = 1;
    parar = true;
    tempo = new Tempo(Atributo.tempo);
    questoes = (new TratarArquivo()).obterDados(Atributo.totQuestao);
    qstTotal = questoes.size();
    mostrar();
  }

  public final void mostrar() {
    getContentPane().setLayout(null);
    setResizable(false);
    objeto0 = new JLabel("Anterior");
    objeto0.setBounds(new java.awt.Rectangle(20, 80, 67, 13));
    getContentPane().add(objeto0, null);
    objeto1 = new JLabel("Pr\363ximo");
    objeto1.setBounds(new java.awt.Rectangle(90, 80, 57, 13));
    getContentPane().add(objeto1, null);
    objeto2 = new JLabel("Ir Para:");
    objeto2.setBounds(new java.awt.Rectangle(160, 49, 57, 13));
    getContentPane().add(objeto2, null);
    edtIrQuestao = new JTextField();
    edtIrQuestao.setBounds(new java.awt.Rectangle(215, 46, 50, 21));
    getContentPane().add(edtIrQuestao, null);
    edtIrQuestao.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        pularQuestao();
      }
    });
    chkMarcar = new JCheckBox("Marcar para revis\343o");
    chkMarcar.setBounds(new java.awt.Rectangle(270, 40, 170, 30));
    getContentPane().add(chkMarcar, null);
    chkMarcar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        marcarQuestao();
      }
    });
    butResumo = new JButton("Resumo");
    butResumo.setBounds(new java.awt.Rectangle(450, 40, 100, 30));
    getContentPane().add(butResumo, null);
    butResumo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        mostrarResumo();
      }
    });
    labTempo = new JLabel("Tempo Transcorrido: HH:MM:SS");
    labTempo.setBounds(new java.awt.Rectangle(560, 10, 220, 21));
    getContentPane().add(labTempo, null);
    butFinalizar = new JButton("Finalizar");
    butFinalizar.setBounds(new java.awt.Rectangle(680, 40, 100, 30));
    getContentPane().add(butFinalizar, null);
    butFinalizar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        finalizar();
      }
    });
    labRef = new JLabel("#Ref.C99Q99");
    labRef.setBounds(new java.awt.Rectangle(670, 75, 100, 21));
    getContentPane().add(labRef, null);
    objeto5 = new JLabel("Pergunta:");
    objeto5.setBounds(new java.awt.Rectangle(10, 110, 80, 30));
    getContentPane().add(objeto5, null);
    txaPerg = new JTextArea();
    txaPerg.setLineWrap(true);
    txaPerg.setFont(new java.awt.Font("Arial", 0, 14));
    txaPerg.setWrapStyleWord(true);
    txaPerg.setEditable(false);
    JScrollPane spPerg = new JScrollPane(txaPerg);
    spPerg.setHorizontalScrollBarPolicy(31);
    spPerg.setBounds(new java.awt.Rectangle(100, 110, 680, 100));
    getContentPane().add(spPerg, null);
    txaOpcA = new JTextArea();
    txaOpcA.setLineWrap(true);
    txaOpcA.setFont(new java.awt.Font("Arial", 0, 14));
    txaOpcA.setWrapStyleWord(true);
    txaOpcA.setEditable(false);
    JScrollPane spOpcA = new JScrollPane(txaOpcA);
    spOpcA.setHorizontalScrollBarPolicy(31);
    spOpcA.setBounds(new java.awt.Rectangle(100, 220, 680, 70));
    getContentPane().add(spOpcA, null);
    txaOpcB = new JTextArea();
    txaOpcB.setLineWrap(true);
    txaOpcB.setFont(new java.awt.Font("Arial", 0, 14));
    txaOpcB.setWrapStyleWord(true);
    txaOpcB.setEditable(false);
    JScrollPane spOpcB = new JScrollPane(txaOpcB);
    spOpcB.setHorizontalScrollBarPolicy(31);
    spOpcB.setBounds(new java.awt.Rectangle(100, 300, 680, 70));
    getContentPane().add(spOpcB, null);
    txaOpcC = new JTextArea();
    txaOpcC.setLineWrap(true);
    txaOpcC.setFont(new java.awt.Font("Arial", 0, 14));
    txaOpcC.setWrapStyleWord(true);
    txaOpcC.setEditable(false);
    JScrollPane spOpcC = new JScrollPane(txaOpcC);
    spOpcC.setHorizontalScrollBarPolicy(31);
    spOpcC.setBounds(new java.awt.Rectangle(100, 380, 680, 70));
    getContentPane().add(spOpcC, null);
    txaOpcD = new JTextArea();
    txaOpcD.setLineWrap(true);
    txaOpcD.setFont(new java.awt.Font("Arial", 0, 14));
    txaOpcD.setWrapStyleWord(true);
    txaOpcD.setEditable(false);
    JScrollPane spOpcD = new JScrollPane(txaOpcD);
    spOpcD.setHorizontalScrollBarPolicy(31);
    spOpcD.setBounds(new java.awt.Rectangle(100, 460, 680, 70));
    getContentPane().add(spOpcD, null);
    opcoes = new ButtonGroup();
    radOpcA = new JRadioButton("Op\347\343o A:");
    opcoes.add(radOpcA);
    radOpcA.setBounds(new java.awt.Rectangle(10, 220, 90, 30));
    getContentPane().add(radOpcA, null);
    radOpcA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        marcarOpcao('A');
      }
    });
    radOpcB = new JRadioButton("Op\347\343o B:");
    opcoes.add(radOpcB);
    radOpcB.setBounds(new java.awt.Rectangle(10, 300, 90, 30));
    getContentPane().add(radOpcB, null);
    radOpcB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        marcarOpcao('B');
      }
    });
    radOpcC = new JRadioButton("Op\347\343o C:");
    opcoes.add(radOpcC);
    radOpcC.setBounds(new java.awt.Rectangle(10, 380, 90, 30));
    getContentPane().add(radOpcC, null);
    radOpcC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        marcarOpcao('C');
      }
    });
    radOpcD = new JRadioButton("Op\347\343o D:");
    opcoes.add(radOpcD);
    radOpcD.setBounds(new java.awt.Rectangle(10, 460, 90, 30));
    getContentPane().add(radOpcD, null);
    radOpcD.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        marcarOpcao('D');
      }
    });
    labQuestao = new JLabel("Quest\343o 999 de 200");
    labQuestao.setBounds(new java.awt.Rectangle(10, 10, 140, 21));
    getContentPane().add(labQuestao, null);
    imgAnt = new JButton();
    imgAnt.setIcon(Atributo.getImage("back.gif"));
    imgAnt.setBounds(new java.awt.Rectangle(23, 35, 40, 40));
    getContentPane().add(imgAnt, null);
    imgAnt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        antQuestao();
      }
    });
    imgProx = new JButton();
    imgProx.setIcon(Atributo.getImage("forward.gif"));
    imgProx.setBounds(new java.awt.Rectangle(95, 35, 40, 40));
    getContentPane().add(imgProx, null);
    imgProx.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        prxQuestao();
      }
    });
    labDetalhe = new JLabel("2020 \251 Fernando Anselmo");
    labDetalhe.setBounds(new java.awt.Rectangle(320, 535, 300, 30));
    getContentPane().add(labDetalhe, null);
    mostrarQuestao();
    th.start();
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
        finalizar();
      }
    });
    setDefaultCloseOperation(0);
    setVisible(true);
  }

  private void antQuestao() {
    if (qstAtual > 1) {
      qstAtual--;
      mostrarQuestao();
    }
  }

  private void prxQuestao() {
    if (qstAtual < qstTotal) {
      qstAtual++;
      mostrarQuestao();
    }
  }

  private void mostrarQuestao() {
    if (questoes.size() > 0) {
      Questao qst = (Questao) questoes.get(qstAtual - 1);
      labQuestao.setText((new StringBuilder("Quest\343o ")).append(Atributo.colocaZero(qstAtual, 3)).append(" de ")
          .append(Atributo.colocaZero(qstTotal, 3)).toString());
      labRef.setText((new StringBuilder("#Ref.")).append(qst.getIdentificacao()).toString());
      txaPerg.setText(qst.getPergunta());
      txaPerg.setCaretPosition(0);
      txaOpcA.setText(qst.getOpcaoA());
      txaOpcA.setCaretPosition(0);
      txaOpcB.setText(qst.getOpcaoB());
      txaOpcB.setCaretPosition(0);
      txaOpcC.setText(qst.getOpcaoC());
      txaOpcC.setCaretPosition(0);
      txaOpcD.setText(qst.getOpcaoD());
      txaOpcD.setCaretPosition(0);
      chkMarcar.setSelected(qst.isMarcar());
      opcoes.clearSelection();
      switch (qst.getOpcaoEscolhida()) {
      case 65: // 'A'
        radOpcA.setSelected(true);
        break;
      case 66: // 'B'
        radOpcB.setSelected(true);
        break;
      case 67: // 'C'
        radOpcC.setSelected(true);
        break;
      case 68: // 'D'
        radOpcD.setSelected(true);
        break;
      }
    }
  }

  private void pularQuestao() {
    try {
      qstAtual = Integer.parseInt(edtIrQuestao.getText());
      irQuestao();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Informar um N\372mero de Quest\343o v\341lido", "Erro Informa\347\343o", 0);
    }
  }

  private void irQuestao() {
    int questao = qstAtual;
    try {
      if (questao < 1)
        throw new Exception("menor");
      if (questao > qstTotal)
        throw new Exception("maior");
      qstAtual = questao;
      mostrarQuestao();
      edtIrQuestao.setText("");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, (new StringBuilder("N\372mero informado \351 ")).append(e.getMessage())
          .append(" que o total da quest\343o").toString(), "Erro Informa\347\343o", 0);
    }
  }

  private void marcarOpcao(char opc) {
    ((Questao) questoes.get(qstAtual - 1)).setOpcaoEscolhida(opc);
  }

  private void marcarQuestao() {
    ((Questao) questoes.get(qstAtual - 1)).setMarcar(chkMarcar.isSelected());
  }

  private void mostrarResumo() {
    lista = new JList(questoes.toArray());
    Resumo resumo = new Resumo(lista, tempo);
    if (resumo.isFinalizar()) {
      finalizar();
    }
    if (resumo.getQstAtual() > -1) {
      qstAtual = resumo.getQstAtual();
      irQuestao();
    }
  }

  private void finalizar() {
    if (JOptionPane.showConfirmDialog(this, "Confirma o T\351rmino do Simulado", "Finalizar?", 0) == 0) {
      parar = false;
      new Desempenho(questoes, tempo.getIntHora());
      this.dispose();
    }
  }

  public void run() {
    while (parar && tempo.isMaiorZero()) {
      labTempo.setText((new StringBuilder("Tempo Transcorrido: ")).append(tempo.transHora()).toString());
      tempo.reduz();
      try {
        Thread.sleep(1000L);
      } catch (Exception exception) {
      }
    }
    if (!tempo.isMaiorZero()) {
      JOptionPane.showMessageDialog(this, "Tempo Terminado. Programa ser\341 finalizado");
      new Desempenho(questoes, tempo.getIntHora());
      this.dispose();
    }
  }
}
