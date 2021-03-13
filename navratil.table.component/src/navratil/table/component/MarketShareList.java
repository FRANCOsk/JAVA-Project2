/**
 * 
 */
package navratil.table.component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * @author nav
 *
 */
public class MarketShareList {
	private List<MarketShareBean> beanList;
	
	// constructor cez nazov subor, pomocou kniznice OpenCSV sa nacita subor do zoznamu List<MarketShareBean>
	public MarketShareList(String fileName) throws FileNotFoundException {
		HeaderColumnNameMappingStrategy<MarketShareBean> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(MarketShareBean.class);
        CsvToBean<MarketShareBean> csvToBean = new CsvToBean<>();
        beanList = csvToBean.parse(strategy, new CSVReader(new FileReader(fileName)));
	}
	
	// copy constructor skopiruje zo zoznamu len vybranu krajinu a stvrtrok
	public MarketShareList(MarketShareList msList, String country, String timescale) {
		beanList = new ArrayList<MarketShareBean>();
		for (MarketShareBean item : msList.getBeanList()) {
			if ( (item.getCountry().compareTo(country) == 0) && (item.getTimescale().compareTo(timescale) == 0) ) {
				beanList.add(item);
			}
		}
	}

	/**
	 * @return the beanList
	 */
	public List<MarketShareBean> getBeanList() {
		return beanList;
	}
	
	// najde podiel vybraneho vendora a vypocita jeho podiel na trhu
	public VendorUnitsShare getVendorUnitsShare(String vendor) {
		double units = 0;
		double total = 0;
		for (MarketShareBean item : beanList) {
			total += item.getUnits();
			if (item.getVendor().compareTo(vendor) == 0) {
				units = item.getUnits();
			}
		}
		return new VendorUnitsShare(units, 100.0 * units / total);
	}
	
	// najde index vendora v zozname
	public int getVendorIndex(String vendor) {
		for (int i = 0; i < beanList.size(); i++) {
			
			if (beanList.get(i).getVendor().compareTo(vendor) == 0) {
				return i;
			}
		}
		return -1;
	}
	
	// zoradi zoznam podla vendora
	public void sortByVendor() {
		Collections.sort(beanList, MarketShareBean.getVendorComparator());
	}
	
	// zoradi zoznam podla mnozstva
	public void sortByUnits() {
		Collections.sort(beanList, MarketShareBean.getUnitsComparator());
	}
	
	// vrati retazec obsahujuci HTML kod zoznamu v tvare tabulky 
	public String toHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<body>\n<table>\n<tr><td>Vendor</td><td>Units</td><td>Share</td></tr>\n");
		double total = 0;
		for (MarketShareBean item : beanList) {
			total += item.getUnits();
		}
		for (MarketShareBean item : beanList) {
			sb.append("<tr><td>");
			sb.append(item.getVendor());
			sb.append("</td><td>");
			sb.append(item.getUnits());
			sb.append("</td><td>");
			sb.append(100.0 * item.getUnits() / total);
			sb.append("%</td></tr>\n");
		}
		sb.append("<tr><td>Total</td><td>");
		sb.append(total);
		sb.append("</td><td>100%</td></tr>\n</table>\n</body>\n");
		return sb.toString();
	}

	// pomocny vypis zoznamu na stdout
	public void print() {
		System.out.println("country timescale vendor units");
		for (MarketShareBean item : beanList) {
			System.out.println(item.getCountry() + " " + item.getTimescale() + " " + item.getVendor() + " " + item.getUnits());
		}
		System.out.println("");
	}
}
