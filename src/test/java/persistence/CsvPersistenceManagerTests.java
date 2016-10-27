package persistence;

import model.credentials.CredentialsEntity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CsvPersistenceManagerTests {

    private CsvPersistenceManager manager;
    @Mock
    private CredentialsEntity credentialsEntity1;
    @Mock
    private CredentialsEntity credentialsEntity2;
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException, NoSuchAlgorithmException {
        MockitoAnnotations.initMocks(this);

        Path dirPath = temporaryFolder.newFolder("password_keeper_tests").toPath();
        Path filePath = dirPath.resolve("passwords.csv");

        manager = new CsvPersistenceManager(filePath.toFile());
    }

    @Test
    public void testThatCredentialsAfterSaveAndLoadAreTheSame() throws IOException {
        //given
        String place1 = "place1";
        String username1 = "username1";
        String encryptedPassword1 = "password1";
        LocalDateTime dateTime1 = LocalDateTime.now();
        System.out.println(dateTime1);
        String note1 = "note1";
        CredentialsEntity credentialsEntity1 = new CredentialsEntity(place1, username1, encryptedPassword1, dateTime1, note1);

        String place2 = "place2";
        String username2 = "username2";
        String encryptedPassword2 = "password2";
        LocalDateTime dateTime2 = LocalDateTime.now();
        String note2 = "note2";
        CredentialsEntity credentialsEntity2 = new CredentialsEntity(place2, username2, encryptedPassword2, dateTime2, note2);

        List<CredentialsEntity> credentialsEntities = new LinkedList<>();
        credentialsEntities.add(credentialsEntity1);
        credentialsEntities.add(credentialsEntity2);

        //when
        manager.saveCredentials(credentialsEntities);
        List<CredentialsEntity> returnedCredentials = manager.loadCredentials();

        //then
        boolean firstCredentialsChecked = false;
        boolean secondCredentialsChecked = false;
        for (CredentialsEntity entity : returnedCredentials) {
            if(entity.getPlace().equals(place1) && !firstCredentialsChecked) {
                assertEquals(entity.getUsername(), username1);
                assertEquals(entity.getEncryptedPassword(), encryptedPassword1);
                assertEquals(entity.getModificationDate(), dateTime1);
                assertEquals(entity.getNote(), note1);
                firstCredentialsChecked = true;
            } else if(entity.getPlace().equals(place2) && !secondCredentialsChecked) {
                assertEquals(entity.getUsername(), username2);
                assertEquals(entity.getEncryptedPassword(), encryptedPassword2);
                assertEquals(entity.getModificationDate(), dateTime2);
                assertEquals(entity.getNote(), note2);
                secondCredentialsChecked = true;
            } else {
                fail();
            }
        }
    }
}
