package index;

import controlador.CtrlHiloConexion;
import controlador.CtrlInicio;
import hilos.HiloConexion;
import hilos.HiloPrincipal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JOptionPane;
import modelo.dao.EjecucionDao;
import modelo.dao.PreferenciaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ConexionDto;
import modelo.dto.EjecucionDto;
import modelo.dto.PreferenciaDto;
import modelo.dto.UsuarioDto;
import src.Info;
import src.Recursos;
import src.idiomas.Idiomas;
import vista.ventanas.VentanaHome;
import vista.ventanas.VentanaInicio;

public class Veontec {
    
    public static VentanaHome ventanaHome;
    public static Properties idioma = new Idiomas("en");
    public static long ctrlID;
    public static String IdiomaDefinido;
    public static UsuarioDao usuarioDao;
    public static UsuarioDto usuarioDto;
    
    public void mtdTagInit() {
        
        Veontec.mtdVerificarArranque();
        
        Recursos.dataConexion().exists();
        
        // * Establecer conexion..
        //ConexionDto conec = new ConexionDto("3306", "sql3.freesqldatabase.com", "sql3432572", "sql3432572", "R9p2mht4YB");
        ConexionDto conec = new ConexionDto("3306", "localhost", "sql3432572", "root", "");
        CtrlHiloConexion.ctrlDatos = conec;
        
        if(!CtrlHiloConexion.mtdEstablecer()){
            JOptionPane.showMessageDialog(null, "No hay conexion al servidor.");
            System.exit(0);
        }
        
        mtdCargarPreferencias();
        HiloConexion hc = new HiloConexion();
        HiloPrincipal hp = new HiloPrincipal();
        //HiloSplash hs = new HiloSplash();
        hc.setDaemon(true);
        hp.setDaemon(true);
        //hs.setDaemon(true);
        
        VentanaInicio ni = new VentanaInicio();
        CtrlInicio ctrl = new CtrlInicio(ni, ni.pnRegistrarme, ni.pnLoggin); 
        ctrl.mtdInit();
        
        // * Ejecutar hilos
        //hs.start();
        hc.start();
        hp.start();
        
    }
    
    public static void mtdVerificarArranque(){
        if( Recursos.dataRun().exists() ){
            
            // Si tiene un PID en ejecucion o ctrlID mayor a 3
            if( mtdVerificarPID() ){
                //System.out.println("Tienes un ctrlID no validos o un PID en ejecucion");
                System.exit(0);
            }
            
            // Si tiene un ctrlID no valido
            if( mtdObtenerID() < 0 ){
                Recursos.dataRun().delete();
                //System.out.println("Tienes un ctrlID no validos");
                //System.exit(0);
                
            }
            
            // Si tiene un PID no en ejecucion
            if( mtdVerificarPID() == false ){
                Recursos.dataRun().delete();
                //System.out.println("Tienes un PID no valido");
                //System.exit(0);
            }
            
            // Si no se puede eliminar el archivo .run
            if( Recursos.dataRun().exists() && !Recursos.dataRun().delete() ){
                //System.out.println("El archivo .run no se puede eliminar, alguien esta usandolo.");
                System.exit(0);
            }
            
        }
            
        // ***** FASE 1  | Verificar ID
        //System.out.println("***** FASE 1 | Verificar ID");
        System.out.println(Info.NombreSoftware + " running.");
        Veontec.ctrlID = Recursos.PID * 3 + 7;

        // * Guardar datos de inicialización del programa
        EjecucionDao archivoRun = new EjecucionDao();
        EjecucionDto dto = new EjecucionDto();
        dto.setId(Veontec.ctrlID);
        dto.setPid(Recursos.PID);
        dto.setEstado(1);
        dto.setTiempo_inicial(System.nanoTime());
        dto.setTiempo_ejecucion(System.nanoTime());
        archivoRun.mtdRegistrarDatos(dto);
            
        
    }
    
    private void mtdCargarPreferencias(){
        PreferenciaDao dao = new PreferenciaDao();
        PreferenciaDto dto;
        
        if( dao.obtener_datos() == null ){ 
            dto = new PreferenciaDto();
            Recursos.dataPreferencias().delete();
        } else{
            dto = dao.obtener_datos();
        }
            
            if( dto.getIdioma().trim().equals("Español") || dto.getIdioma().trim().equals("Spanish") ){
                idioma = new Idiomas("es");
                Veontec.IdiomaDefinido = "ESP";
            }else{
                idioma = new Idiomas("en");
                Veontec.IdiomaDefinido = "ENG";
            }
            Recursos.mtdCambiarFuente( dto.getFuente() );
            Recursos.styleButtonDefault = dto.getEstilo();
            
    }
    
    public static void mtdVerificarID(){
        EjecucionDao archivoRun = new EjecucionDao();
        long estadoA = Veontec.ctrlID;
        long estadoC = estadoA * 3 + 7;
        
        if( Recursos.dataRun().getAbsoluteFile().exists() ){
            try {
                
                if( mtdVerificarPID() || mtdObtenerID() > 0 ){
                    EjecucionDto xml = archivoRun.mtdObetenerDatos();
                    String estado = "" + xml.getId();

                    if( Long.parseLong(estado) == estadoA ){
                        xml.setId(estadoC);
                        xml.setPid(Recursos.PID);
                        xml.setEstado(2);
                        xml.setTiempo_ejecucion(System.nanoTime());
                        archivoRun.mtdRegistrarDatos(xml);
                        
                    }else{
                        System.exit(0);
                    
                    }
                    
                }else{
                    System.exit(0);
                }
                
            } catch (Exception e) {
                System.exit(0);
            }
            
        }else{
            System.exit(0);
        }
        
        Veontec.ctrlID = estadoC;
    }
    
    public static boolean mtdVerificarPID(){
         EjecucionDao archivoRun = new EjecucionDao();
         boolean runPID = false;
        
        if( Recursos.dataRun().getAbsoluteFile().exists() ){
            try {
                String pid = "" + archivoRun.mtdObetenerDatos().getPid();

                // * Obtener todos los procesos PID de java
                String result = null;
                String cmd = null;
            
                if( Recursos.OsWin )
                    cmd = "tasklist /fi \"imagename eq java*\" ";
                else
                    cmd = "ps -C java -o pid=";
                
                try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                        Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                    result = s.hasNext() ? s.next() : null;
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                
                if( result.contains(pid) ){
                    return true;
                }
                
            } catch (Exception e) {
                System.exit(0);
            }

        }else{
            System.exit(0);
        }
        
        return runPID;
    }
    
    public static long mtdObtenerID(){
        EjecucionDao archivoRun = new EjecucionDao();
        long runID = -1000;
        
        try {
            if( Recursos.dataRun().getAbsoluteFile().exists() ){
                //System.out.println("XX " + Recursos.dataRun.getAbsolutePath());
                String estado = "" + archivoRun.mtdObetenerDatos().getId();
                runID = Long.parseLong(estado);

                return runID;
            }else {
                System.exit(0);
            }
            
        } catch (Exception e) {
            System.exit(0);
        }
        
        return runID;
    }
    
    
    
    // * Inicializar el programa de pruebas
    public void mtdTagTest(){
        mtdVerInformacionDelSoftware();
        
        // * Establecer conexion..
        ConexionDto conec = new ConexionDto("3306", "sql3.freesqldatabase.com", "sql3432572", "sql3432572", "R9p2mht4YB");
        CtrlHiloConexion.ctrlDatos = conec;
        CtrlHiloConexion.mtdEstablecer();
        
        if( CtrlHiloConexion.checkConexion() ){
          
        }else{
            JOptionPane.showMessageDialog(null, "No existe conexion.");
        }
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
    
    // * Mostrar mensaje de ayuda en la terminal
    public void mtdTagHelp(){
        mtdCargarPreferencias();
        System.out.println(Info.NombreSoftware);
        System.out.println("");
        System.out.println(Veontec.idioma.get("MyFreeLab.mtdTagHelp.msg1"));
        
        System.out.print("  --init              ");
        System.out.println(Veontec.idioma.get("MyFreeLab.mtdTagHelp.msg2") +" "+Info.NombreSoftware);
        
        System.out.print("  --mkconn, -mc       ");
        System.out.println(Veontec.idioma.get("MyFreeLab.mtdTagHelp.msg3") + " conn");
        
        System.out.print("  --mkpref, -mp       ");
        System.out.println(Veontec.idioma.get("MyFreeLab.mtdTagHelp.msg3") + " .pconfig");
        
        System.out.print("  --pid               ");
        System.out.println(Veontec.idioma.get("MyFreeLab.mtdTagHelp.msg4"));
        
        System.out.print("  --help, -h          ");
        System.out.println(Veontec.idioma.get("MyFreeLab.mtdTagHelp.msg5"));
   
    }

    private void mtdVerInformacionDelSoftware() {
        System.out.println("#DirHome : " + Recursos.dirHome );
        System.out.println("#Path Actual : " + new File(".").getAbsolutePath());
        System.out.println("#PID : " + mtdObtenerPID() );
        System.out.println("#SO : " + Recursos.SistemaOs);
        System.out.println("#TimeTmp : " + Recursos.timeTmp);
        System.out.println("#bkgAside : " + Recursos.bkgAside);
        System.out.println("#bkgLogo : " + Recursos.bkgLogo);
        System.out.println("#dataPreferencias : " + Recursos.dataPreferencias().getAbsolutePath());
        System.out.println("#dataConexion : " + Recursos.dataConexion().getAbsolutePath());
        System.out.println("#dataRun : " + Recursos.dataRun().getAbsolutePath());
        System.out.println("#docVersionesXml : " + Recursos.docVersionesXml);
        System.out.println("#docReporte : " + Recursos.docCotizacionJasper());
        System.out.println("#\n");
    }
    
    private int mtdObtenerPID(){
        EjecucionDao archivoRun = new EjecucionDao();

        if( Recursos.dataRun().getAbsoluteFile().exists() ){
                String pid = "" + archivoRun.mtdObetenerDatos().getPid();

                // * Obtener todos los procesos PID de java
                String result = null;
                String cmd = "";

                // * Verificar el sistema operativo
                if( Recursos.OsLinuxDeb ){
                    cmd = "ps -C java -o pid=";
                }if ( Recursos.OsWin ){
                    cmd = "tasklist /fi \"imagename eq java*\" ";
                }

                // * Obtener todo los procesos PID de java
                try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                        Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                    result = s.hasNext() ? s.next() : null;
                    //System.out.println( result );
                } catch (IOException e) {
                    // e.printStackTrace();
                }

                // * Si el pid almacenado en el archivo .run
                // esta en ejecución devuelve el pid 
                if( result.contains(pid) )
                    return Integer.parseInt(pid);        
                
            }
        
        // * Si el pid almacenado en el archivo .run
        // no está en ejecución devuelve una nueva PID 
        return Recursos.PID;
    }
    
}
