package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
   public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws MessagingException, IOException {
        Users allUsers = app.db().users();
        User modifiedUser = allUsers.iterator().next();
        if (modifiedUser.getUsername().equals("administrator")) modifiedUser = allUsers.iterator().next();
        String email = modifiedUser.getEmail();
        String username = modifiedUser.getUsername();
        app.login().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        Assert.assertTrue(app.login().isLoggedInAs(app.getProperty("web.adminLogin")));
        app.admin().resetPassword(username);
        String newpassword = "newpassword";
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = app.login().findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, newpassword);
        assertTrue(app.newSession().login(username, newpassword));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
