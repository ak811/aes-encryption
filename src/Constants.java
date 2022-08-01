public class Constants {
    public final static int ENCRYPTION_BTN = 1;
    public final static int DECRYPTION_BTN = 2;
    public final static int EXIT_BTN = 3;
    public final static int NEW_KEY_LENGTH = 16;
    public final static int EXIT_STATUS_CODE = 0;

    public final static String MENU_MSG = "Menu: \n" +
            "1- Encrypt a file \n" +
            "2- Decrypt a file \n" +
            "3- Exit \n";
    public final static String ENTER_FILE_PATH_FOR_ENCRYPTION_MSG = "Enter the file path you want to encrypt: ";
    public final static String ENTER_FILE_PATH_FOR_DECRYPTION_MSG = "Enter the file path you want to decrypt:";
    public final static String ENTER_THE_DESTINATION_FILE_PATH_MSG = "Enter the destination file path: ";
    public final static String ENCRYPTING_MSG = "\nEncrypting ...\n";
    public final static String DECRYPTING_MSG = "\nDecrypting ...\n";
    public final static String FILE_ENCRYPTED_MSG = "The file has been encrypted.\n";
    public final static String FILE_DECRYPTED_MSG = "The file has been decrypted.\n";
    public final static String ERR_WHILE_ENCRYPTING = "Error while encrypting: " ;
    public final static String ERR_WHILE_DECRYPTING = "Error while decrypting: " ;
    public final static String INCORRECT_FILE_NAME_MSG = "Incorrect file name entered.\n";
    public final static String ENTER_THE_KEY_MSG = "Enter the key: ";
    public final static String WRONG_INPUT_MSG = "Wrong input entered.";
    public final static String CIPHER_INSTANCE_STR = "AES/ECB/PKCS5Padding";
    public final static String MESSAGE_DIGEST_INSTANCE = "SHA-1";
    public final static String AES_STR = "AES";
    public final static String NEW_LINE = "\n";

}
