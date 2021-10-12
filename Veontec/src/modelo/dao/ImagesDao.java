package modelo.dao;

import controlador.CtrlHiloConexion;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.dto.ImagesDto;
import modelo.interfaces.keyword_query;

public class ImagesDao implements keyword_query<ImagesDto>{

    private final String tabla = "tblusuarios";
    
    @Override
    public boolean mtdInsetar(ImagesDto obj_dto) {
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "INSERT INTO "+tabla+" (imagUsuario, imagProducto, imagMedia) "
                + "VALUES (?, ?, ?); ";        
        try {
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setInt(1, obj_dto.getImagUsuario());
            ps.setInt(2, obj_dto.getImagProducto());
            ps.setBlob(3, obj_dto.getImagMediaBinary());
            int rs = ps.executeUpdate();
            
            if( rs > 0 )
            return true;
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean mtdRemover(ImagesDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mtdActualizar(ImagesDto obj_dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ImagesDto mtdConsultar(ImagesDto obj_dto) {
        ImagesDto img = null;
        PreparedStatement ps = null;
        Connection conn = CtrlHiloConexion.getConexion();
        String sql = "SELECT * FROM "+tabla+" WHERE imagUsuario = ? AND imagProducto = ?; ";        
        
        try {
            // * Preparar la consulta
            ps = conn.prepareStatement(sql.toLowerCase());
            ps.setInt(1, obj_dto.getImagUsuario());
            ps.setInt(1, obj_dto.getImagProducto());
            
            // * Obtener registros
            ResultSet rs = ps.executeQuery();
            
            img = new ImagesDto();
            while ( rs.next() ) {
                img.setImagID( rs.getInt("imagID") );
                img.setImagUsuario( rs.getInt("imagUsuario") );
                img.setImagProducto( rs.getInt("imagProducto") );
                img.setImagMediaBinary( (ObjectInputStream) rs.getBinaryStream("imagMedia") );
                System.out.println("mtdConsultar \n" + img.toString());
            }
            
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        
        return img;
    }
    
}
