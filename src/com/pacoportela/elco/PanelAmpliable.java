
package com.pacoportela.elco;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/**
 * Esta clase hereda de JPanel para crear un panel que puede cambiar de tamaño
 * y ser movido de sitio (siempre que el componente donde se visualiza tenga
 * su layout declarado como mull).
 * 
 * @author Francisco Portela Henche (Febrero 2017)
 */
public class PanelAmpliable extends JPanel{
    private boolean estaInmovil = false;
    
    /**
     * Constructor de la clase. Llama al constructor de su clase padre, le añade
     * un borde ampliable, le ajusta el color de fondo a blanco y le añade un 
     * escuchador de eventos del mouse y otro escuchador de movimientos del 
     * mouse.
     */
    public PanelAmpliable(){
        super();
        this.resizeListener = new MouseInputAdapter(){
            /**
             * Metodo que se ejecuta cuando el mouse se ha movido.
             * Si el panel esta definido como inmovil salimos del metodo.
             * De lo contrario comprobamos si el panelAmpliable tiene el foco y si
             * lo tiene obtenemos su borde ampliable y ponemos el cursor adecuado
             * al lugar al que este señalando el mouse.
             * @param me el objeto que contiene la informacion sobre el evento.
             */
            @Override
            public void mouseMoved(MouseEvent me){
                PanelAmpliable p = (PanelAmpliable)me.getComponent();
                if(p.estaInmovil() == true) return;
                if(hasFocus()){
                    ResizableBorder border = (ResizableBorder)getBorder();
                    setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
                }
            }
            
            /**
             * Metodo que se ejecuta cuando el mouse sale de un componente.
             * Si el panelAmpliable esta inmovil salimos del metodo.
             * En caso contrario como el cursor salio de el (perdio el foco),
             * cambiamos el cursor al cursor por defecto.
             * @param me el objeto que contiene la informacion sobre el evento.
             */
            @Override
            public void mouseExited(MouseEvent me){
                PanelAmpliable p = (PanelAmpliable)me.getComponent();
                if(p.estaInmovil() == true) return;
                setCursor(Cursor.getDefaultCursor());
                repaint();
            }
            
            // definimos dos variable para usar en los proximos metodos.
            private int cursor;
            private Point startPos = null;
            
            /**
             * Metodo ejecutado cuando pulsamos un boton del mouse.
             * Si el panelAmpliable esta inmovil salimos del metodo.
             * De lo contrario obtenemos el borde del panel, obtenemos el cursor
             * asociado a dicho borde, y definimos la posicion donde se genero el
             * evento en la variable startPos, reclamamos el foco sobre el panel
             * y repintamos el panel.
             * @param me
             */
            @Override
            public void mousePressed(MouseEvent me){
                PanelAmpliable p = (PanelAmpliable)me.getComponent();
                if(p.estaInmovil() == true) return;
                ResizableBorder border = (ResizableBorder)getBorder();
                cursor = border.getCursor(me);
                startPos = me.getPoint();
                requestFocus();
            }
            
            /**
             * Metodo ejecutado cuando arrastramos el mouse con un boton pulsado.
             * Si el panel esta inmovil salimos del metodo.
             * Obtenemos la posicion inicial y la anchura y altura del panel.
             * Obtenemos la posicion inicial del evento.
             * Dependiendo del cursor que estaba seleccionado (que nos muestra la
             * direccion en la que queremos ampliar o disminuir el panel) ajustamos
             * los limites del panel y lo repintamos.
             * @param me el objeto que contiene la informacion sobre el evento.
             */
            @Override
            public void mouseDragged(MouseEvent me){
                PanelAmpliable p = (PanelAmpliable)me.getComponent();
                if(p.estaInmovil() == true) return;
                if(startPos != null){
                    int x = getX();
                    int y = getY();
                    int w = getWidth();
                    int h = getHeight();
                    
                    int dx = me.getX() - startPos.x;
                    int dy = me.getY() - startPos.y;
                    
                    switch(cursor){
                        case Cursor.N_RESIZE_CURSOR:
                            if(!(h-dy < 50)){
                                setBounds(x, y+dy ,w ,h-dy);
                                revalidate();
                            }
                            break;
                        case Cursor.S_RESIZE_CURSOR:
                            if(!(h+dy < 50)){
                                setBounds(x, y, w, h+dy);
                                startPos = me.getPoint();
                                revalidate();
                            }
                            break;
                        case Cursor.W_RESIZE_CURSOR:
                            if(!(w-dx < 50)){
                                setBounds(x+dx, y, w-dx, h);
                                revalidate();
                            }
                            break;
                        case Cursor.E_RESIZE_CURSOR:
                            if(!(w+dx < 50)){
                                setBounds(x, y, w+dx, h);
                                startPos = me.getPoint();
                                revalidate();
                            }
                            break;
                        case Cursor.NW_RESIZE_CURSOR:
                            if(!(w-dx < 50) && !(h-dy < 50)){
                                setBounds(x+dx, y+dy, w-dx, h-dy);
                                revalidate();
                            }
                            break;
                        case Cursor.NE_RESIZE_CURSOR:
                            if(!(w+dx < 50) && !(h-dy < 50)){
                                setBounds(x, y+dy, w+dx, h-dy);
                                startPos = new Point(me.getX(), startPos.y);
                                revalidate();
                            }
                            break;
                        case Cursor.SW_RESIZE_CURSOR:
                            if(!(w-dx < 50 && !(h+dy < 50))){
                                setBounds(x+dx, y, w-dx, h+dy);
                                startPos = new Point(startPos.x, me.getY());
                                revalidate();
                            }
                            break;
                        case Cursor.SE_RESIZE_CURSOR:
                            if(!(w+dx < 50) && !(h+dy < 50)){
                                setBounds(x, y, w+dx, h+dy);
                                startPos = me.getPoint();
                                revalidate();
                            }
                            break;
                        case Cursor.MOVE_CURSOR:
                            setLocation(x + me.getX() - w/2,
                                    y + me.getY() - h/2);
                            revalidate();
                            
                    }
                    setCursor(Cursor.getPredefinedCursor(cursor));
                }
            }
            
            /**
             * Metodo ejecutado cuando soltamos el boton del mouse.
             * Si el panel esta inmovil salimos del metodo.
             * Ponemos a null la posicion inicial.
             * @param me
             */
            @Override
            public void mouseReleased(MouseEvent me){
                PanelAmpliable p = (PanelAmpliable)me.getComponent();
                if(p.estaInmovil() == true) return;
                startPos = null;
            }
        };
        setBorder(new ResizableBorder(8));
        this.setBackground(Color.WHITE);
        addMouseListener(resizeListener);
        addMouseMotionListener(resizeListener);
    }
    
    /**
     * Metodo que pone a true la variable boolean estaInmovil.
     */
    public void inmovilizarPanel(){
        this.estaInmovil = true;
    }
    
    /**
     * Metodo que pone a false la variable boolean estaInmovil.
     */
    public void movilizarPanel(){
        this.estaInmovil = false;
    }
    
    /**
     * Metodo que devuelve la variable boolean estaInmovil.
     * @return la variable estaImovil.
     */
    public boolean estaInmovil(){
        return this.estaInmovil;
    }
    /**
     * Clase interna que sobreescribe la clase MouseInputAdapter. En ella 
     * definimos el comportamiento del panel dependiendo del evento del mouse
     * que se produzca.
     */
    MouseInputListener resizeListener;
}
