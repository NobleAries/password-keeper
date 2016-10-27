package persistence;


import model.credentials.CredentialsEntity;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class CsvPersistenceManager implements PersistenceManager {
    private File file;

    public CsvPersistenceManager(File file) {
        this.file = file;
    }

    @Override
    public void saveCredentials(List<CredentialsEntity> credentialsEntities) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (CredentialsEntity entity : credentialsEntities) {
            bufferedWriter.write(buildCsvEntity(entity));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    @Override
    public List<CredentialsEntity> loadCredentials() throws IOException {
        List<CredentialsEntity> credentialsEntities = new LinkedList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] entityArray = line.split(",");
            String place = entityArray[0];
            String username = entityArray[1];
            String encryptedPassword = entityArray[2];
            LocalDateTime modificationTime = LocalDateTime.parse(entityArray[3]);
            String note = entityArray[4];

            CredentialsEntity credentialsEntity = new CredentialsEntity(place, username, encryptedPassword, modificationTime, note);
            credentialsEntities.add(credentialsEntity);
        }
        return credentialsEntities;
    }

    private String buildCsvEntity(CredentialsEntity entity) {
        return String.join(",", entity.getPlace(), entity.getUsername(), entity.getEncryptedPassword(), entity.getModificationDate().toString(), entity.getNote());
    }
}
