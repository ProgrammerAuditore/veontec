package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.PreguntaDto;
import modelo.interfaces.keyword_extra;
import modelo.interfaces.keyword_query;

public class PreguntaDao implements keyword_query<PreguntaDto> , keyword_extra<PreguntaDto>{

    private final String nombreTabla = "tblpreguntas";
    
    @Override
    public boolean mtdInsetar(PreguntaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "INSERT INTO " + nombreTabla + " "
                + "( pregProducto, pregVendedor, pregComprador, pregPregunta, pregFecha, pregEstado )"
                + "VALUES "
                + "( ?, ?, ?, ?, ?, ? ) ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getPregProducto());
            ps.setInt(2, obj_dto.getPregVendedor());
            ps.setInt(3, obj_dto.getPregComprador());
            ps.setString(4, obj_dto.getPregPregunta());
            ps.setString(5, obj_dto.getPregFecha());
            ps.setInt(6, obj_dto.getPregEstado());
            
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
    public boolean mtdRemover(PreguntaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "DELETE FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE pregID = ?; "; // AND pregComprador = ? AND pregVendedor = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getPregID());
            //ps.setInt(2, obj_dto.getPregComprador());
            //ps.setInt(3, obj_dto.getPregVendedor());
            
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
    public PreguntaDto mtdConsultar(PreguntaDto obj_dto) {
        // Funciona correctamente
        
        PreguntaDto pregunta = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE pregID = ? ;"; // AND pregComprador = ? AND pregVendedor = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getPregID());
            //ps.setInt(2, obj_dto.getPregComprador());
            //ps.setInt(3, obj_dto.getPregVendedor());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( rs.next() ){
                pregunta = new PreguntaDto();
                pregunta.setPregID( rs.getInt("pregID") );
                pregunta.setPregProducto( rs.getInt("pregProducto") );
                pregunta.setPregComprador( rs.getInt("pregComprador") );
                pregunta.setPregVendedor( rs.getInt("pregVendedor") );
                pregunta.setPregPregunta( rs.getString("pregPregunta") );
                pregunta.setPregFecha( rs.getString("pregFecha") );
                pregunta.setPregEstado( rs.getInt("pregEstado") );
            }

            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return pregunta;
    }

    @Override
    public List<PreguntaDto> mtdListar(PreguntaDto obj_dto) {
        // Funciona correctamente
        
        List<PreguntaDto> preguntas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE pregVendedor = ? OR pregComprador = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getPregVendedor());
            ps.setInt(2, obj_dto.getPregComprador());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            preguntas = new ArrayList<>();
            while( rs.next() ){
                PreguntaDto pregunta = new PreguntaDto();
                pregunta.setPregID( rs.getInt("pregID") );
                pregunta.setPregComprador( rs.getInt("pregComprador") );
                pregunta.setPregVendedor( rs.getInt("pregVendedor") );
                pregunta.setPregPregunta( rs.getString("pregPregunta") );
                pregunta.setPregFecha( rs.getString("pregFecha") );
                pregunta.setPregEstado( rs.getInt("pregEstado") );
                preguntas.add(pregunta);
            }

            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return preguntas;
    }

    @Override
    public List<PreguntaDto> mtdListar(PreguntaDto obj_dto, int cantidad, int inicio) {
        // Funciona correctamente
        
        List<PreguntaDto> preguntas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE pregVendedor = ? OR pregComprador = ? "
                + "LIMIT ? OFFSET ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getPregVendedor());
            ps.setInt(2, obj_dto.getPregComprador());
            ps.setInt(3, cantidad);
            ps.setInt(4, inicio);
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            preguntas = new ArrayList<>();
            while( rs.next() ){
                PreguntaDto pregunta = new PreguntaDto();
                pregunta.setPregID( rs.getInt("pregID") );
                pregunta.setPregComprador( rs.getInt("pregComprador") );
                pregunta.setPregVendedor( rs.getInt("pregVendedor") );
                pregunta.setPregPregunta( rs.getString("pregPregunta") );
                pregunta.setPregFecha( rs.getString("pregFecha") );
                pregunta.setPregEstado( rs.getInt("pregEstado") );
                preguntas.add(pregunta);
            }

            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return preguntas;
    }
    
    @Override
    public boolean mtdActualizar(PreguntaDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<PreguntaDto> mtdListar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<PreguntaDto> mtdListar(int inicio, int fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount(PreguntaDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long mtdRowCount(int estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdComprobar(PreguntaDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdEliminar(PreguntaDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}