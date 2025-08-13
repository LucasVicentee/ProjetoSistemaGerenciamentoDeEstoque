package entities;

public class Login {

    private int id;
    private String usuario;
    private String email;
    private String senha_hash;
    private String salt;

    public Login() {

    }

    public Login(int id, String usuario, String email, String senha_hash, String salt) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.senha_hash = senha_hash;
        this.salt = salt;
    }

    public Login(String usuario, String email, String senha_hash, String salt) {
        this.usuario = usuario;
        this.email = email;
        this.senha_hash = senha_hash;
        this.salt = salt;
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

    public String getSenha_hash() {
        return senha_hash;
    }

    public void setSenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
