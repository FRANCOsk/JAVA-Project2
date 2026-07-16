package navratil.table.component;

import com.opencsv.bean.CsvBindByName;

import java.util.Comparator;

public class MarketShareBean {

    @CsvBindByName(column = "Country", required = true)
    private String country;

    @CsvBindByName(column = "Timescale", required = true)
    private String timescale;

    @CsvBindByName(column = "Vendor", required = true)
    private String vendor;

    @CsvBindByName(column = "Units", required = true)
    private double units;

    public String getCountry() {
        return country;
    }

    public String getTimescale() {
        return timescale;
    }

    public String getVendor() {
        return vendor;
    }

    public double getUnits() {
        return units;
    }

    public static Comparator<MarketShareBean> byVendor() {
        return Comparator.comparing(MarketShareBean::getVendor, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<MarketShareBean> byUnits() {
        return Comparator.comparingDouble(MarketShareBean::getUnits);
    }
}
