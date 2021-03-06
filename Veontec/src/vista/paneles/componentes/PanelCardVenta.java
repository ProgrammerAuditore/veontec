/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.paneles.componentes;

import java.awt.Color;
import java.awt.Dimension;

public class PanelCardVenta extends javax.swing.JPanel {

    /**
     * Creates new form PanelCard
     */
    public PanelCardVenta() {
        initComponents();
        Dimension tam = new Dimension(682, 254);
        this.setPreferredSize( tam );
        this.setSize( tam );
        this.panelBackground.setBackground(new Color(40,175,176));
        this.panelBackground.setEnabled(true);
        this.panelBackground.setVisible(true);
        
        Dimension tamImgPortada = new Dimension(225, 228);
        this.pnImgPortada.setPreferredSize(tamImgPortada);
        this.pnImgPortada.setSize(tamImgPortada);
       
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
        cmpStockVendido = new vista.componentes.campos.CampoTexto();
        jScrollPane1 = new javax.swing.JScrollPane();
        cmpDetalleVenta = new javax.swing.JTextArea();
        pnImgPortada = new vista.componentes.jpanelbackground.JPanelBackground();
        etqFecha = new vista.componentes.etiqueta.Mensaje();
        etqUsuario = new vista.componentes.etiqueta.Mensaje();

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
        etqStockDisponible.setText("Stock vendido");

        cmpPrecioUnidad.setEditable(false);
        cmpPrecioUnidad.setBorder(null);
        cmpPrecioUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cmpPrecioUnidad.setText("199.98");
        cmpPrecioUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmpPrecioUnidadActionPerformed(evt);
            }
        });

        cmpStockVendido.setEditable(false);
        cmpStockVendido.setBorder(null);
        cmpStockVendido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cmpStockVendido.setText("15");
        cmpStockVendido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmpStockVendidoActionPerformed(evt);
            }
        });

        cmpDetalleVenta.setColumns(20);
        cmpDetalleVenta.setLineWrap(true);
        cmpDetalleVenta.setRows(5);
        cmpDetalleVenta.setText("<Descripcion>");
        jScrollPane1.setViewportView(cmpDetalleVenta);

        pnImgPortada.setImgBackgroundEnabled(true);

        javax.swing.GroupLayout pnImgPortadaLayout = new javax.swing.GroupLayout(pnImgPortada);
        pnImgPortada.setLayout(pnImgPortadaLayout);
        pnImgPortadaLayout.setHorizontalGroup(
            pnImgPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );
        pnImgPortadaLayout.setVerticalGroup(
            pnImgPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        etqFecha.setText("Fecha de venta");

        etqUsuario.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etqUsuario.setText("Nombre de usuario");

        javax.swing.GroupLayout panelBackgroundLayout = new javax.swing.GroupLayout(panelBackground);
        panelBackground.setLayout(panelBackgroundLayout);
        panelBackgroundLayout.setHorizontalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                .addComponent(pnImgPortada, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(etqFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(etqStockDisponible, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(cmpStockVendido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBackgroundLayout.createSequentialGroup()
                                .addGap(0, 102, Short.MAX_VALUE)
                                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cmpPrecioUnidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(etqPrecioUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(etqUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelBackgroundLayout.setVerticalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etqFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etqUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(etqStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmpStockVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(etqPrecioUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmpPrecioUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
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

    private void cmpStockVendidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmpStockVendidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmpStockVendidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea cmpDetalleVenta;
    public vista.componentes.campos.CampoTexto cmpPrecioUnidad;
    public vista.componentes.campos.CampoTexto cmpStockVendido;
    public vista.componentes.etiqueta.Mensaje etqFecha;
    private vista.componentes.etiqueta.Etiqueta etqPrecioUnidad;
    private vista.componentes.etiqueta.Etiqueta etqStockDisponible;
    public vista.componentes.etiqueta.Etiqueta etqTitulo;
    public vista.componentes.etiqueta.Mensaje etqUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private vista.componentes.jpanelbackground.JPanelBackground panelBackground;
    public vista.componentes.jpanelbackground.JPanelBackground pnImgPortada;
    // End of variables declaration//GEN-END:variables
}
