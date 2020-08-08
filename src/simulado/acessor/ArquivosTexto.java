package simulado.acessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ArquivosTexto {

  private static final String ARQ_OPCAO = "opcao.sim";

  private ArquivosTexto() {
  }

  public static boolean verificarExistencia(String nomeArq) {
    File f = new File(nomeArq);
    return f.exists();
  }

  public static List<String> abrirArquivo() {
    List<String> lista = null;
    try {
      if (verificarExistencia(ARQ_OPCAO)) {
        lista = new ArrayList<>();
        BufferedReader arqEntrada = new BufferedReader(new FileReader(ARQ_OPCAO));
        String linMnt = "";
        while ((linMnt = arqEntrada.readLine()) != null) {
          lista.add(linMnt);
        }
        arqEntrada.close();
      }
    } catch (IOException e) {
      System.out.println((new StringBuilder("Erro no arquivo 'opcao.sim': ")).append(e.getMessage()).toString());
    }
    return lista;
  }
}
