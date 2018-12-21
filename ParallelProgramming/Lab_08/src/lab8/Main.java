package lab8;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, JAXBException, XPathExpressionException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new FileInputStream("C:\\CODE\\KPI-Semester-7\\ParallelProgramming\\Lab_08\\resources\\computer.xml"));
        Element root = document.getDocumentElement();

        Computer domComputer = new Computer();

        NodeList rootChildren = root.getChildNodes();
        for (int i = 0; i < rootChildren.getLength(); ++i) {
            Node node = rootChildren.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element el = (Element) node;
                String name = el.getNodeName();
                if (name.equals("Name")) {
                    domComputer.setName(el.getTextContent());
                } else if (name.equals("Origin")) {
                    domComputer.setOrigin(el.getTextContent());
                } else if (name.equals("Price")) {
                    domComputer.setPrice(Integer.parseInt(el.getTextContent()));
                } else if (name.equals("IsCritical")) {
                    domComputer.setIsCritical(Boolean.parseBoolean(el.getTextContent()));
                }  else if (name.equals("Type")) {
                    Type domType = new Type();
                    NodeList childDomType = el.getChildNodes();
                    for (int j = 0; j < childDomType.getLength(); ++j) {
                        Node charNode = childDomType.item(j);
                        if (charNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element charEl = (Element) charNode;
                            String charName = charEl.getTagName();

                            if (charName.equals("IsPeripheral")) {
                                domType.setIsPeripheral(Boolean.parseBoolean(charEl.getTextContent()));
                            } else if (charName.equals("EnergyConsumption")) {
                                domType.setEnergyConsumption(Integer.parseInt(charEl.getTextContent()));
                            } else if (charName.equals("HasCooler")) {
                                domType.setHasCooler(Boolean.parseBoolean(charEl.getTextContent()));
                            } else if (charName.equals("TypeGroup")) {
                                domType.setTypeGroup(charEl.getTextContent());
                            } else if (charName.equals("Port")) {
                                domType.setPort(charEl.getTextContent());
                            }
                        }
                    }

                    domComputer.addType(domType);

                }
            }
        }

        System.out.println("DOM:");
        System.out.println(domComputer);
        System.out.println();

        JAXBContext context = JAXBContext.newInstance(Computer.class);
        Unmarshaller unm = context.createUnmarshaller();
        Computer jaxbComputer = (Computer) unm.unmarshal(new File("C:\\CODE\\KPI-Semester-7\\ParallelProgramming\\Lab_08\\resources\\computer.xml"));

        System.out.println("JAXB:");
        System.out.println(jaxbComputer);
        System.out.println();

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        Computer xPathComputer = new Computer();
        XPathExpression expr = xPath.compile("/Computer/Name");
        xPathComputer.setName((String) expr.evaluate(document, XPathConstants.STRING));

        expr = xPath.compile("/Computer/Origin");
        xPathComputer.setOrigin((String) expr.evaluate(document, XPathConstants.STRING));

        expr = xPath.compile("/Computer/Price");
        xPathComputer.setPrice(Integer.parseInt((String) expr.evaluate(document, XPathConstants.STRING)));

        expr = xPath.compile("/Computer/IsCritical");
        xPathComputer.setIsCritical(Boolean.parseBoolean((String) expr.evaluate(document, XPathConstants.STRING)));

        Type type = new Type();
        expr = xPath.compile("/Computer/Type/IsPeripheral");
        type.setIsPeripheral(Boolean.parseBoolean((String) expr.evaluate(document, XPathConstants.STRING)));

        expr = xPath.compile("/Computer/Type/EnergyConsumption");
        type.setEnergyConsumption(Integer.parseInt((String) expr.evaluate(document, XPathConstants.STRING)));

        expr = xPath.compile("/Computer/Type/HasCooler");
        type.setHasCooler(Boolean.parseBoolean((String) expr.evaluate(document, XPathConstants.STRING)));

        expr = xPath.compile("/Computer/Type/TypeGroup");
        type.setTypeGroup((String) expr.evaluate(document, XPathConstants.STRING));

        expr = xPath.compile("/Computer/Type/Port");
        type.setPort((String) expr.evaluate(document, XPathConstants.STRING));

        xPathComputer.addType(type);

        System.out.println("xPath:");
        System.out.println(xPathComputer);
        System.out.println();

        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        JAXBContext jsonContext = org.eclipse.persistence.jaxb.JAXBContext.newInstance(Computer.class);
        Marshaller marshaller = jsonContext.createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        System.out.println("JSON:");
        marshaller.marshal(jaxbComputer, System.out);
    }
}
