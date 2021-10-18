package modelo.dto;

public class PreguntaDto {
    
    private Integer pregID;
    private String pregTitulo;
    private Integer pregProducto;
    private Integer pregVendedor;
    private Integer pregComprador;
    private String pregPregunta;
    private String pregFecha;
    private Integer pregEstado;

    public Integer getPregID() {
        return pregID;
    }

    public String getPregTitulo() {
        return pregTitulo;
    }

    public void setPregTitulo(String pregTitulo) {
        this.pregTitulo = pregTitulo;
    }

    public void setPregID(Integer pregID) {
        this.pregID = pregID;
    }

    public Integer getPregProducto() {
        return pregProducto;
    }

    public void setPregProducto(Integer pregProducto) {
        this.pregProducto = pregProducto;
    }

    public Integer getPregVendedor() {
        return pregVendedor;
    }

    public void setPregVendedor(Integer pregVendedor) {
        this.pregVendedor = pregVendedor;
    }

    public Integer getPregComprador() {
        return pregComprador;
    }

    public void setPregComprador(Integer pregComprador) {
        this.pregComprador = pregComprador;
    }

    public String getPregPregunta() {
        return pregPregunta;
    }

    public void setPregPregunta(String pregPregunta) {
        this.pregPregunta = pregPregunta;
    }

    public String getPregFecha() {
        return pregFecha;
    }

    public void setPregFecha(String pregFecha) {
        this.pregFecha = pregFecha;
    }

    public Integer getPregEstado() {
        return pregEstado;
    }

    public void setPregEstado(Integer pregEstado) {
        this.pregEstado = pregEstado;
    }

    @Override
    public String toString() {
        return "PreguntaDto{" + "pregID=" + pregID + ", pregProducto=" + pregProducto + ", pregVendedor=" + pregVendedor + ", pregComprador=" + pregComprador + ", pregPregunta=" + pregPregunta + ", pregFecha=" + pregFecha + ", pregEstado=" + pregEstado + '}';
    }
    
}
