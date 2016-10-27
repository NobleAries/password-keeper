package model.credentials;

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

    private CredentialsEntity(String place, String username, LocalDateTime modificationTime, String note) {
        this.place.set(place);
        this.username.set(username);
        this.modificationDate.set(modificationTime);
        this.note.set(note);
    }

    public CredentialsEntity(String place, String username, char[] password, char[] key, LocalDateTime modificationTime, String note) {
        this(place, username, modificationTime, note);
        this.encryptedPassword = encryptPassword(password, key);
    }

    public CredentialsEntity(String place, String username, String encryptedPassword, LocalDateTime modificationTime, String note) {
        this(place, username, modificationTime, note);
        this.encryptedPassword = encryptedPassword;
    }


    public void setPassword(char[] password, char[] key) {
        encryptedPassword = encryptPassword(password, key);
    }

    public char[] getPassword(char[] key) {
        return encryptedPassword.toCharArray();
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public String getPlace() {
        return place.get();
    }

    public void setPlace(String place) {
        this.place.set(place);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public LocalDateTime getModificationDate() {
        return modificationDate.get();
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate.set(modificationDate);
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    private String encryptPassword(char[] password, char[] passphrase) {
        return new String(password);
    }
}
