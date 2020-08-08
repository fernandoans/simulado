package simulado;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import simulado.acessor.Comuns;
import simulado.acessor.Questao;
import simulado.acessor.Tempo;

public class Desempenho extends JDialog {

  private static final long serialVersionUID = 2L;
  private final String finFonte = "Helvetica";
  private JLabel labGasto;
  private JLabel labAcertadas;
  private JLabel labMedia;
  private JLabel labPercentual;
  private JLabel[] labAC;
  private JLabel[] labGP;
  private List<Questao> questoes;
  private int totalTempo;
  private int totalFeitos;
  private Locale local;
  private DecimalFormat format;
  private int acerto;
  private int[] aAC;
  private int[] tAC;
  private int[] aGP;
  private int[] tGP;

  public Desempenho(List<Questao> questoes, int totalTempo) {
    labAC = new JLabel[Comuns.atributo.getAc().size()];
    labGP = new JLabel[Comuns.atributo.getGp().size()];
    local = new Locale("pt", "BR");
    format = new DecimalFormat("##0.00", new DecimalFormatSymbols(local));
    aAC = new int[Comuns.atributo.getAc().size()];
    tAC = new int[Comuns.atributo.getAc().size()];
    aGP = new int[Comuns.atributo.getGp().size()];
    tGP = new int[Comuns.atributo.getGp().size()];
    this.questoes = questoes;
    this.totalTempo = totalTempo;
    computarAcertos();
    montarTela();
  }

  private final void montarTela() {
    this.setTitle("Desempenho");
    this.setSize(600, 350);
    this.setLocationRelativeTo(null);
    this.setModal(true);

    JPanel pnColuna1 = new JPanel(new GridLayout(10, 1));
    pnColuna1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    pnColuna1.add(new JLabel("De Tempo:"));
    int resHora = Comuns.atributo.getTempo() - totalTempo;
    labGasto = new JLabel("Gasto: " + Tempo.fmtHora(resHora));
    pnColuna1.add(labGasto);
    resHora = totalFeitos != 0 ? resHora / totalFeitos : 0;
    labMedia = new JLabel("M\351dia gasta por quest\343o: " + Tempo.fmtHora(resHora));
    pnColuna1.add(labGasto);
    pnColuna1.add(new JLabel(""));
    pnColuna1.add(new JLabel("De Quest\365es:"));
    labAcertadas = new JLabel("Quantidade Acertadas: " + acerto + " de " + questoes.size());
    pnColuna1.add(labAcertadas);
    labPercentual = new JLabel("Percentual Acertado: " + mstPercentual(acerto, questoes.size()) + " %");
    pnColuna1.add(labMedia);
    pnColuna1.add(new JLabel(""));
    JButton btSalvar = new JButton("Salvar");
    btSalvar.addActionListener(e -> salvar());
    pnColuna1.add(obterCompPanel(btSalvar));

    JPanel pnColuna2 = new JPanel();
    pnColuna2.add(new JLabel("Por \301rea de conhecimento:"));
    JTextArea areas = new JTextArea(8, 40);
    int i = 0;
    for (String area : Comuns.atributo.getAc()) {
      labAC[i] = new JLabel(area + ": " + aAC[i] + " de " + tAC[i] + " (" + mstPercentual(aAC[i], tAC[i]) + " %)");
      areas.append(labAC[i].getText() + '\n');
      i += 1;
    }
    areas.setFont(new Font("Courier New", 0, 12));
    pnColuna2.add(new JScrollPane(areas));
    pnColuna2.add(new JLabel("Por grupo de processo:"));
    JTextArea grupos = new JTextArea(8, 40);
    i = 0;
    for (String grupo : Comuns.atributo.getGp()) {
      labGP[i] = new JLabel(grupo + ": " + aGP[i] + " de " + tGP[i] + " (" + mstPercentual(aGP[i], tGP[i]) + " %)");
      grupos.append(labGP[i].getText() + '\n');
      i += 1;
    }
    grupos.setFont(new Font("Courier New", 0, 12));
    pnColuna2.add(new JScrollPane(grupos));

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        aoFechar();
      }
    });

    this.setLayout(new GridLayout(1, 2));
    this.add(pnColuna1);
    this.add(pnColuna2);

    setVisible(true);
  }

  private void aoFechar() {
    dispose();
  }

  private String mstPercentual(int val1, int val2) {
    if (val2 == 0) {
      return "0,00";
    }
    return format.format(((double) val1 * 100D) / (double) val2);
  }

  public JPanel obterCompPanel(Component c) {
    JPanel pn = new JPanel();
    pn.add(c);
    return pn;
  }

  // ---------------------------------
  // Montar Relatório em PDF
  // ---------------------------------

  private void salvar() {
    Document document = new Document(PageSize.A4, 50F, 50F, 50F, 50F);
    try {
      PdfWriter.getInstance(document, new FileOutputStream("desempenho.pdf"));
      document.open();
      document.add(Image.getInstance(Comuns.getResource("simulado/imagens/simulpeq.png")));
      document.add(
          new Paragraph(Comuns.atributo.getProva(), FontFactory.getFont(finFonte, 22F, 1, new BaseColor(0, 83, 117))));
      document.add(new Paragraph("Desempenho", FontFactory.getFont(finFonte, 18F, 3, new BaseColor(0, 69, 98))));
      document.add(new Paragraph("De Tempo:", FontFactory.getFont(finFonte, 14F, 3, new BaseColor(190, 214, 218))));
      document.add(new Paragraph(labGasto.getText()));
      document.add(new Paragraph(labMedia.getText()));
      document
          .add(new Paragraph("De Quest\365es:", FontFactory.getFont(finFonte, 14F, 3, new BaseColor(190, 214, 218))));
      document.add(new Paragraph(labAcertadas.getText()));
      document.add(new Paragraph(labPercentual.getText()));
      document.add(new Paragraph("Por \301rea de conhecimento:",
          FontFactory.getFont(finFonte, 14F, 3, new BaseColor(190, 214, 218))));
      for (int i = 0; i < Comuns.atributo.getAc().size(); i++) {
        document.add(new Paragraph(labAC[i].getText()));
      }
      document.add(
          new Paragraph("Por grupo de processo:", FontFactory.getFont(finFonte, 14F, 3, new BaseColor(190, 214, 218))));
      for (int i = 0; i < Comuns.atributo.getGp().size(); i++) {
        document.add(new Paragraph(labGP[i].getText()));
      }
      document.add(
          new Paragraph("Resumo das Quest\365es", FontFactory.getFont(finFonte, 18F, 1, new BaseColor(0, 69, 98))));
      String resultado = "";
      boolean acertou = false;
      Iterator<Questao> iterator = questoes.iterator();
      while (iterator.hasNext()) {
        Questao qst = iterator.next();
        acertou = qst.isCorrigir();
        if (acertou) {
          resultado = "Correta";
        } else if (qst.getOpcaoEscolhida() == 0) {
          resultado = "N\343o respondida";
        } else {
          resultado = "Incorreta";
        }
        document.add(new Paragraph((new StringBuilder(String.valueOf(qst.getIdentificacao()))).append(" - ")
            .append(qst.getOpcaoEscolhida()).append(" - ").append(resultado).toString()));
        if (!acertou) {
          switch (qst.getResposta()) {
          case 65: // 'A'
            resultado = qst.getOpcaoA();
            break;
          case 66: // 'B'
            resultado = qst.getOpcaoB();
            break;
          case 67: // 'C'
            resultado = qst.getOpcaoC();
            break;
          case 68: // 'D'
            resultado = qst.getOpcaoD();
            break;
          default: // Errada
            System.out.println("Resposta Errada: " + qst.getResposta());
          }
          document.add(new Paragraph(
              (new StringBuilder(String.valueOf(qst.getPergunta()))).append(" - ").append(resultado).toString(),
              FontFactory.getFont("Courier", 9F, 0, new BaseColor(0, 0, 0))));
        }
      }
      document.close();
      JOptionPane.showMessageDialog(this, "O arquivo 'desempenho.pdf' foi salvo corretamente.");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro na Gera\347\343o do Arquivo de Desempenho", "Erro", 0);
    }
  }

  private void computarAcertos() {
    totalFeitos = 0;
    for (Questao qst : questoes) {
      if ("ABCD".indexOf(qst.getOpcaoEscolhida()) > -1) {
        totalFeitos++;
      }
      int i = 0;
      for (String area : Comuns.atributo.getAc()) {
        if (qst.getArea().equals(area)) {
          tAC[i]++;
          if (qst.isCorrigir()) {
            aAC[i]++;
          }
          break;
        }
        i += 1;
      }
      i = 0;
      for (String grupo : Comuns.atributo.getGp()) {
        if (qst.getGrupo().equals(grupo)) {
          tGP[i]++;
          if (qst.isCorrigir()) {
            aGP[i]++;
          }
          break;
        }
        i += 1;
      }
    }
  }
}
