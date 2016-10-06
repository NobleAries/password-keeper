package model.authentication;

import java.io.*;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticator {
    private final File passwordFile;
    private final MessageDigest messageDigest;

    /**
     * Class for serving user authentication. It uses SHA-512 algorithm.
     * @param passwordFilePath Path to authentication password file
     * @throws NoSuchAlgorithmException SHA-512 algorithm was not found
     */
    public Authenticator(Path passwordFilePath) throws NoSuchAlgorithmException {
        this.passwordFile = passwordFilePath.toFile();
        this.messageDigest = MessageDigest.getInstance("SHA-512");
    }

    /**
     * Checks if password is already set.
     * @return true if file with main password exists or false if not
     */
    public boolean isPasswordSet() {
        return passwordFile.exists();
    }

    /**
     * Sets password the first time. It assumes that program's data directory already exists.
     * @param password First password given by user.
     * @throws IOException
     */
    public void setFirstPassword(String password) throws IOException {
        if (isPasswordSet()) {
            throw new IllegalStateException("Main password already set");
        }

        if (!passwordFile.createNewFile()) {
            throw new IOException("Cannot create file in program's data directory");
        } else {
            processOutputValueOfSettingPermissions(passwordFile.setReadable(false, false));
            processOutputValueOfSettingPermissions(passwordFile.setReadable(true, true));
            processOutputValueOfSettingPermissions(passwordFile.setWritable(false, false));
            processOutputValueOfSettingPermissions(passwordFile.setWritable(true, true));
            processOutputValueOfSettingPermissions(passwordFile.setExecutable(false, false));

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(passwordFile));
            bufferedWriter.write(hashPassword(password));
            bufferedWriter.close();
        }
    }

    /**
     * Changes password.
     * @param newPassword
     * @throws IOException
     */
    public void changePassword(String newPassword) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(passwordFile));
        bufferedWriter.write(hashPassword(newPassword));
        bufferedWriter.close();
    }

    /**
     * Checks if given password is the same as saved.
     * @param givenPassword Password which was used by user to isPasswordValid.
     * @return true if password are same, else other way
     * @throws IOException
     */
    public boolean isPasswordValid(String givenPassword) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(passwordFile));
        String password = bufferedReader.readLine();
        bufferedReader.close();
        return password.equals(hashPassword(givenPassword));
    }

    private String hashPassword(String password) {
        byte[] mdBytes = messageDigest.digest(password.getBytes());

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < mdBytes.length; i++) {
            stringBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    private void processOutputValueOfSettingPermissions(boolean outputValue) throws IOException {
        if (!outputValue) {
            passwordFile.delete();
            throw new IOException("Could not set permissions for main password file");
        }
    }
}
