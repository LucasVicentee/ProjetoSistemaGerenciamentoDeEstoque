package entities;

public class Login {

    private int id;
    private String usuario;
    private String email;
    private String senha;

    public Login() {

    }

    public Login(int id, String usuario, String email, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public Login(String usuario, String email, String senha) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setNome(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
