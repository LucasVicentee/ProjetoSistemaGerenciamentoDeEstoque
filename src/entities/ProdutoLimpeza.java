package entities;

import java.util.Date;

public class ProdutoLimpeza extends Produto{
    private String fragrancia;
    private int volumeMl;
    private String uso;

    public ProdutoLimpeza() {

    }

    public ProdutoLimpeza(Integer id, String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, String fragrancia, int volumeMl, String uso) {
        super(id, nome, preco, quantidade, dataFabricacao, fabricante);
        this.fragrancia = fragrancia;
        this.volumeMl = volumeMl;
        this.uso = uso;
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

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }
}
