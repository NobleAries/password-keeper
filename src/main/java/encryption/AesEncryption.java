package encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

class AesEncryption {
    private static final String ALGORITHM = "AES";

    public String encrypt(char[] valueToEnc, char[] keyValue) throws EncryptionException {
        Key key = generateKey(keyValue);
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(charsToBytes(valueToEnc));
            return new String(Base64.getEncoder().encode(encValue));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionException(e.getMessage());
        }
    }

    public char[] decrypt(String encryptedValue, char[] keyValue) throws EncryptionException {
        try {
            Key key = generateKey(keyValue);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
            byte[] decValue = c.doFinal(decodedValue);
            char[] decrypted = new char[decValue.length];
            for(int i = 0; i < decValue.length; i++) {
                decrypted[i] = (char) decValue[i];
            }
            return decrypted;
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionException(e.getMessage());
        }
    }

    private Key generateKey(char[] keyValue) {
        char[] validatedKeyValue = validateKeyValue(keyValue);
        byte[] keyBytesValue = charsToBytes(validatedKeyValue);
        return new SecretKeySpec(keyBytesValue, ALGORITHM);
    }

    //key has to be 16 bytes length
    private char[] validateKeyValue(char[] keyValue) {
        if (keyValue.length < 16) {
            char[] newKeyValue = new char[16];
            System.arraycopy(keyValue, 0, newKeyValue, 0, keyValue.length);
            for(int i = keyValue.length; i < 16; i++) {
                newKeyValue[i] = 'x';
            }
            return newKeyValue;
        } else if (keyValue.length > 16) {
            char[] newKeyValue = new char[16];
            System.arraycopy(keyValue, 0, newKeyValue, 0, 16);
            return newKeyValue;
        }
        return keyValue;
    }

    private byte[] charsToBytes(char[] chars) {
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(CharBuffer.wrap(chars));
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
