package src;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

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
    
}
