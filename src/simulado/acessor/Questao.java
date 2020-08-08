package simulado.acessor;

public final class Questao {

  private int numQuestao;
  private String identificacao;
  private String pergunta;
  private String opcaoA;
  private String opcaoB;
  private String opcaoC;
  private String opcaoD;
  private char resposta;
  private String area;
  private String grupo;
  private char opcaoEscolhida;
  private boolean marcar;

  public Questao(String identificacao, String pergunta, String opcaoA, String opcaoB, String opcaoC, String opcaoD,
      char resposta, String area, String grupo) {
    setIdentificacao(identificacao);
    setPergunta(pergunta);
    setOpcaoA(opcaoA);
    setOpcaoB(opcaoB);
    setOpcaoC(opcaoC);
    setOpcaoD(opcaoD);
    setResposta(resposta);
    setArea(area);
    setGrupo(grupo);
  }

  public int getNumQuestao() {
    return numQuestao;
  }

  public void setNumQuestao(int numQuestao) {
    this.numQuestao = numQuestao;
  }

  public String getIdentificacao() {
    return identificacao;
  }

  public void setIdentificacao(String identificacao) {
    this.identificacao = identificacao;
  }

  public String getPergunta() {
    return pergunta;
  }

  public void setPergunta(String pergunta) {
    this.pergunta = pergunta;
  }

  public String getOpcaoA() {
    return opcaoA;
  }

  public void setOpcaoA(String opcaoA) {
    this.opcaoA = opcaoA;
  }

  public String getOpcaoB() {
    return opcaoB;
  }

  public void setOpcaoB(String opcaoB) {
    this.opcaoB = opcaoB;
  }

  public String getOpcaoC() {
    return opcaoC;
  }

  public void setOpcaoC(String opcaoC) {
    this.opcaoC = opcaoC;
  }

  public String getOpcaoD() {
    return opcaoD;
  }

  public void setOpcaoD(String opcaoD) {
    this.opcaoD = opcaoD;
  }

  public char getResposta() {
    return resposta;
  }

  public void setResposta(char resposta) {
    this.resposta = resposta;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getGrupo() {
    return grupo;
  }

  public void setGrupo(String grupo) {
    this.grupo = grupo;
  }

  public char getOpcaoEscolhida() {
    return opcaoEscolhida;
  }

  public void setOpcaoEscolhida(char opcaoEscolhida) {
    this.opcaoEscolhida = opcaoEscolhida;
  }

  public boolean isMarcar() {
    return marcar;
  }

  public void setMarcar(boolean marcar) {
    this.marcar = marcar;
  }

  public boolean isCorrigir() {
    return resposta == opcaoEscolhida;
  }

  public String toString() {
    String opcao = "";
    switch (opcaoEscolhida) {
    case 65: // 'A'
      opcao = opcaoA;
      break;
    case 66: // 'B'
      opcao = opcaoB;
      break;
    case 67: // 'C'
      opcao = opcaoC;
      break;
    case 68: // 'D'
      opcao = opcaoD;
      break;
    default: // Errada
      opcao = "Não Informada";
    }
    return (new StringBuilder("  ")).append(Comuns.colocaZero(numQuestao, 3)).append("    ")
        .append(Comuns.montarTam(pergunta, 36)).append("    ").append(Comuns.montarTam(opcao, 44)).append("    ")
        .append(marcar ? "Sim" : "N\343o").toString();
  }
}
