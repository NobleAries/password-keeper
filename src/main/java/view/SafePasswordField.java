package view;

import javafx.scene.control.PasswordField;
import java.lang.reflect.Field;

public class SafePasswordField extends PasswordField {


    /**
     * Method for save access to text field's data.
     * Using reflection is meant to be a temporary solution. In future we plan to create full custom PasswordField without it.
     * @return Password as char array
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public char[] getPassword() throws NoSuchFieldException, IllegalAccessException {
        Content c = getContent();

        Field fld = c.getClass().getDeclaredField("characters");
        fld.setAccessible(true);

        StringBuilder sb = (StringBuilder) fld.get(c);
        char[] result = new char[sb.length()];
        sb.getChars(0, sb.length(), result, 0);

        for(int i = 0; i < sb.length(); i++)
            sb.setCharAt(i, '0');
        sb.delete(0, sb.length());
        clear();

        return result;
    }
}