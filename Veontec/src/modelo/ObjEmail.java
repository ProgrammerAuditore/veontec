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
import src.Software;

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
            emailHtml.setHostName(Software.smtpHostName);
            emailHtml.setSmtpPort(Software.smtpPort);
            emailHtml.setAuthenticator(new DefaultAuthenticator(Software.smtpUser, Software.smtpPasswd));
            emailHtml.setSSLOnConnect(Software.smtpOnSSL);
            emailHtml.setStartTLSEnabled(Software.smtpOnTLS);
            emailHtml.setFrom(Software.smtpSetFrom);
            emailHtml.setSubject("Account verification hash code");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // set the html message
            emailHtml.setHtmlMsg(ObjEmail.mtdMsgHtml(emailHtml, "Account verification hash code:", keyCodificado ));

            // set the alternative message
            emailHtml.setTextMsg("Your email client does not support HTML messages");

            // send the emailHtml
            emailHtml.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
        usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(Software.veontecCuentaNoVerificada);
        
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
            emailHtml.setHostName(Software.smtpHostName);
            emailHtml.setSmtpPort(Software.smtpPort);
            emailHtml.setAuthenticator(new DefaultAuthenticator(Software.smtpUser, Software.smtpPasswd));
            emailHtml.setSSLOnConnect(Software.smtpOnSSL);
            emailHtml.setStartTLSEnabled(Software.smtpOnTLS);
            emailHtml.setFrom(Software.smtpSetFrom);
            emailHtml.setSubject("Temporary password for account recovery");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // set the html message
            emailHtml.setHtmlMsg(ObjEmail.mtdMsgHtml(emailHtml, "Temporary password for account recovery:", keyCodificado ));

            // set the alternative message
            emailHtml.setTextMsg("Your email client does not support HTML messages");

            // send the emailHtml
            emailHtml.send();
        } catch (EmailException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
        usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(Software.veontecRecuperarCuenta);
        
        return true;
    }
    
    public static boolean mtdEnviarBienvenida(UsuarioDto usuarioDto){
        HtmlEmail emailHtml = new HtmlEmail();
        
        try {
            // Create the attachment
            //EmailAttachment attachment = new EmailAttachment();
            //File archivo = new File("P:\\Pictures\\ezio.png");
            
            // Create the emailHtml message
            emailHtml.setHostName(Software.smtpHostName);
            emailHtml.setSmtpPort(Software.smtpPort);
            emailHtml.setAuthenticator(new DefaultAuthenticator(Software.smtpUser, Software.smtpPasswd));
            emailHtml.setSSLOnConnect(Software.smtpOnSSL);
            emailHtml.setStartTLSEnabled(Software.smtpOnTLS);
            emailHtml.setFrom(Software.smtpSetFrom);
            emailHtml.setSubject("Account verification");
            emailHtml.addTo("" + usuarioDto.getCmpCorreo());

            // embed the image and get the content id
            //URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            //String cid = emailHtml.embed(url, "Apache logo");

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
        } 
        
        // usuarioDto.setCmpKey(keyCodificado);
        usuarioDto.setCmpEstado(Software.veontecCuentaVerificada);
        return true;
    }
    
    public static String mtdMsgHtml(HtmlEmail email, String info, String contenido){
        String msg="";
        
        try {
            
            // embed the image and get the content id
            URL url = new URL("https://scontent.fgdl5-4.fna.fbcdn.net/v/t1.15752-9/254093501_1126808778067167_7452774238143269890_n.png?"
            + "_nc_cat=108&ccb=1-5&_nc_sid=ae9488&_nc_ohc=eeMwhOs-kK0AX-9X6NE&_nc_ht=scontent.fgdl5-4.fna&oh=ddb53dcb99c15ecbdfb672c45203f1a9&oe=61B9DF11");
            String cid = email.embed(url, "Apache logo");
            
            msg += "<html>";
            msg += "<img style='width:64%; height:64%; ' src=\"cid:"+cid+"\">";
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
