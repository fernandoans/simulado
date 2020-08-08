package simulado.acessor;

import java.util.HashSet;
import java.util.Set;

public class Atributo {

  public static final String COPYRIGHT = "2020 - Fernando Anselmo - Licença GPL";
  public static final String CFVERSAO = "Versao 1.1";
  private String prova = "";
  private String titulo = "";
  private int tempo = 14400;
  private int totQuestao = -1;
  private int aula = -1;
  private Set<String> ac = new HashSet<>();
  private Set<String> gp = new HashSet<>();
  private String areaEsc = "";
  private String grupoEsc = "";

  public String getProva() {
    return prova;
  }

  public void setProva(String prova) {
    this.prova = prova;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public int getTempo() {
    return tempo;
  }

  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  public int getTotQuestao() {
    return totQuestao;
  }

  public void setTotQuestao(int totQuestao) {
    this.totQuestao = totQuestao;
  }

  public int getAula() {
    return aula;
  }

  public void setAula(int aula) {
    this.aula = aula;
  }

  public Set<String> getAc() {
    return ac;
  }

  public void setAc(Set<String> ac) {
    this.ac = ac;
  }

  public Set<String> getGp() {
    return gp;
  }

  public void setGp(Set<String> gp) {
    this.gp = gp;
  }

  public String getAreaEsc() {
    return areaEsc;
  }

  public void setAreaEsc(String areaEsc) {
    this.areaEsc = areaEsc;
  }

  public String getGrupoEsc() {
    return grupoEsc;
  }

  public void setGrupoEsc(String grupoEsc) {
    this.grupoEsc = grupoEsc;
  }
}
