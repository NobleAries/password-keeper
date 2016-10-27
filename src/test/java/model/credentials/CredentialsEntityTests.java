package model.credentials;


import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertArrayEquals;

public class CredentialsEntityTests {

    @Test
    public void testThatPasswordAfterEncodingAndDecodingIsTheSame() {
        char[] mockedPassword = "testPass".toCharArray();
        char[] key = "key".toCharArray();

        CredentialsEntity credentialsEntity = new CredentialsEntity("place", "username", mockedPassword, key, "note");
        System.out.println(mockedPassword);
        System.out.println(credentialsEntity.getPassword(key));
        assertArrayEquals(mockedPassword, credentialsEntity.getPassword(key));
    }
}
