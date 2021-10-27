package modelo.dto;

import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

public class CuentaDto implements Serializable {
    
    // * Datos codificados
    private String correo;
    private String passwd;
    private byte[] bytes;
 
    public String getCorreo() {
        // * Descodificar
        return new String(Base64.decodeBase64(correo.getBytes()));
    }

    public void setCorreo(String correo) {
        // * Codificar 
        this.correo = new String(Base64.encodeBase64(correo.getBytes()));
    }

    public String getPasswd() {
        // * Descodificar
        return new String(Base64.decodeBase64(passwd.getBytes()));
    }

    public void setPasswd(String passwd) {
        // * Codificar
        this.passwd = new String(Base64.encodeBase64(passwd.getBytes()));
    }

    @Override
    public String toString() {
        return "CuentaDto{" + "correo=" + correo + ", passwd=" + passwd + '}';
    }

}
