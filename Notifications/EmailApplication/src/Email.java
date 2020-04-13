/*
    Authors: 
        u18140565 - Jacobus Janse van Rensburg

*/

import org.json.*;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;

public class Email {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER="cosomegatest@gmail.com";
    private static final String SMTP_AUTH_PASSWORD="Omega312##";   
    
    public static void email(JSONObject json)
    {
        
        System.out.println("Preparing the data to be email");
            //determine if the email is being sent to multiple or a single person

            //determine who the email is being sent to 

            //set the properties 
        Properties prop = getProperties();

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(prop,auth);

            //detrmine the type of message to email

            //create the message to send depending on the json data
        try{
            
            MimeMessage message = Message.createMessage(json,session);

           //send  message that we will send a email
           System.out.println("Sending the emaill");
           Transport t = session.getTransport();
           t.connect();
           t.sendMessage(message,message.getRecipients(javax.mail.Message.RecipientType.TO));
           t.close();
           
           System.out.println("Email sent");

        }catch (AddressException e)
        {
            System.out.println(e);
        }
        catch (MessagingException e)
        {
            System.out.println(e);
        }
    }
    
    public static JSONObject createJSON(String email, String userName, String msgType, String subject)
    {
        JSONObject json = new JSONObject();
        json.put("UserEmail",email);
        json.put("UserName",userName);
        json.put("MsgType", msgType);
        json.put("Subject", subject);
        return json;
    } 
    
    public static void SendSecurityCodeEmail(String email, String userName, int SecurityCode )
    {
        JSONObject json = new JSONObject();
        json.put("UserEmail",email);
        json.put("UserName",userName);
        json.put("MsgType","SecurityCode");
        json.put("Subject","Mouthpiece Security Code");
        json.put("SecurityCode",SecurityCode);

        email(json);
    }

    public static void SendPasswordChangeEmail(String email, String userName , String changeLink,String notYouLink)
    {
        JSONObject json = new JSONObject();
        json.put("UserEmail",email);
        json.put("UserName", userName);
        json.put("Subject","Moutpiece Password change");
        json.put("MsgType","PasswordChange");
        json.put("ChangeLink",changeLink);
        json.put("ErrorLink",notYouLink);

        email(json);
    }

    public static void SendSuccessfullUploadEmail(String email, String userName, String link, String ID)
    {
        JSONObject json = new JSONObject();
        json.put("UserEmail",email);
        json.put("UserName",userName);
        json.put("MsgType","SuccessfullUpload");
        json.put("Subject","Mouthpiece Upload Successfull");
        json.put("ViewLink",link);
        json.put("ID",ID);

        email(json);
    }

    public static Properties getProperties()
    {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_HOST_NAME);
        prop.put("mail.smtp.port","465"); //465
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return prop;
    }
    
    
    public static String getSMTPHostName()
    {
        return SMTP_HOST_NAME;
    }
    public static String getSMTPAuthUser()
    {
        return SMTP_AUTH_USER;
    }
    public static String getSMTPAuthPassword()
    {
        return SMTP_AUTH_PASSWORD;
    }
}
