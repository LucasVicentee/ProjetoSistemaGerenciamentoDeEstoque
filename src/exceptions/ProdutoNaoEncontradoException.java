package exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(int id) {

        super("Produto do ID " + id + " não encontrado no sistema.");
    }

    public ProdutoNaoEncontradoException(String message) {

        super(message);
    }
}
