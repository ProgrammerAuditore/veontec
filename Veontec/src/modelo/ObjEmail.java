package modelo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import modelo.dto.UsuarioDto;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

public class ObjEmail {
    
    public static boolean mtdEnviarValidarEmail(UsuarioDto usuarioDto){
        HtmlEmail emailHtml = new HtmlEmail();
        SecretKey key_codificado = null;
        String keyCodificado = "";
       
        try {
            
            key_codificado = ObjKey.getKeyFromPassword(usuarioDto.getCmpCorreo(), usuarioDto.getCmpPassword());
            keyCodificado = ObjKey.convertSecretKeyToString(key_codificado);
            
        } catch (Exception ex) {
            return false;
        }
        
        try {
            // Create the emailHtml message
            emailHtml.setHostName("in-v3.mailjet.com");
            emailHtml.setSmtpPort(587);
            emailHtml.setAuthenticator(new DefaultAuthenticator("dabf6d9831bdc3404e03d0bd9aebd337", "09af06a814d962d5b7cc20ec093f41e8"));
            emailHtml.setSSLOnConnect(true);
            emailHtml.setFrom("sfw.veontec@gmail.com");
            emailHtml.setSubject("Account verification hash code");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = emailHtml.embed(url, "Apache logo");

            // set the html message
            emailHtml.setHtmlMsg(ObjEmail.mtdMsgHtml(emailHtml, "Account verification code:", keyCodificado ));

            // set the alternative message
            emailHtml.setTextMsg("Your email client does not support HTML messages");

            // send the emailHtml
            emailHtml.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(333); // Verificar correo
        
        return true;
    }
    
    public static boolean mtdEnviarRecuperarCuenta(UsuarioDto usuarioDto){
        HtmlEmail emailHtml = new HtmlEmail();
        SecretKey key_codificado = null;
        String keyCodificado = "";
       
        try {
            
            key_codificado = ObjKey.getKeyFromPassword(usuarioDto.getCmpCorreo(), usuarioDto.getCmpPassword());
            keyCodificado = ObjKey.convertSecretKeyToString(key_codificado);
            
        } catch (Exception ex) {
            return false;
        }
        
        try {
            // Create the emailHtml message
            emailHtml.setHostName("in-v3.mailjet.com");
            emailHtml.setSmtpPort(587);
            emailHtml.setAuthenticator(new DefaultAuthenticator("dabf6d9831bdc3404e03d0bd9aebd337", "09af06a814d962d5b7cc20ec093f41e8"));
            emailHtml.setSSLOnConnect(true);
            emailHtml.setFrom("sfw.veontec@gmail.com");
            emailHtml.setSubject("Temporary password for account recovery");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = emailHtml.embed(url, "Apache logo");

            // set the html message
            emailHtml.setHtmlMsg(ObjEmail.mtdMsgHtml(emailHtml, "Temporary password for account recovery:", keyCodificado ));

            // set the alternative message
            emailHtml.setTextMsg("Your email client does not support HTML messages");

            // send the emailHtml
            emailHtml.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(333); // Verificar correo
        
        return true;
    }
    
    public static boolean mtdEnviarBienvenida(UsuarioDto usuarioDto){
        HtmlEmail email = new HtmlEmail();
        
        try {
            // Create the attachment
            //EmailAttachment attachment = new EmailAttachment();
            //File archivo = new File("P:\\Pictures\\ezio.png");
            
            // Create the emailHtml message
            email.setHostName("in-v3.mailjet.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("dabf6d9831bdc3404e03d0bd9aebd337", "09af06a814d962d5b7cc20ec093f41e8"));
            email.setSSLOnConnect(true);
            email.setFrom("sfw.veontec@gmail.com");
            email.setSubject("Account verification");
            email.addTo("" + usuarioDto.getCmpCorreo());

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = email.embed(url, "Apache logo");

            // set the html message
            email.setHtmlMsg(ObjEmail.mtdMsgHtml(email, "Account verification", "Successfully verified account"));

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");
            
            // add the attachment
            //email.attach(attachment);

            // send the emailHtml
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public static String mtdMsgHtml(HtmlEmail email, String info, String contenido){
        String msg="";
        
        try {
            
            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = email.embed(url, "Apache logo");
            
            msg += "<html>";
            msg += "<img src=\"cid:"+cid+"\">";
            msg += "<h1>Welcome to software veontec</h1>";
            msg += "<legend>"+info+"</legend>";
            msg += "<p style='background-color: green; padding: 10px; border-radius: 20px; text-align: center; color: white; font-size: 14px;'>";
            msg += contenido;
            msg += "</p>";
            msg += "<p>visit to <a href='https://programmerauditore.gitlab.io/veontec/#/'>veontec page</a></p>";
            msg += "<article style='padding: 10px; color: #900c3f; font-size: 14px;'>";
            msg += "<p>Note:</p>";
            msg += "<ul>";
            msg += "<li>He does not respond to the email, for any reason.</li>";
            msg += "<li>If you think this email was sent by mistake, ignore it completely.</li>";
            msg += "<li>If you do not know the reason for this message completely ignored.</li>";
            msg += "</ul>";
            msg += "</article>";
            msg += "<p>&nbsp;</p>";
            msg += "<span>Veontec (c) 2021</span>";
            msg += "</html>";
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return msg;
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
            
            // Create the emailHtml message
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
            
            // send the emailHtml
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
            
            // Create the emailHtml message
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
            
            // send the emailHtml
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mtdEnviarMensajeArchivoAdjunto_HTML(){
        try {
            // Create the emailHtml message
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

            // send the emailHtml
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
