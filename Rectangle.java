/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programdografikiwektorowej;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author ja
 */
public class Rectangle extends Shape{

    private Rectangle2D rect;
    
    public Rectangle(Rectangle2D rect2) {
        rect=rect2;
    }
    public void drawYourself(Graphics2D g)
    {
        g.fill(rect);
    }

    public Element tworzXML(Document doc){
        Element rectangleElement = doc.createElementNS(namespace, "rect");
        rectangleElement.setAttribute("x", "" + rect.getX());
        rectangleElement.setAttribute("y", "" + rect.getY() );
        rectangleElement.setAttribute("width", "" + rect.getWidth());
        rectangleElement.setAttribute("height", "" + rect.getHeight());
        rectangleElement.setAttribute("fill", "#000000");
        return rectangleElement;
    }
public Shape pobierzDaneZXML(Document document, String nazwa, Line line){
    return null;
    }
}