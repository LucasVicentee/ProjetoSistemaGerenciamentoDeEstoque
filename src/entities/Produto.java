package entities;

import java.util.Date;

public class Produto {
    protected Integer id;
    protected String nome;
    protected double preco;
    protected int quantidade;
    protected Date dataFabricacao;
    protected String fabricante;

    public Produto() {

    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Produto(Integer id, String nome, double preco, int quantidade, Date dataFabricacao, String fabricante) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.dataFabricacao = dataFabricacao;
        this.fabricante = fabricante;
    }

    public Produto(String nome, double preco, int quantidade, Date dataFabricacao, String fabricante) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.dataFabricacao = dataFabricacao;
        this.fabricante = fabricante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
