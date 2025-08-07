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
                    throw new ProdutoNaoEncontradoException(id);
                }
                else {
                    System.out.println(" Produto de limpeza do ID: " + id + " excluído com sucesso!");
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
        "produto_limpeza pl ON p.id = pl.id" +
        "WHERE pl.id = ?";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement psProdutoLimpeza = conn.prepareStatement(sqlProdutoLimpeza)) {
            psProdutoLimpeza.setInt(1, id);

            try (ResultSet rs = psProdutoLimpeza.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Produto encontrado!");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Preço: " + rs.getDouble("preco"));
                    System.out.println("Quantidade: " + rs.getInt("quantidade"));
                    System.out.println("Data de fabricação: " + rs.getDate("data_fabricacao"));
                    System.out.println("Fabricante: " + rs.getString("fabricante"));
                    System.out.println("Fragrância: " + rs.getString("fragrancia"));
                    System.out.println("Volume em ML: " + rs.getInt("volume_ml"));
                    System.out.println("Uso: " + rs.getString("uso"));
                }
                else {
                    throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarDadosEspecificosProdutoLimpeza(int id, String novoNome, double novoPreco, int novaQuantidade, Date novaDataFabricacao, String novoFabricante, String novaFragrancia, int novoVolumeMl, String novoUso) {

        String sqlNovosDadosProduto = "UPDATE produto SET nome = ?, preco = ?, quantidade = ?, data_fabricacao = ?, fabricante = ? WHERE id = ?";
        String sqlNovosDadosProdutoLimpeza = "UPDATE produto_limpeza SET fragrancia = ?, volume_ml = ?, uso = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement psNovoProduto = conn.prepareStatement(sqlNovosDadosProduto);
        PreparedStatement psNovoProdutoLimpeza = conn.prepareStatement(sqlNovosDadosProdutoLimpeza)){

            psNovoProduto.setString(1, novoNome);
            psNovoProduto.setDouble(2, novoPreco);
            psNovoProduto.setInt(3, novaQuantidade);
            psNovoProduto.setDate(4, new java.sql.Date(novaDataFabricacao.getTime()));
            psNovoProduto.setString(5, novoFabricante);
            psNovoProduto.setInt(6, id);

            psNovoProdutoLimpeza.setString(1, novaFragrancia);
            psNovoProdutoLimpeza.setInt(2, novoVolumeMl);
            psNovoProdutoLimpeza.setString(3, novoUso);
            psNovoProdutoLimpeza.setInt(4, id);

            int linhasAfetadasProduto = psNovoProduto.executeUpdate();
            int linhasAfetadasLimpeza = psNovoProduto.executeUpdate();

            if (linhasAfetadasProduto == 0 || linhasAfetadasLimpeza == 0) {
                throw new ProdutoNaoEncontradoException(id);
            }
            else {
                System.out.println("Dados do produto atualizados com sucesso!");
            }
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }
}
