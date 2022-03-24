/*
** INF 6450 -  Travail noté 4 - Robert M. Vigneault,  fait avec JDK java 14.0.2
*/

package TN4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
 
public class TN4_Q3 {
   public static void main(String[] args) throws Exception {
      
      // Variables de travail -------------------------------------------------
      String nomFichier = "inventaire.xml";
      String codeProduit = "321";

      // Mettre en place le DOM -----------------------------------------------
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(nomFichier);
 
      // Création de l'objet xPath --------------------------------------------
      XPathFactory xpathfactory = XPathFactory.newInstance();
      XPath xPath = xpathfactory.newXPath();
 
      // Construire la commande xPath (xPathCmd) ------------------------------
      String xPathCmd = "/inventaire/produit[@code = '" + codeProduit +
         "']/@quantite";

      XPathExpression expr = xPath.compile(xPathCmd);
      Object result = expr.evaluate(doc, XPathConstants.NODESET);
      NodeList nodes = (NodeList) result;
      for (int i = 0; i < nodes.getLength(); i++) {
         System.out.println("\nLa quantité pour le produit '" +
            codeProduit + "' est de " + nodes.item(i).getNodeValue() + "\n");
      }
   }
}