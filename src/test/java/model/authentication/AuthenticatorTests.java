package model.authentication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AuthenticatorTests {

    private Authenticator authenticator;
    private char[] mockedPassword;
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException, NoSuchAlgorithmException {
        mockedPassword = "mocked".toCharArray();

        Path dirPath = temporaryFolder.newFolder("password_keeper_tests").toPath();
        Path filePath = dirPath.resolve("authentication.txt");

        authenticator = new Authenticator(filePath);
    }

    @Test
    public void testThatPasswordIsSetAfterSettingFirstPassword() throws IOException {
        authenticator.setFirstPassword(mockedPassword);

        assertTrue(authenticator.isPasswordSet());
    }

    @Test(expected = IllegalStateException.class)
    public void testThatCannotSetFirstPasswordTwice() throws IOException {
        authenticator.setFirstPassword(mockedPassword);

        authenticator.setFirstPassword(mockedPassword);
    }

    @Test
    public void testThatPasswordIsValidAfterSettingFirstPassword() throws IOException {
        authenticator.setFirstPassword(mockedPassword);

        assertTrue(authenticator.isPasswordValid(mockedPassword));
    }

    @Test
    public void testThatOtherThanSetPasswordIsInvalid() throws IOException {
        authenticator.setFirstPassword(mockedPassword);

        assertFalse(authenticator.isPasswordValid("other password".toCharArray()));
    }

    @Test
    public void testThatPasswordIsValidAfterChangingIt() throws IOException {
        char[] newPassword = "newPassword".toCharArray();
        authenticator.setFirstPassword(mockedPassword);

        authenticator.changePassword(newPassword);

        assertTrue(authenticator.isPasswordValid(newPassword));
    }

}
