import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/* Can be used this way:

String recipient = "somebody@gmail.com";
String sender = "ntnblgkv@gmail.com";
String password = "mypassword";

EmailSender em = new EmailSender();
String s = em.sendThroughRemote(sender, password, recipient);
resp.getWriter().write(s);

*/

/*
./mailproperties.txt:

mail.transport.protocol=smtps
mail.smtps.auth=true
mail.smtps.host=smtp.gmail.com
mail.smtps.user=ntnblgkv@gmail.com

*/

/*
Maven dependencies:

	<dependency>
        <groupId>javax.mail</groupId>
        <artifactId>mail</artifactId>
        <version>1.4.5</version>
    </dependency>

    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>smtp</artifactId>
        <version>1.4.4</version>
    </dependency>

    <dependency>
        <groupId>javax.activation</groupId>
        <artifactId>activation</artifactId>
        <version>1.1</version>
    </dependency>
*/

public class EmailSender {

    public EmailSender(){

    }

    public String sendThroughRemote(String sender, String password, String recipient){
        Properties properties = System.getProperties();

        String s = "./mailproperties.txt";
        File file = new File(s);
        try {
            properties.load(new FileInputStream(file));
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject("Train Java mailing");
            message.setText("Hello, World!");

            Transport tr = session.getTransport();
            tr.connect(sender, password);
            tr.sendMessage(message, message.getAllRecipients());
        }catch(FileNotFoundException e){
            return "Fail. Properties were not found";
        }catch(IOException e1){
            return "Fail. IOException was thrown";
        }catch(MessagingException e2){
            return "Fail. MessagingException was thrown";
        }
        return "OK";
    }
}
