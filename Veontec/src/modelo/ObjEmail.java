/*
 * Copyright (C) 2021 victor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package modelo;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author victor
 */
public class ObjEmail {
    
    public void mtdEnviarEmail(String recipiente){
        System.out.println("Preparando el email ...");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        prop.put("mail.smtp.host", "in-v3.mailjet.com");
        prop.put("mail.smtp.port", "587");
        
        String myCuentaGmail = "a1238bce0ca670d72a071451af6accde";
        String password = "11218c5a874df874ef5aca4aedeada36";
        
        Session ss = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myCuentaGmail, password);
            }
        });
        
        Message msg = prepararMensaje(ss, myCuentaGmail, recipiente);
        try {
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Mensaje enviado exitosamente.");
    }
    
    private Message prepararMensaje(Session ss, String mcg, String recipiente){
        Message msg = new MimeMessage(ss);
        try {
            msg.setFrom(new InternetAddress(mcg));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipiente));
            msg.setSubject("Probando MailJet SMS");
            msg.setText("Estoy haciendo pruebas.");
            return msg;
        } catch (AddressException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void enviarMen(){
        // Recipient's email ID needs to be mentioned.
        String to = "fromaddress@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "toaddress@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("fromaddress@gmail.com", "*******");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        
        
    }
    
    public void mtdMSG(){
        Properties props = new Properties();
        props.put("mail.smtp.host","in-v3.mailjet.com");
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.user", "a1238bce0ca670d72a071451af6accde");
        props.setProperty("mail.password", "11218c5a874df874ef5aca4aedeada36");
        
        Session mailSession = Session.getInstance(props,null);
        Message msg = new MimeMessage(mailSession);
        
        try {
            msg.setSubject("Mensaje de Línea de Código");
            msg.setFrom(new InternetAddress("victorvj098@gmail.com","Línea de Código"));
            msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress("victorvj098@gmail.com") });
            
            //DataHandler dh = new DataHandler("sdsdss");
            //msg.setDataHandler(dh);
            javax.mail.Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ObjEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
}
