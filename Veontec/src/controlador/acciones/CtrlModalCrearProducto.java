package controlador.acciones;

import index.Veontec;
import java.awt.Dialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelMiTienda;

public class CtrlModalCrearProducto implements MouseListener{
    
    // Vista
    public PanelMiTienda laVista;
    private JDialog modalCrearProducto;
    
    // Modelos
    private UsuarioDto dto;
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    private static CtrlModalCrearProducto instancia;

    public CtrlModalCrearProducto(PanelMiTienda laVista) {
        this.laVista = laVista;
        
    }

    public static CtrlModalCrearProducto getInstancia(PanelMiTienda laVista){
        if( instancia == null ){
            instancia = new CtrlModalCrearProducto(laVista);
            instancia.mtdInit();
        }
        
        return instancia;
    }
    
    private void mtdInit(){
         this.laVista.btnCrearProducto.addMouseListener(this);
    }
    
    public void mtdRecargarDatos(){
        treeNode1 = new DefaultMutableTreeNode("Categorias");
        laVista.lstProductos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if( e.getSource() == laVista.btnCrearProducto ){
            mtdCrearProducto();
        }
    }
    
    private void mtdCrearProducto(){
        
        //modalCrearProducto.setModal(true);
        //modalCrearProducto.setType(Window.Type.UTILITY);
        modalCrearProducto.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modalCrearProducto.setTitle(Veontec.idioma.getProperty("ctrlDatosPersonales.mtdInit.titulo"));
        modalCrearProducto.setResizable(false);
        modalCrearProducto.setSize( laVista.getSize() );
        modalCrearProducto.setPreferredSize(laVista.getSize() );
        modalCrearProducto.setContentPane(laVista);
        JOptionPane.showMessageDialog(laVista, "Crear un producto...");
        
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
