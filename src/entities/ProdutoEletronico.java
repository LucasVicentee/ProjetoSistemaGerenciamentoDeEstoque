package entities;

import java.util.Date;

public class ProdutoEletronico extends Produto{
    private int garantiaMeses;
    private String voltagem;

    public ProdutoEletronico() {

    }

    public ProdutoEletronico(Integer id, String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, int garantiaMeses, String voltagem) {
        super(id, nome, preco, quantidade, dataFabricacao, fabricante);
        this.garantiaMeses = garantiaMeses;
        this.voltagem = voltagem;
    }

    public ProdutoEletronico(String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, int garantiaMeses, String voltagem) {
        super(nome, preco, quantidade, dataFabricacao, fabricante);
        this.garantiaMeses = garantiaMeses;
        this.voltagem = voltagem;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }
}
