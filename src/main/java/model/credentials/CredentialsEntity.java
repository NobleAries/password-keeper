package model.credentials;

import encryption.AesEncryption;
import encryption.EncryptionException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class CredentialsEntity {

    private StringProperty place = new SimpleStringProperty();
    private StringProperty username = new SimpleStringProperty();
    private ObjectProperty<LocalDateTime> modificationDate = new SimpleObjectProperty<>();
    private StringProperty note = new SimpleStringProperty();

    private String encryptedPassword;

    /**
     *
     * @param place Place (examaple url, app) where credentials are used
     * @param username Username
     * @param password Password
     * @param key Key which will be used to encrypt password
     * @param note Additional note to credentials
     */
    public CredentialsEntity(String place, String username, char[] password, char[] key, String note) throws EncryptionException {
        this(place, username, note);
        setPassword(password, key);
    }

    public CredentialsEntity(String place, String username, String encryptedPassword, LocalDateTime modificationDate, String note) {
        this(place, username, note);
        this.encryptedPassword = encryptedPassword;
        this.modificationDate.set(modificationDate);
    }

    private CredentialsEntity(String place, String username, String note) {
        this.place.set(place);
        this.username.set(username);
        this.modificationDate.set(LocalDateTime.now());
        this.note.set(note);
    }

    public void setPassword(char[] password, char[] key) throws EncryptionException {
        encryptedPassword = AesEncryption.encrypt(password, key);
        refreshModificationDate();
    }

    public char[] getPassword(char[] key) throws EncryptionException {
        return AesEncryption.decrypt(encryptedPassword, key);
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public String getPlace() {
        return place.get();
    }

    public void setPlace(String place) {
        this.place.set(place);
        refreshModificationDate();
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
        refreshModificationDate();
    }

    public LocalDateTime getModificationDate() {
        return modificationDate.get();
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate.set(modificationDate);
        refreshModificationDate();
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
        refreshModificationDate();
    }

    private void refreshModificationDate() {
        this.modificationDate.set(LocalDateTime.now());
    }
}
