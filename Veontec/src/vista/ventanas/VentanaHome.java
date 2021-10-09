/*
 * Copyright (C) 2021 victor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package vista.ventanas;

/**
 *
 * @author victor
 */
public class VentanaHome extends javax.swing.JFrame {

    /**
     * Creates new form VentanaHome
     */
    public VentanaHome() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnTabMenu = new javax.swing.JTabbedPane();
        pnNose = new vista.componentes.jpanelbackground.JPanelBackground();
        panelHome = new vista.paneles.PanelBienvenida();
        jPanelBackground6 = new vista.componentes.jpanelbackground.JPanelBackground();
        pnMiTienda = new vista.paneles.PanelMiTienda();
        jPanelBackground2 = new vista.componentes.jpanelbackground.JPanelBackground();
        pnCompras = new vista.paneles.PanelCompras();
        jPanelBackground3 = new vista.componentes.jpanelbackground.JPanelBackground();
        pnVentas = new vista.paneles.PanelVentas();
        jPanelBackground5 = new vista.componentes.jpanelbackground.JPanelBackground();
        panelPreguntas = new vista.paneles.PanelPreguntas();
        JPanelBackground4 = new vista.componentes.jpanelbackground.JPanelBackground();
        pnMiCuenta = new vista.paneles.PanelMiCuenta();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnTabMenu.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        javax.swing.GroupLayout pnNoseLayout = new javax.swing.GroupLayout(pnNose);
        pnNose.setLayout(pnNoseLayout);
        pnNoseLayout.setHorizontalGroup(
            pnNoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnNoseLayout.setVerticalGroup(
            pnNoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("Inicio", pnNose);

        javax.swing.GroupLayout jPanelBackground6Layout = new javax.swing.GroupLayout(jPanelBackground6);
        jPanelBackground6.setLayout(jPanelBackground6Layout);
        jPanelBackground6Layout.setHorizontalGroup(
            jPanelBackground6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMiTienda, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );
        jPanelBackground6Layout.setVerticalGroup(
            jPanelBackground6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMiTienda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("Mi tienda", jPanelBackground6);

        javax.swing.GroupLayout jPanelBackground2Layout = new javax.swing.GroupLayout(jPanelBackground2);
        jPanelBackground2.setLayout(jPanelBackground2Layout);
        jPanelBackground2Layout.setHorizontalGroup(
            jPanelBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );
        jPanelBackground2Layout.setVerticalGroup(
            jPanelBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("Compras", jPanelBackground2);

        javax.swing.GroupLayout jPanelBackground3Layout = new javax.swing.GroupLayout(jPanelBackground3);
        jPanelBackground3.setLayout(jPanelBackground3Layout);
        jPanelBackground3Layout.setHorizontalGroup(
            jPanelBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );
        jPanelBackground3Layout.setVerticalGroup(
            jPanelBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("Ventas", jPanelBackground3);

        javax.swing.GroupLayout jPanelBackground5Layout = new javax.swing.GroupLayout(jPanelBackground5);
        jPanelBackground5.setLayout(jPanelBackground5Layout);
        jPanelBackground5Layout.setHorizontalGroup(
            jPanelBackground5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPreguntas, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );
        jPanelBackground5Layout.setVerticalGroup(
            jPanelBackground5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPreguntas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("Preguntas", jPanelBackground5);

        javax.swing.GroupLayout JPanelBackground4Layout = new javax.swing.GroupLayout(JPanelBackground4);
        JPanelBackground4.setLayout(JPanelBackground4Layout);
        JPanelBackground4Layout.setHorizontalGroup(
            JPanelBackground4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMiCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );
        JPanelBackground4Layout.setVerticalGroup(
            JPanelBackground4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMiCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("Mi cuenta", JPanelBackground4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );

        pnTabMenu.addTab("tab7", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnTabMenu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnTabMenu, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public vista.componentes.jpanelbackground.JPanelBackground JPanelBackground4;
    private javax.swing.JPanel jPanel1;
    private vista.componentes.jpanelbackground.JPanelBackground jPanelBackground2;
    private vista.componentes.jpanelbackground.JPanelBackground jPanelBackground3;
    private vista.componentes.jpanelbackground.JPanelBackground jPanelBackground5;
    private vista.componentes.jpanelbackground.JPanelBackground jPanelBackground6;
    public vista.paneles.PanelBienvenida panelHome;
    public vista.paneles.PanelPreguntas panelPreguntas;
    public vista.paneles.PanelCompras pnCompras;
    public vista.paneles.PanelMiCuenta pnMiCuenta;
    public vista.paneles.PanelMiTienda pnMiTienda;
    public vista.componentes.jpanelbackground.JPanelBackground pnNose;
    public javax.swing.JTabbedPane pnTabMenu;
    public vista.paneles.PanelVentas pnVentas;
    // End of variables declaration//GEN-END:variables
}
