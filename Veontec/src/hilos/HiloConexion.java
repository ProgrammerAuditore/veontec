package hilos;

import controlador.CtrlHiloConexion;
import javax.swing.JOptionPane;

// * Este hilo se sincroniza con el Hilo Principal
public class HiloConexion extends Thread {

    public HiloConexion() {
        this.setName("HiloConexion");
    }
    
    @Override
    public void run() {

        // Ejecutar el hilo en segundo plano
        while (true) {
            synchronized (CtrlHiloConexion.ctrlHiloC) {

                while (CtrlHiloConexion.ctrlEstado == true) {
                    try {
                        // * Esperaré a terminar la conexion
                        //System.out.println("HiloConexion :: Wait... ");
                        //System.out.println(" Esperaré a terminar la conexion ");
                        System.out.println("Voentec::NOTA [ Conexión establecida ] ");
                        CtrlHiloConexion.ctrlHiloC.wait();
                    } catch (Exception e) {
                    }
                }

                // * Conexion cerrada
                System.out.println("Sin conexión ... ");
                try {

                    // Verificando cada 1s Si la conexin está establecida 
                    if (CtrlHiloConexion.ctrlConexion != null) {
                        if (CtrlHiloConexion.mtdEstablecer()) {
                            CtrlHiloConexion.ctrlEstado = true;
                            CtrlHiloConexion.ctrlHiloC.notify();
                            //System.out.println("Voentec::NOTA [ Conexión restablecida ] ");
                        }else{
                            System.out.println("Voentec::NOTA [ Sin conexión al servidor ] ");
                            JOptionPane.showMessageDialog(null, "Voentec::NOTA [ Sin conexión al servidor ] ");
                            System.exit(0);
                        }
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }
        }
    }

}
