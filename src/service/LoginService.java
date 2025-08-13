package service;

import connection.Conexao;
import entities.Login;
import utils.SenhaUtils;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class LoginService {

    public void cadastrarUsuario(String usuario, String email, String senha) {
        byte[] salt = SenhaUtils.gerarSalt();
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        String senhaHash = SenhaUtils.gerarHash(senha, salt);

        String sql = "INSERT INTO login (usuario, email, senha, salt) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, usuario);
            ps.setString(2, email);
            ps.setString(3, senhaHash);
            ps.setString(4, saltBase64);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Login verificarUsuarioOuEmail(String usuarioOuEmail, String senha) {
        String sql = "SELECT * FROM login WHERE usuario = ? OR email = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, usuarioOuEmail);
            ps.setString(2, usuarioOuEmail);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String senhaHashBanco = rs.getString("senha");
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
            else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
