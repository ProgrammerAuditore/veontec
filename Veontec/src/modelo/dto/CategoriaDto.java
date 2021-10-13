package modelo.dto;

public class CategoriaDto {
    
    private Integer cateID;
    private Integer cateUsuario;
    private String cateNombre;
    private Integer cateTotalProductos;

    public Integer getCateID() {
        return cateID;
    }

    public void setCateID(Integer cateID) {
        this.cateID = cateID;
    }

    public Integer getCateUsuario() {
        return cateUsuario;
    }

    public void setCateUsuario(Integer cateUsuario) {
        this.cateUsuario = cateUsuario;
    }

    public String getCateNombre() {
        return cateNombre;
    }

    public void setCateNombre(String cateNombre) {
        this.cateNombre = cateNombre;
    }

    public Integer getCateTotalProductos() {
        return cateTotalProductos;
    }

    public void setCateTotalProductos(Integer cateTotalProductos) {
        this.cateTotalProductos = cateTotalProductos;
    }

    @Override
    public String toString() {
        return "CategoriaDto{" + "cateID=" + cateID + ", cateUsuario=" + cateUsuario + ", cateNombre=" + cateNombre + ", cateTotalProductos=" + cateTotalProductos + '}';
    }

}
