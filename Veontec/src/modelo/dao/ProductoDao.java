package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProductoDto;
import modelo.interfaces.keyword_query;
import modelo.interfaces.keyword_extra;
import modelo.interfaces.keyword_producto;

public class ProductoDao implements keyword_query<ProductoDto>, keyword_producto<ProductoDto>{

    @Override
    public boolean mtdInsetar(ProductoDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "INSERT INTO tblproductos "
                + "( prodTitulo, prodDescripcion, prodCategoria, prodPrecio, prodStock, prodTipo, prodEnlace, prodUsuario )"
                + "VALUES "
                + "( ?, ?, ?, ?, ?, ?, ?, ?); ";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setString(1, obj_dto.getProdTitulo());
            ps.setString(2, obj_dto.getProdDescripcion());
            ps.setString(3, obj_dto.getProdCategoria());
            ps.setDouble(4, obj_dto.getProdPrecio());
            ps.setInt(5, obj_dto.getProdStock());
            ps.setInt(6, obj_dto.getProdTipo());
            ps.setString(7, obj_dto.getProdEnlace());
            ps.setInt(8, obj_dto.getProdUsuario());
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
    public boolean mtdRemover(ProductoDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "DELETE FROM tblproductos "
                // * Buscamos el producto del usuario respectivo
                + "WHERE prodUsuario = ? AND prodID = ? ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getProdUsuario());
            ps.setInt(2, obj_dto.getProdID());
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
    public boolean mtdActualizar(ProductoDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "UPDATE tblproductos SET prodTitulo = ?, prodDescripcion = ?, prodCategoria = ?, "
                + "prodPrecio = ?, prodStock = ?, prodTipo = ?, prodEnlace = ? "
                // * Buscamos el producto del usuario respectivo
                + "WHERE prodUsuario = ? AND prodID = ? ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setString(1, obj_dto.getProdTitulo());
            ps.setString(2, obj_dto.getProdDescripcion());
            ps.setString(3, obj_dto.getProdCategoria());
            ps.setDouble(4, obj_dto.getProdPrecio());
            ps.setInt(5, obj_dto.getProdStock());
            ps.setInt(6, obj_dto.getProdTipo());
            ps.setString(7, obj_dto.getProdEnlace());
            ps.setInt(8, obj_dto.getProdUsuario());
            ps.setInt(9, obj_dto.getProdID());
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
    public ProductoDto mtdConsultar(ProductoDto obj_dto) {
        // * Funciona perfectamente
        
        ProductoDto prod = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM tblproductos "
                // * Buscamos el producto del usuario respectivo
                + "WHERE prodUsuario = ? AND prodID = ? ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getProdUsuario());
            ps.setInt(2, obj_dto.getProdID());
            ResultSet rs = ps.executeQuery();
            
            if( rs.next() ){
                prod = new ProductoDto();
                prod.setProdID( rs.getInt("prodID") );
                prod.setProdTitulo(rs.getString("prodTitulo") );
                prod.setProdDescripcion(rs.getString("prodDescripcion") );
                prod.setProdCategoria(rs.getString("prodCategoria") );
                prod.setProdPrecio( rs.getDouble("prodPrecio") );
                prod.setProdTipo(rs.getInt("prodTipo") );
                prod.setProdEnlace(rs.getString("prodEnlace") );
                prod.setProdUsuario(rs.getInt("prodUsuario") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return prod;
    }

    @Override
    public List<ProductoDto> mtdListar() {
        // * Funciona perfectamente
        
        List<ProductoDto> productos = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM tblproductos;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ResultSet rs = ps.executeQuery();
            
            productos = new ArrayList<>();
            while( rs.next() ){
                ProductoDto prod = new ProductoDto();
                
                prod.setProdID( rs.getInt("prodID") );
                prod.setProdTitulo(rs.getString("prodTitulo") );
                prod.setProdDescripcion(rs.getString("prodDescripcion") );
                prod.setProdCategoria(rs.getString("prodCategoria") );
                prod.setProdPrecio( rs.getDouble("prodPrecio") );
                prod.setProdTipo(rs.getInt("prodTipo") );
                prod.setProdEnlace(rs.getString("prodEnlace") );
                prod.setProdUsuario(rs.getInt("prodUsuario") );
                
                productos.add(prod);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return productos;
    }
    
    @Override
    public List<ProductoDto> mtdListar(ProductoDto dto) {
        // * Funciona perfectamente
        
        List<ProductoDto> productos = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM tblproductos "
                + "WHERE prodUsuario = ?  ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, dto.getProdUsuario());
            ResultSet rs = ps.executeQuery();
            
            productos = new ArrayList<>();
            while( rs.next() ){
                ProductoDto prod = new ProductoDto();
                
                prod.setProdID( rs.getInt("prodID") );
                prod.setProdTitulo(rs.getString("prodTitulo") );
                prod.setProdDescripcion(rs.getString("prodDescripcion") );
                prod.setProdCategoria(rs.getString("prodCategoria") );
                prod.setProdPrecio( rs.getDouble("prodPrecio") );
                prod.setProdTipo(rs.getInt("prodTipo") );
                prod.setProdEnlace(rs.getString("prodEnlace") );
                prod.setProdUsuario(rs.getInt("prodUsuario") );
                
                productos.add(prod);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return productos;
    }

    @Override
    public List<ProductoDto> mtdListar(int inicio, int fin) {
        // * Funciona perfectamente
        
        List<ProductoDto> productos = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM tblproductos LIMIT ? OFFSET ?;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, inicio);
            ps.setInt(2, fin);
            ResultSet rs = ps.executeQuery();
            
            productos = new ArrayList<>();
            while( rs.next() ){
                ProductoDto prod = new ProductoDto();
                
                prod.setProdID( rs.getInt("prodID") );
                prod.setProdTitulo(rs.getString("prodTitulo") );
                prod.setProdDescripcion(rs.getString("prodDescripcion") );
                prod.setProdCategoria(rs.getString("prodCategoria") );
                prod.setProdPrecio( rs.getDouble("prodPrecio") );
                prod.setProdTipo(rs.getInt("prodTipo") );
                prod.setProdEnlace(rs.getString("prodEnlace") );
                prod.setProdUsuario(rs.getInt("prodUsuario") );
                
                productos.add(prod);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return productos;
    }
    
    @Override
    public List<ProductoDto> mtdListar(ProductoDto obj_dto, int inicio, int fin) {
        // * Funciona perfectamente
        
        List<ProductoDto> productos = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM tblproductos "
                + "WHERE prodUsuario = ?  "
                + "LIMIT ? OFFSET ? ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getProdUsuario());
            ps.setInt(2, inicio);
            ps.setInt(3, fin);
            ResultSet rs = ps.executeQuery();
            
            productos = new ArrayList<>();
            while( rs.next() ){
                ProductoDto prod = new ProductoDto();
                
                prod.setProdID( rs.getInt("prodID") );
                prod.setProdTitulo(rs.getString("prodTitulo") );
                prod.setProdDescripcion(rs.getString("prodDescripcion") );
                prod.setProdCategoria(rs.getString("prodCategoria") );
                prod.setProdPrecio( rs.getDouble("prodPrecio") );
                prod.setProdTipo(rs.getInt("prodTipo") );
                prod.setProdEnlace(rs.getString("prodEnlace") );
                prod.setProdUsuario(rs.getInt("prodUsuario") );
                
                productos.add(prod);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return productos;
    }

    @Override
    public long mtdRowCount() {
        // * Funciona perfectamente
        
        long filas = 0;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM tblproductos ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ResultSet rs = ps.executeQuery();
            rs.next();
            filas = rs.getInt(1);
            
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return filas;
    }

    @Override
    public long mtdRowCount(ProductoDto obj_dto) {
        // * Funciona perfectamente
        
        long filas = 0;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM tblproductos "
                + "WHERE prodUsuario = ? ;";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getProdUsuario());
            ResultSet rs = ps.executeQuery();
            rs.next();
            filas = rs.getInt(1);
            
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return filas;
    }
    
    @Override
    public long mtdRowCount(int estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean mtdComprobar(ProductoDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean mtdEliminar(ProductoDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
