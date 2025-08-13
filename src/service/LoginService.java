package service;

import DAO.LoginDAO;
import entities.Login;


public class LoginService {

    private LoginDAO loginDAO = new LoginDAO();

    // Cadastro de usuário
    public void cadastrarUsuario(String nome, String email, String senha_hash) {

        Login usuario = new Login();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha_hash(senha_hash);
        loginDAO.cadastrarUsuario(usuario);
    }

    // Login do usuário
    public Login verificarUsuarioOuEmail(String usuarioOuEmail, String senha) {

        return loginDAO.verificarUsuarioOuEmail(usuarioOuEmail, senha);
    }
}
