package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;


import org.testng.annotations.*;
import org.openqa.selenium.*;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() throws Exception {
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("test1", "test2", "test3"));
        submitGroupCreation();
        returnToGroupPage();

    }

}
