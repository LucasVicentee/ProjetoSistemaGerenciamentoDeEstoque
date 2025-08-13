package service;

import connection.Conexao;
import entities.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public Login verificarUsuarioOuEmail(String usuarioOuEmail) {
        String sql = "SELECT * FROM login WHERE usuario = ? OR email = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, usuarioOuEmail);
            ps.setString(2, usuarioOuEmail);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Login login = new Login();
                login.setId(rs.getInt("id"));
                login.setNome(rs.getString("usuario"));
                login.setEmail(rs.getString("email"));
                login.setSenha(rs.getString("senha"));
                return login;
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
