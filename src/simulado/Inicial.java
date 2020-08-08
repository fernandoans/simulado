package simulado;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import simulado.acessor.Atributo;
import simulado.acessor.Comuns;
import simulado.acessor.ErroOperacaoException;
import simulado.acessor.Questao;
import simulado.acessor.Tempo;
import simulado.acessor.TratarArquivo;

public class Inicial extends JDialog implements Runnable {

  private static final long serialVersionUID = 1L;
  private int qstAtual;
  private boolean parar;
  private Tempo tempo;
  private List<Questao> questoes;
  private final int qstTotal;
  private Thread th;
  // Painel Superior
  private JLabel labQuestao;
  private JTextField edtIrQuestao;
  private JLabel labRef;
  private JCheckBox chkMarcar;
  private JLabel labTempo;
  // Painel Central
  private JTextArea[] taQuestao;
  private GridBagConstraints gbc;
  private GridBagLayout layCentral;
  private JPanel pnSuperior;
  private JPanel pnCentral;
  private ButtonGroup opcoes;
  private JRadioButton[] radOpc;

  public Inicial() {
    this.qstAtual = 1;
    this.parar = true;
    this.tempo = new Tempo(Comuns.atributo.getTempo());
    this.questoes = (new TratarArquivo()).obterDados(Comuns.atributo.getTotQuestao());
    this.qstTotal = questoes.size();
    // Ativar o Contador
    th = new Thread(this);
    montarTela();
  }

  private final void montarTela() {
    this.setTitle(new StringBuilder(Comuns.atributo.getTitulo()).append(" - ").append("Versao 1.12").toString());
    this.setSize(800, 620);
    this.setLocationRelativeTo(null);
    this.setModal(true);
    this.gbc = new GridBagConstraints();

    this.montagemPainelSuperior();
    this.montagemPainelCentral();
    JPanel pnInferior = new JPanel();
    pnInferior.add(new JLabel(Atributo.COPYRIGHT));

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        finalizar();
      }
    });

    this.add(pnSuperior, BorderLayout.NORTH);
    this.add(pnCentral, BorderLayout.CENTER);
    this.add(pnInferior, BorderLayout.SOUTH);
    this.mostrarQuestao();

    th.start();
    this.setVisible(true);
  }

  private void montagemPainelSuperior() {
    pnSuperior = new JPanel();
    pnSuperior.setLayout(new GridBagLayout());
    // 1a. parte: montar a linha de informação
    JPanel pnLinhaSuperior = new JPanel();
    pnLinhaSuperior.setLayout(new GridLayout(1, 3, 5, 5));
    pnLinhaSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    labQuestao = new JLabel("Quest\343o 999 de 200");
    pnLinhaSuperior.add(labQuestao);
    labRef = new JLabel("#Ref.C99Q99");
    pnLinhaSuperior.add(labRef);
    labTempo = new JLabel("Tempo Transcorrido: HH:MM:SS");
    pnLinhaSuperior.add(labTempo);
    // 2a. parte: montar a linha de botões
    JPanel pnBotoesSuperior = new JPanel();
    // a. Botões de navegação
    JPanel pnNavegacao = new JPanel();
    pnNavegacao.setLayout(new GridBagLayout());
    JButton imgAnt = new JButton();
    imgAnt.setIcon(Comuns.getImage("back.gif"));
    imgAnt.addActionListener(e -> antQuestao());
    JButton imgProx = new JButton();
    imgProx.setIcon(Comuns.getImage("forward.gif"));
    imgProx.addActionListener(e -> prxQuestao());
    this.acertarGbc(1, 1);
    pnNavegacao.add(imgAnt, gbc);
    acertarGbc(1, 2);
    pnNavegacao.add(imgProx, gbc);
    acertarGbc(2, 1);
    pnNavegacao.add(new JLabel("Anterior"), gbc);
    acertarGbc(2, 2);
    pnNavegacao.add(new JLabel("Pr\363ximo"), gbc);
    pnBotoesSuperior.add(pnNavegacao);
    // b. Atalho rápido
    JPanel pnIrPara = new JPanel();
    pnIrPara.add(new JLabel("Ir Para:"));
    edtIrQuestao = new JTextField("", 3);
    pnIrPara.add(edtIrQuestao);
    edtIrQuestao.addActionListener(e -> pularQuestao());
    pnBotoesSuperior.add(pnIrPara);
    // c. Marcar
    chkMarcar = new JCheckBox("Marcar para revis\343o");
    pnBotoesSuperior.add(chkMarcar);
    chkMarcar.addActionListener(e -> marcarQuestao());
    // d. Botoes finais
    JButton butResumo = new JButton("Resumo");
    pnBotoesSuperior.add(butResumo);
    butResumo.addActionListener(e -> mostrarResumo());
    JButton butFinalizar = new JButton("Finalizar");
    pnBotoesSuperior.add(butFinalizar);
    butFinalizar.addActionListener(e -> finalizar());
    // Montagem Final
    acertarGbc(1, 1);
    pnSuperior.add(pnLinhaSuperior, gbc);
    acertarGbc(2, 1);
    pnSuperior.add(pnBotoesSuperior, gbc);
  }

  private void montagemPainelCentral() {
    layCentral = new GridBagLayout();
    pnCentral = new JPanel();
    pnCentral.setLayout(layCentral);
    // Montar o grupo de Opções
    opcoes = new ButtonGroup();
    radOpc = new JRadioButton[4];
    for (char c = 'A'; c < 'E'; c++) {
      radOpc[c - 65] = new JRadioButton("Op\347\343o " + c + ":");
      opcoes.add(radOpc[c - 65]);
      this.acaoRadio(radOpc[c - 65], c);
    }
    // Criar os TextArea
    taQuestao = new JTextArea[5];
    for (byte i = 0; i < taQuestao.length; i++) {
      taQuestao[i] = new JTextArea();
    }
    // Montar o Layout
    this.adicionarCentral(new JLabel("Pergunta:"), 1, 1, 1, 1);
    this.adicionarCentral(this.getScroll(taQuestao[0]), 1, 2, 1, 1);
    this.adicionarCentral(radOpc[0], 2, 1, 1, 1);
    this.adicionarCentral(this.getScroll(taQuestao[1]), 2, 2, 1, 1);
    this.adicionarCentral(radOpc[1], 3, 1, 1, 1);
    this.adicionarCentral(this.getScroll(taQuestao[2]), 3, 2, 1, 1);
    this.adicionarCentral(radOpc[2], 4, 1, 1, 1);
    this.adicionarCentral(this.getScroll(taQuestao[3]), 4, 2, 1, 1);
    this.adicionarCentral(radOpc[3], 5, 1, 1, 1);
    this.adicionarCentral(this.getScroll(taQuestao[4]), 5, 2, 1, 1);
  }

  private void acaoRadio(JRadioButton radio, final char opc) {
    radio.addActionListener(e -> marcarOpcao(opc));
  }

  private JScrollPane getScroll(JTextArea ta) {
    formatarArea(ta);
    JScrollPane scroll = new JScrollPane(ta);
    scroll.setHorizontalScrollBarPolicy(31);
    scroll.setPreferredSize(new Dimension(700, 80));
    return scroll;
  }

  private void formatarArea(JTextArea ta) {
    ta.setLineWrap(true);
    ta.setFont(new java.awt.Font("Arial", 0, 14));
    ta.setWrapStyleWord(true);
    ta.setEditable(false);
  }

  private void acertarGbc(int lin, int col) {
    gbc.gridy = lin;
    gbc.gridx = col;
  }

  private void adicionarCentral(Component component, int lin, int col, int larg, int alt) {
    acertarGbc(lin, col);
    gbc.gridwidth = larg;
    gbc.gridheight = alt;
    layCentral.setConstraints(component, gbc);
    pnCentral.add(component);
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
    if (!questoes.isEmpty()) {
      Questao qst = questoes.get(qstAtual - 1);
      labQuestao.setText((new StringBuilder("Quest\343o ")).append(Comuns.colocaZero(qstAtual, 3)).append(" de ")
          .append(Comuns.colocaZero(qstTotal, 3)).toString());
      labRef.setText((new StringBuilder("#Ref.")).append(qst.getIdentificacao()).toString());
      taQuestao[0].setText(qst.getPergunta());
      taQuestao[1].setText(qst.getOpcaoA());
      taQuestao[2].setText(qst.getOpcaoB());
      taQuestao[3].setText(qst.getOpcaoC());
      taQuestao[4].setText(qst.getOpcaoD());
      for (byte i = 0; i < taQuestao.length; i++) {
        taQuestao[i].setCaretPosition(0);
      }
      chkMarcar.setSelected(qst.isMarcar());
      opcoes.clearSelection();
      if (qst.getOpcaoEscolhida() > 0) {
        radOpc[qst.getOpcaoEscolhida() - 65].setSelected(true);
      }
    }
  }

  private void pularQuestao() {
    try {
      int pularPara = Integer.parseInt(edtIrQuestao.getText());
      irParaQuestao(pularPara);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Informar um N\372mero de Quest\343o v\341lido", "Erro Informa\347\343o", 0);
    }
  }

  private void irParaQuestao(int pos) {
    try {
      if (pos < 1) {
        throw new ErroOperacaoException("menor");
      }
      if (pos > qstTotal) {
        throw new ErroOperacaoException("maior");
      }
      qstAtual = pos;
      mostrarQuestao();
      edtIrQuestao.setText("");
    } catch (ErroOperacaoException e) {
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
    DefaultListModel<Questao> lstModel = new DefaultListModel<>();
    for (Questao q : questoes) {
      lstModel.addElement(q);
    }
    JList<Questao> lista = new JList<>(lstModel);
    Resumo resumo = new Resumo(lista, tempo);
    if (resumo.isFinalizar()) {
      finalizar();
    }
    if (resumo.getQstAtual() > -1) {
      irParaQuestao(resumo.getQstAtual());
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
      labTempo.setText("Tempo Transcorrido: " + tempo.transHora());
      tempo.reduz();
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException exception) {
        Thread.currentThread().interrupt();
      }
    }
    if (!tempo.isMaiorZero()) {
      JOptionPane.showMessageDialog(this, "Tempo Terminado. Prova ser\341 finalizada");
      new Desempenho(questoes, tempo.getIntHora());
      this.dispose();
    }
  }
}
