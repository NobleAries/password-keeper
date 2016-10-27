package encryption;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class AesEncryptionTests {

    private AesEncryption aesEncryption;

    @Before
    public void setUp() {
        aesEncryption = new AesEncryption();
    }

    @Test
    public void testThatSameBeforeAndAfterEncryptionWhenKeyIs16Bytes() throws Exception {
        char[] mockedPassword = "heheheshki".toCharArray();
        char[] key = "ojejejejojejejej".toCharArray();

        String encrypted = aesEncryption.encrypt(mockedPassword, key);

        assertArrayEquals(mockedPassword, aesEncryption.decrypt(encrypted, key));
    }

    @Test
    public void testThatSameBeforeAndAfterEncryptionWhenKeyIsShortenThan16Bytes() throws EncryptionException {
        char[] mockedPassword = "heheheshki".toCharArray();
        char[] key = "ojejej".toCharArray();

        String encrypted = aesEncryption.encrypt(mockedPassword, key);

        assertArrayEquals(mockedPassword, aesEncryption.decrypt(encrypted, key));
    }

    @Test
    public void testThatSameBeforeAndAfterEncryptionWhenKeyIsLongerThan16Bytes() throws EncryptionException {
        char[] mockedPassword = "heheheshki".toCharArray();
        char[] key = "ojejejojejejejojejejej".toCharArray();

        String encrypted = aesEncryption.encrypt(mockedPassword, key);

        assertArrayEquals(mockedPassword, aesEncryption.decrypt(encrypted, key));
    }
}
