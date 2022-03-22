/*
** INF 6450 -  Travail noté 4 - Robert M. Vigneault,  fait avec JDK java 14.0.2
*/

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Scanner;
 
public class Inventaire {
   public static void main(String[] args) throws Exception {
      /* Traitement du fichier XML */
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder parser = factory.newDocumentBuilder();
      Document docXML = parser.parse(args[1]);
      Element racine = docXML.getDocumentElement();
      NodeList nl = racine.getChildNodes();
 
      /* Traitement du fichier TXT */
      File docTXT = new File(args[0]);
      Scanner oTXT = new Scanner(docTXT);
      while (oTXT.hasNextLine())
         System.out.println(oTXT.nextLine());
      
      oTXT.close();

      System.out.println(oTXT.hasNext());
 
/*       if (args[1].equals("ajoute")) {
            for (int k = 0; k < nl.getLength(); ++k) {
               if(nl.item(k).getNodeType()==Node.ELEMENT_NODE) {
                  Element e = (Element) nl.item(k);
                  if(e.getAttribute("code").equals(args[0])) {
                     e.setAttribute("quantité",args[2]);
                     ajout=true;
                  }
               }
            }
       if( ! ajout) {
         Element p = docXML.createElement("produit");
         p.setAttribute("code", args[0]);
         p.setAttribute("quantité", args[2]);
         racine.appendChild(p);
     }
    }
     
      TransformerFactory tfact = TransformerFactory.newInstance();
      Transformer transformer = tfact.newTransformer();
      transformer.setOutputProperty("encoding", "UTF-8");
      DOMSource source = new DOMSource(docXML);
      FileWriter fw = new FileWriter("inventaire.xml");
      StreamResult result = new StreamResult(fw);    
      transformer.transform(source, result);
 */   
}

  }