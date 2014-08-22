/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programdografikiwektorowej;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author ja
 */
public class Ellipse extends Shape{
    
    private Ellipse2D ellipse;
    
    public Ellipse(Ellipse2D ellipse2) {
        ellipse=ellipse2;
    }
    public void drawYourself(Graphics2D g)
    {
        g.fill(ellipse);
    }
    
    public Element tworzXML(Document doc){
        Element ellipseElement = doc.createElementNS(namespace, "ellipse");
        ellipseElement.setAttribute("cx", "" + (ellipse.getX()+ellipse.getWidth()/2) );
        ellipseElement.setAttribute("cy", "" + (ellipse.getY()+ellipse.getHeight()/2) );
        ellipseElement.setAttribute("rx", "" + ellipse.getWidth()/2);
        ellipseElement.setAttribute("ry", "" + ellipse.getHeight()/2);
        ellipseElement.setAttribute("fill", "#000000");
        return ellipseElement;
    }
    
    public Shape pobierzDaneZXML(Document document, String nazwa, Line line){
        return null;
    }
    
}