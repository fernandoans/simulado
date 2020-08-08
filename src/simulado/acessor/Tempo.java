package simulado.acessor;

public class Tempo {

  private int intHora;
  private int resHora;

  public Tempo() {
    intHora = 0;
    resHora = intHora;
  }

  public Tempo(int intHora) {
    this.intHora = 0;
    resHora = intHora;
    this.intHora = intHora;
  }

  public boolean isMaiorZero() {
    return intHora > 0;
  }

  public void reduz() {
    intHora--;
    resHora = intHora;
  }

  public int getIntHora() {
    return intHora;
  }

  public int getResHora() {
    return resHora;
  }

  public String transHora() {
    return fmtHora(resHora);
  }

  public static String fmtHora(int horaFmt) {
    int horHora = 0;
    int minHora = 0;
    int segHora = 0;
    if (horaFmt > 0) {
      horHora = horaFmt / 60 / 60;
      horaFmt -= horHora * 60 * 60;
      if (horaFmt > 0) {
        minHora = horaFmt / 60;
        segHora = horaFmt - minHora * 60;
      }
      return (new StringBuilder()).append(Comuns.colocaZero(horHora, 2)).append(":")
          .append(Comuns.colocaZero(minHora, 2)).append(":").append(Comuns.colocaZero(segHora, 2)).toString();
    }
    return "00:00:00";
  }

}
