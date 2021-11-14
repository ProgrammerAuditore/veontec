package index;

import controlador.CtrlHiloConexion;
import controlador.ventanas.CtrlMain;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JOptionPane;
import modelo.ObjPID;
import modelo.dao.CuentaDao;
import modelo.dao.EjecucionDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ConexionDto;
import modelo.dto.CuentaDto;
import modelo.dto.UsuarioDto;
import src.Recursos;
import src.idiomas.Idiomas;
import vista.ventanas.VentanaHome;
import vista.ventanas.VentanaMain;

public class Veontec {
    
    public static VentanaHome ventanaHome;
    public static VentanaMain ventanaSession;
    public static Properties idioma = new Idiomas("es");
    public static long ctrlID;
    public static String IdiomaDefinido;
    public static UsuarioDao usuarioDao;
    public static UsuarioDto usuarioDto;
    public static CuentaDao cuentaDao;
    public static CuentaDto cuentaDto;
    
    public void mtdTagInit() {
        
        ObjPID.mtdVerificarArranque();
        
        Recursos.dataConexion().exists();
        
        // * Establecer conexion..
        ConexionDto conec = new ConexionDto("3306", "localhost", "veontecdb", "root", "");
        CtrlHiloConexion.ctrlDatos = conec;
        CtrlHiloConexion.mtdEstablecer();
        
        if(CtrlHiloConexion.ctrlEstado == false){
            JOptionPane.showMessageDialog(null, "No hay conexion al servidor.");
            System.exit(0);
        }
        
        HiloConexion hc = new HiloConexion();
        HiloPrincipal hp = new HiloPrincipal();
        //HiloSplash hs = new HiloSplash();
        hc.setDaemon(true);
        hp.setDaemon(true);
        //hs.setDaemon(true);
        
        Veontec.ventanaSession = new VentanaMain();
        CtrlMain main = new CtrlMain(Veontec.ventanaSession);
        main.mtdInitLoggin();
        
        // * Ejecutar hilos
        //hs.start();
        hc.start();
        hp.start();
        
    }
    
    // * Obtener el PID del programa
    public void mtdTagPID(){
        EjecucionDao archivoRun = new EjecucionDao();
        
        if( Recursos.dataRun().getAbsoluteFile().exists() ){
            String pid = "" + archivoRun.mtdObetenerDatos().getId();

            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "";
            
            // * Verificar el sistema operativo
            if( Recursos.OsLinuxDeb )
                cmd = "ps -C java -o pid=";
            if ( Recursos.OsWin )
                cmd = "tasklist /fi \"imagename eq java*\" ";
            
            try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                result = s.hasNext() ? s.next() : null;
            } catch (IOException e) {
                // e.printStackTrace();
            }
            
            if( result.contains(pid) ){
                // *WARNING* PID satisfactoria
                System.out.println("[¡] " + pid );
            }else{
                // *WARNING* PID propia
                System.out.println("[x] " + Recursos.PID);
            }
            
        } else{
            // *WARNING* PID propia
            System.out.println("[x] " + Recursos.PID);
        }
    }
        
}
