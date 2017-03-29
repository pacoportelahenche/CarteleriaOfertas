
package com.pacoportela.elco;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * 
 * Esta clase hereda de la clase PanelAmpliable para crear un panel especial
 * para la clase CarteleriaOfertas. Este panel tiene tres miembros 
 * TextFieldConPosicion que contendran los datos del articulo que pongamos en
 * dicho panel.
 * @author Francisco Portela Henche (Febrero 2017)
 */
public class PanelOferta extends PanelAmpliable{
    private final TextFieldConPosicion textoArriba;
    private final TextFieldConPosicion textoMedio;
    private final TextFieldConPosicion textoAbajo;
    private int anchoPanel;
    private int altoPanel;
    
    /**
     * Constructor de la clase. Ajusta el layout a BoxLayout, inicializa los
     * miembros TextFieldConPosicion y los añade al panel.
     */
    public PanelOferta(){
        super();
        setLayout(new GridLayout(3,1));
        
        textoArriba = 
             new TextFieldConPosicion(TextFieldConPosicion.ARRIBA, "");
        textoArriba.setHorizontalAlignment(JTextField.CENTER);
        
        textoMedio = 
              new TextFieldConPosicion(TextFieldConPosicion.MEDIO, "");
        textoMedio.setHorizontalAlignment(JTextField.CENTER);
        
        textoAbajo = 
              new TextFieldConPosicion(TextFieldConPosicion.ABAJO, "");
        textoAbajo.setHorizontalAlignment(JTextField.CENTER);
        
        textoArriba.setFont(new Font("Arial", Font.PLAIN, 9));
        textoMedio.setFont(new Font("Arial", Font.PLAIN, 16));
        textoAbajo.setFont(new Font("Arial", Font.PLAIN, 16));
        
        add(textoArriba);
        add(textoMedio);
        add(textoAbajo);
    }
    
    /**
     * Este metodo nos devuelve el ancho del panel.
     * @return un entero indicando el ancho del panel.
     */
    public int getAncho(){
        return this.anchoPanel;
    }
    
    /**
     * Este metodo nos devuelve el alto del panel.
     * @return un entero indicando el alto del panel.
     */
    public int getAlto(){
        return this.altoPanel;
    }
    
    /**
     * Este metodo nos permite ajustar el ancho del panel.
     * @param ancho el ancho que deseamos darle al panel.
     */
    public void setAncho(int ancho){
        this.anchoPanel = ancho;
    }
    /**
     * Este metodo nos permite ajustar el alto del panel.
     * @param alto el alto que deseamos darle al panel.
     */
    public void setAlto(int alto){
        this.altoPanel = alto;
    }
    /**
     * Este metodo devuelve el TextFieldConPosicion que le indicamos como 
     * parametro.
     * @param posicion la posicion del campo de texto que queremos recuperar.
     * @return el TextFieldConPosicion que ocupa dicha posicion.
     */
    public TextFieldConPosicion getCampoDeTexto(int posicion){
        TextFieldConPosicion tf = null;
        switch(posicion){
            case(TextFieldConPosicion.ARRIBA):
                tf = this.textoArriba;
                break;
            case(TextFieldConPosicion.MEDIO):
                tf = this.textoMedio;
                break;
            case(TextFieldConPosicion.ABAJO):
                tf = this.textoAbajo;
                break;
        }
        return tf;
    }
    
    /**
     * Este metodo devuelve todos los TextFieldConPosicion que contiene este
     * panelOferta.
     * @return un Vector con todos los TextFieldConPosicion de este panel.
     */
    public ArrayList<TextFieldConPosicion> getTodosLosCamposDeTexto(){
        ArrayList<TextFieldConPosicion> v = new ArrayList<>();
        v.add(this.textoArriba);
        v.add(this.textoMedio);
        v.add(this.textoAbajo);
        return v;
    }
    
    /**
     * Este metodo devuelve el texto que contiene el TextFieldConPosicion que 
     * indicamos como parametro.
     * @param posicion la posicion del campo del que queremos obtener el texto.
     * @return un String que contiene el texto del campo seleccionado.
     */
    public String getTexto(int posicion){
        return this.getCampoDeTexto(posicion).getText();
    }
    
    /**
     * Este método inserta el texto pasado como parámetro en el campo de texto
     * indicado en el otro parámetro.
     * @param posicion la posición del campo de texto
     * @param texto el texto que mostrará el campo de texto.
     */
    public void setTexto(int posicion, String texto){
        this.getCampoDeTexto(posicion).setText(texto);
    }
    

    /**
     * Este metodo borra los texto contenidos en los TextFieldConPosicion de
     * este panelOferta.
     */
    public void borrarCamposTexto(){
        this.textoAbajo.setText("");
        this.textoArriba.setText("");
        this.textoMedio.setText("");
    }
}
