package DAO;

import entities.ProdutoPerecivel;

public class ProdutoPerecivelDAO {

    public void cadastrarProdutoPerecivel(ProdutoPerecivel pp) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlProdutoPerecivel = "INSERT INTO produto_perecivel (data_validade,     )"
    }
}
