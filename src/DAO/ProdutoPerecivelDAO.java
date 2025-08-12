package DAO;

import connection.Conexao;
import entities.ProdutoPerecivel;
import exceptions.ProdutoNaoEncontradoException;

import javax.xml.transform.Result;
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

    public void listarProdutoPerecivel(int id) {

        String sqlProdutoPerecivel = "SELECT\n" +
                "p.id,\n" +
                "p.nome,\n" +
                "p.preco,\n" +
                "p.quantidade,\n" +
                "p.data_fabricacao,\n" +
                "p.fabricante,\n" +
                "pp.data_vencimento,\n" +
                "pp.tipo_produto,\n" +
                "pp.peso_gramas,\n" +
                "pp.temperatura_armazenamento\n" +
        "FROM\n" +
        "produto p\n" +
        "JOIN\n" +
        "produto_perecivel pp ON p.id = pp.id\n" +
        "WHERE pp.id = ?";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement psProdutoPerecivel = conn.prepareStatement(sqlProdutoPerecivel)){
            psProdutoPerecivel.setInt(1,id);

            try (ResultSet rs = psProdutoPerecivel.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Produto encontrado!");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Preço: " + rs.getDouble("preco"));
                    System.out.println("Quantidade: " + rs.getInt("quantidade"));
                    System.out.println("Data de fabricação" + rs.getDate("data_fabricacao"));
                    System.out.println("Fabricante: " + rs.getString("fabricante"));
                    System.out.println("Data de vencimento: " + rs.getDate("data_vencimento"));
                    System.out.println("Tipo do produto: " + rs.getString("tipo_produto"));
                    System.out.println("Peso em gramas: " + rs.getDouble("peso_gramas"));
                    System.out.println("Temperatura de armazenamento: " + rs.getString("temperatura_armazenamento"));
                }
                else {
                    throw new ProdutoNaoEncontradoException(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alterarTodosDadosProdutoPerecivel(int id, String novoNome, double novoPreco, int novaQuantidade, Date novaDataFabricacao, String novoFabricante, Date novaDataVencimento, String novoTipoProduto, double novoPesoGramas, String novaTemperaturaArmazenamento) {

        String sqlAlterarDadosProduto = "UPDATE produto SET nome = ?, preco = ?, quantidade = ?, data_fabricacao = ?, fabricante = ? WHERE id = ?";
        String sqlAlterarDadosProdutoPerecivel = "UPDATE produto_perecivel SET data_vencimento = ?, tipo_produto = ?, peso_gramas = ?, temperatura_armazenamento = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement psProduto = conn.prepareStatement(sqlAlterarDadosProduto);
        PreparedStatement psProdutoPerecivel = conn.prepareStatement(sqlAlterarDadosProdutoPerecivel)){

            psProduto.setString(1, novoNome);
            psProduto.setDouble(2, novoPreco);
            psProduto.setInt(3, novaQuantidade);
            psProduto.setDate(4, new java.sql.Date(novaDataFabricacao.getTime()));
            psProduto.setString(5, novoFabricante);
            psProduto.setInt(6, id);

            psProdutoPerecivel.setDate(1, new java.sql.Date(novaDataVencimento.getTime()));
            psProdutoPerecivel.setString(2, novoTipoProduto);
            psProdutoPerecivel.setDouble(3, novoPesoGramas);
            psProdutoPerecivel.setString(4, novaTemperaturaArmazenamento);
            psProdutoPerecivel.setInt(5, id);

            int dadosAlteradosProduto = psProduto.executeUpdate();
            int dadosAlteradosProdutoPerecivel = psProdutoPerecivel.executeUpdate();

            if (dadosAlteradosProduto == 0 || dadosAlteradosProdutoPerecivel == 0) {
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

        public void alterarDadosEspecificosProdutoPerecivel(int id, String campo, Object novoValor) {

            String sql;

            boolean campoDasTabelas = campo.equals("nome") || campo.equals("preco") ||campo.equals("quantidade") || campo.equals("data_fabricacao") || campo.equals("fabricante");

            if (campoDasTabelas) {
                sql = "UPDATE produto SET " + campo + " = ? WHERE id = ?";
            }
            else {
                sql = "UPDATE produto_perecivel SET " + campo + " = ? WHERE id = ?";
            }

            try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setObject(1, novoValor);
                ps.setInt(2, id);

                int linhasAfetadas = ps.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new ProdutoNaoEncontradoException(id);
                }
                else {
                    System.out.println("Dados atualizados com sucesso!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
