package exceptions;

public class DataFormatoIncorretoException extends RuntimeException {

    public DataFormatoIncorretoException() {
        super("A data está em um formato incorreto. O formato esperado é dd/MM/yyyy.");
    }

    public DataFormatoIncorretoException(String message) {
        super(message);
    }
}
