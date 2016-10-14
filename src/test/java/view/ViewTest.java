package view;

import java.io.File;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;

public class ViewTest extends FxRobot{

    private final String password = "testPassword";
    private final String incorrectPassword = "incorrectPassword";
    // TODO There will be a proper getter or field somewhere in the future for path to password's file.
    private final File passwords = new File("samplename.txt");
    private final static Logger LOGGER = Logger.getLogger(ViewTest.class.getName());


    @Before
    public void setup() throws Exception{
        ApplicationTest.launch(View.class);
        if(passwords.exists())
            if( !passwords.delete() )
                LOGGER.warning("Could not delete file with password during tests.");
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();

        if( !passwords.delete() )
            LOGGER.warning("Could not delete file with password during tests.");
    }

    @Test
    public void shouldOpenRegisterScene() {
        verifyThat("#setup-password", hasText("Choose password"));
        verifyThat("#registerButton", hasText("Register"));
    }

    @Test
    public void shouldBeAbleToCreateMainPassword() {
        clickOn("#registerPasswordField").write(password);
        clickOn("#registerButton");

        clickOn("#loginPasswordField").write(password);
        clickOn("#loginButton");

        // TODO Update text or element which is validated, after this part of GUI is changed.
        verifyThat(".label", hasText("Success!"));
    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPassword() {
        clickOn("#registerPasswordField").write(password);
        clickOn("#registerButton");

        clickOn("#loginPasswordField").write(incorrectPassword);
        clickOn("#loginButton");
        verifyThat("#passwordValid", hasText("Password incorrect!"));
    }
}
