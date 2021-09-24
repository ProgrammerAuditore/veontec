/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes.campos;

import index.Veontec;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import src.Recursos;

/**
 *
 * @author victo
 */
public class CampoCorreo extends JTextField implements FocusListener, KeyListener{
    
    
    // * Propiedadades
    public String Placeholder = "Establezca un placeholder";
    private final Border BorderMargin = BorderFactory.createEmptyBorder(0, 10, 0, 10);
    private final Color borderColor = new Color(192, 192, 192);
    private final Color backgroundColor = new Color(255, 255, 255);
    private final Color cursorColor = new Color(255, 255, 0);
    private final Color textoColor = new Color(64, 64, 64);
    private final Color placeholderColor = new Color(192, 192, 192);
    private final Dimension tamahno = new Dimension(280, 27);
    public JLabel componenteDidireccional;
    
    public CampoCorreo() {
        super(14);
        this.iniciar_propiedades();
    }
    
    private void iniciar_propiedades() {
        
        // * Personalizar los colores al seleccionar el texto
        setSelectionColor(Color.LIGHT_GRAY);
        setSelectedTextColor(Color.WHITE);
        
        // Establecer texto de ayuda
        setToolTip();
        
        // Fuentes
        setFont(Recursos.fontTextField );
        //setHorizontalAlignment(javax.swing.JTextField.CENTER);
        //setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 204, 255), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), null));
        setBorderMargin( borderColor );
        setSize(tamahno);
        setPreferredSize(tamahno);
        getEstiloTextEstablecido();
        addFocusListener(this);
        addKeyListener(this);

        //System.out.println("Texto = " + getText());
        //System.out.println("Placeholder = " + getPlaceholder());
        
    }
    
    // Métodos custom
    private void getEstiloTextEmpty(){
        setBackground( backgroundColor );
        setBorderMargin( Color.RED );
        setForeground( placeholderColor );
        setCaretColor( cursorColor );
    }
    
    private void getEstiloTextEscritura(){
        setBackground( backgroundColor );
        setBorderMargin( borderColor );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    private void getEstiloTextEstablecido(){
        setBackground( backgroundColor );
        setBorderMargin( borderColor );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    public void getEstiloAprobado(){
        setBackground( backgroundColor );
        setBorderMargin( Color.GREEN );
        setForeground( placeholderColor );
        setCaretColor( cursorColor );
    }
    
    public void getEstiloNoAprobado(){
        setBackground( backgroundColor );
        setBorderMargin( Color.RED );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    private void setBorderMargin(Color c){
        setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder( c ), BorderMargin ) );
    }
    
    private void setToolTip(){
        String aqui= Veontec.idioma.getProperty("componente.setToolTip.aqui");
        String EstiloToolTip = "<html><b><font color=white>" + getPlaceholder() + " " + aqui + "</font></b></html>" ;
        setToolTipText( EstiloToolTip );
    }
    
    public void aceptarCampo(){
        getEstiloTextEstablecido();
    }
    
    public void rechazarCampo(){
        getEstiloTextEmpty();
        setText(getPlaceholder());
    }
       
    // Setters y Getters
    public String getPlaceholder() {
        return Placeholder;
    }

    public void setPlaceholder(String Placeholder) {
        this.Placeholder = Placeholder;
        setText(Placeholder);
        setToolTip();
    }
    
    private boolean comprobarCorreo(){
        String campo = getText();
        String nombre = null;
        String dominio = null;
        String extension = null;

        /* // Corregir bug
        if( campo.contains("@") && campo.contains(".") ){
            nombre = getText().substring(0, getText().indexOf("@"));
            dominio = getText().substring(getText().indexOf("@") + 1, getText().indexOf("."));
            extension = getText().substring(getText().indexOf(".") + 1, campo.length());
            
            if( nombre.length() > 3 && dominio.length() > 3 && extension.length() > 2 ){
                //System.out.println("campo : " + campo);
                //System.out.println("nombre : " + nombre);
                //System.out.println("dominio : " + dominio);
                //System.out.println("extension : " + extension);
                return true;
            }
        }
        */
        
        return true;
    }
    
    public boolean isAprobado(){
        return !getText().equals( getPlaceholder() ) && comprobarCorreo();          
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        getEstiloTextEscritura();
        if( getText().equals( getPlaceholder() ) ){
            setText(null);
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        if( getText().isEmpty() ){
            setText(getPlaceholder());
            getEstiloTextEmpty();
        }else{
            if( comprobarCorreo() )
                getEstiloAprobado();
            else
                getEstiloNoAprobado();
        }
    }

    public JLabel getComponenteDidireccional() {
        return componenteDidireccional;
    }

    public void setComponenteDidireccional(JLabel componenteDidireccional) {
        this.componenteDidireccional = componenteDidireccional;
        setPlaceholder(componenteDidireccional.getText());
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        char charCap = evt.getKeyChar();
        
        if (charCap == '@') {
            if (getText().contains("@")) {
                evt.consume();
            }
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
}