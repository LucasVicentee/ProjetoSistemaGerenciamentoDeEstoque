package service;

import DAO.LoginDAO;
import entities.Login;


public class LoginService {

    private LoginDAO loginDAO = new LoginDAO(); //Criando o objeto LoginDAO do pacote DAO

    // Cadastro de usuário
    public void cadastrarUsuario(String nome, String email, String senha_hash) { //Criação do método com a passagem dos dados

        Login usuario = new Login();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha_hash(senha_hash);
        loginDAO.cadastrarUsuario(usuario); //Chama o método do LoginDAO para realmente inserir esse usuário no banco
    }

    // Login do usuário
    public Login verificarUsuarioOuEmail(String usuarioOuEmail, String senha) { //Método que verifica se possuí ou não um usuário com um usuário ou email cadastrados

        return loginDAO.verificarUsuarioOuEmail(usuarioOuEmail, senha); //Retorna se tem ou não este usuário no sistema cadastrado
    }
}
