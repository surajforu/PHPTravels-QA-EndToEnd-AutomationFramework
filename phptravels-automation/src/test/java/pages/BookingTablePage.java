package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookingTablePage extends BasePage {
    // Broadened to search text paths natively
    private final By bookingsLink = By.xpath("//a[contains(@href, 'bookings')] | //*[contains(text(), 'My Bookings')] | //a[contains(text(),'Bookings')]");
    private final By tableRows = By.xpath("//table//tbody/tr");
    private final By tableCells = By.xpath("./td");

    public void openBookingsPage() {
        try {
            // Step 1: Attempt to look up and click the dashboard link menu navigation 
            waitUtil.waitForClickable(bookingsLink).click();
        } catch (Exception e) {
            System.out.println("[Debug Engine] Menu link blocked or hidden by layout context. Initiating URL fallback...");
            
            // Step 2: Extract current baseline domain environment address and clean up tail strings
            String currentUrl = getDriver().getCurrentUrl();
            String baseUrl = currentUrl.replaceAll("(/account/.*|/dashboard.*|/index.*)$", "");
            
            if (!baseUrl.endsWith("/")) {
                baseUrl += "/";
            }
            
            String targetBookingsUrl = baseUrl + "account/bookings";
            System.out.println("[Debug Engine] Forcing direct browser routing instance to: " + targetBookingsUrl);
            getDriver().get(targetBookingsUrl);
        }
    }

    public List<Map<String, String>> getBookingTableData() {
        try { waitUtil.waitForVisible(tableRows); } catch (Exception ignored) {}
        
        List<Map<String, String>> tableData = new ArrayList<>();
        List<WebElement> rows = getDriver().findElements(tableRows);
        
        for (int i = 0; i < rows.size(); i++) {
            try {
                List<WebElement> cells = rows.get(i).findElements(tableCells);
                if (cells.isEmpty()) continue; // Skipping header or empty placeholder rows safely
                
                Map<String, String> rowMap = new HashMap<>();
                rowMap.put("bookingId", cells.size() > 0 ? cells.get(0).getText().trim() : "0");
                rowMap.put("hotelName", cells.size() > 1 ? cells.get(1).getText().trim() : "");
                rowMap.put("amount",    cells.size() > 2 ? cells.get(2).getText().trim() : "0");
                rowMap.put("status",    cells.size() > 3 ? cells.get(3).getText().trim() : "");
                tableData.add(rowMap);
            } catch (Exception e) {
                System.out.println("[Debug Engine] Skipping processing row array block " + i + " due to structural shift.");
            }
        }
        return tableData;
    }

    public boolean hasDuplicateRows() {
        Set<String> uniqueRows = new HashSet<>();
        for (Map<String, String> row : getBookingTableData()) {
            String rowText = row.toString();
            if (!uniqueRows.add(rowText)) {
                return true;
            }
        }
        return false;
    }

    public int getHighestBookingAmount() {
        int highest = 0;
        for (Map<String, String> row : getBookingTableData()) {
            String cleaning = row.get("amount").replaceAll("[^0-9]", "");
            int amount = cleaning.isEmpty() ? 0 : Integer.parseInt(cleaning);
            highest = Math.max(highest, amount);
        }
        return highest;
    }

    public int getLowestBookingAmount() {
        int lowest = Integer.MAX_VALUE;
        for (Map<String, String> row : getBookingTableData()) {
            String cleaning = row.get("amount").replaceAll("[^0-9]", "");
            int amount = cleaning.isEmpty() ? 0 : Integer.parseInt(cleaning);
            lowest = Math.min(lowest, amount);
        }
        return lowest == Integer.MAX_VALUE ? 0 : lowest;
    }
}