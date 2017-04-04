package com.pacoportela.elco;

// Importaciones necesarias para el correcto funcionamiento del programa.
import com.pacoportela.utilidades.IO.IOFichero;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Esta clase nos muestra la interfaz gráfica que el usuario va a ver y le va a
 * permitir preparar la carteleria de productos para una oferta. La interfaz
 * nos muestra una serie de paneles, organizados mediante un TabbedPane, que nos
 * muestran una serie de diseños para que el usuario pueda insertar la
 * informacion de cada producto que quiera incluir en la oferta.
 *
 * @author Francisco Portela Henche - Febrero 2017
 */
public class CarteleriaOfertas extends javax.swing.JFrame
        implements Printable {

    /**
     * Construtor de la clase. Nos permite crear un objeto CarteleriaOfertas.
     */
    public CarteleriaOfertas() {
        initComponents();
        initMisComponentes();
        cargarAreasTexto();
        cargarPanelesOferta();
    }

    /**
     * Este metodo carga desde el disco duro los ficheros que contienen los
     * datos que se visualizaran en las areas de texto de los paneles Listas.
     */
    private void cargarAreasTexto() {
        File ficheroAlimentacion1 = new File("datos/alimentacion1.txt");
        File ficheroBebidas1 = new File("datos/bebidas1.txt");
        File ficheroDrogueria1 = new File("datos/drogueria1.txt");
        File ficheroAlimentacion2 = new File("datos/alimentacion2.txt");
        File ficheroBebidas2 = new File("datos/bebidas2.txt");
        File ficheroDrogueria2 = new File("datos/drogueria2.txt");
        String alimentacion1;
        alimentacion1 = IOFichero.ficheroAString
                    (ficheroAlimentacion1, Charset.forName("UTF-16"));
        String bebidas1 = IOFichero.ficheroAString
            (ficheroBebidas1, Charset.forName("UTF-16"));
        String drogueria1;
        drogueria1 = IOFichero.ficheroAString
                    (ficheroDrogueria1, Charset.forName("UTF-16"));
        String alimentacion2;
        alimentacion2 = IOFichero.ficheroAString
                    (ficheroAlimentacion2, Charset.forName("UTF-16"));
        String bebidas2;
        bebidas2 = IOFichero.ficheroAString
                    (ficheroBebidas2, Charset.forName("UTF-16"));
        String drogueria2;
        drogueria2 = IOFichero.ficheroAString
                    (ficheroDrogueria2, Charset.forName("UTF-16"));
        this.textAreaAlimentacion1.setText(alimentacion1);
        this.textAreaBebidas1.setText(bebidas1);
        this.textAreaDrogueria1.setText(drogueria1);
        this.textAreaAlimentacion2.setText(alimentacion2);
        this.textAreaBebidas2.setText(bebidas2);
        this.textAreaDrogueria2.setText(drogueria2);
    }

    /**
     * Este metodo guarda en ficheros de texto en el disco duro la informacion
     * que contienen las areas de texto del panelListas.
     */
    private void guardarAreasTexto() {
        File drogueria1 = new File("datos/drogueria1.txt");
        File bebidas1 = new File("datos/bebidas1.txt");
        File alimentacion1 = new File("datos/alimentacion1.txt");
        File drogueria2 = new File("datos/drogueria2.txt");
        File bebidas2 = new File("datos/bebidas2.txt");
        File alimentacion2 = new File("datos/alimentacion2.txt");
        guardarDatos(drogueria1, this.textAreaDrogueria1.getText());
        guardarDatos(bebidas1, this.textAreaBebidas1.getText());
        guardarDatos(alimentacion1, this.textAreaAlimentacion1.getText());
        guardarDatos(drogueria2, this.textAreaDrogueria2.getText());
        guardarDatos(bebidas2, this.textAreaBebidas2.getText());
        guardarDatos(alimentacion2, this.textAreaAlimentacion2.getText());
    }

    /**
     * Este metodo transfiere los datos contenidos en el String pasado como
     * parametro al fichero pasado como parametro. Utiliza la funcionalidad de
     * la clase FicheroIO.
     *
     * @param fich el fichero al que se transferiran los datos.
     * @param datos el String que contiene los datos a trasferir.
     */
    private void guardarDatos(File fich, String datos) {
        IOFichero.stringAFichero(datos, fich, Charset.forName("UTF-16"));
    }
    
    /**
     * Este método guarda la información contenida en los PanelOferta de la 
     * aplicación en el disco.
     */
    private void guardarPanelesOferta(){
        guardarPanelesXml(this.panel24, "panel_24");
        guardarPanelesXml(this.panel9, "panel_9");
        guardarPanelesXml(this.panel12, "panel_12");
    }
    
    /**
     * Este método se usa para guardar los datos de los PanelOferta contenidos
     * en un panel de la aplicación.
     * @param comp el panel que contiene los PanelOferta que contienen los datos
     * a guardar
     * @param nombrePanel el nombre del panel contenedor.
     */
    private void guardarPanelesXml(Component comp, String nombrePanel){
        // creamos el formato en el que será creada la salida.
        Format formato = Format.getPrettyFormat();
        // le asignamos el encoding para guardar datos en español.
        formato.setEncoding("UTF-16");
        // creamos el objeto para serializar el documento.
        XMLOutputter xmlOutputter = new XMLOutputter(formato);
        // obtenemos el documento XML de los objetos Aviso.
        Document doc = new Document();
        Element raiz = new Element("Paneles");
        doc.addContent(raiz);
        Component[] paneles = this.getPanelesOferta(comp);
        for(Component c: paneles){
            PanelOferta panelOferta = (PanelOferta)c;
            Element panel = new Element("Panel");
            Element f1 = new Element("f1");
            f1.setText(panelOferta.getTexto(TextFieldConPosicion.ARRIBA));
            panel.addContent(f1);
            Element f2 = new Element("f2");
            f2.setText(panelOferta.getTexto(TextFieldConPosicion.MEDIO));
            panel.addContent(f2);
            Element f3 = new Element("f3");
            f3.setText(panelOferta.getTexto(TextFieldConPosicion.ABAJO));
            panel.addContent(f3);
            raiz.addContent(panel);
            String docString;
            docString = xmlOutputter.outputString(doc);
            File f = new File("datos/"+nombrePanel+".xml");
            this.guardarDatos(f, docString);
        }
    }
    
    /**
     * Este método se usa para cargar de datos todos los PanelOferta que se
     * muestran en la aplicación. Los datos son cargados desde distintos
     * ficheros.
     */
    private void cargarPanelesOferta(){
        Document doc;
        try {
            doc = this.getDocumentoXml(new File("datos/panel_24.xml"));
            if(doc != null){
                this.cargarPanel(this.panel24, doc);
            }
            doc = this.getDocumentoXml(new File("datos/panel_9.xml"));
            if(doc != null){
                this.cargarPanel(this.panel9, doc);
            }
            doc = this.getDocumentoXml(new File("datos/panel_12.xml"));
            if(doc != null){
                this.cargarPanel(this.panel12, doc);
            }
        } catch (JDOMException | IOException ex) {
            CarteleriaOfertas.crearPanelAviso(ex.toString());
        }
    }
    
    /**
     * Este método rellena los PanelOferta de un JPanel con los datos obtenidos
     * de un documento XML. 
     * @param padre el panel que contiene los PanelOferta a rellenar.
     * @param doc el documento JDom que contiene los datos XML.
     */
    private void cargarPanel(JPanel padre, Document doc){
        Component[]comps = this.getPanelesOferta(padre);
        Element raiz = doc.getRootElement();
        List<Element> listaPaneles = raiz.getChildren();
        for(int j = 0; j < comps.length; j++){
            PanelOferta panelOferta = (PanelOferta)comps[j];
            Element panel = listaPaneles.get(j);
            List<Element> listaFilas = panel.getChildren();
               for (int i = 0; i < listaFilas.size(); i++) {
                    Element fila = listaFilas.get(i);
                    panelOferta.setTexto(i, fila.getText());
                }
        }
    }

    /**
     * Metodo invocado cuando queremos salir del programa. Invoca al metodo
     * guardarAreasTexto para grabar los datos contenidos en las areas de texto
     * del panelListas y luego sale del programa.
     */
    private void salir() {
        guardarAreasTexto();
        guardarPanelesOferta();
        System.exit(0);
    }

    /**
     * Este metodo despliega un JFileChooser que nos va a permitir seleccionar
     * el archivo donde estan almacenados los datos de los articulos que vamos a
     * utilizar para preparar la oferta. Por defecto nos muestra la ruta donde
     * estan los ficheros de datos de las ofertas.
     * @return el fichero que elegimos.
     */
    private File buscarDatos() {
        File ficheroDatos = null;
        JFileChooser fc = new JFileChooser
            ("\\\\ALMACEN\\Archivos de programa\\OfertasElco\\datos");
        if (fc.showOpenDialog(this.getContentPane())
                == JFileChooser.APPROVE_OPTION) {
            ficheroDatos = fc.getSelectedFile();
        }
        return ficheroDatos;
    }

    /**
     * Este metodo utiliza la funcionalidad de la API JDom para extraer los
     * datos, en formato XML, de un archivo e introducirlos en un Vector que
     * luego le pasaremos como modelo de datos a una lista que nos mostrara
     * dichos datos.
     *
     * @param datos el fichero que contiene los datos xml.
     * @return true si la operacion se realizó con exito, false si el fichero es
     * null.
     * @throws JDOMException
     * @throws IOException
     */
    private boolean getObjetoXML(File datos)
            throws JDOMException, IOException {
        if (datos == null) {
            return false;
        }
        SAXBuilder constructor = new SAXBuilder();
        Document doc = constructor.build(datos);
        Element raiz = doc.getRootElement();
        ArrayList<String> arrayDatos = new ArrayList<>();
        List<Element> hijosRaiz = raiz.getChildren();
        hijosRaiz.forEach((hijo) -> {
            arrayDatos.add(hijo.getChildText("Nombre"));
        });
        listaDatos.setListData(arrayDatos.toArray(new String[0]));
        listaDatos.setSize(300, 650);
        listaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //listaDatos.setTransferHandler(new ListTransferHandler());
        return true;
        
    }
    
    /**
     * Este método nos permite obtener un Document JDom (para trabajar con XML)
     * desde un fichero del disco.
     * @param fichero el fichero donde están los datos XML.
     * @return Un Document JDom.
     * @throws JDOMException si hay algún problema con los datos del fichero.
     * @throws IOException si hay algún problema con el fichero.
     */
    private Document getDocumentoXml(File fichero)throws JDOMException, IOException{
        if(!fichero.exists())return null;
        SAXBuilder constructor = new SAXBuilder();
        Document doc = constructor.build(fichero);
        return doc;
    }

    /**
     * Este metodo crea una ventana (objeto JFrame) donde se muestra al usuario
     * la lista de los objetos que se van a utilizar para preparar la carteleria
     * de la oferta.
     */
    private void crearVentanaListaDatos() {
        JFrame v = new JFrame("Listado de productos de oferta");
        v.add(this.scrollPaneListaDatos);
        v.setBounds(700, 10, 300, 600);
        v.setVisible(true);
        v.setIconImage(icono.getImage());
        // hacemos que este siempre visible por encima de la interfaz
        v.setAlwaysOnTop(true);

    }

    /**
     * Este metodo crea un PanelOferta y lo muestra. Sólo funcionará si está
     * seleccionado el panel en blanco.
     */
    private void crearNuevoPanelOferta() {
        if (this.jTabbedPane1.getSelectedComponent()
                .getName().equals("panelBlanco")) {
            PanelOferta p = new PanelOferta();
            p.setBounds(10, 400, 130, 150);
            JPanel panel = (JPanel) this.jTabbedPane1.getSelectedComponent();
            panel.setLayout(null);
            panel.add(p);
            panel.revalidate();
        }
    }

    /**
     * Este método crea un panel de aviso para mostrar al usuario.
     *
     * @param textoAviso el texto que saldrá en el aviso.
     */
    private static void crearPanelAviso(String textoAviso) {
        JOptionPane.showMessageDialog(null, textoAviso,
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Este metodo es llamado desde el constructor para inicializar el
     * formulario. ATENCION: NO modificar este codigo. El contenido de este
     * metodo es siempre regenerado por el editor de formularios automaticamente
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneListaDatos = new javax.swing.JScrollPane();
        listaDatos = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelListas1 = new javax.swing.JPanel();
        scrollPaneAlimentacion1 = new javax.swing.JScrollPane();
        textAreaAlimentacion1 = new javax.swing.JTextArea();
        scrollPaneBebidas1 = new javax.swing.JScrollPane();
        textAreaBebidas1 = new javax.swing.JTextArea();
        scrollPaneDrogueria1 = new javax.swing.JScrollPane();
        textAreaDrogueria1 = new javax.swing.JTextArea();
        panelListas2 = new javax.swing.JPanel();
        scrollPaneBebidas2 = new javax.swing.JScrollPane();
        textAreaBebidas2 = new javax.swing.JTextArea();
        scrollPaneAlimentacion2 = new javax.swing.JScrollPane();
        textAreaAlimentacion2 = new javax.swing.JTextArea();
        scrollPaneDrogueria2 = new javax.swing.JScrollPane();
        textAreaDrogueria2 = new javax.swing.JTextArea();
        panelListaPersonal = new javax.swing.JPanel();
        panelPadre24 = new javax.swing.JPanel();
        panel24 = new javax.swing.JPanel();
        panel9_12 = new javax.swing.JPanel();
        panel9 = new javax.swing.JPanel();
        panel12 = new javax.swing.JPanel();
        panelListadoPaneles = new javax.swing.JScrollPane();
        textAreaListadoPaneles = new javax.swing.JTextArea();
        panelBlanco = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFichero = new javax.swing.JMenu();
        menuItemCerrar = new javax.swing.JMenuItem();
        menuDatos = new javax.swing.JMenu();
        menuItemActual = new javax.swing.JMenuItem();
        menuItemCargarDatos = new javax.swing.JMenuItem();
        menuCrear = new javax.swing.JMenu();
        menuItemCrearPanel = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuImprimir = new javax.swing.JMenu();
        menuItemImprimir = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        menuItemAyuda = new javax.swing.JMenuItem();

        listaDatos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaDatos.setDragEnabled(true);
        listaDatos.setDropMode(javax.swing.DropMode.ON);
        scrollPaneListaDatos.setViewportView(listaDatos);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cartelería Ofertas Elco");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                ventanaWindowClosing(evt);
            }
        });

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1020, 750));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChangedListener(evt);
            }
        });

        panelListas1.setBackground(new java.awt.Color(255, 255, 255));
        panelListas1.setName("panelListas1"); // NOI18N

        scrollPaneAlimentacion1.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneAlimentacion1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Alimentación 25 lineas", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        textAreaAlimentacion1.setColumns(20);
        textAreaAlimentacion1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textAreaAlimentacion1.setRows(5);
        textAreaAlimentacion1.setDragEnabled(true);
        textAreaAlimentacion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreasMouseRelease(evt);
            }
        });
        scrollPaneAlimentacion1.setViewportView(textAreaAlimentacion1);

        scrollPaneBebidas1.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneBebidas1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bebidas 10 lineas", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        textAreaBebidas1.setColumns(20);
        textAreaBebidas1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textAreaBebidas1.setRows(5);
        textAreaBebidas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreasMouseRelease(evt);
            }
        });
        scrollPaneBebidas1.setViewportView(textAreaBebidas1);

        scrollPaneDrogueria1.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneDrogueria1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Droguería 13 lineas", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        textAreaDrogueria1.setColumns(20);
        textAreaDrogueria1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textAreaDrogueria1.setRows(5);
        textAreaDrogueria1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreasMouseRelease(evt);
            }
        });
        scrollPaneDrogueria1.setViewportView(textAreaDrogueria1);

        javax.swing.GroupLayout panelListas1Layout = new javax.swing.GroupLayout(panelListas1);
        panelListas1.setLayout(panelListas1Layout);
        panelListas1Layout.setHorizontalGroup(
            panelListas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListas1Layout.createSequentialGroup()
                .addComponent(scrollPaneAlimentacion1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelListas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneDrogueria1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListas1Layout.createSequentialGroup()
                        .addComponent(scrollPaneBebidas1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panelListas1Layout.setVerticalGroup(
            panelListas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListas1Layout.createSequentialGroup()
                .addGroup(panelListas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelListas1Layout.createSequentialGroup()
                        .addComponent(scrollPaneBebidas1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneDrogueria1))
                    .addComponent(scrollPaneAlimentacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lista1", panelListas1);

        panelListas2.setBackground(new java.awt.Color(255, 255, 255));

        scrollPaneBebidas2.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneBebidas2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bebidas", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        textAreaBebidas2.setColumns(20);
        textAreaBebidas2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textAreaBebidas2.setRows(5);
        textAreaBebidas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreasMouseRelease(evt);
            }
        });
        scrollPaneBebidas2.setViewportView(textAreaBebidas2);

        scrollPaneAlimentacion2.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneAlimentacion2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Alimentación", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        textAreaAlimentacion2.setColumns(20);
        textAreaAlimentacion2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textAreaAlimentacion2.setRows(5);
        textAreaAlimentacion2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreasMouseRelease(evt);
            }
        });
        scrollPaneAlimentacion2.setViewportView(textAreaAlimentacion2);

        scrollPaneDrogueria2.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneDrogueria2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Droguería", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N

        textAreaDrogueria2.setColumns(20);
        textAreaDrogueria2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        textAreaDrogueria2.setRows(5);
        textAreaDrogueria2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                textAreasMouseRelease(evt);
            }
        });
        scrollPaneDrogueria2.setViewportView(textAreaDrogueria2);

        javax.swing.GroupLayout panelListas2Layout = new javax.swing.GroupLayout(panelListas2);
        panelListas2.setLayout(panelListas2Layout);
        panelListas2Layout.setHorizontalGroup(
            panelListas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListas2Layout.createSequentialGroup()
                .addComponent(scrollPaneBebidas2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelListas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneAlimentacion2, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addComponent(scrollPaneDrogueria2, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelListas2Layout.setVerticalGroup(
            panelListas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListas2Layout.createSequentialGroup()
                .addGroup(panelListas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneBebidas2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListas2Layout.createSequentialGroup()
                        .addComponent(scrollPaneAlimentacion2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneDrogueria2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listas2", panelListas2);

        panelListaPersonal.setBackground(new java.awt.Color(255, 255, 255));
        panelListaPersonal.setLayout(new javax.swing.BoxLayout(panelListaPersonal, javax.swing.BoxLayout.LINE_AXIS));
        jTabbedPane1.addTab("ListaPersonal", panelListaPersonal);

        panelPadre24.setBackground(new java.awt.Color(255, 255, 255));
        panelPadre24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Panel 24 cuadros", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panelPadre24.setName("panelPadre24"); // NOI18N
        panelPadre24.setPreferredSize(new java.awt.Dimension(950, 650));

        panel24.setBackground(new java.awt.Color(255, 255, 255));
        panel24.setName("panel24"); // NOI18N
        panel24.setPreferredSize(new java.awt.Dimension(950, 600));
        panel24.setLayout(new java.awt.GridLayout(4, 6));
        panelPadre24.add(panel24);

        jTabbedPane1.addTab("Panel 24", panelPadre24);

        panel9_12.setBackground(new java.awt.Color(255, 255, 255));
        panel9_12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel9_12.setName("panel9_12"); // NOI18N
        panel9_12.setPreferredSize(new java.awt.Dimension(1020, 700));

        panel9.setBackground(new java.awt.Color(255, 255, 255));
        panel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Panel 9 cuadros", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panel9.setName("panel9"); // NOI18N
        panel9.setPreferredSize(new java.awt.Dimension(485, 600));
        panel9.setLayout(new java.awt.GridLayout(3, 3, 1, 1));
        panel9_12.add(panel9);

        panel12.setBackground(new java.awt.Color(255, 255, 255));
        panel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Panel 12 cuadros", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panel12.setName("panel12"); // NOI18N
        panel12.setOpaque(false);
        panel12.setPreferredSize(new java.awt.Dimension(485, 600));
        panel12.setLayout(new java.awt.GridLayout(6, 2, 1, 1));
        panel9_12.add(panel12);

        jTabbedPane1.addTab("Panel 9-12", panel9_12);

        panelListadoPaneles.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Listado de los productos de los paneles", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panelListadoPaneles.setName("panelListadoPaneles"); // NOI18N

        textAreaListadoPaneles.setColumns(20);
        textAreaListadoPaneles.setRows(5);
        panelListadoPaneles.setViewportView(textAreaListadoPaneles);

        jTabbedPane1.addTab("Listado paneles", panelListadoPaneles);
        panelListadoPaneles.getAccessibleContext().setAccessibleName("panelListado");

        panelBlanco.setBackground(new java.awt.Color(255, 255, 255));
        panelBlanco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Diseñe aquí su propio panel de ofertas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panelBlanco.setName("panelBlanco"); // NOI18N
        panelBlanco.setLayout(null);
        jTabbedPane1.addTab("Panel blanco", panelBlanco);

        menuFichero.setText("Fichero");

        menuItemCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCerrar.setText("Salir");
        menuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCerrarActionPerformed(evt);
            }
        });
        menuFichero.add(menuItemCerrar);

        jMenuBar1.add(menuFichero);

        menuDatos.setText("Datos");

        menuItemActual.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuItemActual.setText("Actual");
        menuItemActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActualActionPerformed(evt);
            }
        });
        menuDatos.add(menuItemActual);

        menuItemCargarDatos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCargarDatos.setText("Cargar");
        menuItemCargarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCargarActionPerformed(evt);
            }
        });
        menuDatos.add(menuItemCargarDatos);

        jMenuBar1.add(menuDatos);

        menuCrear.setText("Crear");

        menuItemCrearPanel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCrearPanel.setText("Nuevo Panel");
        menuItemCrearPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoPanelActionPerformed(evt);
            }
        });
        menuCrear.add(menuItemCrearPanel);

        jMenuItem1.setText("Nueva AreaTexto");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevaAreaTextoActionPerformed(evt);
            }
        });
        menuCrear.add(jMenuItem1);

        jMenuBar1.add(menuCrear);

        menuImprimir.setText("Imprimir");

        menuItemImprimir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        menuItemImprimir.setText("Imprimir");
        menuItemImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemImprimirActionPerformed(evt);
            }
        });
        menuImprimir.add(menuItemImprimir);

        jMenuBar1.add(menuImprimir);

        menuAyuda.setText("Ayuda");

        menuItemAyuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menuItemAyuda.setText("Ayuda");
        menuItemAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAyudaActionPerformed(evt);
            }
        });
        menuAyuda.add(menuItemAyuda);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Panel24");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Este metodo inicializa algunas propiedades de los objetos de esta clase
     */
    private void initMisComponentes() {
        // insertamos un icono personalizado
        URL url = this.getClass().getResource("Recursos/cartel.jpg");
        if (url != null) {
            icono = new ImageIcon(url);
            this.setIconImage(icono.getImage());
        }
        // creamos un trasfer handler personalizado para las areas de texto
        TextAreaTransferHandler th = new TextAreaTransferHandler();
        this.textAreaAlimentacion1.setTransferHandler(th);
        this.textAreaBebidas1.setTransferHandler(th);
        this.textAreaDrogueria1.setTransferHandler(th);
        this.textAreaAlimentacion2.setTransferHandler(th);
        this.textAreaBebidas2.setTransferHandler(th);
        this.textAreaDrogueria2.setTransferHandler(th);

        this.panelPadre24.add(rellenarPanel(this.panel24, 24));
        this.panel9_12.add(rellenarPanel(this.panel9, 9));
        this.panel9_12.add(rellenarPanel(this.panel12, 12));
        
        panelContenedor = new PanelContenedor();
        panelContenedor.setBackground(Color.WHITE);
        this.panelListaPersonal.add(panelContenedor);
    }

    /**
     * Este metodo rellena de panelOferta el JPanel pasado como parametro.
     *
     * @param p el JPanel que se va a rellenar de PanelOferta.
     * @param numPaneles el numero de PanelOferta que va a contener.
     * @return
     */
    private JPanel rellenarPanel(JPanel p, int numPaneles) {
        for (int i = 0; i < numPaneles; i++) {
            PanelOferta po = new PanelOferta();
            po.inmovilizarPanel();
            p.add(po);
        }
        return p;
    }

    /**
     * Metodo invocado cuando pulsamos el menu item Cerrar.
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void menuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCerrarActionPerformed
        salir();
    }//GEN-LAST:event_menuItemCerrarActionPerformed

    /**
     * Metodo invocado cuando pulsamos el menu item Nuevo Panel.
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void menuItemNuevoPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoPanelActionPerformed
        crearNuevoPanelOferta();
    }//GEN-LAST:event_menuItemNuevoPanelActionPerformed

    /**
     * Metodo invocado cuando pulsamos el menu item Imprimir.
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void menuItemImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemImprimirActionPerformed
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(OrientationRequested.LANDSCAPE);
            aset.add(MediaSizeName.ISO_A4);
            job.setPrintable(this);
            job.printDialog();
            job.print(aset);
        } catch (PrinterException ex) {
            crearPanelAviso(ex.toString());
        }
    }//GEN-LAST:event_menuItemImprimirActionPerformed

    /**
     * Metodo invocado cuando pulsamos sobre las pestañas del tabbed pane.
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void tabbedPaneStateChangedListener(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChangedListener
        JTabbedPane tp = (JTabbedPane) evt.getSource();
        if ("panelListadoPaneles".equals(tp.getSelectedComponent().getName())) {
            this.textAreaListadoPaneles.setText("");
            getTextoPaneles();
        }
    }//GEN-LAST:event_tabbedPaneStateChangedListener

    private void menuItemAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAyudaActionPerformed
        mostrarAyuda();
    }//GEN-LAST:event_menuItemAyudaActionPerformed

    /**
     * Metodo invocado cuando se cierra el JFrame que contiene la aplicacion
     * (cuando salimos del programa). Invoca al metodo salir().
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void ventanaWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ventanaWindowClosing
        salir();
    }//GEN-LAST:event_ventanaWindowClosing

    /**
     * Metodo invocado cuando pulsamos con el boton derecho del raton sobre un
     * area de texto. Invoca al metodo crearPopupMenu().
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void textAreasMouseRelease(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textAreasMouseRelease
        if (evt.isPopupTrigger()) {
            crearPopupMenu(evt);
        }
    }//GEN-LAST:event_textAreasMouseRelease

    /**
     * Metodo invocado cuando pulsamos sobre el menu item Actual. Carga la
     * oferta que esta actualmente en uso.
     *
     * @param evt el objeto que contiene la informacion de este evento.
     */
    private void menuItemActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemActualActionPerformed

        String path = "\\\\ALMACEN\\Archivos de programa\\OfertasElco\\datos\\";
        File fic = new File(path + "ofertaActual.txt");
        String fechaOferta;
        fechaOferta = IOFichero.ficheroAString
            (fic, Charset.forName("UTF-16"));
        
        fechaOferta = fechaOferta.trim();
        File ficOferta = new File(path + fechaOferta);
        try {
            if(this.getObjetoXML(ficOferta)){
                this.crearVentanaListaDatos();
            }
        } catch (JDOMException | IOException ex) {
            CarteleriaOfertas.crearPanelAviso(ex.toString());
        }
    }//GEN-LAST:event_menuItemActualActionPerformed

    private void menuItemCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCargarActionPerformed
        try {
            if (this.getObjetoXML(this.buscarDatos())) {
                this.crearVentanaListaDatos();
            }
        } catch (JDOMException | IOException ex) {
            CarteleriaOfertas.crearPanelAviso(ex.toString());
        }
    }//GEN-LAST:event_menuItemCargarActionPerformed

    private void menuItemNuevaAreaTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevaAreaTextoActionPerformed
        JTextArea area = new JTextArea();
        area.setBackground(Color.WHITE);
        Resizable res = new Resizable(area);
        res.setBounds(100, 100, 150, 150);
        panelContenedor.add(res);
        panelContenedor.getParent().revalidate();
    }//GEN-LAST:event_menuItemNuevaAreaTextoActionPerformed

    /**
     * Este metodo crea un JPopupMenu que contiene un JMenuItem llamado borrar.
     * Nos va a permitir borrar el texto contenido en el area de texto desde la
     * que se llama al popup menu.
     *
     * @param me el objeto que contiene la informacion de este evento.
     */
    private void crearPopupMenu(MouseEvent me) {
        JTextArea ta = (JTextArea) me.getComponent();
        JPopupMenu menu = new JPopupMenu();
        ImageIcon iconoPapelera = getImagen("Recursos/papelera.png");
        //creamos el item del menu con un nombre y una imagen
        JMenuItem borrar = new JMenuItem("Borrar texto", iconoPapelera);
        borrar.addActionListener((ActionEvent ae) -> {
            ta.setText("");
        });
        menu.add(borrar);
        menu.show(ta, me.getX(), me.getY());

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

    /**
     * Este metodo nos muestra una ventana con la ayuda del programa.
     */
    private void mostrarAyuda() {
        // creamos el texto en formato html para que se vea mas bonito
        String textoAyuda = "<html><head><body><div align=center><h1><b><u>"
                + "FUNCIONAMIENTO DEL PROGRAMA</u></b></h1></div>"
                + "El programa muestra una serie de paneles, organizados"
                + " en pestañas, cada uno con un diseño "
                + "específico. Son los siguientes:<ol>"
                + "<li><b>LISTAS</b>: muestra tres areas distintas:"
                + "<ul><li>Zona <b>Alimentación</b>: "
                + "para añadir 25 productos.</li>"
                + "<li>Zona <b>Bebidas</b>: para añadir 10 productos.</li>"
                + "<li>Zona <b>Droguería</b>: para añadir 13 productos</li>"
                + "</ul></li><li><b>PANEL 24</b>: muestra un diseño"
                + " de 24 paneles de oferta.</li>"
                + "<li><b>PANEL 9_12</b>: muestra "
                + "dos zonas, una con 9 paneles y "
                + "otra con 12.</li>"
                + "<li><b>LISTADO PANELES</b>: muestra el texto que contienen "
                + "los paneles de oferta de PANEL 24 y PANEL 9_12.</li>"
                + "<li><b>PANEL BLANCO</b>: permite hacer un diseño libre. "
                + "Seleccione el menú <b>'Crear'->'Nuevo Panel'</b> y mueva ó "
                + "redimensione cada panel a su gusto.</li></ol>"
                + "<div align=center><h2><b><u>"
                + "FORMA DE TRABAJAR</u></b></h2></div>"
                + "<ol><li>Pulse en el menú <b>'Datos'->'Cargar'</b></li>"
                + "<li>Elija el archivo que coincida con la fecha de "
                + "la oferta que desea organizar.</li>"
                + "<li>Arrastre desde el listado de productos de oferta "
                + "cada producto al lugar donde quiera visualizarlo.</li>"
                + "<li>Puede borrar el texto de un panel de oferta "
                + "pulsando con el botón derecho del ratón "
                + "sobre él y seleccionando<b>'Borrar texto'</b>.</li>"
                + "<li>También puede eliminar por completo un panel de "
                + "oferta (si está en el panel en blanco) pulsando con el botón"
                + " derecho del ratón sobre <br>"
                + "él y seleccionando <b>'Eliminar panel'</b>.</li>"
                + "<li>Si lo desea puede imprimir el contenido de cualquier "
                + "pantalla seleccionando el menú <b>'Imprimir'->'Imprimir'</b>"
                + "</li></ol></head></body></html>";
        //mostramos el texto de ayuda en un panel de aviso
        CarteleriaOfertas.crearPanelAviso(textoAyuda);
    }

    /**
     * Este metodo permite recoger el texto contenido en todos los paneles de
     * oferta de la aplicacion para luego mostrarlo en un text area.
     */
    private void getTextoPaneles() {
        //Component[] comps = this.panel24.getComponents();
        Component[] comps = getPanelesOferta(this.panel24);
        getPaneles(comps, "P24>>");
        //comps = this.panel9.getComponents();
        comps = this.getPanelesOferta(this.panel9);
        getPaneles(comps, "P9>>");
        //comps = this.panel12.getComponents();
        comps = this.getPanelesOferta(this.panel12);
        getPaneles(comps, "P12>>");
    }
    
    /**
     * Método que devuelve los componentes contenidos dentro de otro 
     * componente.
     * @param comp el Component que contiene los componentes que queremos
     * recuperar.
     * @return una matriz de Component con todos los componentes que contiene
     * el Component pasado como argumento al método.
     */
    private Component[] getPanelesOferta(Component comp){
        JPanel panel = (JPanel)comp;
        return panel.getComponents();
    }

    /**
     * Metodo que extrae cada panel de oferta de una matriz de paneles y obtiene
     * el texto contenido en cada uno de ellos.
     *
     * @param comps la matriz de componentes (paneles oferta)
     * @param grupoPaneles el nombre del conjunto de paneles del que proviene
     * cada panel (grupo 24 paneles, 9 paneles o 12 paneles).
     */
    private void getPaneles(Component[] comps, String grupoPaneles) {
        for (Component c : comps) {
            PanelOferta p = (PanelOferta) c;
            String texto = recuperarTextoPanelOferta(p, grupoPaneles);
            this.textAreaListadoPaneles.append(texto);
        }
        this.textAreaListadoPaneles.append("\n");
    }

    /**
     * Este metodo recupera el texto contenido en un panel oferta especifico.
     *
     * @param p el panel oferta del que recuperaremos el texto.
     * @param grupoPaneles en nombre del grupo de paneles del que proviene el
     * panel.
     * @return un String con el texto que contiene el panel.
     */
    private String recuperarTextoPanelOferta(PanelOferta p, String grupoPaneles) {
        String arriba = p.getTexto(TextFieldConPosicion.ARRIBA);
        if (arriba.length() == 0) {
            return "";
        }
        String medio = p.getTexto(TextFieldConPosicion.MEDIO);
        String abajo = p.getTexto(TextFieldConPosicion.ABAJO);
        String texto = grupoPaneles + medio + " " + arriba + " " + abajo + "\n";
        return texto;
    }

    /**
     * Este metodo nos permite imprimir lo que estamos visualizando en la
     * pantalla del ordenador. Obtiene el panel que esta seleccionado en el
     * momento de invocar al metodo (el que se esta visualizando en pantalla) e
     * imprime todos los graficos contenidos en el.
     *
     * @param graphics el objet sobre el que se van a dibujar los graficos
     * contenidos en el panel seleccionado.
     * @param pageFormat objeto que contiene informacion sobre el formato de la
     * pagina a imprimir.
     * @param pageIndex el indice de la pagina a imprimir.
     * @return PAGE_EXIST si la pagina se ha impreso con exito o NO_SUCH_PAGE si
     * pageIndex especifica una pagina inexistente.
     * @throws PrinterException una excepcion si se produce algun error.
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) graphics;
        //Punto donde empezará a imprimir dentro la pagina (5, 50)
        g2d.translate(pageFormat.getImageableX() + 5,
                pageFormat.getImageableY() + 5);
        g2d.scale(0.78, 0.78); //Reducción de la impresión al 78%
        this.jTabbedPane1.getSelectedComponent().paintAll(graphics);
        return PAGE_EXISTS;
    }

    /**
     * Metodo de entrada a la aplicacion
     *
     * @param args los argumentos pasados en la linea de comandos.
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc="
        //Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available,
         * stay with the default look and feel.
         * For details see http://download.oracle.com/javase/
         * tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            CarteleriaOfertas.crearPanelAviso(ex.toString());
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CarteleriaOfertas().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listaDatos;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuCrear;
    private javax.swing.JMenu menuDatos;
    private javax.swing.JMenu menuFichero;
    private javax.swing.JMenu menuImprimir;
    private javax.swing.JMenuItem menuItemActual;
    private javax.swing.JMenuItem menuItemAyuda;
    private javax.swing.JMenuItem menuItemCargarDatos;
    private javax.swing.JMenuItem menuItemCerrar;
    private javax.swing.JMenuItem menuItemCrearPanel;
    private javax.swing.JMenuItem menuItemImprimir;
    private javax.swing.JPanel panel12;
    private javax.swing.JPanel panel24;
    private javax.swing.JPanel panel9;
    private javax.swing.JPanel panel9_12;
    private javax.swing.JPanel panelBlanco;
    private javax.swing.JPanel panelListaPersonal;
    private javax.swing.JScrollPane panelListadoPaneles;
    private javax.swing.JPanel panelListas1;
    private javax.swing.JPanel panelListas2;
    private javax.swing.JPanel panelPadre24;
    private javax.swing.JScrollPane scrollPaneAlimentacion1;
    private javax.swing.JScrollPane scrollPaneAlimentacion2;
    private javax.swing.JScrollPane scrollPaneBebidas1;
    private javax.swing.JScrollPane scrollPaneBebidas2;
    private javax.swing.JScrollPane scrollPaneDrogueria1;
    private javax.swing.JScrollPane scrollPaneDrogueria2;
    private javax.swing.JScrollPane scrollPaneListaDatos;
    private javax.swing.JTextArea textAreaAlimentacion1;
    private javax.swing.JTextArea textAreaAlimentacion2;
    private javax.swing.JTextArea textAreaBebidas1;
    private javax.swing.JTextArea textAreaBebidas2;
    private javax.swing.JTextArea textAreaDrogueria1;
    private javax.swing.JTextArea textAreaDrogueria2;
    private javax.swing.JTextArea textAreaListadoPaneles;
    // End of variables declaration//GEN-END:variables

    private ImageIcon icono;
    private PanelContenedor panelContenedor;
}
