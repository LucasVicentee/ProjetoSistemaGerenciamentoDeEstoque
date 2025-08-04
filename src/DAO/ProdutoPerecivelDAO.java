package DAO;

import connection.Conexao;
import entities.ProdutoPerecivel;

import java.sql.*;

public class ProdutoPerecivelDAO {

    public void cadastrarProdutoPerecivel(ProdutoPerecivel pp) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlProdutoPerecivel = "INSERT INTO produto_perecivel (data_vencimento, tipo_produto, peso_gramas, temperatura_armazenamento) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement psProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS))
        {
            psProduto.setString(1, pp.getNome());
            psProduto.setDouble(2, pp.getPreco());
            psProduto.setInt(3, pp.getQuantidade());
            psProduto.setDate(4, new java.sql.Date(pp.getDataFabricacao().getTime()));
            psProduto.setString(5, pp.getFabricante());

            try (ResultSet rs = psProduto.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    pp.setId(idGerado);
                }
            }

            try (PreparedStatement psProdutoPerecivel = conn.prepareStatement(sqlProdutoPerecivel)) {
                psProdutoPerecivel.setDate(1, new java.sql.Date(pp.getDataVencimento().getTime()));
                psProdutoPerecivel.setString(2, pp.getTipoProduto());
                psProdutoPerecivel.setDouble(3, pp.getPesoGramas());
                psProdutoPerecivel.setString(4, pp.getTemperaturaArmazenamento());

                System.out.println("Produto perecível cadastrado com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
