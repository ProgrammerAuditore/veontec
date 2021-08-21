/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.paneles;

import index.MyFreeLab;
import java.awt.Color;
import src.Recursos;

/**
 *
 * @author victo
 */
public class PanelPreferencias extends javax.swing.JPanel {

    /**
     * Creates new form p_mis_datos
     */
    public PanelPreferencias() {
        initComponents();
        this.setSize(Recursos.tamDialogModal );
        this.setPreferredSize(Recursos.tamDialogModal );
        bkgAside.setImgBackgroundEnabled(true);
        bkgAside.setImgBackgroundIn_Ex(true);
        bkgAside.setImgRutaInterno(Recursos.bkgAside );
        
        mtdEstablecerIdioma();
    }
    
    private void mtdEstablecerIdioma(){
        
        // * Paneles
        tpanelOpciones.setTitleAt(0, MyFreeLab.idioma.getProperty("panelPreferencias.tpanelOpciones"));
        
        // * Etiquetas
        this.etqTitulo.setText(MyFreeLab.idioma.getProperty("panelPreferencias.etqTitulo"));
        this.etqEstilo.setText(MyFreeLab.idioma.getProperty("panelPreferencias.etqEstilo"));
        this.etqFuente.setText(MyFreeLab.idioma.getProperty("panelPreferencias.etqFuente"));
        this.etqIdioma.setText(MyFreeLab.idioma.getProperty("panelPreferencias.etqIdioma"));
        //this.etqSimboloMoneda.setText(MyFreeLab.idioma.getProperty("panelPreferencias.etqSimboloMoneda"));
        
        // * Campos
        //this.cmpSimboloMoneda.setPlaceholder(MyFreeLab.idioma.getProperty("panelPreferencias.etqSimboloMoneda"));
        
        // * Botones
        this.btnAceptar.setTexto(MyFreeLab.idioma.getProperty("panelPreferencias.btnAceptar"));
        this.btnCancelar.setTexto(MyFreeLab.idioma.getProperty("panelPreferencias.btnCancelar"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bkgAside = new vista.componentes.jpanelbackground.JPanelBackground();
        jPanel1 = new javax.swing.JPanel();
        etqTitulo = new vista.componentes.etiqueta.Titulo();
        tpanelOpciones = new javax.swing.JTabbedPane();
        panelConfiguracion = new javax.swing.JPanel();
        etqIdioma = new vista.componentes.etiqueta.Etiqueta();
        cmboxIdiomas = new javax.swing.JComboBox<>();
        etqFuente = new vista.componentes.etiqueta.Etiqueta();
        cmboxFuentes = new javax.swing.JComboBox<>();
        etqEstilo = new vista.componentes.etiqueta.Etiqueta();
        cmboxEstilos = new javax.swing.JComboBox<>();
        btnAceptar = new vista.componentes.boton.Boton();
        btnCancelar = new vista.componentes.boton.Boton();

        bkgAside.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout bkgAsideLayout = new javax.swing.GroupLayout(bkgAside);
        bkgAside.setLayout(bkgAsideLayout);
        bkgAsideLayout.setHorizontalGroup(
            bkgAsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );
        bkgAsideLayout.setVerticalGroup(
            bkgAsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        etqTitulo.setForeground(new java.awt.Color(255, 255, 255));
        etqTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqTitulo.setText("PREFERENCIAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        );

        etqIdioma.setText("Selecciona una idioma");

        cmboxIdiomas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Español", "Inglés" }));
        cmboxIdiomas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxIdiomasActionPerformed(evt);
            }
        });

        etqFuente.setText("Selecciona tipo de fuentes");

        cmboxFuentes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barlow", "Inter", "K2D", "Lato", "Nunito" }));

        etqEstilo.setText("Selecciona un tipo de estilo (Botones)");

        cmboxEstilos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bootstrap", "Bulma", "Oval" }));

        javax.swing.GroupLayout panelConfiguracionLayout = new javax.swing.GroupLayout(panelConfiguracion);
        panelConfiguracion.setLayout(panelConfiguracionLayout);
        panelConfiguracionLayout.setHorizontalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(etqIdioma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmboxIdiomas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etqEstilo, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(cmboxEstilos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmboxFuentes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etqFuente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(340, Short.MAX_VALUE))
        );
        panelConfiguracionLayout.setVerticalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmboxIdiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(etqFuente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmboxFuentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(etqEstilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmboxEstilos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        tpanelOpciones.addTab("Configuración", panelConfiguracion);

        btnAceptar.setImgButtonType("success");
        btnAceptar.setTexto("Aceptar");

        btnCancelar.setImgButtonType("secondary");
        btnCancelar.setTexto("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bkgAside, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tpanelOpciones))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bkgAside, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tpanelOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmboxIdiomasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxIdiomasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmboxIdiomasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private vista.componentes.jpanelbackground.JPanelBackground bkgAside;
    public vista.componentes.boton.Boton btnAceptar;
    public vista.componentes.boton.Boton btnCancelar;
    public javax.swing.JComboBox<String> cmboxEstilos;
    public javax.swing.JComboBox<String> cmboxFuentes;
    public javax.swing.JComboBox<String> cmboxIdiomas;
    private vista.componentes.etiqueta.Etiqueta etqEstilo;
    private vista.componentes.etiqueta.Etiqueta etqFuente;
    private vista.componentes.etiqueta.Etiqueta etqIdioma;
    public vista.componentes.etiqueta.Titulo etqTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelConfiguracion;
    private javax.swing.JTabbedPane tpanelOpciones;
    // End of variables declaration//GEN-END:variables
}
