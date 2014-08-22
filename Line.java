/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programdografikiwektorowej;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author ja
 */
public class Line extends Shape{
    
    private Line2D line;
    private NamedNodeMap attrMap;
    
    public Line(Line2D line2) {
        line=line2;
    }
    
    public Element tworzXML(Document doc){
        Element lineElement = doc.createElementNS(namespace, "line");
        lineElement.setAttribute("x1", "" + line.getX1());
        lineElement.setAttribute("y1", "" + line.getY1() );
        lineElement.setAttribute("x2", "" + line.getX2());
        lineElement.setAttribute("y2", "" + line.getY2());
        lineElement.setAttribute("style", "stroke:black ;stroke-width:1" );
        lineElement.setAttribute("fill", "#000000");
        return lineElement;
    }
    
    public void drawYourself(Graphics2D g)
    {
        g.draw(line);
    }
    public Shape pobierzDaneZXML(Document document, String nazwa, Line line)
    {
        double x1,x2,y1,y2;
        attrMap = document.getElementsByTagName(nazwa).item(0).getAttributes();
        x1=Double.parseDouble(attrMap.getNamedItem("x1").getNodeValue());
        x2=Double.parseDouble(attrMap.getNamedItem("x2").getNodeValue());
        y1=Double.parseDouble(attrMap.getNamedItem("y1").getNodeValue());
        y2=Double.parseDouble(attrMap.getNamedItem("y2").getNodeValue());
        Line2D line2d=new Line2D.Double(x1,y1,x2,y2);
        line=new Line(line2d);
        return line;
    }
         
}
