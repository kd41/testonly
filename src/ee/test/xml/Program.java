package ee.test.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * The program creates information about countries and saves it to file countries.txt.
 */
public class Program {
    private static final String countriesLocation = "C:\\projects\\test\\countries.xml";
    private static final String currenciesLocation = "C:\\projects\\test\\currencies.xml";
    private static final String resultLocation = "C:\\projects\\test\\result.xml";

    public static void main(String... args) throws Exception {
        List<Country> countries = getCountries();
        System.out.println(countries);
        List<Currency> currencies = getCurrencies();
        System.out.println(currencies);

        for (Country country : countries) {
            boolean finded = false;
            List<Currency> foundCurrencies = new ArrayList<Currency>();
            for (Currency currency : currencies) {
                if (country.getName().equalsIgnoreCase(currency.getCountry())) {
                    currency.setHasCountry(true);
                    foundCurrencies.add(currency);
                    finded = true;
                }
            }
            country.setCurrencies(foundCurrencies);
            if (!finded) {
                System.out.println("Not foud currency for: " + country);
                // throw new Exception("Not foud currency for: " + country);
            }
        }

        for (Currency currency : currencies) {
            if (!currency.isHasCountry()) {
                System.out.println("Currency not belong to any country. currency=" + currency);
            }
        }
        System.out.println(countries);

        save(countries);

    }

    private static List<Country> getCountries() {
        List<Country> countries = new ArrayList<Country>();
        try {
            File xmlFile = new File(countriesLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ISO_3166-1_Entry");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = getTextByTagName(eElement, "ISO_3166-1_Country_name");
                    String code = getTextByTagName(eElement, "ISO_3166-1_Alpha-2_Code_element");
                    Country country = new Country();
                    country.setName(name);
                    country.setCode(code);
                    countries.add(country);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    private static List<Currency> getCurrencies() {
        List<Currency> currencies = new ArrayList<Currency>();
        try {
            File xmlFile = new File(currenciesLocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("CcyNtry");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String country = getTextByTagName(eElement, "CtryNm");
                    String name = getTextByTagName(eElement, "CcyNm");
                    String code = getTextByTagName(eElement, "Ccy");
                    String number = getTextByTagName(eElement, "CcyNbr");
                    String minorUtints = getTextByTagName(eElement, "CcyMnrUnts");
                    String information = getTextByTagName(eElement, "AddtlInf");
                    Currency currency = new Currency();
                    currency.setCountry(country);
                    currency.setName(name);
                    currency.setCode(code);
                    currency.setNumber(number);
                    currency.setMinorUtints(minorUtints);
                    currency.setInformation(information);
                    currencies.add(currency);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencies;
    }

    private static String getTextByTagName(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0) != null ? element.getElementsByTagName(tagName).item(0)
                .getTextContent() : "";
    }

    private static void save(List<Country> countries) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.newDocument();
        Element rootEle = dom.createElement("Countries");
        Attr attrDate = dom.createAttribute("date");
        attrDate.setValue("" + new Date());
        rootEle.setAttributeNode(attrDate);
        dom.appendChild(rootEle);
        for (Country country : countries) {
            Element eCountry = dom.createElement("Country");
            Element eName = dom.createElement("Name");
            Element eCode = dom.createElement("Code");
            Text cNameText = dom.createTextNode(country.getName());
            Text cCodeText = dom.createTextNode(country.getCode());
            eName.appendChild(cNameText);
            eCode.appendChild(cCodeText);
            eCountry.appendChild(eName);
            eCountry.appendChild(eCode);
            Element eCurrencies = dom.createElement("Currencies");
            for (Currency currency : country.getCurrencies()) {
                Element eCurrency = dom.createElement("Currency");
                Element ecurName = dom.createElement("Name");
                Text curNameText = dom.createTextNode(currency.getName());
                ecurName.appendChild(curNameText);
                eCurrency.appendChild(ecurName);
                Element ecurCode = dom.createElement("Code");
                Text curCodeText = dom.createTextNode(currency.getCode());
                ecurCode.appendChild(curCodeText);
                eCurrency.appendChild(ecurCode);
                Element ecurNumber = dom.createElement("Number");
                Text curNumberText = dom.createTextNode(currency.getNumber());
                ecurNumber.appendChild(curNumberText);
                eCurrency.appendChild(ecurNumber);
                Element ecurMinorUtints = dom.createElement("MinorUtints");
                Text curMinorUtintsText = dom.createTextNode(currency.getMinorUtints());
                ecurMinorUtints.appendChild(curMinorUtintsText);
                eCurrency.appendChild(ecurMinorUtints);
                Element ecurInformation = dom.createElement("Information");
                Text curInformationText = dom.createTextNode(currency.getInformation());
                ecurInformation.appendChild(curInformationText);
                eCurrency.appendChild(ecurInformation);
                eCurrencies.appendChild(eCurrency);
            }
            eCountry.appendChild(eCurrencies);
            rootEle.appendChild(eCountry);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(dom);
        StreamResult result = new StreamResult(new File(resultLocation));

        transformer.transform(source, result);

        System.out.println("File saved to " + resultLocation);
    }
}
