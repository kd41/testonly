package ee.test.xml;

/**
 * http://www.currency-iso.org/dam/downloads/table_a1.xml
 */
public class Currency {
    private String country;
    private String name;
    private String code;
    private String number;
    private String minorUtints;
    private String information;
    private boolean hasCountry;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMinorUtints() {
        return minorUtints;
    }

    public void setMinorUtints(String minorUtints) {
        this.minorUtints = minorUtints;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public boolean isHasCountry() {
        return hasCountry;
    }

    public void setHasCountry(boolean hasCountry) {
        this.hasCountry = hasCountry;
    }

    @Override
    public String toString() {
        return "Currency [country=" + country + ", name=" + name + ", code=" + code + ", number=" + number
                + ", minorUtints=" + minorUtints + ", information=" + information + ", hasCountry=" + hasCountry + "]";
    }
}
