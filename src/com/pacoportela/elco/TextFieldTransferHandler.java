
package com.pacoportela.elco;

import static java.awt.datatransfer.DataFlavor.stringFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.TransferHandler;

/**
 * 
 * Esta clase hereda de la clase TransferHandler que es la clase que engloba
 * el proceso de cortar y pegar(cut/copy paste) y de arratrar y soltar
 * (drag and drop). Lo que hace es definir un nuevo comportamiento cuando
 * arratramos un item de la lista de articulos de oferta a uno de los campos de
 * texto que tiene un panelOferta.
 * @author Francisco Portela Henche (Febrero 2017)
 */
public class TextFieldTransferHandler extends TransferHandler {
    
    /**
     * Constructor de la clase. Invoca al constructor de su clase padre.
     */
    public TextFieldTransferHandler(){
        super();
    }

    /**
     * Metodo que define que tipo de datos puede recibir este componente en
     * la operacion de drag and drop.
     * @param supp el objeto que contiene la informacion y los datos de la
     * operacion de transferencia en curso.
     * @return true o false dependiendo de si la operacion es valida o no.
     */
    @Override
    public boolean canImport(TransferSupport supp) {
        // Comprueba que el tipo de datos sean String.
        return supp.isDataFlavorSupported(stringFlavor);
    }

    /**
     * Metodo que se ejecuta cuando la text area recibe los datos de la
     * operacion de arratrar y soltar.
     * Comprobamos que TextFieldConPosicion recibe la transferencia y partimos
     * es String en distintas partes que pondremos en los distintos
     * TextFieldConPosicion (arriba la descripcion del articulo, en el medio
     * la marca del articulo y abajo el precio del articulo).
     * @param supp el objeto que contiene informacion sobre la operacion
     * drag and drop.
     * @return un boolean que nos indica si la operacion tuvo exito.
     */
    @Override
    public boolean importData(TransferSupport supp) {
        if (!canImport(supp)) {
            return false;
        }

        // Fetch the Transferable and its data
        Transferable t = supp.getTransferable();
        String data = null;
        try {
            data = (String) t.getTransferData(stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(TextFieldTransferHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        TextFieldConPosicion tfRecibe = 
                (TextFieldConPosicion) supp.getComponent();
        PanelOferta p = (PanelOferta)tfRecibe.getParent();
        ArrayList<TextFieldConPosicion> v = p.getTodosLosCamposDeTexto();
       
        String[]datos = separarDatos(data);
        insertarDatosEnCamposDeTexto(v, datos);
        return true;
    }
    
    /**
     * Este metodo corta el string que contiene los datos del articulo y los
     * almacena en una matriz de Strings. Corta por los espacios en blanco 
     * entre las palabras.
     * @param d el String que contiene los datos del articulo.
     * @return una matriz de Strings con cada palabra del articulo.
     */
    private String[] separarDatos(String d){
        String[] datos = d.split(" ");
        return datos;
    }
    
    /**
     * Este metodo inserta el dato adecuado en cada campo de texto.
     * Recorre la matriz de Strings y en el caso de que la posicion sea 1
     * (por ejemplo: vino beronia crianza 9.00 €) se trata de la marca del 
     * articulo (recordemos que el indice empieza en cero que en este caso 
     * seria la palabra vino) y lo colocamos en el campo del medio.
     * Si la posicion es la longitud de la matriz menos 2 se trata del precio 
     * del articulo (quitamos la ultima letra que es la letra €) y lo insertamos
     * en el campo de abajo.
     * En el resto de los casos se trata de la descripcion del articulo, a la
     * que le vamos añadiendo espacios entre las palabras otra vez para luego
     * insertarlo en el campo de arriba.
     * @param v el Vector que contiene todos los campos de texto.
     * @param d la matriz de Strings que tiene todas las palabras del articulo.
     */
   private void insertarDatosEnCamposDeTexto
        (ArrayList<TextFieldConPosicion> v, String[] d){
       String descripcion = "";
       for(int i = 0; i < d.length-1; i++){
           if(i == 1){
               v.get(TextFieldConPosicion.MEDIO).setText(d[i]);
           }
           else if(i == d.length-2){
               v.get(TextFieldConPosicion.ABAJO).setText(d[i]);
           }
           else{
               descripcion += d[i] + " ";
           }
       }
       v.get(TextFieldConPosicion.ARRIBA).setText(descripcion);
   }
}
