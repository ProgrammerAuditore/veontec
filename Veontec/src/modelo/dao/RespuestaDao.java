package modelo.dao;

import controlador.CtrlHiloConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.RespuestaDto;
import modelo.interfaces.keyword_query;

public class RespuestaDao implements keyword_query<RespuestaDto>{

    private final String nombreTabla = "tblrespuestas";
    
    @Override
    public boolean mtdInsetar(RespuestaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "INSERT INTO " + nombreTabla + " "
                + "( respPregunta, respProducto, respVendedor, respComprador, respRespuesta, respFecha, respEstado )"
                + "VALUES "
                + "( ?, ?, ?, ?, ?, ?, ? ) ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getRespPregunta());
            ps.setInt(2, obj_dto.getRespProducto());
            ps.setInt(3, obj_dto.getRespVendedor());
            ps.setInt(4, obj_dto.getRespComprador());
            ps.setString(5, obj_dto.getRespRespuesta());
            ps.setString(6, obj_dto.getRespFecha());
            ps.setInt(7, obj_dto.getRespEstado());
            
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
    public boolean mtdRemover(RespuestaDto obj_dto) {
        // * Funciona perfectamente
        
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "DELETE FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE respID = ? "; // AND respComprador = ? AND respVendedor = ? AND respPregunta = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getRespID());
            //ps.setInt(2, obj_dto.getRespComprador());
            //ps.setInt(3, obj_dto.getRespVendedor());
            //ps.setInt(4, obj_dto.getRespPregunta());
            
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
    public RespuestaDto mtdConsultar(RespuestaDto obj_dto) {
        // * Funciona perfectamente
        
        RespuestaDto respuesta = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE respID = ? "; // AND respComprador = ?  AND respVendedor = ? AND respPregunta = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getRespID());
            //ps.setInt(2, obj_dto.getRespComprador());
            //ps.setInt(3, obj_dto.getRespVendedor());
            //ps.setInt(4, obj_dto.getRespPregunta());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            if( rs.next() ){
                respuesta = new RespuestaDto();
                respuesta.setRespID( rs.getInt("respID") );
                respuesta.setRespPregunta( rs.getInt("respPregunta") );
                respuesta.setRespProducto( rs.getInt("respProducto") );
                respuesta.setRespComprador( rs.getInt("respComprador") );
                respuesta.setRespVendedor( rs.getInt("respVendedor") );
                respuesta.setRespRespuesta( rs.getString("respRespuesta") );
                respuesta.setRespFecha( rs.getString("respFecha") );
                respuesta.setRespEstado( rs.getInt("respEstado") );
            }

            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return respuesta;
    }

    public List<RespuestaDto> mtdListar(RespuestaDto obj_dto) {
        // * Funciona perfectamente
        
        List<RespuestaDto> preguntas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE respPregunta = ? AND respProducto = ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getRespPregunta());
            ps.setInt(2, obj_dto.getRespProducto());
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            preguntas = new ArrayList<>();
            while( rs.next() ){
                RespuestaDto respuesta = new RespuestaDto();
                respuesta.setRespID( rs.getInt("respID") );
                respuesta.setRespPregunta( rs.getInt("respPregunta") );
                respuesta.setRespProducto( rs.getInt("respProducto") );
                respuesta.setRespComprador( rs.getInt("respComprador") );
                respuesta.setRespVendedor( rs.getInt("respVendedor") );
                respuesta.setRespRespuesta( rs.getString("respRespuesta") );
                respuesta.setRespFecha( rs.getString("respFecha") );
                respuesta.setRespEstado( rs.getInt("respEstado") );
                preguntas.add(respuesta);
            }

            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return preguntas;
    }

    public List<RespuestaDto> mtdListar(RespuestaDto obj_dto, int cantidad, int inicio) {
        // * Funciona perfectamente
        
        List<RespuestaDto> respuestas = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String query = "SELECT * FROM " + nombreTabla + " "
                // * Buscamos el producto del usuario respectivo
                + "WHERE respVendedor = ? OR respComprador = ? "
                + "LIMIT ? OFFSET ? ;";
        
        try {
            
            // * Preparar la consulta
            ps = conn.prepareStatement(query.toLowerCase());
            ps.setInt(1, obj_dto.getRespVendedor());
            ps.setInt(2, obj_dto.getRespComprador());
            ps.setInt(3, cantidad);
            ps.setInt(4, inicio);
            
            // * Ejecutar la consulta
            ResultSet rs = ps.executeQuery();
            
            // * Si la respuesta es mayor a 0 significa que la consulta fue exitosa.
            respuestas = new ArrayList<>();
            while( rs.next() ){
                RespuestaDto respuesta = new RespuestaDto();
                respuesta.setRespID( rs.getInt("respID") );
                respuesta.setRespPregunta( rs.getInt("respPregunta") );
                respuesta.setRespProducto( rs.getInt("respProducto") );
                respuesta.setRespComprador( rs.getInt("respComprador") );
                respuesta.setRespVendedor( rs.getInt("respVendedor") );
                respuesta.setRespRespuesta( rs.getString("respRespuesta") );
                respuesta.setRespFecha( rs.getString("respFecha") );
                respuesta.setRespEstado( rs.getInt("respEstado") );
                respuestas.add(respuesta);
            }

            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return respuestas;
    }
    
    @Override
    public boolean mtdActualizar(RespuestaDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
