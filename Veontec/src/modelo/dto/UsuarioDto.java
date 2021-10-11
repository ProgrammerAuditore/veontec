package modelo.dto;

public class UsuarioDto {
    
    private Integer cmpID;
    private String cmpNombreCompleto;
    private String cmpCorreo;
    private String cmpPassword;
    private String cmpDireccion;
    private String cmpTelefono;

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

    @Override
    public String toString() {
        return "UsuarioDto{" + "cmpID=" + cmpID + ", cmpNombreCompleto=" + cmpNombreCompleto + ", cmpCorreo=" + cmpCorreo + ", cmpPassword=" + cmpPassword + ", cmpDireccion=" + cmpDireccion + ", cmpTelefono=" + cmpTelefono + '}';
    }
    
}
