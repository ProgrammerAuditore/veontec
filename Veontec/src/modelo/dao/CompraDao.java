package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.CompraDto;
import modelo.interfaces.keyword_extra;
import modelo.interfaces.keyword_query;


public class CompraDao implements keyword_query<CompraDto>, keyword_extra<CompraDto> {

    private final String nombreTabla= "tblcompras";
    
    /**
     * Se usa para insertar o agregar un registro en la tabla
     * <tt>tblcompras</tt> de la base de datos remoto.<br>
     * Los datos a insertar son <tt>compProducto</tt>, <tt>compVendedor</tt>, <br>
     * <tt>compComprador</tt>, <tt>compTitulo</tt>, <tt>compCantidad</tt>, <br>
     * <tt>compPrecio</tt>, <tt>compFecha</tt>, <tt>compEstado</tt> <br>
     * 
     * @param obj_dto Es una instancia o objeto de tipo  <tt>CompraDto</tt> 
     * @return <tt>true</tt> si se agrego un registro con exito <br>
     *          <tt>false</tt> si no se pudo agregar un registro
     * @throws SQLException si ocurre una error intentar registrar
     *          un registro en la bae de datos
     * @see CompraDto
     */
    @Override
    public boolean mtdInsetar(CompraDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "INSERT INTO " + nombreTabla + " "
                + "( compProducto, compVendedor, compComprador, compTitulo, compCantidad, compPrecio, compFecha, compEstado )"
                + "VALUES "
                + "( ?, ?, ?, ?, ?, ?, ?, ?); ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompProducto());
            ps.setInt(2, obj_dto.getCompVendedor());
            ps.setInt(3, obj_dto.getCompComprador());
            ps.setString(4, obj_dto.getCompTitulo());
            ps.setInt(5, obj_dto.getCompCantidad());
            ps.setDouble(6, obj_dto.getCompPrecio());
            ps.setString(7, obj_dto.getCompFecha());
            ps.setInt(8, obj_dto.getCompEstado());
            
            // * Ejecutar la consulta
            int respuesta = ps.executeUpdate();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( respuesta > 0 )
                return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdRemover(CompraDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "DELETE FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? AND compID = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompComprador());
            ps.setInt(2, obj_dto.getCompID());
            
            // * Ejecutar la consulta
            int respuesta = ps.executeUpdate();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( respuesta > 0 )
                return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdActualizar(CompraDto obj_dto) {
        // ^ Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "UPDATE " + nombreTabla + " SET compProducto = ?, compVendedor = ?, compEstado = ? "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? AND compID = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompProducto());
            ps.setInt(2, obj_dto.getCompVendedor());
            ps.setInt(3, obj_dto.getCompEstado());
            ps.setInt(4, obj_dto.getCompComprador());
            ps.setInt(5, obj_dto.getCompID());
            
            // * Ejecutar la consulta
            int respuesta = ps.executeUpdate();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( respuesta > 0 )
                return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public CompraDto mtdConsultar(CompraDto obj_dto) {
        CompraDto compra = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? AND compID = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompComprador());
            ps.setInt(2, obj_dto.getCompID());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            if( rs.next() ){
                compra = new CompraDto();
                compra.setCompID( rs.getInt("compID") );
                compra.setCompProducto( rs.getInt("compProducto") );
                compra.setCompComprador( rs.getInt("compComprador") );
                compra.setCompVendedor(rs.getInt("compVendedor") );
                compra.setCompTitulo( rs.getString("compTitulo") );
                compra.setCompFecha( rs.getString("compFecha") );
                compra.setCompPrecio( rs.getDouble("compPrecio") );
                compra.setCompCantidad( rs.getInt("compCantidad") );
                compra.setCompEstado( rs.getInt("compEstado") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return compra;
    }

    @Override
    public List<CompraDto> mtdListar(CompraDto obj_dto) {
        // * Funciona perfectamente
        
        List<CompraDto> ventas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompComprador());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            ventas = new ArrayList<>();
            while( rs.next() ){
                CompraDto compra = new CompraDto(); 
                compra.setCompID( rs.getInt("compID") );
                compra.setCompProducto( rs.getInt("compProducto") );
                compra.setCompComprador( rs.getInt("compComprador") );
                compra.setCompVendedor(rs.getInt("compVendedor") );
                compra.setCompTitulo( rs.getString("compTitulo") );
                compra.setCompFecha( rs.getString("compFecha") );
                compra.setCompPrecio( rs.getDouble("compPrecio") );
                compra.setCompCantidad( rs.getInt("compCantidad") );
                compra.setCompEstado( rs.getInt("compEstado") );
                ventas.add(compra);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return ventas;
    }
    
    /**
    * Returns an Image object that can then be painted on the screen. 
    * The url argument must specify an absolute {@link URL}. The name
    * argument is a specifier that is relative to the url argument. 
    * <p>
    * This method always returns immediately, whether or not the 
    * image exists. When this applet attempts to draw the image on
    * the screen, the data will be loaded. The graphics primitives 
    * that draw the image will incrementally paint on the screen. 
    *
    * @param  obj_dto  Es una instancia o objeto <tt>CompraDto<tt>
    * @param  inicio  Es un entero representa la <tt>cantidad de registros<tt> a mostrar
    * @param  fin Es un entero representa el punto de parte de los registros
    * @return      Una lista de objetos de tipo <tt>CompraDto<tt>
    * @see         CompraDto
    */
    @Override
    public List<CompraDto> mtdListar(CompraDto obj_dto, int inicio, int fin) {
        // Funciona perfectamente
        
        List<CompraDto> ventas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? "
                + "LIMIT ? OFFSET ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompComprador());
            ps.setInt(2, inicio);
            ps.setInt(3, fin);
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            ventas = new ArrayList<>();
            while( rs.next() ){
                CompraDto compra = new CompraDto(); 
                compra.setCompID( rs.getInt("compID") );
                compra.setCompProducto( rs.getInt("compProducto") );
                compra.setCompComprador( rs.getInt("compComprador") );
                compra.setCompVendedor(rs.getInt("compVendedor") );
                compra.setCompTitulo( rs.getString("compTitulo") );
                compra.setCompFecha( rs.getString("compFecha") );
                compra.setCompPrecio( rs.getDouble("compPrecio") );
                compra.setCompCantidad( rs.getInt("compCantidad") );
                compra.setCompEstado( rs.getInt("compEstado") );
                ventas.add(compra);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return ventas;
    }
    
    public List<CompraDto> mtdListarBuscarCompras(CompraDto obj_dto, int inicio, int fin) {
        // Funciona perfectamente
        
        List<CompraDto> ventas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? AND compTitulo LIKE ? "
                + "LIMIT ? OFFSET ? ; ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompComprador());
            ps.setString(2, obj_dto.getCompTitulo());
            ps.setInt(3, inicio);
            ps.setInt(4, fin);
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            ventas = new ArrayList<>();
            while( rs.next() ){
                CompraDto compra = new CompraDto(); 
                compra.setCompID( rs.getInt("compID") );
                compra.setCompProducto( rs.getInt("compProducto") );
                compra.setCompComprador( rs.getInt("compComprador") );
                compra.setCompVendedor(rs.getInt("compVendedor") );
                compra.setCompTitulo( rs.getString("compTitulo") );
                compra.setCompFecha( rs.getString("compFecha") );
                compra.setCompPrecio( rs.getDouble("compPrecio") );
                compra.setCompCantidad( rs.getInt("compCantidad") );
                compra.setCompEstado( rs.getInt("compEstado") );
                ventas.add(compra);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return ventas;
    }

    @Override
    public long mtdRowCount(CompraDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE compComprador = ? ;";
        long registros = 0;
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCompComprador());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( rs.next() )
                registros = rs.getInt(1);
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return registros;
    }
    
    @Override
    public long mtdRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<CompraDto> mtdListar(int inicio, int fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<CompraDto> mtdListar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public long mtdRowCount(int estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdComprobar(CompraDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdEliminar(CompraDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
