package src;

// Aquí se incluye la información del softwares
public class Software {
    public static final String sNombre = "Veontec";
    public static final String sVersionName = "v0.9.97";
    public static final String sVersionNum = "0997";
    public static final String sProduccion = "Alpha";
    public static final String NombreSoftware = sNombre + " " + sVersionName + sProduccion;
    
    public static final Integer veontecCuentaVerificada = 1333;
    public static final Integer veontecCuentaNoVerificada = 333;
    public static final Integer veontecRecuperarCuenta = 777;
    
    public static final Integer veontecResultadoPorPagina = 3;
    public static final Integer veontecCardsPorFila = 3;
    
    /* Produccción  mailjet <-> GMAIL
    public static final String smtpHostName = "HOST";
    public static final String smtpUser = "USER";
    public static final String smtpPasswd = "PASSWORD";
    public static final String smtpSetFrom = "sfw.veontec@support.com";
    public static final Integer smtpPort = 587;
    public static final boolean smtpOnSSL = true;
    public static final boolean smtpOnTLS = true;
    */
    
    /* Desarrollo mailtrap */
    public static final String smtpHostName = "HOST";
    public static final String smtpUser = "USER";
    public static final String smtpPasswd = "PASSWORD";
    public static final Integer smtpPort = 2525;
    public static final String smtpSetFrom = "sfw.veontec@support.com";
    public static final boolean smtpOnSSL = false;
    public static final boolean smtpOnTLS = false;
}
