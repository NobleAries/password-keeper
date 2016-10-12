package model.authentication;

import java.io.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Authenticator {
    private final static Logger LOGGER = Logger.getLogger(Authenticator.class.getName());

    private final File passwordFile;
    private final MessageDigest messageDigest;

    /**
     * Class for serving user authentication. It uses SHA-512 algorithm for generating password hash.
     * @param passwordFilePath Path to authentication password file
     * @throws NoSuchAlgorithmException SHA-512 algorithm was not found
     */
    public Authenticator(Path passwordFilePath) throws NoSuchAlgorithmException {
        this.passwordFile = passwordFilePath.toFile();
        this.messageDigest = MessageDigest.getInstance("SHA-512");
    }

    /**
     * @return true if password is set or false if it has not been set yet
     */
    public boolean isPasswordSet() {
        return passwordFile.exists();
    }

    /**
     * Sets password the first time.
     * @param password First password given by user.
     * @throws IOException
     */
    public void setFirstPassword(char[] password) throws IOException {
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
     *
     * @param newPassword New password
     * @throws IOException
     */
    public void changePassword(char[] newPassword) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(passwordFile));
        bufferedWriter.write(hashPassword(newPassword));
        bufferedWriter.close();
    }

    /**
     *
     * @param givenPassword Password to cheeck if it's valid
     * @return true if password is valid, else other way
     * @throws IOException
     */
    public boolean isPasswordValid(char[] givenPassword) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(passwordFile));
        String password = bufferedReader.readLine();
        bufferedReader.close();
        return password.equals(hashPassword(givenPassword));
    }

    private String hashPassword(char[] password) {
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(CharBuffer.wrap(password));
        byte[] passwordBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(passwordBytes);
        Arrays.fill(byteBuffer.array(), (byte) 0);

        byte[] mdBytes = messageDigest.digest(passwordBytes);

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: mdBytes) {
            stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }

    private void processOutputValueOfSettingPermissions(boolean outputValue) throws IOException {
        if (!outputValue) {
            LOGGER.warning("Could not set permission");
        }
    }
}
