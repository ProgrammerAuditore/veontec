package modelo.dao;

import java.sql.Connection;
import controlador.CtrlHiloConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.CategoriaDto;
import modelo.interfaces.keyword_extra;
import modelo.interfaces.keyword_query;

public class CategoriaDao implements keyword_query<CategoriaDto>, keyword_extra<CategoriaDto>{

    @Override
    public boolean mtdInsetar(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " INSERT INTO tblcategorias (cateUsuario, cateNombre, cateTotalProductos ) "
                + " VALUES (?, ?, ? ); ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query);
            ps.setInt(1, obj_dto.getCateUsuario());
            ps.setString(2, obj_dto.getCateNombre());
            ps.setInt(3, obj_dto.getCateTotalProductos());
            
            // * Ejecutar la consulta
            int respuesta =  ps.executeUpdate();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( respuesta > 0 )
                return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdRemover(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " DELETE FROM tblcategorias "
                + " WHERE cateID = ?; ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query);
            ps.setInt(1, obj_dto.getCateID());
            
            // * Ejecutar la consulta
            int respuesta =  ps.executeUpdate();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( respuesta > 0 )
                return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mtdActualizar(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " UPDATE tblcategorias SET cateUsuario = ?, cateNombre = ?, cateTotalProductos = ?"
                + " WHERE cateID = ?; ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query);
            ps.setInt(1, obj_dto.getCateUsuario());
            ps.setString(2, obj_dto.getCateNombre());
            ps.setInt(3, obj_dto.getCateTotalProductos());
            ps.setInt(4, obj_dto.getCateID());
            
            // * Ejecutar la consulta
            int respuesta =  ps.executeUpdate();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( respuesta > 0 )
                return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return false;
    }

    @Override
    public CategoriaDto mtdConsultar(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        CategoriaDto categoria = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " SELECT * FROM tblcategorias "
                + " WHERE cateID = ?; ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query);
            ps.setInt(1, obj_dto.getCateID());
            
            // * Ejecutar la consulta
            ResultSet resultado = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            while (resultado.next()) {
                categoria = new CategoriaDto();
                categoria.setCateID( resultado.getInt("cateID") );
                categoria.setCateUsuario(resultado.getInt("cateUsuario") );
                categoria.setCateNombre(resultado.getString("cateNombre") );
                categoria.setCateTotalProductos(resultado.getInt("cateTotalProductos") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return categoria;
    }
    
    public CategoriaDto mtdConsultarNombreUsuario(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        CategoriaDto categoria = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " SELECT * FROM tblcategorias "
                + " WHERE cateUsuario = ? AND cateNombre = ?; ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query);
            ps.setInt(1, obj_dto.getCateUsuario());
            ps.setString(2, obj_dto.getCateNombre());
            
            // * Ejecutar la consulta
            ResultSet resultado = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            while (resultado.next()) {
                categoria = new CategoriaDto();
                categoria.setCateID( resultado.getInt("cateID") );
                categoria.setCateUsuario(resultado.getInt("cateUsuario") );
                categoria.setCateNombre(resultado.getString("cateNombre") );
                categoria.setCateTotalProductos(resultado.getInt("cateTotalProductos") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        } 
        
        return categoria;
    }

    @Override
    public List<CategoriaDto> mtdListar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategoriaDto> mtdListar(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        List<CategoriaDto> categorias = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " SELECT * FROM tblcategorias "
                + " WHERE cateUsuario = ?; ";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query);
            ps.setInt(1, obj_dto.getCateUsuario());
            
            // * Ejecutar la consulta
            ResultSet resultado = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            categorias = new ArrayList<>();
            while (resultado.next()) {
                CategoriaDto categoria = new CategoriaDto();
                categoria.setCateID( resultado.getInt("cateID") );
                categoria.setCateUsuario(resultado.getInt("cateUsuario") );
                categoria.setCateNombre(resultado.getString("cateNombre") );
                categoria.setCateTotalProductos(resultado.getInt("cateTotalProductos") );
                categorias.add(categoria);
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return categorias;
    }

    @Override
    public List<CategoriaDto> mtdListar(int inicio, int fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategoriaDto> mtdListar(CategoriaDto obj_dto, int inicio, int fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM tblcategorias "
                // * Buscamos el producto del usuario respectivo
                + "WHERE cateUsuario = ? ;";
        long registros = 0;
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCateUsuario());
            
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
    public long mtdRowCount(int estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdComprobar(CategoriaDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT COUNT(*) FROM tblcategorias "
                // * Buscamos el producto del usuario respectivo
                + "WHERE cateUsuario = ? AND cateNombre = ? ;";
        long registros = 0;
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getCateUsuario());
            ps.setString(2, obj_dto.getCateNombre());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( rs.next() )
                registros = rs.getInt(1);
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return (registros == 0);
    }

    @Override
    public boolean mtdEliminar(CategoriaDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
