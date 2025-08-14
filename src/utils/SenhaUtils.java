package utils;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class SenhaUtils {

    private static final int ITERATIONS = 65536; //Número de vezes que o algoritmo PBKDF2 será repetido
    private static final int KEY_LENGTH = 256; //Tamanho da chave gerada

    public static String gerarHash(String senha, byte[] salt) { //Senha que será digitada pelo usuário e o Salt serve para adicionar um valor uníco a esta senha
        try {
            PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte [] hash = skf.generateSecret(spec).getEncoded(); //Gera a chave baseada nas especificações (spec) e retorna o resultado como array de bytes
            return Base64.getEncoder().encodeToString(hash); //Converte o array para a Base64 e salva no banco
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] gerarSalt() {
        SecureRandom sr = new SecureRandom(); //Utiliza o SecureRandom para criar uma criptografia de números aleatórios
        byte[] salt = new byte[16]; //Cria um array de 16 bytes
        sr.nextBytes(salt); //Preenche o array criado com bytes aleatórios
        return salt; //Retorna o salt para ser usado junto com a senha no hash
    }
}
