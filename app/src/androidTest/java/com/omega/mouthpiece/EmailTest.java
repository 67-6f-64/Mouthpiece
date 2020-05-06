package com.omega.mouthpiece;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class EmailTest {

    ArrayList<JSONObject> units;
    int count=0;
    ArrayList<String[]> test;
    public EmailTest() throws JSONException {
        units = new ArrayList();
        test = new ArrayList();
        //test cases
        //to add case
        //add
        //
        //a =new String[]{"<email>", "<name>", "<tipe>", "<heding>"};
        //test.add(a);
        String[] a =new String[]{"cosomegatest@gmail.com", "MadMan1", "SecurityCode", "hello "};
        test.add(a);
        a =new String[]{"cosomegatest@gmail.com", "MadMan2", "PasswordChange", "Password Change"};
        test.add(a);
        a =new String[]{"cosomegatest@gmail.com", "MadMan3", "SuccessfullUpload", "SuccessfullUpload"};

        test.add(a);
        a =new String[]{"cosomegatest@gmail.com", "MadMan4", "<h3>user inputted message body<h3>", "user inputted message"};
        test.add(a);
        test.forEach(new Consumer<String[]>() {
            @Override
            public void accept(String[] n) {
                try {
                    units.add(Email.createJSON(n[0], n[1], n[2], n[3]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        int num =12345;
        units.get(0).put("SecurityCode",num );
        units.get(1).put("ChangeLink","https://www.agegeek.com/" );
        units.get(1).put("ErrorLink","https://www.boredbutton.com/coronavirus.php" );
        units.get(2).put("ViewLink","https://www.anothersadtrombone.com/" );
        units.get(2).put("ID","159159" );
    }

    /**
     * Test of createJSON method, of class Email.
     */
    @Test
    public void testCreateJSON() throws JSONException {
        System.out.println("createJSON");
        String email = "cosomegatest@gmail.com";
        String userName = "MadMan1";
        String msgType = "SecurityCode";
        String subject = "hello ";
        JSONObject expResult = new JSONObject();
        expResult.put("UserEmail",email);
        expResult.put("UserName",userName);
        expResult.put("MsgType", msgType);
        expResult.put("Subject", subject);
        JSONObject result = Email.createJSON(email, userName, msgType, subject);
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of SendSecurityCodeEmail method, of class Email.
     */
    @Test
    public void testSendSecurityCodeEmail() throws JSONException {
        System.out.println("SendSecurityCodeEmail");
        String email = units.get(0).getString("UserEmail");
        String userName = units.get(0).getString("UserName");
        int SecurityCode = units.get(0).getInt("SecurityCode");
        Email.SendSecurityCodeEmail(email, userName, SecurityCode);
    }

    /**
     * Test of SendPasswordChangeEmail method, of class Email.
     */
    @Test
    public void testSendPasswordChangeEmail() throws JSONException {
        System.out.println("SendPasswordChangeEmail");
        String email = units.get(1).getString("UserEmail");
        String userName = units.get(1).getString("UserName");
        String changeLink = units.get(1).getString("ChangeLink");
        String notYouLink = units.get(1).getString("ErrorLink");
        Email.SendPasswordChangeEmail(email, userName, changeLink, notYouLink);
    }

    /**
     * Test of SendSuccessfullUploadEmail method, of class Email.
     */
    @Test
    public void testSendSuccessfullUploadEmail() throws JSONException {
        System.out.println("SendSuccessfullUploadEmail");
        String email = units.get(2).getString("UserName");
        String userName = units.get(2).getString("UserEmail");
        String link = units.get(2).getString("ViewLink");
        String ID = units.get(2).getString("ID");
        Email.SendSuccessfullUploadEmail(email, userName, link, ID);
    }
    /**
     * Test of SendSendNewUserCreated method, of class Email.
     */
    @Test
    public void testSendNewUserCreated() throws JSONException {
        System.out.println("SendNewUserCreatedEmail");
        String email = units.get(2).getString("UserEmail");
        String userName = units.get(2).getString("UserName");
        String link = units.get(2).getString("ViewLink");
        String ID = units.get(2).getString("ID");
        Email.SendNewUserCreated(email,userName);
    }
    /**
     * Test of email method, of class Email.
     */
    @Test
    public void testEmail() {
        System.out.println("email");
        JSONObject json = units.get(3);
        Email.email(json);
    }
}