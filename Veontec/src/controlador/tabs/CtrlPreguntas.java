package controlador.tabs;

import controlador.componentes.CtrlCardPregunta;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dao.PreguntaDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.PreguntaDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import src.Software;
import vista.paneles.PanelPreguntas;

public class CtrlPreguntas{
    
    // ***** Vista
    PanelPreguntas pnPreguntas;
    
    // ***** Modelo
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private PreguntaDao preguntaDao;
    private PreguntaDto preguntaDto;
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    
    // ***** Atributos
    private static CtrlPreguntas instancia;
    private List<PreguntaDto> lstPreguntas;
    Integer usuarioID;
    private Integer cantidadResultado;
    private Integer totalProductosExistentes;
    private Integer cantidadPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlBienvenida.class.getName());
    
    // ***** Constructor
    private CtrlPreguntas(PanelPreguntas laVista, UsuarioDto dto, UsuarioDao dao){
        // * Se recibe por parametros
        this.pnPreguntas = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
        
        // * Es necesario instanciarlos
        this.preguntaDto = new PreguntaDto();
        this.preguntaDao = new PreguntaDao();
        this.productoDto = new ProductoDto();
        this.productoDao = new ProductoDao();
        this.cantidadResultado = 0;
        this.cantidadPorPagina = Software.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // ***** Eventos    
    private void mtdEventoBtnBuscar(){
        pnPreguntas.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarPreguntas(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        pnPreguntas.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarPreguntas(activarBusqueda);
                } 
            }
        });
    }
    
    private void mtdEventoBtnPrevia(){
        pnPreguntas.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        pnPreguntas.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosSiguiente();
            }
        });
    }
        
    
    // ***** Métodos
    public static CtrlPreguntas getInstancia(PanelPreguntas laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.warning("Inicializando controlador.... ");
        if( instancia == null ){
            LOG.warning("Creando instancia.... ");
            instancia = new CtrlPreguntas(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarPreguntas(false);
        
        }
        
        return instancia;
    }
    
    public static boolean mtdRecargarPreguntas(){
        instancia.mtdMostrarPreguntas(false);
        return true;
    }
    
    private void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnPrevia();
        mtdEventoBtnSiguiente();
        mtdEventoCmpBuscarProducto();
        mtdMostrarPreguntas(false);
    }
    
    private void mtdMostrarPreguntas(boolean busqueda){
        LOG.info("Iniciando ...");
        
        int totalPreguntas = 0;
        pnPreguntas.pnContenedor.setLayout(new GridBagLayout());
        pnPreguntas.pnContenedor.removeAll();
        
        LOG.info("Listando preguntas...");
        preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
        preguntaDto.setPregVendedor( Veontec.usuarioDto.getCmpID() );
        
        
        if( busqueda == false ){
            lstPreguntas = preguntaDao.mtdListarAllPreguntasPorUsuario(preguntaDto ,cantidadPorPagina, cantidadResultado);
            totalProductosExistentes = Integer.parseInt(""+preguntaDao.mtdRowCountAllPreguntasPorUsuario(preguntaDto));
        }else{
            preguntaDto.setPregTitulo('%'+pnPreguntas.cmpBusqueda.getText()+'%');
            lstPreguntas = preguntaDao.mtdBuscarAllPreguntasPorUsuarioSimilares(preguntaDto, cantidadPorPagina, cantidadResultado);
            totalProductosExistentes = Integer.parseInt(""+preguntaDao.mtdRowCountAllPreguntasPorUsuarioSimilares(preguntaDto));
        }
        
        totalPreguntas = lstPreguntas.size();
        if( totalPreguntas > 0 ){
            
            LOG.warning("Recorriendo productos ....");
            for (int i = 0; i < totalPreguntas; i++) {
                productoDto = productoDao.mtdConsultar( lstPreguntas.get(i).getPregProducto() );
                
                // * Verificar si soy un vendedor 
                // Si lo soy, muestrame al comprador; en caso contrario muestrame al vendedor
                if( lstPreguntas.get(i).getPregVendedor().equals(Veontec.usuarioDto.getCmpID()) ){
                    usuarioID = lstPreguntas.get(i).getPregComprador();
                }else{
                    usuarioID = lstPreguntas.get(i).getPregVendedor();
                }
                
                usuarioDto = usuarioDao.mtdConsultar( usuarioID );
                
                if( productoDto != null && usuarioDto != null ){
                    CtrlCardPregunta card_pregunta = new CtrlCardPregunta( lstPreguntas.get(i), productoDto, usuarioDto );
                    card_pregunta.setItem(i);
                    card_pregunta.mtdInit();
                    pnPreguntas.pnContenedor.add(card_pregunta.getPnCardPregunta(), card_pregunta.getTarjeta_dimensiones() );
                }
            }
            
        }
        
        pnPreguntas.pnContenedor.validate();
        pnPreguntas.pnContenedor.revalidate();
        pnPreguntas.pnContenedor.repaint();
    }
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultado -= cantidadPorPagina;
        //pagProductos = cantidadResultado <= 0 ? 0 : cantidadResultado;
        //LOG.info("Productos previas : " + cantidadResultado );
        
        if( cantidadResultado < 0  ){
            cantidadResultado = 0;
            JOptionPane.showMessageDialog(pnPreguntas, "No hay más resultados por mostrar.");
            pnPreguntas.btnPrevia.setEnabled(false);
            return;
        }
        
        pnPreguntas.btnSiguiente.setEnabled(true);
        mtdMostrarPreguntas(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultado += cantidadPorPagina;
        //pagProductos = cantidadResultado >= totalProductosExistentes ? totalProductosExistentes: cantidadResultado;
        //LOG.info("Productos siguientes : " + cantidadResultado );
        
        if( cantidadResultado >= totalProductosExistentes ){
            cantidadResultado = totalProductosExistentes;
            JOptionPane.showMessageDialog(pnPreguntas, "No hay más resultados por mostrar.");
            pnPreguntas.btnSiguiente.setEnabled(false);
            return;
        }
        
        pnPreguntas.btnPrevia.setEnabled(true);
        mtdMostrarPreguntas(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        pnPreguntas.btnPrevia.setEnabled(true);
        pnPreguntas.btnSiguiente.setEnabled(true);
        
        cantidadResultado=0;
        if( pnPreguntas.cmpBusqueda.getText().trim().isEmpty() || pnPreguntas.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
