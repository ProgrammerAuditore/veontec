package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.VentaDto;
import modelo.interfaces.keyword_query;

public class VentaDao implements keyword_query<VentaDto> {

    private final String nombreTabla= "tblventas";
    
    @Override
    public boolean mtdInsetar(VentaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "INSERT INTO " + nombreTabla + " "
                + "( ventProducto, ventVendedor, ventComprador, ventTitulo, ventCantidad, ventPrecio, ventFecha, ventEstado, ventHashCode )"
                + "VALUES "
                + "( ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentProducto());
            ps.setInt(2, obj_dto.getVentVendedor());
            ps.setInt(3, obj_dto.getVentComprador());
            ps.setString(4, obj_dto.getVentTitulo());
            ps.setInt(5, obj_dto.getVentCantidad());
            ps.setDouble(6, obj_dto.getVentPrecio());
            ps.setString(7, obj_dto.getVentFecha());
            ps.setInt(8, obj_dto.getVentEstado());
            ps.setInt(9, obj_dto.getVentHashCode());
            
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
    public boolean mtdRemover(VentaDto obj_dto) {
        // * Funciona 
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "DELETE FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? AND ventID = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            ps.setInt(2, obj_dto.getVentID());
            
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
    
    public boolean mtdRemoverPorHashCode(VentaDto obj_dto) {
        // * Funciona 
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "DELETE FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? AND ventComprador = ? AND ventHashCode = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            ps.setInt(2, obj_dto.getVentComprador());
            ps.setInt(3, obj_dto.getVentHashCode());
            
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
    public boolean mtdActualizar(VentaDto obj_dto) {
        // ^ Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "UPDATE " + nombreTabla + " SET ventProducto = ?, ventComprador = ?, ventEstado = ? "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? AND ventID = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentProducto());
            ps.setInt(2, obj_dto.getVentComprador());
            ps.setInt(3, obj_dto.getVentEstado());
            ps.setInt(4, obj_dto.getVentVendedor());
            ps.setInt(5, obj_dto.getVentID());
            
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
    public VentaDto mtdConsultar(VentaDto obj_dto) {
        VentaDto venta = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? AND ventID = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            ps.setInt(2, obj_dto.getVentID());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            if( rs.next() ){
                venta = new VentaDto();
                venta.setVentID( rs.getInt("ventID") );
                venta.setVentProducto( rs.getInt("ventProducto") );
                venta.setVentComprador( rs.getInt("ventComprador") );
                venta.setVentVendedor(rs.getInt("ventVendedor") );
                venta.setVentTitulo( rs.getString("ventTitulo") );
                venta.setVentFecha( rs.getString("ventFecha") );
                venta.setVentPrecio( rs.getDouble("ventPrecio") );
                venta.setVentCantidad( rs.getInt("ventCantidad") );
                venta.setVentEstado( rs.getInt("ventEstado") );
                venta.setVentHashCode( rs.getInt("ventHashCode") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return venta;
    }

    public List<VentaDto> mtdListar(VentaDto obj_dto) {
        // * Funciona perfectamente
        
        List<VentaDto> ventas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            ventas = new ArrayList<>();
            while( rs.next() ){
                VentaDto venta = new VentaDto(); 
                venta.setVentID( rs.getInt("ventID") );
                venta.setVentProducto( rs.getInt("ventProducto") );
                venta.setVentComprador( rs.getInt("ventComprador") );
                venta.setVentVendedor(rs.getInt("ventVendedor") );
                venta.setVentTitulo( rs.getString("ventTitulo") );
                venta.setVentFecha( rs.getString("ventFecha") );
                venta.setVentPrecio( rs.getDouble("ventPrecio") );
                venta.setVentCantidad( rs.getInt("ventCantidad") );
                venta.setVentEstado( rs.getInt("ventEstado") );
                venta.setVentHashCode( rs.getInt("ventHashCode") );
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return ventas;
    }

    public List<VentaDto> mtdListarAllVentasPorUsuario(VentaDto obj_dto, int inicio, int fin) {
        // Funciona perfectamente
        
        List<VentaDto> ventas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? "
                + "LIMIT ? OFFSET ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            ps.setInt(2, inicio);
            ps.setInt(3, fin);
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            ventas = new ArrayList<>();
            while( rs.next() ){
                VentaDto venta = new VentaDto(); 
                venta.setVentID( rs.getInt("ventID") );
                venta.setVentProducto( rs.getInt("ventProducto") );
                venta.setVentComprador( rs.getInt("ventComprador") );
                venta.setVentVendedor(rs.getInt("ventVendedor") );
                venta.setVentTitulo( rs.getString("ventTitulo") );
                venta.setVentFecha( rs.getString("ventFecha") );
                venta.setVentPrecio( rs.getDouble("ventPrecio") );
                venta.setVentCantidad( rs.getInt("ventCantidad") );
                venta.setVentEstado( rs.getInt("ventEstado") );
                venta.setVentHashCode( rs.getInt("ventHashCode") );
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return ventas;
    }
    
    public List<VentaDto> mtdBuscarAllVentasPorUsuarioSimilares(VentaDto obj_dto, int inicio, int fin) {
        // Funciona perfectamente
        
        List<VentaDto> ventas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? AND ventTitulo LIKE ? "
                + "LIMIT ? OFFSET ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            ps.setString(2, obj_dto.getVentTitulo());
            ps.setInt(3, inicio);
            ps.setInt(4, fin);
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            ventas = new ArrayList<>();
            while( rs.next() ){
                VentaDto venta = new VentaDto(); 
                venta.setVentID( rs.getInt("ventID") );
                venta.setVentProducto( rs.getInt("ventProducto") );
                venta.setVentComprador( rs.getInt("ventComprador") );
                venta.setVentVendedor(rs.getInt("ventVendedor") );
                venta.setVentTitulo( rs.getString("ventTitulo") );
                venta.setVentFecha( rs.getString("ventFecha") );
                venta.setVentPrecio( rs.getDouble("ventPrecio") );
                venta.setVentCantidad( rs.getInt("ventCantidad") );
                venta.setVentEstado( rs.getInt("ventEstado") );
                venta.setVentHashCode( rs.getInt("ventHashCode") );
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return ventas;
    }

    public long mtdRowCountAllVentasPorUsuario(VentaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? ;";
        long registros = 0;
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            
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
        
    public long mtdRowCountAllVentasPorUsuarioSimilares(VentaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE ventVendedor = ? AND ventTitulo LIKE ? ;";
        long registros = 0;
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getVentVendedor());
            ps.setString(2, obj_dto.getVentTitulo());
            
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
    
}
