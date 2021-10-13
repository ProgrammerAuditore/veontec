package modelo.dto;

import java.util.logging.Logger;

public class UsuarioDto {
    
    private Integer cmpID;
    private String cmpNombreCompleto;
    private String cmpCorreo;
    private String cmpPassword;
    private String cmpDireccion;
    private String cmpTelefono;
    private String cmpKey;
    private Integer cmpEstado;

    public Integer getCmpID() {
        return cmpID;
    }

    public void setCmpID(Integer cmpID) {
        this.cmpID = cmpID;
    }

    public String getCmpNombreCompleto() {
        return cmpNombreCompleto;
    }

    public void setCmpNombreCompleto(String cmpNombreCompleto) {
        this.cmpNombreCompleto = cmpNombreCompleto;
    }

    public String getCmpCorreo() {
        return cmpCorreo;
    }

    public void setCmpCorreo(String cmpCorreo) {
        this.cmpCorreo = cmpCorreo;
    }

    public String getCmpPassword() {
        return cmpPassword;
    }

    public void setCmpPassword(String cmpPassword) {
        this.cmpPassword = cmpPassword;
    }

    public String getCmpDireccion() {
        return cmpDireccion;
    }

    public void setCmpDireccion(String cmpDireccion) {
        this.cmpDireccion = cmpDireccion;
    }

    public String getCmpTelefono() {
        return cmpTelefono;
    }

    public void setCmpTelefono(String cmpTelefono) {
        this.cmpTelefono = cmpTelefono;
    }

    public String getCmpKey() {
        return cmpKey;
    }

    public void setCmpKey(String cmpKey) {
        this.cmpKey = cmpKey;
    }

    public Integer getCmpEstado() {
        return cmpEstado;
    }

    public void setCmpEstado(Integer cmpEstado) {
        this.cmpEstado = cmpEstado;
    }

    @Override
    public String toString() {
        return "UsuarioDto{" + "cmpID=" + cmpID + ", cmpNombreCompleto=" + cmpNombreCompleto + ", cmpCorreo=" + cmpCorreo + ", cmpPassword=" + cmpPassword + ", cmpDireccion=" + cmpDireccion + ", cmpTelefono=" + cmpTelefono + ", cmpKey=" + cmpKey + ", cmpEstado=" + cmpEstado + '}';
    }
    
}
