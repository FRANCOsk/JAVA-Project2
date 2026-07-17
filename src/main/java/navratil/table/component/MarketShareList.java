package navratil.table.component;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MarketShareList {

    private final List<MarketShareBean> entries;

    public MarketShareList(Path csvFile) throws IOException {
        try (Reader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {
            this.entries = parse(reader);
        }
    }

    private MarketShareList(List<MarketShareBean> entries) {
        this.entries = new ArrayList<>(entries);
    }

    public static MarketShareList fromResource(String resourceName) throws IOException {
        InputStream inputStream = MarketShareList.class.getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new IOException("CSV resource not found: " + resourceName);
        }

        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return new MarketShareList(parse(reader));
        }
    }

    private static List<MarketShareBean> parse(Reader reader) {
        return new CsvToBeanBuilder<MarketShareBean>(reader)
                .withType(MarketShareBean.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withThrowExceptions(true)
                .build()
                .parse();
    }

    public MarketShareList filteredBy(String country, String timescale) {
        Objects.requireNonNull(country, "country");
        Objects.requireNonNull(timescale, "timescale");

        List<MarketShareBean> filtered = entries.stream()
                .filter(item -> item.getCountry().equalsIgnoreCase(country))
                .filter(item -> item.getTimescale().equalsIgnoreCase(timescale))
                .toList();

        return new MarketShareList(filtered);
    }

    public List<MarketShareBean> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public VendorUnitsShare getVendorUnitsShare(String vendor) {
        Objects.requireNonNull(vendor, "vendor");

        double totalUnits = entries.stream()
                .mapToDouble(MarketShareBean::getUnits)
                .sum();

        double vendorUnits = entries.stream()
                .filter(item -> item.getVendor().equalsIgnoreCase(vendor))
                .mapToDouble(MarketShareBean::getUnits)
                .sum();

        double sharePercentage = totalUnits == 0 ? 0 : 100.0 * vendorUnits / totalUnits;
        return new VendorUnitsShare(vendorUnits, sharePercentage);
    }

    public int getVendorIndex(String vendor) {
        Objects.requireNonNull(vendor, "vendor");

        for (int index = 0; index < entries.size(); index++) {
            if (entries.get(index).getVendor().equalsIgnoreCase(vendor)) {
                return index;
            }
        }

        return -1;
    }

    public void sortByVendor() {
        entries.sort(MarketShareBean.byVendor());
    }

    public void sortByUnits() {
        entries.sort(MarketShareBean.byUnits());
    }

    public String toHtml() {
        double totalUnits = entries.stream()
                .mapToDouble(MarketShareBean::getUnits)
                .sum();

        StringBuilder html = new StringBuilder();
        html.append("<table>\n")
                .append("  <thead><tr><th>Vendor</th><th>Units</th><th>Share</th></tr></thead>\n")
                .append("  <tbody>\n");

        for (MarketShareBean item : entries) {
            double share = totalUnits == 0 ? 0 : 100.0 * item.getUnits() / totalUnits;
            html.append("    <tr><td>")
                    .append(escapeHtml(item.getVendor()))
                    .append("</td><td>")
                    .append(String.format(Locale.ROOT, "%.2f", item.getUnits()))
                    .append("</td><td>")
                    .append(String.format(Locale.ROOT, "%.2f%%", share))
                    .append("</td></tr>\n");
        }

        html.append("    <tr><td>Total</td><td>")
                .append(String.format(Locale.ROOT, "%.2f", totalUnits))
                .append("</td><td>100.00%</td></tr>\n")
                .append("  </tbody>\n")
                .append("</table>\n");

        return html.toString();
    }

    public void print(PrintStream output) {
        output.println("Country | Timescale | Vendor | Units");
        entries.forEach(item -> output.printf(
                Locale.ROOT,
                "%s | %s | %s | %.2f%n",
                item.getCountry(),
                item.getTimescale(),
                item.getVendor(),
                item.getUnits()));
    }

    private static String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
