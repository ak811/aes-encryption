import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Formatter;
import java.util.Scanner;

public class AES {

    private static SecretKeySpec secretKeySpec;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        while (true) {
            System.out.println("Menu:\n" +
                    "1- Encrypt a file \n" +
                    "2- Decrypt a file\n" +
                    "3- Exit\n");

            Scanner input = new Scanner(System.in);
            int num = input.nextInt();
            input.nextLine();
            switch (num) {
                case 1:
                    System.out.println("Enter the file path you want to encrypt:");
                    String filePath = input.nextLine();

                    System.out.println("Enter the key:");
                    String key = input.nextLine();

                    System.out.println("Enter the destination file path:");
                    String destinationFilePath = input.nextLine();

                    System.out.println("\nEncrypting ...\n");

                    try {
                        Scanner scanner = new Scanner(new File(filePath));
                        StringBuilder originalString = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            originalString.append(line).append("\n");
                        }
                        String encryptedString = AES.encrypt(originalString.toString(), key);
                        Formatter formatter = new Formatter(destinationFilePath);
                        assert encryptedString != null;
                        formatter.format(encryptedString);
                        formatter.flush();
                        formatter.close();

                        System.out.println("The file encrypted.\n");
                    } catch (Exception e) {
                        System.out.println("incorrect file name entered.\n");
                        break;
                    }
                    break;
                case 2:
                    System.out.println("Enter the file path you want to decrypt:");
                    filePath = input.nextLine();

                    System.out.println("Enter the key:");
                    key = input.nextLine();

                    System.out.println("Enter the destination file path:");
                    destinationFilePath = input.nextLine();

                    System.out.println("\nDecrypting ...\n");

                    try {
                        Scanner scanner = new Scanner(new File(filePath));
                        StringBuilder encryptedString = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            encryptedString.append(line);
                        }
                        String decryptedString = AES.decrypt(encryptedString.toString(), key);
                        Formatter formatter = new Formatter(destinationFilePath);
                        assert decryptedString != null;
                        formatter.format(decryptedString);
                        formatter.flush();

                        System.out.println("The file decrypted.\n");
                    } catch (Exception e) {
                        System.out.println("incorrect file name entered.\n");
                        break;
                    }
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong key entered.\n");
            }
        }
    }

    public static void setKey(String myKey) {
        MessageDigest SHA;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            SHA = MessageDigest.getInstance("SHA-1");
            key = SHA.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}