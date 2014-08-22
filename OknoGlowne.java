package programdografikiwektorowej;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OknoGlowne extends JFrame{
    
    private String namespace = "http://www.w3.org/2000/svg";
    private int szerokoscPlotna = 700;
    private int wysokoscPlotna = 600;
    private int Xpocz, Ypocz, Xkon, Ykon;
    private java.util.List<Shape> listaKsztaltow=new ArrayList<Shape>();
    private java.util.List<Shape> listaKsztaltow2=new ArrayList<Shape>();
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItemZapisz, menuItemOtworz;
    private JFileChooser chooser;
    private Rectangle rectangle;
    private Ellipse ellipse;
    private Line line;
    private Line2D line2d;
    private Rectangle2D rectangle2d;
    private Ellipse2D ellipse2d;

    private enum Tool {Line, Rectangle, Ellipse}
    private Tool wybraneNarzedzie;
    private static DocumentBuilder builder;
    private java.util.List<Shape> fromFile;
    
    public OknoGlowne()
    {
        super("Program do grafiki wektorowej");
        setSize(600, 600);
        wybraneNarzedzie=Tool.Line;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItemZapisz = new JMenuItem("Zapisz");
        menuItemOtworz = new JMenuItem("Otwórz");
        menu.add(menuItemZapisz);
        menu.add(menuItemOtworz);
        menuItemZapisz.addActionListener(EventHandler.create(ActionListener.class, this,"saveDocument"));
        menuItemOtworz.addActionListener(EventHandler.create(ActionListener.class, this,"openDocument"));
        
        chooser = new JFileChooser();
        
        Box lewyBox = Box.createVerticalBox();
        ButtonGroup radioGroup = new ButtonGroup();
        
        final JRadioButton rysujLinieRadioB = new JRadioButton("Rysuj linię");
        final JRadioButton rysujKwadratRadioB = new JRadioButton("Rysuj kwadrat");
        final JRadioButton rysujKoloRadioB = new JRadioButton("Rysuj koło");
        
        radioGroup.add(rysujLinieRadioB);
        radioGroup.add(rysujKwadratRadioB);
        radioGroup.add(rysujKoloRadioB);
        rysujLinieRadioB.setSelected(true);
        
        rysujLinieRadioB.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
                wybraneNarzedzie=Tool.Line;
            }
        });
        rysujKwadratRadioB.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
                wybraneNarzedzie=Tool.Rectangle;
            }
        });
        rysujKoloRadioB.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
                wybraneNarzedzie=Tool.Ellipse;
            }
        });

        
        lewyBox.add(rysujLinieRadioB);
        lewyBox.add(rysujKwadratRadioB);
        lewyBox.add(rysujKoloRadioB);
        
        Box prawyBox = Box.createVerticalBox();
        JPanel panelRysowania=new JPanel() {
            @Override
            public void paint(Graphics g) 
            {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setColor(Color.BLACK);

                for (int i = 0; i < listaKsztaltow.size(); i++)
                {
                    listaKsztaltow.get(i).drawYourself(g2);
                } 
                for (int i = 0; i < listaKsztaltow2.size(); i++)
                {
                    listaKsztaltow2.get(i).drawYourself(g2);
                } 

            }
        };
        
        panelRysowania.setBackground(Color.white);
        panelRysowania.setPreferredSize(new Dimension(szerokoscPlotna, wysokoscPlotna));
        prawyBox.add(panelRysowania);
        
        Box polaczenieLP = Box.createHorizontalBox();
        polaczenieLP.add(lewyBox);
        polaczenieLP.add(prawyBox);
        
        Box glownyBox = Box.createVerticalBox();
        glownyBox.add(menuBar);
        glownyBox.add(polaczenieLP);
        
        
        panelRysowania.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Xpocz=e.getX();
                Ypocz=e.getY();
            }
        });
        
        panelRysowania.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Xkon=e.getX();
                Ykon=e.getY();
                switch (wybraneNarzedzie)
                {
                    case Line:
                        line2d=new Line2D.Double(Xpocz,Ypocz,Xkon,Ykon);
                        line=new Line(line2d);
                        listaKsztaltow.add(line);
                        break;
                        
                    case Rectangle:
                        rectangle2d=new Rectangle2D.Double(Xpocz,Ypocz,Xkon-Xpocz,Ykon-Ypocz);
                        rectangle=new Rectangle(rectangle2d);
                        listaKsztaltow.add(rectangle);
                        break;
                        
                    case Ellipse:
                        ellipse2d=new Ellipse2D.Double(Xpocz,Ypocz,Xkon-Xpocz,Ykon-Ypocz);
                        ellipse=new Ellipse(ellipse2d);
                        listaKsztaltow.add(ellipse);
                        break;
                    
                }
              
                repaint();
            }
        });
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(menuBar, BorderLayout.PAGE_START);
        content.add(glownyBox, BorderLayout.CENTER);
        this.pack();

        setVisible(true);
    } 
    
    
    
    public void saveDocument() throws TransformerException, IOException
    {
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try
        {
            builder = factory.newDocumentBuilder();
        }
            catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        Document dokument = builder.newDocument();
        Element svgElement = dokument.createElementNS(namespace, "svg");
        dokument.appendChild(svgElement);
        svgElement.setAttribute("width", "" + szerokoscPlotna );
        svgElement.setAttribute("height", "" + wysokoscPlotna);
        for(Shape s : listaKsztaltow)
        {
            svgElement.appendChild(s.tworzXML(dokument));
        }
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20000802//EN");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.transform(new DOMSource(dokument), new StreamResult(Files.newOutputStream(file.toPath())));
    }
    
    public List<Shape> readShapesFromFile(Document doc)
    {
        List<Shape> fromFile = new ArrayList<Shape>();
        Node svg = (Element)doc.getElementsByTagName("svg").item(0);
        NodeList shapes = svg.getChildNodes();
        System.out.println(""+shapes);
        
        for(int i=0;i<shapes.getLength();i++){
            Node shape = shapes.item(i);
            Shape parsedShape = createShapeFromXML(shape,doc);
            fromFile.add(parsedShape);
        }
        return fromFile;
    }
 
    
    private Shape createShapeFromXML(Node shape, Document doc) 
    {
        double x1,x2,y1,y2;
        NamedNodeMap attrMap;
        Line linia;
        String nazwa=shape.getNodeName();
        System.out.println(nazwa);
            attrMap = doc.getElementsByTagName(nazwa).item(0).getAttributes();
            x1=Double.parseDouble(attrMap.getNamedItem("x1").getNodeValue());
            x2=Double.parseDouble(attrMap.getNamedItem("x2").getNodeValue());
            y1=Double.parseDouble(attrMap.getNamedItem("y1").getNodeValue());
            y2=Double.parseDouble(attrMap.getNamedItem("y2").getNodeValue());
            Line2D line2dz=new Line2D.Double(x1,y1,x2,y2);
            linia=new Line(line2dz);
        
        return linia;
    }
    
    public void openDocument()
    {
        try
        {
            File xmlFile = new File("E:/aaa.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            List<Shape> fromFile = readShapesFromFile(doc);
            listaKsztaltow2.addAll(fromFile);
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        repaint();
    }   
}
 