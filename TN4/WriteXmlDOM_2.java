/**
*** INF 6450 -  Travail noté 4 - Robert M. Vigneault,  fait avec JDK java 14.0.2
**/
package TN4;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WriteXmlDOM_2 {
   public static void main(String[] args) throws ParserConfigurationException, TransformerException {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
      dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      // Create 'root' element
      Element rootElement = doc.createElement("employees");
      doc.appendChild(rootElement);
      
      // Append first child element to root element
      rootElement.appendChild(buildEmployee(doc, "1", "Robert", "63", "Future Java Developer", "Male"));

      // Append second child
      rootElement.appendChild(buildEmployee(doc, "2", "Marie", "60", "House Wife", "Female"));

      // For output to fileXML, console
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      
      // For pretty print
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);

      // Write to console AND fileXML
      StreamResult console = new StreamResult(System.out);
      StreamResult fileXML = new StreamResult(new File("emps.xml"));

      // Final Write
      System.out.print("\n");
      transformer.transform(source, console);
      transformer.transform(source, fileXML);
      System.out.println("DONE\n");
   }

   private static Node buildEmployee(Document doc, String id, String name, String age,
                                                String role, String gender) {
      Element employee = doc.createElement("Employee");

      employee.setAttribute("id", id);
      employee.appendChild(getEmployeeElements(doc, employee, "name", name));
      employee.appendChild(getEmployeeElements(doc, employee, "age", age));
      employee.appendChild(getEmployeeElements(doc, employee, "role", role));
      employee.appendChild(getEmployeeElements(doc, employee, "gender", gender));

      return employee;
   }

   // Utility method to create text node
   private static Node getEmployeeElements(Document doc, Element element, String name, String value) {
      Element node = doc.createElement(name);
      node.appendChild(doc.createTextNode(value));
      return node;
   }
}