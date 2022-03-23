/*
** INF 6450 -  Travail noté 4 - Robert M. Vigneault,  fait avec JDK java 14.0.2
*/

import org.w3c.dom.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
public class Inventaire {
   public static void main(String[] args) throws Exception {
      /* Traitement du fichier XML */
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder parser = factory.newDocumentBuilder();
      Document docXML = parser.parse(args[1]);
      Element racine = docXML.getDocumentElement();

      /* Traitement du fichier TXT */
      List<String> arrayTXT = new ArrayList<String>();
      FileReader fr = new FileReader(args[0]);
      BufferedReader br = new BufferedReader(fr);
      for (String line = br.readLine(); line != null; line = br.readLine()) {
          arrayTXT.add(line);
      }

      /* Analyse du tableau des achats.txt - Une ligne à la fois */
      for (int i = 0; i < arrayTXT.size(); ++i) {
         String ligne = arrayTXT.get(i);
         List<String> result = Arrays.asList(ligne.split("\\s*,\\s*"));
         System.out.println("Code=" + result.get(2) + " Qté=" + result.get(3));
         NodeList nl = racine.getChildNodes();

         /* Trouve l'enregistrement d'inventaire.XML pour le produit d'achats */
         for (int k = 0; k < nl.getLength(); ++k) {
            if (nl.item(k).getNodeType() == Node.ELEMENT_NODE) {
               Node e = nl.item(k);
               if (e.getAttributes().getNamedItem("code").getNodeValue() == (result.get(2))) {
                  int qteInventaire = Integer.parseInt(e.getAttributes().getNamedItem("quantite").getNodeValue());
                  qteInventaire -= Integer.parseInt(result.get(3));
                  System.out.println(result.get(2) + ": " + e.getAttributes().getNamedItem("quantite").getNodeValue() +
                        " - " + result.get(3) + " = " + qteInventaire);
//                        e.setAttribute("quantite", toString(qteInventaire));
               }
            }
         }
      }

      br.close();
      fr.close();
     
      TransformerFactory tfact = TransformerFactory.newInstance();
      Transformer transformer = tfact.newTransformer();
      transformer.setOutputProperty("encoding", "UTF-8");
      DOMSource source = new DOMSource(docXML);
      FileWriter fw = new FileWriter(args[1]);
      StreamResult result = new StreamResult(fw);    
      transformer.transform(source, result);
   }
}
