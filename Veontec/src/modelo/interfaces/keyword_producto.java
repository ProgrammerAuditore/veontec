package modelo.interfaces;

import java.util.List;

public interface keyword_producto<none> {
    public List<none> mtdListar();
    public List<none> mtdListar(none dto);
    public List<none> mtdListar(int inicio, int fin);
    public List<none> mtdListar(none obj_dto, int inicio, int fin);
    public long mtdRowCount();
    public long mtdRowCount(none obj_dto);
    public long mtdRowCount(int estado);
    public boolean mtdComprobar(none obj_dto);
    public boolean mtdEliminar(none obj_dto);
}
