package modelo.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.dto.CuentaDto;
import src.Recursos;
import modelo.interfaces.keyword_binario;

public class CuentaDao implements keyword_binario<CuentaDto>{

    @Override
    public CuentaDto obtener_datos() {
        CuentaDto db = null;
        
        try(FileInputStream fis = new FileInputStream( Recursos.dataCuenta() )){
            ObjectInputStream ois = null;
            
            while(fis.available() > 0){
                ois = new ObjectInputStream(fis);
                db = (CuentaDto) ois.readObject();
                
                //System.out.println("" + db);
            }
            
            ois.close();
            fis.close();
            
        } catch(Exception e){
            //System.out.println("Error");
            Recursos.dataCuenta().delete();
            //e.printStackTrace();
        }
        
        return db;
    }

    @Override
    public void actualizar_datos(CuentaDto c) {
        try (FileOutputStream fos = new FileOutputStream( Recursos.dataCuenta() )) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            //System.out.println("Successfully actualizar data to the file");
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void regitrar_datos(CuentaDto c) {
        try (FileOutputStream fos = new FileOutputStream( Recursos.dataCuenta() )) {
            
            ObjectOutputStream oss = new ObjectOutputStream(fos);
            oss.writeObject(c);
            oss.close();
            fos.close();
            
            //System.out.println("Successfully escribir data to the file");
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
}
