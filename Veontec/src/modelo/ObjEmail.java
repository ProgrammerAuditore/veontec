package modelo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import modelo.dto.UsuarioDto;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import src.Info;

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
            emailHtml.setHostName(Info.smtpHostName);
            emailHtml.setSmtpPort(Info.smtpPort);
            emailHtml.setAuthenticator(new DefaultAuthenticator(Info.smtpUser, Info.smtpPasswd));
            emailHtml.setSSLOnConnect(Info.smtpOnSSL);
            emailHtml.setStartTLSEnabled(Info.smtpOnTLS);
            emailHtml.setFrom(Info.smtpSetFrom);
            emailHtml.setSubject("Account verification hash code");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = emailHtml.embed(url, "Apache logo");

            // set the html message
            emailHtml.setHtmlMsg(ObjEmail.mtdMsgHtml(emailHtml, "Account verification hash code:", keyCodificado ));

            // set the alternative message
            emailHtml.setTextMsg("Your email client does not support HTML messages");

            // send the emailHtml
            emailHtml.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(Info.veontecCuentaNoVerificada);
        
        return true;
    }
    
    public static boolean mtdEnviarRecuperarCuenta(UsuarioDto usuarioDto){
        HtmlEmail emailHtml = new HtmlEmail();
        SecretKey key_codificado = null;
        String keyCodificado = "";
       
        try {
            
            key_codificado = ObjKey.getKeyFromPassword(usuarioDto.getCmpCorreo()+"."+usuarioDto.getCmpPassword(), 
                    usuarioDto.getCmpCorreo()+"."+new ObjEmail().fncObtenerFechaYHoraActual());
            keyCodificado = ObjKey.convertSecretKeyToString(key_codificado);
            keyCodificado = keyCodificado.substring(0, 16);
            
        } catch (Exception ex) {
            return false;
        }
        
        try {
            // Create the emailHtml message
            emailHtml.setHostName(Info.smtpHostName);
            emailHtml.setSmtpPort(Info.smtpPort);
            emailHtml.setAuthenticator(new DefaultAuthenticator(Info.smtpUser, Info.smtpPasswd));
            emailHtml.setSSLOnConnect(Info.smtpOnSSL);
            emailHtml.setStartTLSEnabled(Info.smtpOnTLS);
            emailHtml.setFrom(Info.smtpSetFrom);
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
            return false;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(Info.veontecRecuperarCuenta);
        
        return true;
    }
    
    public static boolean mtdEnviarBienvenida(UsuarioDto usuarioDto){
        HtmlEmail emailHtml = new HtmlEmail();
        
        try {
            // Create the attachment
            //EmailAttachment attachment = new EmailAttachment();
            //File archivo = new File("P:\\Pictures\\ezio.png");
            
            // Create the emailHtml message
            emailHtml.setHostName(Info.smtpHostName);
            emailHtml.setSmtpPort(Info.smtpPort);
            emailHtml.setAuthenticator(new DefaultAuthenticator(Info.smtpUser, Info.smtpPasswd));
            emailHtml.setSSLOnConnect(Info.smtpOnSSL);
            emailHtml.setStartTLSEnabled(Info.smtpOnTLS);
            emailHtml.setFrom(Info.smtpSetFrom);
            emailHtml.setSubject("Account verification");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = emailHtml.embed(url, "Apache logo");

            // set the html message
            emailHtml.setHtmlMsg(ObjEmail.mtdMsgHtml(emailHtml, "Account verification", "Successfully verified account"));

            // set the alternative message
            emailHtml.setTextMsg("Your email client does not support HTML messages");
            
            // add the attachment
            //email.attach(attachment);

            // send the emailHtml
            emailHtml.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        // usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(Info.veontecCuentaVerificada);
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
    
    public String fncObtenerFechaYHoraActual(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
        
}
