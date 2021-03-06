package modelo.dto;

public class ProductoDto {
    
    private Integer prodID;
    private String prodTitulo;
    private String prodDescripcion;
    private String prodCategoria;
    private Double prodPrecio;
    private Integer prodStock;
    private Integer prodTipo;
    private String prodEnlace;
    private Integer prodUsuario;
    private byte[] prodImg;
    private String prodCreadoEn;
    private String prodActualizadoEn;

    public Integer getProdID() {
        return prodID;
    }

    public void setProdID(Integer prodID) {
        this.prodID = prodID;
    }

    public String getProdTitulo() {
        return prodTitulo;
    }

    public void setProdTitulo(String prodTitulo) {
        this.prodTitulo = prodTitulo;
    }

    public String getProdDescripcion() {
        return prodDescripcion;
    }

    public void setProdDescripcion(String prodDescripcion) {
        this.prodDescripcion = prodDescripcion;
    }

    public String getProdCategoria() {
        return prodCategoria;
    }

    public void setProdCategoria(String prodCategoria) {
        this.prodCategoria = prodCategoria;
    }

    public Double getProdPrecio() {
        return prodPrecio;
    }

    public void setProdPrecio(Double prodPrecio) {
        this.prodPrecio = prodPrecio;
    }

    public Integer getProdStock() {
        return prodStock;
    }

    public void setProdStock(Integer prodStock) {
        this.prodStock = prodStock;
    }

    public Integer getProdTipo() {
        return prodTipo;
    }

    public void setProdTipo(Integer prodTipo) {
        this.prodTipo = prodTipo;
    }

    public String getProdEnlace() {
        return prodEnlace;
    }

    public void setProdEnlace(String prodEnlace) {
        this.prodEnlace = prodEnlace;
    }

    public Integer getProdUsuario() {
        return prodUsuario;
    }

    public void setProdUsuario(Integer prodUsuario) {
        this.prodUsuario = prodUsuario;
    }

    public byte[] getProdImg() {
        return prodImg;
    }

    public void setProdImg(byte[] prodImg) {
        this.prodImg = prodImg;
    }

    public String getProdCreadoEn() {
        return prodCreadoEn;
    }

    public void setProdCreadoEn(String prodCreadoEn) {
        this.prodCreadoEn = prodCreadoEn;
    }

    public String getProdActualizadoEn() {
        return prodActualizadoEn;
    }

    public void setProdActualizadoEn(String prodActualizadoEn) {
        this.prodActualizadoEn = prodActualizadoEn;
    }

    @Override
    public String toString() {
        return "ProductoDto{" + "prodID=" + prodID + ", prodTitulo=" + prodTitulo + ", prodDescripcion=" + prodDescripcion + ", prodCategoria=" + prodCategoria + ", prodPrecio=" + prodPrecio + ", prodStock=" + prodStock + ", prodTipo=" + prodTipo + ", prodEnlace=" + prodEnlace + ", prodUsuario=" + prodUsuario + ", prodImg=" + prodImg + ", prodCreadoEn=" + prodCreadoEn + ", prodActualizadoEn=" + prodActualizadoEn + '}';
    }
    
}
