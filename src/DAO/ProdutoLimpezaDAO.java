package DAO;

import connection.Conexao;

import entities.ProdutoLimpeza;

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
}
