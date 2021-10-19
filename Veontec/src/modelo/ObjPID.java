package modelo;

import index.Veontec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import modelo.dao.EjecucionDao;
import modelo.dto.EjecucionDto;
import src.Recursos;

public class ObjPID {

    public static long mtdObtenerID() {
        EjecucionDao archivoRun = new EjecucionDao();
        long runID = -1000;
        try {
            if (Recursos.dataRun().getAbsoluteFile().exists()) {
                //System.out.println("XX " + Recursos.dataRun.getAbsolutePath());
                String estado = "" + archivoRun.mtdObetenerDatos().getId();
                runID = Long.parseLong(estado);
                return runID;
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            System.exit(0);
        }
        return runID;
    }

    public static boolean mtdVerificarPID() {
        EjecucionDao archivoRun = new EjecucionDao();
        boolean runPID = false;
        if (Recursos.dataRun().getAbsoluteFile().exists()) {
            try {
                String pid = "" + archivoRun.mtdObetenerDatos().getPid();
                // * Obtener todos los procesos PID de java
                String result = null;
                String cmd = null;
                if (Recursos.OsWin) {
                    cmd = "tasklist /fi \"imagename eq java*\" ";
                } else {
                    cmd = "ps -C java -o pid=";
                }
                try (final InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();final Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                    result = s.hasNext() ? s.next() : null;
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                if (result.contains(pid)) {
                    return true;
                }
            } catch (Exception e) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
        return runPID;
    }

    public static void mtdVerificarID() {
        EjecucionDao archivoRun = new EjecucionDao();
        long estadoA = Veontec.ctrlID;
        long estadoC = estadoA * 3 + 7;
        if (Recursos.dataRun().getAbsoluteFile().exists()) {
            try {
                if (ObjPID.mtdVerificarPID() || ObjPID.mtdObtenerID() > 0) {
                    EjecucionDto xml = archivoRun.mtdObetenerDatos();
                    String estado = "" + xml.getId();
                    if (Long.parseLong(estado) == estadoA) {
                        xml.setId(estadoC);
                        xml.setPid(Recursos.PID);
                        xml.setEstado(2);
                        xml.setTiempo_ejecucion(System.nanoTime());
                        archivoRun.mtdRegistrarDatos(xml);
                    } else {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            } catch (Exception e) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
        Veontec.ctrlID = estadoC;
    }

    public static void mtdVerificarArranque() {
        if (Recursos.dataRun().exists()) {
            // Si tiene un PID en ejecucion o ctrlID mayor a 3
            if (ObjPID.mtdVerificarPID()) {
                //System.out.println("Tienes un ctrlID no validos o un PID en ejecucion");
                System.exit(0);
            }
            // Si tiene un ctrlID no valido
            if (ObjPID.mtdObtenerID() < 0) {
                Recursos.dataRun().delete();
                //System.out.println("Tienes un ctrlID no validos");
                //System.exit(0);
            }
            // Si tiene un PID no en ejecucion
            if (ObjPID.mtdVerificarPID() == false) {
                Recursos.dataRun().delete();
                System.out.println("Tienes un PID no valido");
                //System.exit(0);
            }
            // Si no se puede eliminar el archivo .run
            if (Recursos.dataRun().exists() && !Recursos.dataRun().delete()) {
                System.out.println("El archivo .run no se puede eliminar, alguien esta usandolo.");
                System.exit(0);
            }
        }
        // ***** FASE 1  | Verificar ID
        //System.out.println("***** FASE 1 | Verificar ID");
        //System.out.println(Info.NombreSoftware + " running.");
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

    public static int mtdObtenerPID() {
        EjecucionDao archivoRun = new EjecucionDao();
        if (Recursos.dataRun().getAbsoluteFile().exists()) {
            String pid = "" + archivoRun.mtdObetenerDatos().getPid();
            // * Obtener todos los procesos PID de java
            String result = null;
            String cmd = "";
            // * Verificar el sistema operativo
            if (Recursos.OsLinuxDeb) {
                cmd = "ps -C java -o pid=";
            }
            if (Recursos.OsWin) {
                cmd = "tasklist /fi \"imagename eq java*\" ";
            }
            // * Obtener todo los procesos PID de java
            try (final InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();final Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
                result = s.hasNext() ? s.next() : null;
                //System.out.println( result );
            } catch (IOException e) {
                // e.printStackTrace();
            }
            // * Si el pid almacenado en el archivo .run
            // esta en ejecución devuelve el pid
            if (result.contains(pid)) {
                return Integer.parseInt(pid);
            }
        }
        // * Si el pid almacenado en el archivo .run
        // no está en ejecución devuelve una nueva PID
        return Recursos.PID;
    }
    
}
