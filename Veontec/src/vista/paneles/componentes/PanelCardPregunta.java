/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.paneles.componentes;

import index.Veontec;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author victo
 */
public class PanelCardPregunta extends javax.swing.JPanel {

    /**
     * Creates new form PanelCard
     */
    public PanelCardPregunta() {
        initComponents();
        Dimension tam = new Dimension(654, 247);
        this.setPreferredSize( tam );
        this.setSize( tam );
        this.panelBackground.setBackground(new Color(40,175,176));
        this.panelBackground.setEnabled(true);
        this.panelBackground.setVisible(true);
        
        Dimension tamImg = new Dimension(202, 245);
        this.pnImgPortada.setSize(tamImg);
        this.pnImgPortada.setPreferredSize(tamImg);
       
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelBackground = new vista.componentes.jpanelbackground.JPanelBackground();
        pnImgPortada = new javax.swing.JPanel();
        etqTitulo = new vista.componentes.etiqueta.Etiqueta();
        jScrollPane1 = new javax.swing.JScrollPane();
        cmpDetalleCompra = new javax.swing.JTextArea();
        btnCancelarCompra = new vista.componentes.boton.Boton();
        btnResponder = new vista.componentes.boton.Boton();
        cmpFecha = new vista.componentes.etiqueta.Mensaje();
        cmpVendedor = new vista.componentes.etiqueta.Mensaje();
        etiqueta1 = new vista.componentes.etiqueta.Etiqueta();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(204, 204, 204));

        panelBackground.setBackground(new java.awt.Color(40, 175, 176));
        panelBackground.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray));
        panelBackground.setImgOpacidad(1.0F);
        panelBackground.setImgRutaInternoActivo(false);

        pnImgPortada.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnImgPortadaLayout = new javax.swing.GroupLayout(pnImgPortada);
        pnImgPortada.setLayout(pnImgPortadaLayout);
        pnImgPortadaLayout.setHorizontalGroup(
            pnImgPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        pnImgPortadaLayout.setVerticalGroup(
            pnImgPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        etqTitulo.setForeground(new java.awt.Color(255, 255, 255));
        etqTitulo.setText("Titulo del producto para vender el la tienda");

        cmpDetalleCompra.setEditable(false);
        cmpDetalleCompra.setColumns(20);
        cmpDetalleCompra.setRows(5);
        cmpDetalleCompra.setEnabled(false);
        jScrollPane1.setViewportView(cmpDetalleCompra);

        btnCancelarCompra.setImgButtonType("danger");
        btnCancelarCompra.setTexto("Cancelar compra");

        btnResponder.setTexto("Responder");

        cmpVendedor.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        etiqueta1.setText("Pregunta o comentario");

        javax.swing.GroupLayout panelBackgroundLayout = new javax.swing.GroupLayout(panelBackground);
        panelBackground.setLayout(panelBackgroundLayout);
        panelBackgroundLayout.setHorizontalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addComponent(pnImgPortada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                        .addComponent(btnCancelarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(btnResponder, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(cmpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(cmpVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(etiqueta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBackgroundLayout.setVerticalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmpVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(etiqueta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResponder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
            .addComponent(pnImgPortada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public vista.componentes.boton.Boton btnCancelarCompra;
    public vista.componentes.boton.Boton btnResponder;
    public javax.swing.JTextArea cmpDetalleCompra;
    public vista.componentes.etiqueta.Mensaje cmpFecha;
    public vista.componentes.etiqueta.Mensaje cmpVendedor;
    private vista.componentes.etiqueta.Etiqueta etiqueta1;
    public vista.componentes.etiqueta.Etiqueta etqTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private vista.componentes.jpanelbackground.JPanelBackground panelBackground;
    public javax.swing.JPanel pnImgPortada;
    // End of variables declaration//GEN-END:variables
}