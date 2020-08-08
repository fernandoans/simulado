package simulado.acessor;

public class ErroOperacaoException extends RuntimeException {

  private static final long serialVersionUID = 7L;

  public ErroOperacaoException(String mensagem) {
    super(mensagem);
  }
}
