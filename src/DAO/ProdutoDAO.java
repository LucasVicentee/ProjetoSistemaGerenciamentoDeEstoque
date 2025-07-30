package DAO;

import connection.Conexao;
import entities.Produto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    public void cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, quantidade, datafabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            ps.setDate(4, new java.sql.Date(produto.getDataFabricacao().getTime()));
            ps.setString(5, produto.getFabricante());

            ps.executeUpdate();
            System.out.println("Produto inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerProduto(Produto produto) {
        String sql = "DELETE FROM PRODUTO WHERE ID = ?";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, produto.getId());

            ps.execute();
            ps.close();
            System.out.println("Produto removido com sucesso!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Produto buscarProdutoPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        Produto produto = null;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setDataFabricacao(rs.getDate("datafabricacao"));
                produto.setFabricante(rs.getString("fabricante"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }
}
