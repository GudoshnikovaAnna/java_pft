package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class AdminHelper extends HelperBase {
    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void resetPassword(String username) {
       wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
       int size = wd.findElements(By.xpath("//table/tbody/tr")).size();
       click(By.linkText(username));
       click(By.xpath("//input[@value='Сбросить пароль']"));
    }
}
