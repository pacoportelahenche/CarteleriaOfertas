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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Francisco Portela Henche
 * 15-mar-2017
 */
public class PanelContenedor extends JPanel{
    private ArrayList<Component> listaComponentes;
    
    public PanelContenedor(){
        super();
        init();
        
    }

    private void init(){
        listaComponentes = new ArrayList<>();
        setLayout(null);
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me){
                requestFocusInWindow();
                listaComponentes.forEach((res) -> {
                    res.repaint();
                });
            }
        });
    }
    
    @Override
    public Component add(Component comp){
        listaComponentes.add(comp);
        return super.add(comp);
    }
}
