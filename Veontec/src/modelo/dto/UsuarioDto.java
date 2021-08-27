package modelo.dto;

public class UsuarioDto {
    
    private Integer cmpID;
    private String cmpNombreCompleto;
    private String cmpCorreo;
    private String cmpPassword;

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

    @Override
    public String toString() {
        String info = "\n"
                + "cmpID : " + cmpID + "\n"
                + "cmpNombre : " + cmpNombreCompleto + "\n"
                + "cmpPassword : " + cmpPassword + "\n"
                + "cmpCorreo : " + cmpCorreo + "\n";
        return info;
    }
    
}
