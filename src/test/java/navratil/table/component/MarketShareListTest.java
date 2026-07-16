package navratil.table.component;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketShareListTest {

    @Test
    void filtersEntriesAndCalculatesVendorShare() throws IOException {
        MarketShareList marketShares = MarketShareList.fromResource("data.csv");
        MarketShareList filtered = marketShares.filteredBy("Czech Republic", "2010 Q4");

        assertEquals(7, filtered.getEntries().size());

        VendorUnitsShare dellShare = filtered.getVendorUnitsShare("Dell");
        assertEquals(11455.09902, dellShare.units(), 0.00001);
        assertTrue(dellShare.sharePercentage() > 50.0);
    }

    @Test
    void sortsByVendorAndUnits() throws IOException {
        MarketShareList filtered = MarketShareList.fromResource("data.csv")
                .filteredBy("Czech Republic", "2010 Q4");

        filtered.sortByVendor();
        assertEquals("ASUS", filtered.getEntries().getFirst().getVendor());

        filtered.sortByUnits();
        assertEquals("Apple", filtered.getEntries().getFirst().getVendor());
    }

    @Test
    void generatesEscapedHtmlTable() throws IOException {
        MarketShareList filtered = MarketShareList.fromResource("data.csv")
                .filteredBy("Czech Republic", "2010 Q4");

        String html = filtered.toHtml();

        assertTrue(html.startsWith("<table>"));
        assertTrue(html.contains("<th>Vendor</th>"));
        assertTrue(html.contains("100.00%"));
    }
}
