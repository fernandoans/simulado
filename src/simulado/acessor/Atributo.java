package simulado.acessor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

public final class Atributo {

  public static final String COPYRIGHT = "2020 \251 Fernando Anselmo";
  public static final String CFVERSAO = "Versao 1.0";
  public static String prova = "";
  public static String titulo = "";
  public static int tempo = 14400;
  public static int totQuestao = -1;
  public static int aula = -1;
  public static List<String> ac = new ArrayList<>();
  public static List<String> gp = new ArrayList<>();
  public static String areaEsc = "";
  public static String grupoEsc = "";

  private Atributo() {
  }

  public static void carAtributo() {
    List<String> opcoes = ArquivosTexto.abrirArquivo();
    if (opcoes != null) {
      String chave;
      String valor;
      try {
        for (String linha : opcoes) {
          chave = linha.substring(0, linha.indexOf('='));
          if (linha.indexOf('=') + 1 == linha.length()) {
            continue;
          }
          valor = linha.substring(linha.indexOf('=') + 1);
          if ("PROVA".equals(chave)) {
            prova = valor;
          } else if ("TEMPO".equals(chave)) {
            StringTokenizer tok = new StringTokenizer(valor, "*");
            int hora;
            for (hora = 1; tok.hasMoreTokens(); hora *= Integer.parseInt(tok.nextToken()))
              ;
            tempo = hora;
          } else if ("TOTAL_QUESTAO".equals(chave)) {
            totQuestao = Integer.parseInt(valor);
          } else if ("AREA_ESCOLHIDA".equals(chave)) {
            areaEsc = valor;
          } else if ("GRUPO_ESCOLHIDO".equals(chave)) {
            grupoEsc = valor;
          } else if ("AULA".equals(chave)) {
            aula = Integer.parseInt(valor);
          }
        }
      } catch (Exception e) {
        System.out.println("Arquivo 'opcao.sim' mal formado, usando valores padr\365es.");
      }
    } else {
      System.out.println("N\343o existe o arquivo 'opcao.sim'.");
    }
  }

  public static ImageIcon getImage(String s) {
    URL url = getResource((new StringBuilder("simulado/imagens/")).append(s).toString());
    if (url != null) {
      return new ImageIcon(url);
    }
    return null;
  }

  public static URL getResource(String s) {
    return ClassLoader.getSystemResource(s);
  }

  public static String colocaZero(int num, int tam) {
    String ret;
    for (ret = (new StringBuilder()).append(num).toString(); ret.length() < tam; ret = (new StringBuilder("0"))
        .append(ret).toString())
      ;
    return ret;
  }

  public static String montarTam(String texto, int tam) {
    String ret = texto;
    if (ret.length() > tam + 3)
      ret = (new StringBuilder(String.valueOf(ret.substring(0, tam)))).append("...").toString();
    for (; ret.length() < tam + 3; ret = (new StringBuilder(String.valueOf(ret))).append(" ").toString())
      ;
    return ret;
  }
}
