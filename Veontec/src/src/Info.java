package src;

// Aquí se incluye la información del softwares
public class Info {
    public static final String sNombre = "Veontec";
    public static final String sVersionName = "v0.9.87";
    public static final String sVersionNum = "0987";
    public static final String sProduccion = "Alpha";
    public static final String NombreSoftware = sNombre + " " + sVersionName + sProduccion;
    
    public static final Integer veontecCuentaVerificada = 1333;
    public static final Integer veontecCuentaNoVerificada = 333;
    public static final Integer veontecRecuperarCuenta = 777;
    
    /* Produccción  mailjet <-> GMAIL
    public static final String smtpUser = "dabf6d9831bdc3404e03d0bd9aebd337";
    public static final String smtpPasswd = "09af06a814d962d5b7cc20ec093f41e8";
    public static final String smtpHostName = "in-v3.mailjet.com";
    public static final String smtpSetFrom = "sfw.veontec@support.com";
    public static final Integer smtpPort = 587;
    public static final boolean smtpOnSSL = true;
    public static final boolean smtpOnTLS = true;
    */
    
    /* Desarrollo mailtrap */
    public static final String smtpHostName = "HOST";
    public static final Integer smtpPort = 2525;
    public static final String smtpUser = "USER";
    public static final String smtpPasswd = "PASSWORD";
    public static final String smtpSetFrom = "sfw.veontec@support.com";
    public static final boolean smtpOnSSL = false;
    public static final boolean smtpOnTLS = false;
}
