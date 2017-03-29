/*
 * Copyright (C) 2017 Francisco Portela Henche
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

package com.pacoportela.elco;

/**
 * Esta clase permite la creación de un componente JSwing redimensionable.
 * @author Desconocido
 * 15-mar-2017
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


public class Resizable extends JComponent {

    /**
     * Constructor de la clase.
     * @param comp el componente que podrá redimensionarse.
     */
    public Resizable(Component comp) {
        this(comp, new ResizableBorder(8));
    }

    /**
     * Constructor con dos parámetros.
     * @param comp el componente que podrá redimensionarse.
     * @param border el borde que permitirá el redimensionado.
     */
    public Resizable(Component comp, ResizableBorder border) {
        setLayout(new BorderLayout());
        add(comp);
        addMouseListener(resizeListener);
        addMouseMotionListener(resizeListener);
        setBorder(border);
    }

    /**
     * Este método será invocado cada vez que el componente se redimensione.
     * La llamada a revalidate() hará que el componente sea redibujado.
     */
    private void resize() {
        if (getParent() != null) {
            ((JComponent) getParent()).revalidate();
        }
    }

    /**
     * Clase interna anónima. Es un mouseListener que definirá lo que ocurrirá
     * cuando movamos ó arrastremos el mouse dentro del componente 
     * redimensionable.
     */
    MouseInputListener resizeListener = new MouseInputAdapter() {
        
        @Override
        public void mouseMoved(MouseEvent me) {
            if (hasFocus()) {
                ResizableBorder border = (ResizableBorder) getBorder();
                setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            setCursor(Cursor.getDefaultCursor());
        }

        private int cursor;
        private Point startPos = null;

        @Override
        public void mousePressed(MouseEvent me) {
            ResizableBorder border = (ResizableBorder) getBorder();
            cursor = border.getCursor(me);
            startPos = me.getPoint();
            requestFocus();
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (startPos != null) {
                int x = getX();
                int y = getY();
                int w = getWidth();
                int h = getHeight();

                int dx = me.getX() - startPos.x;
                int dy = me.getY() - startPos.y;

                switch (cursor) {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < 50)) {
                            setBounds(x, y + dy, w, h - dy);
                            resize();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < 50)) {
                            setBounds(x, y, w, h + dy);
                            startPos = me.getPoint();
                            resize();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < 50)) {
                            setBounds(x + dx, y, w - dx, h);
                            resize();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < 50)) {
                            setBounds(x, y, w + dx, h);
                            startPos = me.getPoint();
                            resize();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < 50) && !(h - dy < 50)) {
                            setBounds(x + dx, y + dy, w - dx, h - dy);
                            resize();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 50) && !(h - dy < 50)) {
                            setBounds(x, y + dy, w + dx, h - dy);
                            startPos = new Point(me.getX(), startPos.y);
                            resize();
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 50) && !(h + dy < 50)) {
                            setBounds(x + dx, y, w - dx, h + dy);
                            startPos = new Point(startPos.x, me.getY());
                            resize();
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < 50) && !(h + dy < 50)) {
                            setBounds(x, y, w + dx, h + dy);
                            startPos = me.getPoint();
                            resize();
                        }
                        break;

                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = getBounds();
                        bounds.translate(dx, dy);
                        setBounds(bounds);
                        resize();
                }

                setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            startPos = null;
        }
    };
}
