package com.pacoportela.elco;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.TransferHandler;

/**
 * 
 * Esta clase hereda de la clase TransferHandler que es la clase que engloba
 * el proceso de cortar y pegar(cut/copy paste) y de arratrar y soltar
 * (drag and drop). Lo que hace es definir un nuevo comportamiento cuando
 * arratramos un item de la lista. Basicamente lo que hace es eliminar
 * dicho item de la lista.
 * @author Francisco Portela Henche (Febrero 2017)
 */
public class ListTransferHandler extends TransferHandler{
    /**
     * Constructor de la clase. Llama al contructor de su clase padre.
     */
    public ListTransferHandler(){
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
        // Si los datos son de tipo String aceptamos la importacion
        return supp.isDataFlavorSupported(DataFlavor.stringFlavor);
    }
    /**
     * Metodo que devuelve el tipo de accion que se emplea en la tranferencia.
     * @param c el componente origen de la operacion de transferencia.
     * @return un entero que indica el tipo de accion que realizamos.
     */
    @Override
     public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }
    
    /**
     * Metodo que se ejecuta cuando se ha realizado con exito la operacion de
     * transferencia de los datos de un componente al otro.
     * Aqui es donde eliminamos el item movido de la lista.
     * @param c el componente origen de la operacion de transferencia.
     * @param data el objeto que contiene los datos de la transferencia.
     * @param action el tipo de accion usado en la transferencia.
     */
    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
        JList lista = (JList)c;
        ListModel listModel = lista.getModel();
        
        int indice = lista.getSelectedIndex();
        DefaultListModel dlm = new DefaultListModel();
        for(int i = 0; i < listModel.getSize(); i++){
            dlm.addElement(listModel.getElementAt(i));
        }
        dlm.remove(indice);
        lista.setModel(dlm);
    }
    /**
     * En este metodo se crea el objeto Transferable que contiene la informacion
     * que se va a mover de un componente al otro. En este caso es el String
     * que contiene el item seleccionado en la lista.
     * @param c el componente origen de la operacion de transferencia.
     * @return el objeto Transferable creado.
     */
    @Override
    protected Transferable createTransferable(JComponent c) {
        JList list = (JList)c;
        Object value = list.getSelectedValue();
        return new StringSelection(value.toString());
    }
}
