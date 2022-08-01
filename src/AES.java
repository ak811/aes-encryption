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
            System.out.println(Constants.MENU_MSG);

            Scanner input = new Scanner(System.in);
            int num = input.nextInt();
            input.nextLine();

            if (num == Constants.EXIT_BTN) {
                System.exit(Constants.EXIT_STATUS_CODE);
                return;
            }

            System.out.println(num == Constants.ENCRYPTION_BTN ?
                    Constants.ENTER_FILE_PATH_FOR_ENCRYPTION_MSG : Constants.ENTER_FILE_PATH_FOR_DECRYPTION_MSG);
            String filePath = input.nextLine();

            System.out.println(Constants.ENTER_THE_KEY_MSG);
            String key = input.nextLine();

            System.out.println(Constants.ENTER_THE_DESTINATION_FILE_PATH_MSG);
            String destinationFilePath = input.nextLine();

            System.out.println(num == Constants.ENCRYPTION_BTN ?
                    Constants.ENCRYPTING_MSG : Constants.DECRYPTING_MSG);

            switch (num) {
                case Constants.ENCRYPTION_BTN:
                    encryptFile(key, filePath, destinationFilePath);
                    break;
                case Constants.DECRYPTION_BTN:
                    decryptFile(key, filePath, destinationFilePath);
                    break;
                default:
                    System.out.println(Constants.WRONG_INPUT_MSG);
            }
        }
    }

    private static void encryptFile(String key, String filePath, String destinationFilePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            StringBuilder originalString = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                originalString.append(line).append(Constants.NEW_LINE);
            }
            String encryptedString = AES.encrypt(originalString.toString(), key);
            Formatter formatter = new Formatter(destinationFilePath);
            assert encryptedString != null;
            formatter.format(encryptedString);
            formatter.flush();
            formatter.close();

            System.out.println(Constants.FILE_ENCRYPTED_MSG);
        } catch (Exception e) {
            System.out.println(Constants.ENTER_FILE_PATH_FOR_DECRYPTION_MSG);
        }
    }

    private static void decryptFile(String key, String filePath, String destinationFilePath) {
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
            formatter.close();

            System.out.println(Constants.FILE_DECRYPTED_MSG);
        } catch (Exception e) {
            System.out.println(Constants.INCORRECT_FILE_NAME_MSG);
        }
    }

    public static void setKey(String myKey) {
        MessageDigest SHA;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            SHA = MessageDigest.getInstance(Constants.MESSAGE_DIGEST_INSTANCE);
            key = SHA.digest(key);
            key = Arrays.copyOf(key, Constants.NEW_KEY_LENGTH);
            secretKeySpec = new SecretKeySpec(key, Constants.AES_STR);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_INSTANCE_STR);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(Constants.ERR_WHILE_ENCRYPTING + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_INSTANCE_STR);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println(Constants.ERR_WHILE_DECRYPTING + e.toString());
        }
        return null;
    }
}