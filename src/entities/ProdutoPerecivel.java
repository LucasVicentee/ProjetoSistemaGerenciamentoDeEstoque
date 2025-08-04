package entities;

import java.util.Date;

public class ProdutoPerecivel extends Produto {
    private Date dataVencimento;
    private String tipoProduto;
    private double pesoGramas;
    private String temperaturaArmazenamento;

    public ProdutoPerecivel() {

    }

    public ProdutoPerecivel(Integer id, String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, Date dataVencimento, String tipoProduto, double pesoGramas, String temperaturaArmazenamento) {
        super(id, nome, preco, quantidade, dataFabricacao, fabricante);
        this.dataVencimento = dataVencimento;
        this.tipoProduto = tipoProduto;
        this.pesoGramas = pesoGramas;
        this.temperaturaArmazenamento = temperaturaArmazenamento;
    }

    public ProdutoPerecivel(String nome, double preco, int quantidade, Date dataFabricacao, String fabricante, Date dataVencimento, String tipoProduto, double pesoGramas, String temperaturaArmazenamento) {
        super(nome, preco, quantidade, dataFabricacao, fabricante);
        this.dataVencimento = dataVencimento;
        this.tipoProduto = tipoProduto;
        this.pesoGramas = pesoGramas;
        this.temperaturaArmazenamento = temperaturaArmazenamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public double getPesoGramas() {
        return pesoGramas;
    }

    public void setPesoGramas(double pesoGramas) {
        this.pesoGramas = pesoGramas;
    }

    public String getTemperaturaArmazenamento() {
        return temperaturaArmazenamento;
    }

    public void setTemperaturaArmazenamento(String temperaturaArmazenamento) {
        this.temperaturaArmazenamento = temperaturaArmazenamento;
    }
}
