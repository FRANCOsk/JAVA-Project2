/**
 * 
 */
package navratil.table.component;

/**
 * @author nav
 *
 */
public class VendorUnitsShare {
	private double units;
	private double share;
	
	public VendorUnitsShare(double units, double share) {
		this.units = units;
		this.share = share;
	}

	/**
	 * @return the units
	 */
	public double getUnits() {
		return units;
	}

	/**
	 * @return the share
	 */
	public double getShare() {
		return share;
	}
}
