package entities;

import java.util.Date;

public class ProdutoLimpeza extends Produto{
    private String fragrancia;
    private int volumeMl;

    public ProdutoLimpeza() {

    }

    public ProdutoLimpeza(Integer id, String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, String fragrancia, int volumeMl) {
        super(id, nome, preco, quantidade, dataFabricacao, fabricante);
        this.fragrancia = fragrancia;
        this.volumeMl = volumeMl;
    }

    public String getFragrancia() {
        return fragrancia;
    }

    public void setFragrancia(String fragrancia) {
        this.fragrancia = fragrancia;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }
}
