
package com.pacoportela.elco;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 * Esta clase hereda de JTextField y define un posicion para dicho campo de
 * texto.
 *
 * @author Francisco Portela Henche (Febrero 2017)
 */
public class TextFieldConPosicion extends JTextField
        implements MouseWheelListener, FocusListener, MouseListener {

    private int posicion;
    public final static int ARRIBA = 0;
    public final static int MEDIO = 1;
    public final static int ABAJO = 2;

    /**
     * Constructor de la clase. Añadimos un eschuchador de eventos de la rueda
     * del mouse, uno para controlar cuando el campo gana el foco y otra para
     * controlar las pulsaciones de los botones del mouse. Ademas le colocamos
     * un TransferHandler especifico para controlar lo que ocurre cuando
     * arrastramos texto sobre el.
     *
     * @param pos posicion del campo de texto.
     * @param texto texto del campo de texto.
     */
    public TextFieldConPosicion(int pos, String texto) {
        super(texto);
        this.posicion = pos;
        inicializar();
    }
    
    /**
     * Este método inicializa ciertas propiedades del TextFieldConPosicion.
     * Le añade escuchadores para el mouse y le pone un nuevo transferHandler
     * para cuando se realizen sobre el operaciones de arrastrar y soltar.
     */
    private void inicializar(){
        addMouseWheelListener(this);
        addMouseListener(this);
        addFocusListener(this);
        setDragEnabled(true);
        setTransferHandler(new TextFieldTransferHandler());
    }

    /**
     * Metodo que devuelve la posicion de este campo de texto.
     *
     * @return un entero que indica la posicion.
     */
    public int getPosicion() {
        return this.posicion;
    }

    /**
     * Metodo que ajusta la posicion de este campo de texto.
     *
     * @param pos la posicion que deseamos para este campo.
     */
    public void setPosicion(int pos) {
        this.posicion = pos;
    }

    /**
     * Metodo que se ejecuta cuando se mueve la rueda del mouse. Controlamos en
     * que direccion se mueve. Si es hacia arriba reducimos el tamaño de la
     * fuente del texto contenido en campo de texto y si es hacia abajo la
     * aumentamos.
     *
     * @param mwe el objeto que contiene la informacion sobre el evento generado
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int rotacion = mwe.getWheelRotation();
        if (rotacion < 0) {
            this.setFont(new Font("Arial", Font.PLAIN, this.getFont().getSize() - 1));
            this.repaint();
        } else {
            this.setFont(new Font("Arial", Font.PLAIN, this.getFont().getSize() + 1));
            this.repaint();
        }
    }

    /**
     * Metodo que se ejecuta cuando el campo de texto gana el foco.
     * Seleccionamos todo el texto contenido en el campo de texto.
     *
     * @param fe el objeto que contiene la informacion sobre el evento generado
     */
    @Override
    public void focusGained(FocusEvent fe) {
        this.selectAll();
    }

    @Override
    public void focusLost(FocusEvent fe) {

    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    /**
     * Metodo ejecutado cuando soltamos el boton del mouse. Comprobamos si se
     * solto el boton asociado a la creacion de un menu contextual (en Windows
     * el boton derecho del raton) y si es asi creamos un JPopupMenu quen
     * contiene dos menu item. El primero 'Borrar' nos va a permitir borrar el
     * texto contenido en este PanelOferta. El segundo 'Eliminar' nos va a
     * permitir eliminar por completo el PanelOferta.
     *
     * @param me el objeto que contiene la informacion sobre el evento generado
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.isPopupTrigger()) {
            /* obtenemos el panel que contiene al panel de oferta. Primero
            obtenemos el componente en el que se genera el evento (un 
            TextFieldConPosicion),luego su padre (un PanelOferta) y por último
            el padre de este (un JPanel).
             */
            JPanel panelPadre
                    = (JPanel) me.getComponent().getParent().getParent();
            JPopupMenu menu = new JPopupMenu();
            ImageIcon iconoPapelera = getImagen("Recursos/papelera.png");
            ImageIcon iconoEliminar = getImagen("Recursos/eliminar.png");
            //creamos menu item para borrar el texto de un panel con un nombre
            // y una imagen
            JMenuItem borrarUno = new JMenuItem("Borrar uno", iconoPapelera);
            borrarUno.addActionListener((ActionEvent ae) -> {
                borrarTextoUno(me);
            });
            // menu item para eliminar el texto de todos los paneles
            JMenuItem borrarTodos = new JMenuItem
                ("Borrar todos", iconoPapelera);
            borrarTodos.addActionListener((ActionEvent ae)->{
                borrarTextoTodos(panelPadre);
            });
            // menu item para eliminar el panel
            JMenuItem eliminar = new JMenuItem("Eliminar panel", iconoEliminar);
            eliminar.addActionListener((ActionEvent ae) -> {
                eliminarPanel(me);
            });
            // añadimos los menu items al menu contextual
            menu.add(borrarUno);
            menu.add(borrarTodos);
            /* Si el panel padre es el panel en blanco permitimos borrar el
            PanelOferta; añadimos en el popup menu el item eliminar.
             */
            if (panelPadre.getName().equals("panelBlanco")) {
                menu.add(eliminar);
            }
            // mostramos el menu contextual en el componente que lo origino.
            menu.show(me.getComponent(), me.getX(), me.getY());
        }
    }

    /**
     * Metodo que borra el texto contenido dentro de un PanelOferta.
     *
     * @param me el objeto que contiene la informacion sobre el evento generado
     */
    private void borrarTextoUno(MouseEvent me) {
        TextFieldConPosicion tf = (TextFieldConPosicion) me.getSource();
        PanelOferta p = (PanelOferta) tf.getParent();
        p.borrarCamposTexto();
    }
    
    /**
     * Metodo que borra el texto de todos 
     * @param me el objeto que contiene la informacion sobre el evento generado
     */
    private void borrarTextoTodos(JPanel panelPadre){
        Component[] comps = panelPadre.getComponents();
        for(Component c: comps){
            PanelOferta p = (PanelOferta)c;
            p.borrarCamposTexto();
        }
    }

    /**
     * Metodo que elimina un PanelOferta de su panel contenedor.
     *
     * @param me el objeto que contiene la informacion sobre el evento generado
     */
    private void eliminarPanel(MouseEvent me) {
        TextFieldConPosicion tf = (TextFieldConPosicion) me.getSource();
        PanelOferta p = (PanelOferta) tf.getParent();
        JPanel panelPadre = (JPanel) p.getParent();
        panelPadre.remove(p);
        panelPadre.repaint();

    }

    /**
     * Metodo que devuelve un objeto ImageIcon.
     *
     * @param rutaImagen la ruta a la imagen (recurso grafico).
     * @return un objeto ImageIcon que contiene la imagen.
     */
    private ImageIcon getImagen(String rutaImagen) {
        ImageIcon imagen = null;
        URL url = this.getClass().getResource(rutaImagen);
        if (url != null) {
            imagen = new ImageIcon(url);
            imagen.setImage(imagen.getImage()
                    .getScaledInstance(50, -1, Image.SCALE_DEFAULT));
        }
        return imagen;
    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }
}
