/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programdografikiwektorowej;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author ja
 */
public abstract class  Shape  {
    
    protected String namespace = "http://www.w3.org/2000/svg";

    public abstract void drawYourself(Graphics2D g2) ;

    public abstract Element tworzXML(Document dokument);
    
    public abstract Shape pobierzDaneZXML(Document document, String nazwa, Line line);
    

    
}
