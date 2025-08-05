package DAO;

import connection.Conexao;
import entities.ProdutoPerecivel;
import exceptions.ProdutoNaoEncontradoException;

import java.sql.*;

public class ProdutoPerecivelDAO {

    public void cadastrarProdutoPerecivel(ProdutoPerecivel pp) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlProdutoPerecivel = "INSERT INTO produto_perecivel (id, data_vencimento, tipo_produto, peso_gramas, temperatura_armazenamento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement psProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS))
        {
            psProduto.setString(1, pp.getNome());
            psProduto.setDouble(2, pp.getPreco());
            psProduto.setInt(3, pp.getQuantidade());
            psProduto.setDate(4, new java.sql.Date(pp.getDataFabricacao().getTime()));
            psProduto.setString(5, pp.getFabricante());

            psProduto.executeUpdate();

            try (ResultSet rs = psProduto.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    pp.setId(idGerado);
                }
            }

            try (PreparedStatement psProdutoPerecivel = conn.prepareStatement(sqlProdutoPerecivel)) {
                psProdutoPerecivel.setInt(1, pp.getId());
                psProdutoPerecivel.setDate(2, new java.sql.Date(pp.getDataVencimento().getTime()));
                psProdutoPerecivel.setString(3, pp.getTipoProduto());
                psProdutoPerecivel.setDouble(4, pp.getPesoGramas());
                psProdutoPerecivel.setString(5, pp.getTemperaturaArmazenamento());

                psProdutoPerecivel.executeUpdate();

                System.out.println("Produto perecível cadastrado com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirProdutoPerecivel(int id) {

        String sqlProduto = "DELETE FROM produto WHERE id = ?";
        String sqlProdutoPerecivel = "DELETE FROM produto_perecivel WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement psProduto = conn.prepareStatement(sqlProduto);
        PreparedStatement psProdutoPerecivel = conn.prepareStatement(sqlProdutoPerecivel)) {

            conn.setAutoCommit(false);

            psProdutoPerecivel.setInt(1, id);
            int ProdutoExcluidoPerecivel = psProdutoPerecivel.executeUpdate();

            psProduto.setInt(1, id);
            int ProdutoExcluido = psProduto.executeUpdate();

            if (ProdutoExcluidoPerecivel == 0 || ProdutoExcluido == 0) {
                conn.rollback();
                throw new ProdutoNaoEncontradoException(id);
            }
            else {
                conn.commit();
                System.out.println("Produto do ID " + id + " excluído com sucesso!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
