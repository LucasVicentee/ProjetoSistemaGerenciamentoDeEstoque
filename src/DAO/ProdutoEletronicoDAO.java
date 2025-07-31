package DAO;

import connection.Conexao;
import entities.ProdutoEletronico;

import java.sql.*;

public class ProdutoEletronicoDAO {

    public void cadastrarProdutoEletronico(ProdutoEletronico pe) {

        String sqlProduto = "INSERT INTO produto (nome, preco, quantidade, data_fabricacao, fabricante) VALUES (?, ?, ?, ?, ?)";
        String sqlEletronico = "INSERT INTO produto_eletronico (id, garantia_meses, voltagem) VALUES (?, ?, ?)";

        // Usando try-with-resources para abrir a conexão e garantir que será fechada automaticamente
        try (Connection conn = Conexao.getConexao();
             PreparedStatement psProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)) //Statement.RETURN_GENERATED_KEYS Pega a chave primária criada
        {
            // Inserir no produto
            psProduto.setString(1, pe.getNome());
            psProduto.setDouble(2, pe.getPreco());
            psProduto.setInt(3, pe.getQuantidade());
            psProduto.setDate(4, new java.sql.Date(pe.getDataFabricacao().getTime()));
            psProduto.setString(5, pe.getFabricante());
            psProduto.executeUpdate();

            // Obter o ID gerado do auto_increment do banco de dados
            try (ResultSet rs = psProduto.getGeneratedKeys();) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    pe.setId(idGerado); // define o ID no objeto também
                }
            }

            // Inserir na tabela produto_eletronico
            try (PreparedStatement psEletronico = conn.prepareStatement(sqlEletronico))
            {
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

        // Usando try-with-resources para abrir a conexão e garantir que será fechada automaticamente
        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);

            // Usando try-with-resources para preparar as statements
            try (PreparedStatement psProdutoEletronico = conn.prepareStatement(sqlProdutoEletronico);
                 PreparedStatement psProduto = conn.prepareStatement(sqlProduto)) {

                psProdutoEletronico.setInt(1, id);
                psProdutoEletronico.executeUpdate();

                psProduto.setInt(1, id);
                psProduto.executeUpdate();

                int produtoExcluidoEletronico = psProdutoEletronico.executeUpdate();
                int produtExcluido = psProduto.executeUpdate();

                //Caso o ID seja inválido o sistema não realizará a exclusão de nada
                if (produtoExcluidoEletronico == 0 || produtExcluido == 0) {
                    System.out.println("Nenhum produto com este ID foi encontrado.");
                    conn.rollback();
                }
                else {
                    //Quando o ID foi encontrado o sistema avisará e excluirá o produto do sistema
                    System.out.println("Produto Eletrônico excluído com sucesso.");
                    conn.commit();
                }

            } catch (SQLException e) {
                // Se der erro, faz rollback da transação
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarProdutoEletronicoPorID(int id) {
        String sqlProdutoEletronico = "SELECT \n" +
                "    p.id,\n" +
                "    p.nome,\n" +
                "    p.preco,\n" +
                "    p.quantidade,\n" +
                "    p.data_fabricacao,\n" +
                "    p.fabricante,\n" +
                "    pe.garantia_meses,\n" +
                "    pe.voltagem\n" +
                "FROM \n" +
                "    produto p\n" +
                "JOIN \n" +
                "    produto_eletronico pe ON p.id = pe.id\n" +
                "WHERE pe.id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement psProdutoEletronico = conn.prepareStatement(sqlProdutoEletronico)) {

            psProdutoEletronico.setInt(1, id);

            try (ResultSet rs = psProdutoEletronico.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Produto encontrado!");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Preço: " + rs.getDouble("preco"));
                    System.out.println("Quantidade: " + rs.getDouble("quantidade"));
                    System.out.println("Data de fabricação: " + rs.getDate("data_fabricacao"));
                    System.out.println("Fabricante: " + rs.getString("fabricante"));
                    System.out.println("Garantia em meses: " + rs.getInt("garantia_meses"));
                    System.out.println("Voltagem: " + rs.getString("voltagem"));
                } else {
                    System.out.println("Nenhum produto foi encontrado.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
