package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import modelo.dto.UsuarioDto;
import modelo.interfaces.keyword_extra;
import modelo.interfaces.keyword_query;
import modelo.interfaces.keyword_proyectos;

public class UsuarioDao implements keyword_query<UsuarioDto>, keyword_proyectos<UsuarioDto>, keyword_extra<UsuarioDto>{

    @Override
    public boolean mtdInsetar(UsuarioDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO tblusuarios (usuaNombre, usuaCorreo, usuaPassword) "
                + "VALUES (?, ?, ?); ";        
        try {
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setString(1, obj_dto.getCmpNombreCompleto());
            ps.setString(2, obj_dto.getCmpCorreo());
            ps.setString(3, obj_dto.getCmpPassword());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean mtdEliminar(UsuarioDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdRemover(UsuarioDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdActualizar(UsuarioDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioDto mtdConsultar(UsuarioDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM tblusuarios WHERE usuaCorreo = ?; ";        
        UsuarioDto usuario = new UsuarioDto();
        
        try {
            // * Preparar la consulta
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setString(1, obj_dto.getCmpCorreo());
            
            // * Obtener registros
            ResultSet rs = ps.executeQuery();
            
            while ( rs.next() ) {
                usuario.setCmpID( rs.getInt("usuaID") );
                usuario.setCmpNombreCompleto( rs.getString("usuaNombre") );
                usuario.setCmpCorreo( rs.getString("usuaCorreo") );
                usuario.setCmpPassword( rs.getString("usuaPassword") );
                //System.out.println("mtdConsultar \n" + usuario.toString());
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return usuario;
    }

    @Override
    public List<UsuarioDto> mtdListar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioDto> mtdListar(UsuarioDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
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

    @Override
    public List<UsuarioDto> mtdListarProyectoEnProceso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioDto> mtdListarProyectoEliminados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioDto> mtdListarProyectoRealizados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioDto> mtdListar(int inicio, int fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount(int estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount(UsuarioDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioDto> mtdListar(UsuarioDto obj_dto, int inicio, int fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
