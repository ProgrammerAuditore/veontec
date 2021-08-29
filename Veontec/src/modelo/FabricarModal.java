package modelo;


import modelo.dto.UsuarioDto;
import vista.ventanas.VentanaPrincipal;

public class FabricarModal {
    
    private VentanaPrincipal laVista;
    private UsuarioDto proyecto;

    public FabricarModal(VentanaPrincipal laVista) {
        this.laVista = laVista;
    }
    /*
    public boolean construir(String modal){
        
        switch(modal){
            case "Preferencias" : modalPreferencias(); break;
            case "ConfigurarConexion" : modalConfigurarConexion(); break;
            case "GenerarInforme" : modalGenerarInforme(); break;
            case "DatosPersonales" : modalDatosPersonales(); break;
            case "GestionarProyectos" : modalGestionarProyectos(); break;
            case "GestionarEmpresas" : modalGestionarEmpresas(); break;
            case "GestionarRequisitos" : modalGestionarRequisitos(proyecto); break;
            case "Vinculacion" : modalVinculacion(); break;
            case "AcercaDe" : modalAcercaDe(); break;
            case "BuscarActualizacion" : modalBuscarActualizacion(); break;
        }
        
        return false;
    } 
    
    private void modalPreferencias() {

        // * Crear el modal Preferencias con su respectivo patrón de diseño MVC
        PanelPreferencias vista = new PanelPreferencias();
        PreferenciaDto dto = new PreferenciaDto();
        PreferenciaDao dao = new PreferenciaDao();
        CtrlPreferencias controlador = new CtrlPreferencias(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }
    
    private void modalConfigurarConexion() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelConexion vista = new PanelConexion();
        ConexionDto dto = new ConexionDto();
        ConexionDao dao = new ConexionDao();
        CtrlConexion controlador = new CtrlConexion(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
    }

    private void modalDatosPersonales() {

        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelDatosPersonales vista = new PanelDatosPersonales();
        DatosPersonalesDao dao = new DatosPersonalesDao();
        DatosPersonalesDto dto = new DatosPersonalesDto();
        CtrlDatosPersonales controlador = new CtrlDatosPersonales(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }
    
    private void modalGenerarInforme(){
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelResumen vista = new PanelResumen();
        UsuarioDto dto = new UsuarioDto();
        UsuarioDao dao = new UsuarioDao();
        CtrlResumen controlador = new CtrlResumen(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
    }

    private void modalGestionarProyectos() {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarProyectos vista = new PanelGestionarProyectos();
        UsuarioDao dao = new UsuarioDao();
        UsuarioDto dto = new UsuarioDto();
        CtrlGestionarProyectos controlador = new CtrlGestionarProyectos(vista, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalGestionarRequisitos(UsuarioDto proyecto_dto) {
        
        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarRequisitos vista = new PanelGestionarRequisitos();
        RequisitoDao dao = new RequisitoDao();
        RequisitoDto dto = new RequisitoDto();
        CtrlGestionarRequisitos controlador = new CtrlGestionarRequisitos(vista, proyecto_dto, dto, dao);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalGestionarEmpresas() {

        // * Crear el modal Configurar conexión con su respectivo patrón de diseño MVC
        PanelGestionarEmpresas vista = new PanelGestionarEmpresas();
        EmpresaDao dao = new EmpresaDao();
        EmpresaDto dto = new EmpresaDto();
        CtrlGestionarEmpresas controlador = new CtrlGestionarEmpresas(vista, dao, dto);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalVinculacion() {

        // * Crear el modal Vinculación con su respectivo patrón de diseño MVC
        PanelVinculacion vista = new PanelVinculacion();
        EmpresaDao empresa_dao = new EmpresaDao();
        UsuarioDao proyecto_dao = new UsuarioDao();
        VinculacionDao vinculacion_dao = new VinculacionDao();
        VinculacionDto vinculacion_dto = new VinculacionDto();
        CtrlVinculacion controlador = new CtrlVinculacion(vista, proyecto_dao, empresa_dao, vinculacion_dao, vinculacion_dto);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }

    private void modalAcercaDe() {

        // * Crear el modal Vinculación con su respectivo patrón de diseño MVC
        PanelAcercaDe vista = new PanelAcercaDe();
        CtrlAcercaDe controlador = new CtrlAcercaDe(vista);
        controlador.modal = new JDialog(laVista);
        controlador.mtdInit();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);

    }
    
    private void modalBuscarActualizacion(){
        
        // * Crear el modal Vinculación con su respectivo patrón de diseño MVC
        PanelActualizacion vista = new PanelActualizacion();
        ObjVersionesXml modelo = new ObjVersionesXml();
        ctrlBuscarActualizacion controlador = new ctrlBuscarActualizacion(vista, modelo);
        controlador.modal = new JDialog(laVista);
        controlador.init();
        controlador.modal.setLocationRelativeTo(laVista);
        controlador.modal.setVisible(true);
        
    }

    public UsuarioDto getProyecto() {
        return proyecto;
    }

    public void setProyecto(UsuarioDto proyecto) {
        this.proyecto = proyecto;
    }
    */

}
