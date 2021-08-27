package controlador;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.dao.DatosPersonalesDao;
import modelo.dao.UsuarioDao;
import modelo.dto.DatosPersonalesDto;
import modelo.dto.UsuarioDto;
import vista.paneles.veontec.PanelInicarSession;
import vista.paneles.veontec.PanelRegistrarme;
import vista.ventanas.VentanaInicio;

public class CtrlInicio implements MouseListener{
    
    // * Vistas
    private PanelRegistrarme pnRegistrarme;
    private PanelInicarSession pnInicarSession;
    private VentanaInicio ni;
    
    // * Modelos
    private DatosPersonalesDao dao;
    private DatosPersonalesDto dto;

    public CtrlInicio(VentanaInicio ni, PanelRegistrarme pnRegistrarme, PanelInicarSession pnInicarSession) {
        this.ni = ni;
        this.pnRegistrarme = pnRegistrarme;
        this.pnInicarSession = pnInicarSession;
        
        // * Definir oyentes
        this.pnRegistrarme.btnRegistrarme.addMouseListener(this);
        this.pnInicarSession.btnIniciarSession.addMouseListener(this);
        
    }
    
    public void mtdInit(){
        
        
        if(CtrlHiloConexion.checkConexion() == false){
            ni.tabContenedor.getTabComponentAt(1).setEnabled(false);
            ni.tabContenedor.getTabComponentAt(2).setEnabled(false);
        }else{
            System.out.println("Existe conexion a la base de datos... ");
        }
        
        // * Mostrar las ventana
        ni.setLocationRelativeTo(null);
        ni.setVisible(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
        if( e.getSource() == pnRegistrarme.btnRegistrarme  ){
            mtdRegistrarme();
        }
        
        if( e.getSource() == pnInicarSession.btnIniciarSession ){
            mtdIniciarSession();
        }
        
    }
    
    private void mtdIniciarSession(){
        // * Verificar los campos de registrarme
        if( mtdCamposIncorrectos_IniciarSession() ){
            return ;
        }
        
        // * Registrando usuario
        UsuarioDto usuario = new UsuarioDto();
        UsuarioDao dao = new UsuarioDao();
        usuario.setCmpCorreo( pnInicarSession.campoCorreo1.getText().trim() );
        usuario = dao.mtdConsultar(usuario);
        //System.out.println("Iniciar Session : \n " + usuario.toString());
            
        // * Verificar usuario
        if( pnInicarSession.campoCorreo1.getText().trim().equals(usuario.getCmpCorreo().trim())
            && mtdVerificarPassword(usuario.getCmpPassword().trim()) ){
            JOptionPane.showMessageDialog(ni, "Bienvenido " + usuario.getCmpNombreCompleto() );
            
        }else{
            JOptionPane.showMessageDialog(ni, "Vefica que los datos sean correctos.");
        }
        
    }
    
    private void mtdRegistrarme(){
        
        // * Verificar los campos de registrarme
        if( mtdCamposIncorrectos_Registrarme() ){
            return ;
        }
        
        // * Registrando usuario
        UsuarioDto usuario = new UsuarioDto();
        UsuarioDao dao = new UsuarioDao();
        usuario.setCmpNombreCompleto(pnRegistrarme.campoTexto1.getText().trim() );
        usuario.setCmpCorreo( pnRegistrarme.campoCorreo1.getText().trim() );
        usuario.setCmpPassword(mtdObtenerPasswordEncry());
        
        if( !dao.mtdComprobar(usuario) ){
            JOptionPane.showMessageDialog(null, "El correo ya está registrado.");
        }else{
            if( dao.mtdInsetar(usuario) ){
                JOptionPane.showMessageDialog(ni, "Se registro exitosamente.");
                mtdVaciarCampos_Registrarme();
            }
        }
        
    }
    
    private boolean mtdVerificarPassword(String passwd){
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = pnInicarSession.campoPassword1.getPassword();
        
        try {
            // Hash password
            //String hash = argon2.hash(10, 65536, 1, password);
            // // Estará almacenado en la base de datos
            String hash = passwd;
            
            // Verify password
            if (argon2.verify(hash, password)) {
                // Hash matches password
                System.out.println("Hash matches password");
                return true;
            } else {
                // Hash doesn't match password
                System.out.println("Hash doesn't match password");
            }
            
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        
        return false;
    }
    
    private String mtdObtenerPasswordEncry(){
        // Encriptar la contraseña
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = pnRegistrarme.campoPassword2.getPassword();
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
    
    private boolean mtdCamposIncorrectos_IniciarSession(){
    int campos_incorrectos = 0;
    String msg = "Verifica los siguientes datos: \n";
        
        if( pnInicarSession.campoCorreo1.getText().trim().isEmpty() 
                || !pnInicarSession.campoCorreo1.isAprobado()  ){
            campos_incorrectos++;
            msg += "El campo correo es incorrecto. \n";
        }
        
        if( String.valueOf(pnInicarSession.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacio. \n";
        }
    
        if( campos_incorrectos > 0 )
                JOptionPane.showMessageDialog(ni, msg);

        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    private boolean mtdCamposIncorrectos_Registrarme(){
    int campos_incorrectos = 0;
    String msg = "Verifica los siguientes datos: \n";
    
        if( pnRegistrarme.campoTexto1.getText().trim().isEmpty() 
                || !pnRegistrarme.campoTexto1.isAprobado() ){
            campos_incorrectos++;
            msg += "El campo nombre es incorrecto. \n";
        }
        
        if( pnRegistrarme.campoCorreo1.getText().trim().isEmpty() 
                || !pnRegistrarme.campoCorreo1.isAprobado()  ){
            campos_incorrectos++;
            msg += "El campo correo es incorrecto. \n";
        }

        if( String.valueOf(pnRegistrarme.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacio. \n";
        }
        
        if( String.valueOf(pnRegistrarme.campoPassword2.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo repetir contraseña está vacio. \n";
        }
        
        if( !String.valueOf(pnRegistrarme.campoPassword1.getPassword()).trim()
                .equals(String.valueOf(pnRegistrarme.campoPassword2.getPassword()).trim()) ){
            campos_incorrectos++;
            msg += "La contraseña no coindice. \n";
        }
        
        if( campos_incorrectos > 0 )
            JOptionPane.showMessageDialog(ni, msg);
        
        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    private void mtdVaciarCampos_Registrarme(){
        pnRegistrarme.campoTexto1.setText("");
        pnRegistrarme.campoCorreo1.setText("");
        pnRegistrarme.campoPassword1.setText("");
        pnRegistrarme.campoPassword2.setText("");
    }
        
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}


    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
