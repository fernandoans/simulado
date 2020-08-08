package simulado.acessor;

import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

public final class Comuns {

  public static final Atributo atributo = new Atributo();

  private Comuns() {
    // Evitar que a classe gere objetos
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
            atributo.setProva(valor);
          } else if ("TEMPO".equals(chave)) {
            StringTokenizer tok = new StringTokenizer(valor, "*");
            int hora;
            for (hora = 1; tok.hasMoreTokens(); hora *= Integer.parseInt(tok.nextToken()))
              ;
            atributo.setTempo(hora);
          } else if ("TOTAL_QUESTAO".equals(chave)) {
            atributo.setTotQuestao(Integer.parseInt(valor));
          } else if ("AREA_ESCOLHIDA".equals(chave)) {
            atributo.setAreaEsc(valor);
          } else if ("GRUPO_ESCOLHIDO".equals(chave)) {
            atributo.setGrupoEsc(valor);
          } else if ("AULA".equals(chave)) {
            atributo.setAula(Integer.parseInt(valor));
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
    StringBuilder ret = new StringBuilder("" + num);
    while (ret.length() < tam) {
      ret.insert(0, "0");
    }
    return ret.toString();
  }

  public static String montarTam(String texto, int tam) {
    if (texto.length() > tam + 3) {
      texto = texto.substring(0, tam) + "...";
    }
    StringBuilder ret = new StringBuilder(texto);
    while (ret.length() < tam + 3) {
      ret.append(" ");
    }
    return ret.toString();
  }
}
