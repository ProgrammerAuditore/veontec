package controlador;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dto.UsuarioDto;
import vista.paneles.veontec.PanelCrearProducto;
import vista.paneles.veontec.PanelMiTienda;

public class CtrlMiTienda implements MouseListener{
    
    // Vista
    public PanelMiTienda laVista;
    public JDialog modalCrearProducto;
    
    // Modelos
    private UsuarioDto dto;
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    private static CtrlMiTienda instancia;

    public CtrlMiTienda(PanelMiTienda laVista) {
        this.laVista = laVista;
        
    }

    public static CtrlMiTienda getInstancia(PanelMiTienda laVista){
        if( instancia == null ){
            instancia = new CtrlMiTienda(laVista);
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
        PanelCrearProducto  pp = new PanelCrearProducto();
        modalCrearProducto.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modalCrearProducto.setTitle("Crear un nuevo producto");
        modalCrearProducto.setResizable(false);
        modalCrearProducto.setSize( pp.getSize() );
        modalCrearProducto.setPreferredSize(pp.getSize() );
        modalCrearProducto.setContentPane(pp);
        modalCrearProducto.setVisible(true);
        
        modalCrearProducto.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modalCrearProducto.setVisible(false);
                modalCrearProducto.dispose();
            }
        });
        
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
