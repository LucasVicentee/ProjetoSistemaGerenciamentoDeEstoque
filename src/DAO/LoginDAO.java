package DAO;

import connection.Conexao;
import entities.Login;
import utils.SenhaUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class LoginDAO {

    // Inserir usuário no banco
    public void cadastrarUsuario(Login usuario) {
        String sql = "INSERT INTO login (usuario, email, senha_hash, salt) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            byte[] salt = SenhaUtils.gerarSalt();
            String senhaHash = SenhaUtils.gerarHash(usuario.getSenha_hash(), salt);
            String saltBase64 = Base64.getEncoder().encodeToString(salt);

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, senhaHash);
            ps.setString(4, saltBase64);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage(), e);
        }
    }

    // Verificar login
    public Login verificarUsuarioOuEmail(String usuarioOuEmail, String senha) {
        String sql = "SELECT * FROM login WHERE usuario = ? OR email = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioOuEmail);
            ps.setString(2, usuarioOuEmail);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String senhaHashBanco = rs.getString("senha_hash");
                String saltBase64 = rs.getString("salt");
                byte[] salt = Base64.getDecoder().decode(saltBase64);

                String senhaHashDigitada = SenhaUtils.gerarHash(senha, salt);

                if (senhaHashBanco.equals(senhaHashDigitada)) {
                    Login login = new Login();
                    login.setId(rs.getInt("id"));
                    login.setNome(rs.getString("usuario"));
                    login.setEmail(rs.getString("email"));
                    return login;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar usuário: " + e.getMessage(), e);
        }
    }
}
