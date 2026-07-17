package navratil.table.component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public final class NavratilTableComponent {

    private NavratilTableComponent() {
    }

    public static void main(String[] args) throws IOException {
        MarketShareList marketShares = args.length == 0
                ? MarketShareList.fromResource("data.csv")
                : new MarketShareList(Path.of(args[0]));

        MarketShareList czechFourthQuarter =
                marketShares.filteredBy("Czech Republic", "2010 Q4");

        czechFourthQuarter.sortByUnits();
        czechFourthQuarter.print(System.out);

        VendorUnitsShare dellShare = czechFourthQuarter.getVendorUnitsShare("Dell");
        System.out.printf(
                Locale.ROOT,
                "%nDell units: %.2f, market share: %.2f%%%n%n",
                dellShare.units(),
                dellShare.sharePercentage());

        System.out.println(czechFourthQuarter.toHtml());
    }
}
