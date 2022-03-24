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
import java.util.Objects;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
public class Inventaire {
   /* args[0]=Fichier TXT (achats.txt), args[1]=Fichier XML (inventaire.xml) */
   public static void main(String[] args) throws Exception {
      
      /* Traitement du fichier XML - args[1] */
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder parser = factory.newDocumentBuilder();
      Document docXML = parser.parse(args[1]);
      Element racine = docXML.getDocumentElement();
      docXML.getDocumentElement().normalize();

      /* Traitement du fichier TXT - args[0] */
      List<String> arrayTXT = new ArrayList<String>();
      FileReader fr = new FileReader(args[0]);
      BufferedReader br = new BufferedReader(fr);
      for (String line = br.readLine(); line != null; line = br.readLine()) {
         arrayTXT.add(line);
      }

      /* Boucle du tableau des achats - Une ligne à la fois */
      for (int i = 0; i < arrayTXT.size(); ++i) {
         String ligne = arrayTXT.get(i);
         List<String> result = Arrays.asList(ligne.split("\\s*,\\s*"));

         /* Trouve l'enregistrement d'inventaire.XML pour le produit d'achats */
         NodeList nl = racine.getChildNodes();
         for (int k = 0; k < nl.getLength(); ++k) {
            
            /* Only interested in Element Node */
            if (nl.item(k).getNodeType() == Node.ELEMENT_NODE) {
               Element e = (Element) nl.item(k);

               /* Is this product match the product from 'achats.txt' ? */
               String codeProduitNode = e.getAttribute("code");
               String codeProduitAchats = result.get(2);
               if (Objects.equals(codeProduitNode, codeProduitAchats) == true) {
                  int qteInventaire = Integer.parseInt(e.getAttribute("quantite"));
                  qteInventaire = qteInventaire - Integer.parseInt(result.get(3));
                  e.setAttribute("quantite", Integer.toString(qteInventaire));
                  nl.item(k).getParentNode().replaceChild(nl.item(k), e);
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
         StreamResult result1 = new StreamResult(fw);
         transformer.transform(source, result1);
      }
   }
}