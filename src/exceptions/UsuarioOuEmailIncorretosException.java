package exceptions;

public class UsuarioOuEmailIncorretosException extends RuntimeException {
    public UsuarioOuEmailIncorretosException(String message) {
        super(message);
    }
}
