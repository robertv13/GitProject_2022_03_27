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
   /* args[0]=Fichier TXT (achats.txt), args[1]=Fichier XML (inventaire.xml) */
   public static void main(String[] args) throws Exception {
      /* Traitement du fichier XML - args[1] */
      System.out.println("\n--------------------------------------");
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder parser = factory.newDocumentBuilder();
      Document docXML = parser.parse("inventaire.xml");
      Element racine = docXML.getDocumentElement();
      docXML.getDocumentElement().normalize();

      /* Traitement du fichier TXT - args[0] */
      List<String> arrayTXT = new ArrayList<String>();
      FileReader fr = new FileReader("achats.txt");
      BufferedReader br = new BufferedReader(fr);
      for (String line = br.readLine(); line != null; line = br.readLine()) {
         arrayTXT.add(line);
      }

      /* Boucle du tableau des achats - Une ligne à la fois */
      for (int i = 0; i < arrayTXT.size(); ++i) {
         String ligne = arrayTXT.get(i);
         List<String> result = Arrays.asList(ligne.split("\\s*,\\s*"));
         System.out.println("\n" + i + " - Le produit du fichier 'achat.txt' est '" + result.get(2) + "'");

         /* Trouve l'enregistrement d'inventaire.XML pour le produit d'achats */
         NodeList nl = racine.getChildNodes();
         for (int k = 0; k < nl.getLength(); ++k) {
            /* Validate to only handle Element Node */
            if (nl.item(k).getNodeType() == Node.ELEMENT_NODE) {
               Element e = (Element) nl.item(k);
               /* Is this product match the product from 'achats.txt' ? */
               String codeProduitNode = e.getAttribute("code");
               String codeProduitAchats = result.get(2);
               System.out.println("    k = " + k + " et le code de produit = '" + codeProduitNode + "'");
//               if (codeProduitNode == codeProduitAchats) {
                  int qteInventaire = Integer.parseInt(e.getAttribute("quantite"));
                  System.out.println("    L'inventaire du produit '" + e.getAttribute("code") + "' est de "
                        + e.getAttribute("quantite") + " Réduction de " + result.get(3));
                  qteInventaire = qteInventaire - Integer.parseInt(result.get(3));
                  System.out.println(
                        "       Alors on soustrait " + result.get(3) + " de " + e.getAttribute("quantite") + " = "
                              + qteInventaire);
                  e.setAttribute("toto", "999");            
                  e.setAttribute("quantite", Integer.toString(qteInventaire));
                  System.out.println("       Quantité APRÈS le calcul (RAM): " + " = " + qteInventaire);
                  System.out.println("       Quantité APRÈS la calcul (node): " + " = " + e.getAttribute("quantite"));
                  nl.item(k).getParentNode().replaceChild(nl.item(k), e);
//               }
            }
         }

         br.close();
         fr.close();

         TransformerFactory tfact = TransformerFactory.newInstance();
         Transformer transformer = tfact.newTransformer();
         transformer.setOutputProperty("encoding", "UTF-8");
         DOMSource source = new DOMSource(docXML);
         FileWriter fw = new FileWriter("inventaire.xml");
         StreamResult result1 = new StreamResult(fw);
         transformer.transform(source, result1);
      }
   }
}