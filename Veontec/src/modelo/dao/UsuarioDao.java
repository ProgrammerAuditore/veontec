package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.dto.UsuarioDto;
import modelo.interfaces.keyword_query;

public class UsuarioDao implements keyword_query<UsuarioDto>{

    @Override
    public boolean mtdInsetar(UsuarioDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = " INSERT INTO tblusuarios "
                + " (usuaNombre, usuaCorreo, usuaPassword, usuaKey , usuaEstado, usuaCreadoEn, usuaActualizadoEn) "
                + " VALUES ( ?, ?, ?, ?, ?, ?, ? ); ";        
        try {
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setString(1, obj_dto.getCmpNombreCompleto());
            ps.setString(2, obj_dto.getCmpCorreo());
            ps.setString(3, obj_dto.getCmpPassword());
            ps.setString(4, obj_dto.getCmpKey());
            ps.setInt(5, obj_dto.getCmpEstado());
            ps.setString(6, obj_dto.getCmpCreadoEn());
            ps.setString(7, obj_dto.getCmpActualizadoEn());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean mtdRemover(UsuarioDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "DELETE FROM tblusuarios "
                + "WHERE usuaID = ? AND usuaCorreo = ? ; ";        
        try {
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setInt(1, obj_dto.getCmpID());
            ps.setString(2, obj_dto.getCmpCorreo());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean mtdActualizar(UsuarioDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = " UPDATE tblusuarios SET "
                + " usuaNombre = ?, usuaCorreo = ?, usuaPassword = ?, usuaDireccion = ?, usuaTelefono = ? , "
                + " usuaKey = ? , usuaEstado = ?, usuaCreadoEn = ?, usuaActualizadoEn = ? "
                // * Buscamos el producto del usuario respectivo
                + " WHERE usuaID = ? ; ";
        
        try {
            
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setString(1, obj_dto.getCmpNombreCompleto());
            ps.setString(2, obj_dto.getCmpCorreo());
            ps.setString(3, obj_dto.getCmpPassword());
            ps.setString(4, obj_dto.getCmpDireccion());
            ps.setString(5, obj_dto.getCmpTelefono());
            ps.setString(6, obj_dto.getCmpKey());
            ps.setInt(7, obj_dto.getCmpEstado());
            ps.setString(8, obj_dto.getCmpCreadoEn());
            ps.setString(9, obj_dto.getCmpActualizadoEn());
            ps.setInt(10, obj_dto.getCmpID());
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
    public UsuarioDto mtdConsultar(UsuarioDto obj_dto) {
        UsuarioDto usuario = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblusuarios WHERE usuaCorreo = ?; ";        
        
        try {
            // * Preparar la consulta
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setString(1, obj_dto.getCmpCorreo());
            
            // * Obtener registros
            ResultSet rs = ps.executeQuery();
            
            usuario = new UsuarioDto();
            while ( rs.next() ) {
                usuario.setCmpID( rs.getInt("usuaID") );
                usuario.setCmpNombreCompleto( rs.getString("usuaNombre") );
                usuario.setCmpCorreo( rs.getString("usuaCorreo") );
                usuario.setCmpPassword( rs.getString("usuaPassword") );
                usuario.setCmpDireccion( rs.getString("usuaDireccion") );
                usuario.setCmpTelefono( rs.getString("usuaTelefono") );
                usuario.setCmpKey( rs.getString("usuaKey") );
                usuario.setCmpEstado( rs.getInt("usuaEstado") );
                usuario.setCmpCreadoEn( rs.getString("usuaCreadoEn") );
                usuario.setCmpActualizadoEn( rs.getString("usuaActualizadoEn") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return usuario;
    }
    
    public UsuarioDto mtdConsultar(Integer usuario_id) {
        UsuarioDto usuario = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblusuarios WHERE usuaID = ?; ";        
        
        try {
            // * Preparar la consulta
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setInt(1, usuario_id);
            
            // * Obtener registros
            ResultSet rs = ps.executeQuery();
            
            usuario = new UsuarioDto();
            while ( rs.next() ) {
                usuario.setCmpID( rs.getInt("usuaID") );
                usuario.setCmpNombreCompleto( rs.getString("usuaNombre") );
                usuario.setCmpCorreo( rs.getString("usuaCorreo") );
                usuario.setCmpPassword( rs.getString("usuaPassword") );
                usuario.setCmpDireccion( rs.getString("usuaDireccion") );
                usuario.setCmpTelefono( rs.getString("usuaTelefono") );
                usuario.setCmpKey( rs.getString("usuaKey") );
                usuario.setCmpEstado( rs.getInt("usuaEstado") );
                usuario.setCmpCreadoEn( rs.getString("usuaCreadoEn") );
                usuario.setCmpActualizadoEn( rs.getString("usuaActualizadoEn") );
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return usuario;
    }

    public boolean mtdComprobar(UsuarioDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT COUNT(*) FROM tblusuarios WHERE usuaCorreo = ?; ";        
        long registros = 0;
        
        try {
            // * Preparar la consulta
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setString(1, obj_dto.getCmpCorreo());
            
            // * Contar los registros
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            registros = rs.getInt(1);
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return ( registros == 0 );
    }
    
    public boolean mtdComprobar(Integer usuario_id) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT COUNT(*) FROM tblusuarios WHERE usuaID = ?; ";        
        long registros = 0;
        
        try {
            // * Preparar la consulta
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setInt(1, usuario_id);
            
            // * Contar los registros
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            registros = rs.getInt(1);
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return ( registros == 0 );
    }

   
}
