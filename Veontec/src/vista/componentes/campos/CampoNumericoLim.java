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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import src.Recursos;

/**
 *
 * @author victo
 */
public class CampoNumericoLim extends JTextField implements FocusListener, KeyListener{
    
    // * Propiedadades
    public String Placeholder = "Establezca un placeholder";
    public Integer limiteNumerico = 0;
    public boolean verificarCampo = true;
    private final Border BorderMargin = BorderFactory.createEmptyBorder(0, 10, 0, 10);
    private final Color borderColor = new Color(192, 192, 192);
    private final Color backgroundColor = new Color(255, 255, 255);
    private final Color cursorColor = new Color(255, 255, 0);
    private final Color textoColor = new Color(64, 64, 64);
    private final Color placeholderColor = new Color(192, 192, 192);
    private final Dimension tamahno = new Dimension(280, 27);
    public JLabel componenteDidireccional;
    
    public CampoNumericoLim() {
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
    public void getEstiloTextEmpty(){
        setBackground( backgroundColor );
        setBorderMargin( Color.RED );
        setForeground( placeholderColor );
        setCaretColor( cursorColor );
    }
    
    public void getEstiloTextEscritura(){
        setBackground( backgroundColor );
        setBorderMargin( borderColor );
        setForeground( textoColor );
        setCaretColor( cursorColor );
    }
    
    public void getEstiloTextEstablecido(){
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
        String EstiloToolTip = "<html><b><font color=white>" + getPlaceholder() + " "+aqui + "</font></b></html>" ;
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
    
    public boolean isAprobado(){
        return !getText().equals( getPlaceholder() );          
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        if( verificarCampo ){
            getEstiloTextEscritura();
            if( getText().equals( getPlaceholder() ) ){
                setText(null);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if( verificarCampo ){
            if( getText().isEmpty() ){
                setText(getPlaceholder());
                getEstiloTextEmpty();
            }else{
                getEstiloAprobado();
            }
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
        
        // * Establecer maximo 10 digitos
        if ( this.getText().length() >= limiteNumerico ){
            evt.consume(); 
            JOptionPane.showMessageDialog(null, "Ingrese solo "+limiteNumerico+" dígitos.");
            return;
        }
        
        // * Aceptar solo numeros de 0 a 9
        if ( !(charCap >= '0' && charCap <= '9')) {
            
            if( Character.isLetter( charCap ) ){
                evt.consume();
                JOptionPane.showMessageDialog(null, "Ingrese solo números.");

            }else
            evt.consume();
            
        }

    }
    
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public boolean isVerificarCampo() {
        return verificarCampo;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getEstiloTextEscritura();
    }

    public void setVerificarCampo(boolean verificarCampo) {
        this.verificarCampo = verificarCampo;
    }

    public Integer getLimiteNumerico() {
        return limiteNumerico;
    }

    public void setLimiteNumerico(Integer limiteNumerico) {
        this.limiteNumerico = limiteNumerico;
    }
    
}
