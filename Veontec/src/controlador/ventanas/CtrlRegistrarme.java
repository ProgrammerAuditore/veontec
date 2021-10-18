package controlador.ventanas;

import controlador.CtrlHiloConexion;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import index.Veontec;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ObjEmail;
import modelo.dao.CategoriaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CategoriaDto;
import modelo.dto.UsuarioDto;
import src.Funciones;
import vista.paneles.PanelRegistrarme;

public class CtrlRegistrarme {
    
    // * Vista
    private PanelRegistrarme pnSignUp;
    
    // * Modelo
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    private CategoriaDao categoriaDao;
    private CategoriaDto categoriaDto;
    
    // * Atributos
    private static CtrlRegistrarme instancia;
    private static final Logger LOG = Logger.getLogger(CtrlRegistrarme.class.getName());
    
    
    // * Constructor
    private CtrlRegistrarme(PanelRegistrarme pnRegistrarme) {
        this.pnSignUp = pnRegistrarme;
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
    }
    
    // * Eventos
    private void mtdEventoBtnRegistrarme(){
        pnSignUp.btnRegistrarme.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdRegistrarme();
            }
        });
    }
    
    // * Métodos
    public static CtrlRegistrarme getInstancia(PanelRegistrarme pnRegistrarme){
        if( instancia ==  null){
            instancia = new CtrlRegistrarme(pnRegistrarme);
            instancia.mtdInit();
        }else{
            
        }
        
        return instancia;
    }
    
    private void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnRegistrarme();
        
    }
    
    private void mtdRegistrarme(){
        
        if( CtrlHiloConexion.ctrlEstado ){
            // * Verificar los campos de registrarme
            if( mtdObtenerCamposIncorrectos_Registrarme() ){
                return ;
            }

            // * Registrando usuarioDto
            mtdCreandoUsuario();

            // * Comprobar si el correo está disponible
            // es decir, si no está registrado
            if( !usuarioDao.mtdComprobar(usuarioDto) ){
                JOptionPane.showMessageDialog(null, "El correo ya está registrado.");
            }else{
                mtdProcesoRegistrarCuenta();
            }
            
        }else{
            JOptionPane.showMessageDialog(Veontec.ventanaSession, "No hay conexión");
        }
        
    }
    
    private void mtdCreandoUsuario(){
        usuarioDto = new UsuarioDto();
        usuarioDao = new UsuarioDao();
        usuarioDto.setCmpNombreCompleto(pnSignUp.campoTexto1.getText().trim() );
        usuarioDto.setCmpCorreo(pnSignUp.campoCorreo1.getText().trim() );
        usuarioDto.setCmpPassword(new Funciones().mtdObtenerPasswordEncriptado(pnSignUp.campoPassword2.getPassword()));
        usuarioDto.setCmpKey("No");
        usuarioDto.setCmpEstado(0);
    }
    
    private void mtdProcesoRegistrarCuenta(){
        if( ObjEmail.mtdEnviarValidarEmail(usuarioDto) ){
            if( usuarioDao.mtdInsetar(usuarioDto) ){
                // * Obtener datos de la nueva cuenta
                usuarioDto = usuarioDao.mtdConsultar(usuarioDto);

                if(usuarioDao.mtdActualizar(usuarioDto)){
                        // * Crear una categoria por defecto llamda 'nueva'
                        categoriaDto = new CategoriaDto();
                        categoriaDao = new CategoriaDao();
                        categoriaDto.setCateNombre("Nueva");
                        categoriaDto.setCateUsuario(usuarioDto.getCmpID());
                        categoriaDto.setCateTotalProductos(0);
                        categoriaDao.mtdInsetar(categoriaDto);

                        mtdVaciarCampos_Registrarme();
                        JOptionPane.showMessageDialog(Veontec.ventanaSession, "Se registro exitosamente.");

                }
            }
        }
    }
    
    private boolean mtdObtenerCamposIncorrectos_Registrarme(){
    int campos_incorrectos = 0;
    String msg = "Verifica los siguientes datos: \n";
    
        if( pnSignUp.campoTexto1.getText().trim().isEmpty() 
                || !pnSignUp.campoTexto1.isAprobado() ){
            campos_incorrectos++;
            msg += "El campo nombre es incorrecto. \n";
        }
        
        if( pnSignUp.campoCorreo1.getText().trim().isEmpty() 
                || !pnSignUp.campoCorreo1.isAprobado()  ){
            campos_incorrectos++;
            msg += "El campo correo es incorrecto. \n";
        }

        if( String.valueOf(pnSignUp.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacio. \n";
        }
        
        if( String.valueOf(pnSignUp.campoPassword2.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo repetir contraseña está vacio. \n";
        }
        
        if( !String.valueOf(pnSignUp.campoPassword1.getPassword()).trim()
                .equals(String.valueOf(pnSignUp.campoPassword2.getPassword()).trim()) ){
            campos_incorrectos++;
            msg += "La contraseña no coindice. \n";
        }
        
        if( campos_incorrectos > 0 )
            JOptionPane.showMessageDialog(Veontec.ventanaSession, msg);
        
        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    private void mtdVaciarCampos_Registrarme(){
        pnSignUp.campoTexto1.setText("");
        pnSignUp.campoCorreo1.setText("");
        pnSignUp.campoPassword1.setText("");
        pnSignUp.campoPassword2.setText("");
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
