package entities;

import java.util.Date;

public class ProdutoPerecivel extends Produto {
    private Date dataValidade;

    public ProdutoPerecivel() {

    }

    public ProdutoPerecivel(Integer id, String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, Date dataValidade) {
        super(id, nome, preco, quantidade, dataFabricacao, fabricante);
        this.dataValidade = dataValidade;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
}
