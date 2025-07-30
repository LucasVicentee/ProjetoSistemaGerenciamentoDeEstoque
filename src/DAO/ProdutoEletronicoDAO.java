package DAO;

import connection.Conexao;
import entities.ProdutoEletronico;

import java.sql.*;

public class ProdutoEletronicoDAO {

    public void cadastrarProdutoEletronico(ProdutoEletronico produto) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlEletronico = "INSERT INTO produto_eletronico (id, garantia_meses, voltagem) VALUES (?, ?, ?)";

        try (
                Connection conn = Conexao.getConexao();
                PreparedStatement psProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Inserir no produto
            psProduto.setString(1, produto.getNome());
            psProduto.setDouble(2, produto.getPreco());
            psProduto.setInt(3, produto.getQuantidade());
            psProduto.setDate(4, new java.sql.Date(produto.getDataFabricacao().getTime()));
            psProduto.setString(5, produto.getFabricante());
            psProduto.executeUpdate();

            // Obter o ID gerado
            ResultSet rs = psProduto.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                produto.setId(idGerado); // define o ID no objeto também
            }

            // Inserir no produto_eletronico
            try (
                PreparedStatement psEletronico = conn.prepareStatement(sqlEletronico)) {
                psEletronico.setInt(1, produto.getId());
                psEletronico.setInt(2, produto.getGarantiaMeses());
                psEletronico.setString(3, produto.getVoltagem());
                psEletronico.executeUpdate();
            }

            System.out.println("Produto eletrônico cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
