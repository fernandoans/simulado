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
        resHora = this.intHora;
        this.intHora = intHora;
    }

    public boolean isMaiorZero() {
        return intHora > 0;
    }

    public void reduz() {
        intHora--;
        setResHora(intHora);
    }

    public int getIntHora() {
        return intHora;
    }

    public void setResHora(int resHora) {
        this.resHora = resHora;
    }

    public int getResHora() {
        return resHora;
    }

    public String transHora() {
        int horHora = 0;
        int minHora = 0;
        int segHora = 0;
        if (resHora > 0) {
            horHora = resHora / 60 / 60;
            resHora -= horHora * 60 * 60;
            if (resHora > 0) {
                minHora = resHora / 60;
                segHora = resHora - minHora * 60;
            }
        }
        return (new StringBuilder()).append(Atributo.colocaZero(horHora, 2)).append(":")
            .append(Atributo.colocaZero(minHora, 2)).append(":").append(Atributo.colocaZero(segHora, 2)).toString();
    }
}
