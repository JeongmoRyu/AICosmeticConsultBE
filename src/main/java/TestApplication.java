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

// 아래와 같은 rag.proto로 되어 있는 python 서버가 있는데,
// springboot가 client가 되어서 python 서버와 streaming 통신을 하려고 해.

// 기본적인 spring 코드를 작성해줘

// syntax = "proto3";

// package rag_service.rag_module;

// service RagService {
// rpc ChatHandler (stream ChatRequest) returns (stream ChatResponse);
// };

// message Chat {
// string input = 1;
// string output = 2;
// }

// message ChatResponse {
// oneof msg_type {
// string msg = 1;
// Status status = 2;
// }
// }

// message ChatRequest {
// oneof msg_type {
// Config config = 1;
// string msg = 2;
// }
// optional int64 sequence = 3;
// }