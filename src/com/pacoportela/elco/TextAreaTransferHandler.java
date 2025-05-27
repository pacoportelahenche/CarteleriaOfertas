package com.pacoportela.elco;

import static java.awt.datatransfer.DataFlavor.stringFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

/**
 *
 * Esta clase hereda de la clase TransferHandler que es la clase que engloba el
 * proceso de cortar y pegar(cut/copy paste) y de arratrar y soltar (drag and
 * drop). Lo que hace es definir un nuevo comportamiento cuando arratramos un
 * item de la lista de ofertas a un area de texto.
 *
 * @author Francisco Portela Henche (Febrero 2017)
 */
public class TextAreaTransferHandler extends TransferHandler {

    /**
     * Constructor de la clase. Llama al constructor de su clase padre.
     */
    public TextAreaTransferHandler() {
        super();
    }

    /**
     * Metodo que define que tipo de datos puede recibir este componente en la
     * operacion de drag and drop.
     *
     * @param supp el objeto que contiene la informacion y los datos de la
     * operacion de transferencia en curso.
     * @return true o false dependiendo de si la operacion es valida o no.
     */
    @Override
    public boolean canImport(TransferSupport supp) {
        // Comprueba que el tipo de dato sea String.
        return supp.isDataFlavorSupported(stringFlavor);
    }

    /**
     * Metodo que se ejecuta cuando la text area recibe los datos de la
     * operacion de arratrar y soltar. En este caso obtenemos el numero de
     * lineas que contiene el area de texto y numeramos el texto que añadimos
     * dependiendo de ese numero.
     *
     * @param supp el objeto que contiene informacion sobre la operacion drag
     * and drop.
     * @return un boolean que nos indica si la operacion tuvo exito.
     */
    @Override
    public boolean importData(TransferSupport supp) {
        if (!canImport(supp)) {
            return false;
        }

        // Fetch the Transferable and its data
        Transferable t = supp.getTransferable();
        String datos = null;
        try {
            datos = (String) t.getTransferData(stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(TextFieldTransferHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        String[]palabras = separarPalabras(datos);
        String texto = reorganizarTexto(palabras);
        JTextArea ta = (JTextArea) supp.getComponent();
        int lineas = ta.getLineCount();
        texto = Integer.toString(lineas) + "-" + texto + "\n" ;
        ta.append(texto);
        return true;
    }

    /**
     * Este metodo corta el string que contiene los datos del articulo y los
     * almacena en una matriz de Strings. Corta por los espacios en blanco entre
     * las palabras.
     *
     * @param d el String que contiene los datos del articulo.
     * @return una matriz de Strings con cada palabra del articulo.
     */
    private String[] separarPalabras(String d) {
        String[] datos = d.split(" ");
        return datos;
    }

    
    /**
     * Este metodo reorganiza las palabras de un texto para poner primero la
     * descripcion del producto, luego el nombre (separado con arrobas para
     * distinguirlo del resto) y finalmente el precio.
     * @param datos los datos que vamos a reorganizar.
     * @return un String con los datos reorganizados.
     */
    private String reorganizarTexto(String[]datos){
        String nombre = "";
        String descripcion = "";
        String precio = "";
        /* recorremos la matriz hasta la penultima posicion porque la ultima
        posicion es el simbolo del euro y no lo imprime bien la text area.
        En la segunda posicion (i = 1) esta el nombre del producto.
        En la posicion antepenultima (datos.length-2) esta el precio.
        */
        for(int i = 0; i < datos.length; i++){
            if(i == 1){
                //nombre = "@ " + datos[i] + " @ ";
                nombre = datos[i] + " ";
            }
            else if(i == datos.length - 2 ||i == datos.length - 1){
                precio = precio + datos[i] + " ";
            }
            // le añadimos los espacios que faltan a la descripcion.
            else{
                descripcion += datos[i] + " ";
            }
        }
        descripcion = descripcion.toLowerCase();
        char[] caracteres = descripcion.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);
        descripcion = new String(caracteres);
        return descripcion + nombre + precio;
    }
}
