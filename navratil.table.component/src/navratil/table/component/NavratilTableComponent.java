/**
 * 
 */
package navratil.table.component;

import java.io.FileNotFoundException;

/**
 * @author nav
 *
 */
public class NavratilTableComponent {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Task 3a
        MarketShareList msList = new MarketShareList("data/data.csv");
        msList.print();
        MarketShareList czech4Q10 = new MarketShareList(msList, "Czech Republic", "2010 Q4");
        czech4Q10.print();
        
        // Task 3b
        VendorUnitsShare dellShare = czech4Q10.getVendorUnitsShare("Dell");
        System.out.println(dellShare.getUnits() + " " + dellShare.getShare() + "%\n");
        
        // Task 3c
        System.out.println("row id: " + (czech4Q10.getVendorIndex("Dell") + 1) + "\n");
        
        // Task 3d
        czech4Q10.sortByVendor();
        czech4Q10.print();
        
        // Task 3e
        czech4Q10.sortByUnits();
        czech4Q10.print();
        
        // Task 3f
        System.out.println(czech4Q10.toHTML());
	}

}
