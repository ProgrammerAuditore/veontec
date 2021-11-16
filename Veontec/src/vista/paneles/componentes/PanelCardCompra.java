/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.paneles.componentes;

import java.awt.Color;
import java.awt.Dimension;

public class PanelCardCompra extends javax.swing.JPanel {

    /**
     * Creates new form PanelCard
     */
    public PanelCardCompra() {
        initComponents();
        Dimension tam = new Dimension(682, 254);
        this.setPreferredSize( tam );
        this.setSize( tam );
        this.panelBackground.setBackground(new Color(40,175,176));
        this.panelBackground.setEnabled(true);
        this.panelBackground.setVisible(true);
        
        Dimension tamImg = new Dimension(214, 252);
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
        etqTitulo = new vista.componentes.etiqueta.Etiqueta();
        etqPrecioUnidad = new vista.componentes.etiqueta.Etiqueta();
        etqStockDisponible = new vista.componentes.etiqueta.Etiqueta();
        cmpPrecioUnidad = new vista.componentes.campos.CampoTexto();
        cmpStockCompra = new vista.componentes.campos.CampoTexto();
        jScrollPane1 = new javax.swing.JScrollPane();
        cmpDetalleCompra = new javax.swing.JTextArea();
        btnCancelarCompra = new vista.componentes.boton.Boton();
        btnHacerPregunta = new vista.componentes.boton.Boton();
        cmpFecha = new vista.componentes.etiqueta.Mensaje();
        cmpVendedor = new vista.componentes.etiqueta.Mensaje();
        pnImgPortada = new vista.componentes.jpanelbackground.JPanelBackground();

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

        etqTitulo.setForeground(new java.awt.Color(255, 255, 255));
        etqTitulo.setText("Titulo del producto para vender el la tienda");

        etqPrecioUnidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqPrecioUnidad.setText("Precio x Unidad");

        etqStockDisponible.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqStockDisponible.setText("Stock compra");

        cmpPrecioUnidad.setEditable(false);
        cmpPrecioUnidad.setBorder(null);
        cmpPrecioUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cmpPrecioUnidad.setText("199.98");
        cmpPrecioUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmpPrecioUnidadActionPerformed(evt);
            }
        });

        cmpStockCompra.setEditable(false);
        cmpStockCompra.setBorder(null);
        cmpStockCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cmpStockCompra.setText("15");
        cmpStockCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmpStockCompraActionPerformed(evt);
            }
        });

        cmpDetalleCompra.setEditable(false);
        cmpDetalleCompra.setColumns(20);
        cmpDetalleCompra.setLineWrap(true);
        cmpDetalleCompra.setRows(5);
        cmpDetalleCompra.setEnabled(false);
        jScrollPane1.setViewportView(cmpDetalleCompra);

        btnCancelarCompra.setImgButtonType("danger");
        btnCancelarCompra.setTexto("Cancelar compra");

        btnHacerPregunta.setTexto("Hacer pregunta");

        cmpFecha.setText("Fecha");

        cmpVendedor.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cmpVendedor.setText("Nombre");

        pnImgPortada.setImgBackgroundEnabled(true);

        javax.swing.GroupLayout pnImgPortadaLayout = new javax.swing.GroupLayout(pnImgPortada);
        pnImgPortada.setLayout(pnImgPortadaLayout);
        pnImgPortadaLayout.setHorizontalGroup(
            pnImgPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 212, Short.MAX_VALUE)
        );
        pnImgPortadaLayout.setVerticalGroup(
            pnImgPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBackgroundLayout = new javax.swing.GroupLayout(panelBackground);
        panelBackground.setLayout(panelBackgroundLayout);
        panelBackgroundLayout.setHorizontalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addComponent(pnImgPortada, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                        .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(etqStockDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(cmpStockCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(btnCancelarCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnHacerPregunta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmpPrecioUnidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(etqPrecioUnidad, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(cmpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmpVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(etqStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmpStockCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(etqPrecioUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmpPrecioUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHacerPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnImgPortada, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmpPrecioUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmpPrecioUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmpPrecioUnidadActionPerformed

    private void cmpStockCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmpStockCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmpStockCompraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public vista.componentes.boton.Boton btnCancelarCompra;
    public vista.componentes.boton.Boton btnHacerPregunta;
    public javax.swing.JTextArea cmpDetalleCompra;
    public vista.componentes.etiqueta.Mensaje cmpFecha;
    public vista.componentes.campos.CampoTexto cmpPrecioUnidad;
    public vista.componentes.campos.CampoTexto cmpStockCompra;
    public vista.componentes.etiqueta.Mensaje cmpVendedor;
    private vista.componentes.etiqueta.Etiqueta etqPrecioUnidad;
    private vista.componentes.etiqueta.Etiqueta etqStockDisponible;
    public vista.componentes.etiqueta.Etiqueta etqTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private vista.componentes.jpanelbackground.JPanelBackground panelBackground;
    public vista.componentes.jpanelbackground.JPanelBackground pnImgPortada;
    // End of variables declaration//GEN-END:variables
}
