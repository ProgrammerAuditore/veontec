package src;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.dto.CompraDto;
import modelo.dto.VentaDto;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Funciones {
    
    public boolean mtdComprobarPassword(String passwdDB,  char[] passwordInput){
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = passwordInput;
        
        try {
            // Hash password
            //String hash = argon2.hash(10, 65536, 1, password);
            // // Estará almacenado en la base de datos
            String hash = passwdDB;
            
            // Verify password
            if (argon2.verify(hash, password)) {
                // Hash matches password
                //System.out.println("Hash matches password");
                return true;
            } else {
                // Hash doesn't match password
                //System.out.println("Hash doesn't match password");
            }
            
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        
        return false;
    }
    
    public String mtdObtenerPasswordEncriptado(char[] cmpPasswd){
        // Encriptar la contraseña
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = cmpPasswd;
         String hash = "";
         
        try {
            // Hash password
            hash = argon2.hash(10, 65536, 1, password);
            
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        
        //System.out.println("" + hash);
        return hash;
    }
    
    public String fncObtenerFechaYHoraActualNoSQL(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    
    public String fncObtenerFechaYHoraActualSQL(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    
    public String fncObtenerFechaActual(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    
    public int hashCodeCompraVenta(VentaDto ventaDto, CompraDto compDto) {
        return new HashCodeBuilder(17, 37).
        append(ventaDto.getVentComprador()).
        append(ventaDto.getVentVendedor()).
        append(ventaDto.getVentID()).
        append(compDto.getCompID()).
        append(new Funciones().fncObtenerFechaYHoraActualNoSQL()).
        toHashCode();
    }
    
}
