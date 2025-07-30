package DAO;

import connection.Conexao;
import entities.ProdutoEletronico;

import java.sql.*;

public class ProdutoEletronicoDAO {

    public void cadastrarProdutoEletronico(ProdutoEletronico pe) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlEletronico = "INSERT INTO produto_eletronico (id, garantia_meses, voltagem) VALUES (?, ?, ?)";

        try (
                Connection conn = Conexao.getConexao();
                PreparedStatement psProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Inserir no produto
            psProduto.setString(1, pe.getNome());
            psProduto.setDouble(2, pe.getPreco());
            psProduto.setInt(3, pe.getQuantidade());
            psProduto.setDate(4, new java.sql.Date(pe.getDataFabricacao().getTime()));
            psProduto.setString(5, pe.getFabricante());
            psProduto.executeUpdate();

            // Obter o ID gerado
            ResultSet rs = psProduto.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                pe.setId(idGerado); // define o ID no objeto também
            }
            rs.close();

            // Inserir no produto_eletronico
            try (
                PreparedStatement psEletronico = conn.prepareStatement(sqlEletronico)) {
                psEletronico.setInt(1, pe.getId());
                psEletronico.setInt(2, pe.getGarantiaMeses());
                psEletronico.setString(3, pe.getVoltagem());
                psEletronico.executeUpdate();
            }

            System.out.println("Produto eletrônico cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirProdutoEletronico(int id) {
        String sqlProdutoEletronico = "DELETE FROM produto_eletronico WHERE ID = ?";
        String sqlProduto = "DELETE FROM produto WHERE ID = ?";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement psProdutoEletronico = conn.prepareStatement(sqlProdutoEletronico);
            PreparedStatement psProduto = conn.prepareStatement(sqlProduto);

            psProdutoEletronico.setInt(1, id);
            psProdutoEletronico.executeUpdate();

            psProduto.setInt(1, id);
            psProduto.executeUpdate();

            System.out.println("Produto Eletrônico excluído com sucesso.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
