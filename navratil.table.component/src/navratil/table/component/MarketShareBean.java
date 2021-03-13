/**
 * 
 */
package navratil.table.component;

import java.util.Comparator;

import com.opencsv.bean.CsvBind;

/**
 * @author nav
 *
 */
public class MarketShareBean {
	@CsvBind
	private String country;
	@CsvBind
	private String timescale;
	@CsvBind
	private String vendor;
	@CsvBind
	private double units;
	
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * @return the timescale
	 */
	public String getTimescale() {
		return timescale;
	}
	
	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}
	
	/**
	 * @return the units
	 */
	public double getUnits() {
		return units;
	}
	
	// comparator pre zoradenie podla vendora
	static Comparator<MarketShareBean> getVendorComparator() {
        return new Comparator<MarketShareBean>() {
            public int compare(MarketShareBean item1, MarketShareBean item2) {
            	return item1.getVendor().compareTo(item2.getVendor());
            }
        };
    }

	// comparator pre zoradenie podla mnozstva
    static Comparator<MarketShareBean> getUnitsComparator() {
        return new Comparator<MarketShareBean>() {
            public int compare(MarketShareBean item1, MarketShareBean item2) {
            	if (item1.getUnits() == item2.getUnits()) {
            		return 0;
            	}
            	return item1.getUnits() > item2.getUnits() ? 1 : -1;
            }
        };
    }
}
