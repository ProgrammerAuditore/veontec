package modelo.dto;

public class CompraDto {
    
    private Integer compID;
    private Integer compProducto;
    private Integer compComprador;
    private Integer compVendedor;
    private String compTitulo;
    private Integer compCantidad;
    private Double compPrecio;
    private String compFecha;
    private Integer compEstado;
    private Integer compHashCode;

    public Integer getCompID() {
        return compID;
    }

    public void setCompID(Integer compID) {
        this.compID = compID;
    }

    public Integer getCompProducto() {
        return compProducto;
    }

    public void setCompProducto(Integer compProducto) {
        this.compProducto = compProducto;
    }

    public Integer getCompComprador() {
        return compComprador;
    }

    public void setCompComprador(Integer compComprador) {
        this.compComprador = compComprador;
    }

    public Integer getCompVendedor() {
        return compVendedor;
    }

    public void setCompVendedor(Integer compVendedor) {
        this.compVendedor = compVendedor;
    }

    public String getCompTitulo() {
        return compTitulo;
    }

    public void setCompTitulo(String compTitulo) {
        this.compTitulo = compTitulo;
    }

    public Integer getCompCantidad() {
        return compCantidad;
    }

    public void setCompCantidad(Integer compCantidad) {
        this.compCantidad = compCantidad;
    }

    public Double getCompPrecio() {
        return compPrecio;
    }

    public void setCompPrecio(Double compPrecio) {
        this.compPrecio = compPrecio;
    }

    public String getCompFecha() {
        return compFecha;
    }

    public void setCompFecha(String compFecha) {
        this.compFecha = compFecha;
    }

    public Integer getCompEstado() {
        return compEstado;
    }

    public void setCompEstado(Integer compEstado) {
        this.compEstado = compEstado;
    }

    public Integer getCompHashCode() {
        return compHashCode;
    }

    public void setCompHashCode(Integer compHashCode) {
        this.compHashCode = compHashCode;
    }

    @Override
    public String toString() {
        return "CompraDto{" + "compID=" + compID + 
                ", compProducto=" + compProducto + 
                ", compComprador=" + compComprador + 
                ", compVendedor=" + compVendedor + 
                ", compTitulo=" + compTitulo + 
                ", compCantidad=" + compCantidad 
                + ", compPrecio=" + compPrecio + 
                ", compFecha=" + compFecha + 
                ", compEstado=" + compEstado + 
                ", compHashCode=" + compHashCode + '}';
    }
    
}
