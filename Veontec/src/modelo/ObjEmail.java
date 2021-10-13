package modelo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class ObjEmail {
    
    public void mtdEnviarMensajeSimple(){
        try {
            Email email = new SimpleEmail();
            email.setHostName("in-v3.mailjet.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("a1238bce0ca670d72a071451af6accde", "11218c5a874df874ef5aca4aedeada36"));
            email.setSSLOnConnect(true);
            email.setFrom("pv19022441@vallarta.tecmm.edu.mx");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("victorvj098@gmail.com");
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mtdEnviarMensajeArchivoAdjunto(){
        try {
            // Create the attachment
            EmailAttachment attachment = new EmailAttachment();
            File archivo = new File("P:\\Pictures\\ezio.png");
            
            attachment.setPath(archivo.getAbsolutePath());
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("Picture of John");
            attachment.setName("John");
            
            // Create the email message
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("in-v3.mailjet.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("a1238bce0ca670d72a071451af6accde", "11218c5a874df874ef5aca4aedeada36"));
            email.setSSLOnConnect(true);
            email.setFrom("pv19022441@vallarta.tecmm.edu.mx");
            email.setSubject("mtdEnviarMensajeArchivoAdjunto");
            email.setMsg("Este es un mensaje de prueba :-)");
            email.addTo("victorvj098@gmail.com");
            
            // add the attachment
            email.attach(attachment);
            
            // send the email
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mtdEnviarMensajeArchivoAdjunto_URL(){
        try {
            // Create the attachment
            EmailAttachment attachment = new EmailAttachment();
            attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("Apache logo");
            attachment.setName("Apache logo");
            
            // Create the email message
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("in-v3.mailjet.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("a1238bce0ca670d72a071451af6accde", "11218c5a874df874ef5aca4aedeada36"));
            email.setSSLOnConnect(true);
            email.setFrom("pv19022441@vallarta.tecmm.edu.mx");
            email.setSubject("mtdEnviarMensajeArchivoAdjunto");
            email.setMsg("Este es un mensaje de prueba :-)");
            email.addTo("victorvj098@gmail.com");
            
            // add the attachment
            email.attach(attachment);
            
            // send the email
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mtdEnviarMensajeArchivoAdjunto_HTML(){
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName("in-v3.mailjet.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("a1238bce0ca670d72a071451af6accde", "11218c5a874df874ef5aca4aedeada36"));
            email.setSSLOnConnect(true);
            email.setFrom("pv19022441@vallarta.tecmm.edu.mx");
            email.setSubject("mtdEnviarMensajeArchivoAdjunto");
            email.addTo("victorvj098@gmail.com");

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = email.embed(url, "Apache logo");

            // set the html message
            email.setHtmlMsg("<html>The apache logo - <img src=\"cid:"+cid+"\"></html>");

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            // send the email
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
