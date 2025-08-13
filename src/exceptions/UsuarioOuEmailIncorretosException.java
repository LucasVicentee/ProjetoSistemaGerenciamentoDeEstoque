package exceptions;

public class UsuarioOuEmailIncorretosException extends RuntimeException {

  public UsuarioOuEmailIncorretosException(String usuario, String email) {

    super("usuário " + usuario + " e email: " + email + " incorretos.");
  }

    public UsuarioOuEmailIncorretosException(String message) {

      super(message);
    }
}
