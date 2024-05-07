import org.jasypt.util.binary.AES256BinaryEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

public class TestApplication {
    public static void main(String[] args) {
        System.out.println("test");
//        String pass = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
//        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
//        textEncryptor.setPassword(pass);
//
//        String encryptText = textEncryptor.encrypt("test");
//        System.out.println("encryptText:" + encryptText);
//
//        String decryptText = textEncryptor.decrypt(encryptText);
//        System.out.println("decryptText:" + decryptText);
    }
}
