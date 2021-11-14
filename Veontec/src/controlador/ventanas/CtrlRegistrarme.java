package controlador.ventanas;

import controlador.CtrlHiloConexion;
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
import src.FncGlobales;
import vista.paneles.PanelRegistrarme;

public class CtrlRegistrarme {
    
    // ***** Vista
    private PanelRegistrarme pnRegistrarme;
    
    // ***** Modelo
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    private CategoriaDao categoriaDao;
    private CategoriaDto categoriaDto;
    
    // ***** Atributos
    private static CtrlRegistrarme instancia;
    private static final Logger LOG = Logger.getLogger(CtrlRegistrarme.class.getName());
    
    
    // ***** Constructor
    private CtrlRegistrarme(PanelRegistrarme pnRegistrarme) {
        this.pnRegistrarme = pnRegistrarme;
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
    }
    
    // ***** Eventos
    private void mtdEventoBtnRegistrarme(){
        pnRegistrarme.btnRegistrarme.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdRegistrarme();
            }
        });
    }
    
    // ***** Métodos
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

            // * Creando usuarioDto
            mtdCreandoUsuario();

            // * Comprobar si el correo está disponible
            // es decir, si no está registrado
            if( !usuarioDao.mtdComprobar(usuarioDto) ){
                JOptionPane.showMessageDialog(Veontec.ventanaSession, "El correo ya está registrado.");
            }else{
                mtdProcesoRegistrarCuenta();
            }
            
        }else{
            JOptionPane.showMessageDialog(Veontec.ventanaSession, "No hay conexión.");
        }
        
    }
    
    private void mtdCreandoUsuario(){
        usuarioDto = new UsuarioDto();
        usuarioDao = new UsuarioDao();
        usuarioDto.setCmpNombreCompleto(pnRegistrarme.campoTexto1.getText().trim() );
        usuarioDto.setCmpCorreo(pnRegistrarme.campoCorreo1.getText().trim() );
        usuarioDto.setCmpPassword(new FncGlobales().mtdObtenerPasswordEncriptado(pnRegistrarme.campoPassword2.getPassword()));
        usuarioDto.setCmpKey("No");
        usuarioDto.setCmpEstado(0);
        usuarioDto.setCmpCreadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL());
        usuarioDto.setCmpActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL());
    }
    
    private void mtdProcesoRegistrarCuenta(){
        if( ObjEmail.mtdEnviarValidarEmail(usuarioDto) ){
            if( usuarioDao.mtdInsetar(usuarioDto) ){
                // * Obtener datos de la nueva cuenta
                usuarioDto.setCmpActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL());
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
                        JOptionPane.showMessageDialog(Veontec.ventanaSession, "Se registró exitosamente.");

                }
            }
        }
    }
    
    private boolean mtdObtenerCamposIncorrectos_Registrarme(){
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
            msg += "El campo correo está incorrecto. \n";
        }

        if( String.valueOf(pnRegistrarme.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacío. \n";
        }
        
        if( String.valueOf(pnRegistrarme.campoPassword2.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo repetir contraseña está vacío. \n";
        }
        
        if( !String.valueOf(pnRegistrarme.campoPassword1.getPassword()).trim()
                .equals(String.valueOf(pnRegistrarme.campoPassword2.getPassword()).trim()) ){
            campos_incorrectos++;
            msg += "La contraseña no coincide. \n";
        }
        
        if( campos_incorrectos > 0 )
            JOptionPane.showMessageDialog(Veontec.ventanaSession, msg);
        
        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    private void mtdVaciarCampos_Registrarme(){
        pnRegistrarme.campoTexto1.setText("");
        pnRegistrarme.campoCorreo1.setText("");
        pnRegistrarme.campoPassword1.setText("");
        pnRegistrarme.campoPassword2.setText("");
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
