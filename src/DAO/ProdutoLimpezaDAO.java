package DAO;

import connection.Conexao;

import entities.ProdutoLimpeza;
import exceptions.ProdutoNaoEncontradoException;

import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ProdutoLimpezaDAO {

    public void cadastrarProdutoLimpeza(ProdutoLimpeza pl) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlProdutoLimpeza = "INSERT INTO produto_limpeza (id, fragrancia, volume_ml, uso) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement psProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS))
        {
            psProduto.setString(1, pl.getNome());
            psProduto.setDouble(2, pl.getPreco());
            psProduto.setInt(3, pl.getQuantidade());
            psProduto.setDate(4, new java.sql.Date(pl.getDataFabricacao().getTime()));
            psProduto.setString(5, pl.getFabricante());
            psProduto.executeUpdate();

            try (ResultSet rs = psProduto.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    pl.setId(idGerado);
                }
            }

            try (PreparedStatement psLimpeza = conn.prepareStatement(sqlProdutoLimpeza))
            {
                psLimpeza.setInt(1, pl.getId());
                psLimpeza.setString(2, pl.getFragrancia());
                psLimpeza.setInt(3, pl.getVolumeMl());
                psLimpeza.setString(4, pl.getUso());
                psLimpeza.executeUpdate();

                System.out.println("Produto de Limpeza cadastrado com sucesso!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirProdutoLimpeza(int id) {

        String sqlProduto = "DELETE FROM produto WHERE ID = ?";
        String sqlProdutoLimpeza = "DELETE FROM produto_limpeza WHERE ID = ?";

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psProdutoLimpeza = conn.prepareStatement(sqlProdutoLimpeza);
            PreparedStatement psProduto = conn.prepareStatement(sqlProduto)) {

                psProdutoLimpeza.setInt(1, id);
                int produtoExcluidoLimpeza = psProdutoLimpeza.executeUpdate(); //Verificando a quantidade de linhas afetadas no banco de dados

                psProduto.setInt(1, id);
                int produtoExcluido = psProduto.executeUpdate(); //Verificando a quantidade de linhas afetadas no banco de dados


                if (produtoExcluidoLimpeza == 0 || produtoExcluido == 0) {
                    conn.rollback();
                    throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado.");
                }
                else {
                    System.out.println("Produto de limpeza do ID: " + id + " excluído com sucesso!");
                    conn.commit();
                }
            }
            catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buscarProdutoLimpezaPorID(int id) {

        String sqlProdutoLimpeza = "SELECT\n" +
            "p.id,\n" +
            "p.nome,\n" +
            "p.preco,\n" +
            "p.quantidade,\n" +
            "p.data_fabricacao,\n" +
            "p.fabricante,\n" +
            "pl.fragrancia,\n" +
            "pl.volume_ml,\n" +
            "pl.uso\n" +
        "FROM\n" +
        "produto p\n" +
        "JOIN\n" +
        "produto_limpeza pl ON p.id = pl.id";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement psProdutoLimpeza = conn.prepareStatement(sqlProdutoLimpeza)) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
