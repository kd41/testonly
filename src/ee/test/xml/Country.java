package ee.test.xml;

import java.util.List;

/**
 * http://www.iso.org/iso/home/standards/country_codes/country_names_and_code_elements_xml.htm
 */
public class Country {
    private String name;
    private String code;
    private List<Currency> currencies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "Country [name=" + name + ", code=" + code + ", currencies=" + currencies + "]";
    }

}
