package RSADigitalSignature;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class RSADigitalSignature {

    public static void main(String[] args) {
        try {
             
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            
            String message = "This is a secure message from Alice to Bob.";

            System.out.println("Original Message:");
            System.out.println(message);
            System.out.println();

             
            Signature signer = Signature.getInstance("SHA256withRSA");

            signer.initSign(privateKey);
            signer.update(message.getBytes(StandardCharsets.UTF_8));

            byte[] digitalSignature = signer.sign();

            
            String signatureBase64 =
                    Base64.getEncoder().encodeToString(digitalSignature);

            System.out.println("Digital Signature:");
            System.out.println(signatureBase64);
            System.out.println();

           
            Signature verifier = Signature.getInstance("SHA256withRSA");

            verifier.initVerify(publicKey);
            verifier.update(message.getBytes(StandardCharsets.UTF_8));

            boolean isValid = verifier.verify(digitalSignature);

            System.out.println("Verification of Original Message:");
            System.out.println(isValid);
            System.out.println();

          
            String modifiedMessage =
                    "This is a modified message from Alice to Bob.";

            System.out.println("Modified Message:");
            System.out.println(modifiedMessage);
            System.out.println();

            verifier.initVerify(publicKey);
            verifier.update(modifiedMessage.getBytes(StandardCharsets.UTF_8));

            boolean modifiedIsValid =
                    verifier.verify(digitalSignature);

            System.out.println("Verification of Modified Message:");
            System.out.println(modifiedIsValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
