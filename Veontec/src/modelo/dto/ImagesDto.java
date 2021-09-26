package modelo.dto;

import java.awt.Image;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

public class ImagesDto {
    
    private Integer imagID; 
    private Integer imagUsuario; 
    private Integer imagProducto; 
    private ObjectInputStream imagMediaBinary; 
    private Image imagMediaImg;

    public Integer getImagID() {
        return imagID;
    }

    public void setImagID(Integer imagID) {
        this.imagID = imagID;
    }

    public Integer getImagUsuario() {
        return imagUsuario;
    }

    public void setImagUsuario(Integer imagUsuario) {
        this.imagUsuario = imagUsuario;
    }

    public Integer getImagProducto() {
        return imagProducto;
    }

    public void setImagProducto(Integer imagProducto) {
        this.imagProducto = imagProducto;
    }

    public ObjectInputStream getImagMediaBinary() {
        return imagMediaBinary;
    }

    public void setImagMediaBinary(ObjectInputStream imagMediaBinary) {
        this.imagMediaBinary = imagMediaBinary;
    }

    public Image getImagMediaImg() {
        return imagMediaImg;
    }

    public void setImagMediaImg(Image imagMediaImg) {
        this.imagMediaImg = imagMediaImg;
    }
 
    private static final Logger LOG = Logger.getLogger(ImagesDto.class.getName());

    @Override
    public String toString() {
        return "ImagesDto{" + "imagID=" + imagID + ", imagUsuario=" + imagUsuario + ", imagProducto=" + imagProducto + ", imagMediaBinary=" + imagMediaBinary + ", imagMediaImg=" + imagMediaImg + '}';
    }
    
}
