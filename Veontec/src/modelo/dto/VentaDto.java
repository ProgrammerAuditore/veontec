package modelo.dto;

public class VentaDto {
    
    private Integer ventID;
    private Integer ventProducto;
    private Integer ventComprador;
    private Integer ventVendedor;
    private String ventTitulo;
    private Integer ventCantidad;
    private Double ventPrecio;
    private String ventFecha;
    private Integer ventEstado;

    public Integer getVentID() {
        return ventID;
    }

    public void setVentID(Integer ventID) {
        this.ventID = ventID;
    }

    public Integer getVentProducto() {
        return ventProducto;
    }

    public void setVentProducto(Integer ventProducto) {
        this.ventProducto = ventProducto;
    }

    public Integer getVentComprador() {
        return ventComprador;
    }

    public void setVentComprador(Integer ventComprador) {
        this.ventComprador = ventComprador;
    }

    public Integer getVentVendedor() {
        return ventVendedor;
    }

    public void setVentVendedor(Integer ventVendedor) {
        this.ventVendedor = ventVendedor;
    }

    public String getVentTitulo() {
        return ventTitulo;
    }

    public void setVentTitulo(String ventTitulo) {
        this.ventTitulo = ventTitulo;
    }

    public Integer getVentCantidad() {
        return ventCantidad;
    }

    public void setVentCantidad(Integer ventCantidad) {
        this.ventCantidad = ventCantidad;
    }

    public Double getVentPrecio() {
        return ventPrecio;
    }

    public void setVentPrecio(Double ventPrecio) {
        this.ventPrecio = ventPrecio;
    }

    public String getVentFecha() {
        return ventFecha;
    }

    public void setVentFecha(String ventFecha) {
        this.ventFecha = ventFecha;
    }

    public Integer getVentEstado() {
        return ventEstado;
    }

    public void setVentEstado(Integer ventEstado) {
        this.ventEstado = ventEstado;
    }

    @Override
    public String toString() {
        String msg = "";
        msg += "ventID = " + ventID + "\n";
        msg += "ventComprador = " + ventComprador + "\n";
        msg += "ventVendedor = " + ventVendedor + "\n";
        msg += "ventProducto = " + ventProducto + "\n";
        return msg; 
    }
    
}
