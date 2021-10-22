package modelo.dto;

public class RespuestaDto {
    
    private Integer respID;
    private Integer respPregunta;
    private Integer respProducto;
    private Integer respVendedor;
    private Integer respComprador;
    private String respRespuesta;
    private String respFecha;
    private Integer respEstado;
    private String respCreadoEn;
    private String respActualizadoEn;
    
    public Integer getRespID() {
        return respID;
    }

    public void setRespID(Integer respID) {
        this.respID = respID;
    }

    public Integer getRespPregunta() {
        return respPregunta;
    }

    public void setRespPregunta(Integer respPregunta) {
        this.respPregunta = respPregunta;
    }

    public Integer getRespProducto() {
        return respProducto;
    }

    public void setRespProducto(Integer respProducto) {
        this.respProducto = respProducto;
    }

    public Integer getRespVendedor() {
        return respVendedor;
    }

    public void setRespVendedor(Integer respVendedor) {
        this.respVendedor = respVendedor;
    }

    public Integer getRespComprador() {
        return respComprador;
    }

    public void setRespComprador(Integer respComprador) {
        this.respComprador = respComprador;
    }

    public String getRespRespuesta() {
        return respRespuesta;
    }

    public void setRespRespuesta(String respRespuesta) {
        this.respRespuesta = respRespuesta;
    }

    public String getRespFecha() {
        return respFecha;
    }

    public void setRespFecha(String respFecha) {
        this.respFecha = respFecha;
    }

    public Integer getRespEstado() {
        return respEstado;
    }

    public void setRespEstado(Integer respEstado) {
        this.respEstado = respEstado;
    }

    public String getRespCreadoEn() {
        return respCreadoEn;
    }

    public void setRespCreadoEn(String respCreadoEn) {
        this.respCreadoEn = respCreadoEn;
    }

    public String getRespActualizadoEn() {
        return respActualizadoEn;
    }

    public void setRespActualizadoEn(String respActualizadoEn) {
        this.respActualizadoEn = respActualizadoEn;
    }
    
    @Override
    public String toString() {
        String msg = "RespuestaDto { " + "\n";
        msg += "respID = " + respID + "\n";
        msg += "respPregunta = " + respPregunta + "\n";
        msg += "respComprador = " + respComprador + "\n";
        msg += "respVendedor = " + respVendedor + "\n";
        msg += "respProducto = " + respProducto + "\n";
        msg += "respRespuesta = " + respRespuesta + "\n";
        msg += "respFecha = " + respFecha + "\n";
        msg += "respEstado = " + respEstado + "\n";
        msg += "respEstado = " + respCreadoEn + "\n";
        msg += "respEstado = " + respActualizadoEn + "\n";
        msg += "}\n";
        return msg; 
    }
    
}
