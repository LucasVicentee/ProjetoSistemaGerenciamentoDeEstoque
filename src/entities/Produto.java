package entities;

import java.util.Date;

public class Produto {
    private Integer id;
    private String nome;
    private double preco;
    private int quantidade;
    private Date data_fabricacao;
    private String fabricante;

    public Produto() {

    }

    public Produto(Integer id, String nome, double preco, int quantidade, Date data_fabricacao, String fabricante) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data_fabricacao = data_fabricacao;
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

    public Date getData_fabricacao() {
        return data_fabricacao;
    }

    public void setData_fabricacao(Date data_fabricacao) {
        this.data_fabricacao = data_fabricacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
