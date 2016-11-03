package persistence;

import model.credentials.CredentialsEntity;

import java.io.IOException;
import java.util.List;

public interface PersistenceManager {
    public void saveCredentials(List<CredentialsEntity> credentialsEntities) throws IOException;

    public List<CredentialsEntity> loadCredentials() throws IOException;

}
