/**
*** INF 6450 -  Travail noté 4 - Robert M. Vigneault,  fait avec JDK java 14.0.2
**/

import org.w3c.dom.*;
import javax.xml.parsers.*;
System.setProperty("file.encoding","ISO-8859-1");

public class Transactions {
  public static void main(String[] args) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder parser = factory.newDocumentBuilder();
    Document docXML = parser.parse(args[0]);

    Element racine = docXML.getDocumentElement();

    NodeList nlClients = racine.getElementsByTagName("client"); // node des 'client'
    System.out.println("\n");
    int somme = 0;

    for (int i = 0; i < nlClients.getLength(); ++i) { // pour tous les 'client'
      Element unClient = (Element) nlClients.item(i);
      System.out.println("Nom du client: " + unClient.getAttribute("nom"));
      NodeList nlTransactions = unClient.getElementsByTagName("transaction");
      somme = 0;
      for (int j = 0; j < nlTransactions.getLength(); ++j) { // pour toutes les transactions d'un 'client'
        Element uneTransaction = (Element) nlTransactions.item(j);
        int montant = Integer.parseInt(uneTransaction.getAttribute("montant"));
        somme += montant;
      }
      System.out.println("Somme: " + somme);
    }
    System.out.println("\n");
  }
}
