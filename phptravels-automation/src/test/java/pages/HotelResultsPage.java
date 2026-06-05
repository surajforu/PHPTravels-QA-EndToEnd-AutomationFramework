package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HotelResultsPage extends BasePage {
    
    // Updated to match modern stay card listings, anchors, and dynamic search row outputs
    private final By hotelCards = By.xpath(
        "//div[contains(@class, 'hotel-card')] | //div[contains(@class, 'stay-card')] " +
        "| //div[contains(@class, 'card')]//h5/ancestor::div[contains(@class, 'card')] " +
        "| //div[@id='data_results']//div[contains(@class, 'card')] | //div[contains(@class, 'product-body')]"
    );
    
    // Relative Locators adjusted to scan common modern headers and typography wrappers
 // Relative Locators adjusted to grab ANY dominant text block or title wrapper within the card
    private final By relativeHotelName = By.xpath(
        ".//h3 | .//h4 | .//h5 | .//b[contains(@class,'title')] " +
        "| .//div[contains(@class,'title')] | .//a[contains(@class,'title')] " +
        "| .//div[contains(@class,'name')] | .//strong"
    );
    
    private final By relativeHotelPrice = By.xpath(
        ".//span[contains(@class,'price')] | .//span[contains(@class,'rate')] " +
        "| .//div[contains(@class,'price')] | .//b | .//strong[contains(text(),'$')] " +
        "| .//*[contains(text(),'$')] | .//span[contains(text(),'USD')]"
    );
    private final By firstHotelSelect = By.xpath("(//a[contains(text(), 'Book') or contains(text(), 'View') or contains(text(), 'Details') or @type='submit'])[1]");

    private List<Integer> cachedPrices = null;
    private List<String> cachedNames = null;

    private synchronized void parseHotelDataContainers() {
        if (cachedPrices != null && cachedNames != null) return;

        try { waitUtil.waitForVisible(hotelCards); } catch (Exception ignored) {}

        cachedPrices = new ArrayList<>();
        cachedNames = new ArrayList<>();
        
        List<WebElement> cards = getDriver().findElements(hotelCards);
        System.out.println("[Debug Engine] Located total raw card elements on screen: " + cards.size());
        
        int cardIndex = 1;
        for (WebElement card : cards) {
            try {
                String name = "";
                try {
                    name = card.findElement(relativeHotelName).getText().trim();
                } catch (Exception e) {
                    name = "Unknown Hotel #" + cardIndex;
                }

                String priceText = "";
                try {
                    priceText = card.findElement(relativeHotelPrice).getText().replaceAll("[^0-9]", "");
                } catch (Exception e) {
                    priceText = "0";
                }

                // Fallback if price is still a blank string after regex cleaning
                if (priceText.isEmpty()) {
                    priceText = "0";
                }

                System.out.println("[Card " + cardIndex + "] Extracted -> Name: '" + name + "' | Price: '" + priceText + "'");

                // We only require a valid name string now to count the hotel entry
                if (!name.isEmpty()) {
                    cachedNames.add(name);
                    cachedPrices.add(Integer.parseInt(priceText));
                }
            } catch (Exception e) {
                System.out.println("[Card " + cardIndex + "] Completely failed parsing due to: " + e.getMessage());
            }
            cardIndex++;
        }
        System.out.println("[Debug Engine] Final breakdown: Successfully cached " + cachedNames.size() + " hotel entries.");
    }
    public boolean areResultsDisplayed() {
        try {
            return waitUtil.waitForVisible(hotelCards) != null;
        } catch (Exception e) {
            return getDriver().findElements(hotelCards).size() > 0;
        }
    }

    public int getAvailableHotelsCount() {
        parseHotelDataContainers();
        return cachedNames.size();
    }

    public List<Integer> getAllHotelPrices() {
        parseHotelDataContainers();
        return cachedPrices;
    }

    public int getHighestPrice() {
        return getAllHotelPrices().stream().max(Integer::compareTo).orElse(0);
    }

    public int getLowestPrice() {
        return getAllHotelPrices().stream().min(Integer::compareTo).orElse(0);
    }

    public double getAveragePrice() {
        List<Integer> prices = getAllHotelPrices();
        if (prices.isEmpty()) return 0.0;
        
        return prices.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public Map<String, Integer> getHotelPriceMap() {
        parseHotelDataContainers();
        Map<String, Integer> hotelPriceMap = new HashMap<>();
        for (int i = 0; i < cachedNames.size(); i++) {
            hotelPriceMap.put(cachedNames.get(i), cachedPrices.get(i));
        }
        return hotelPriceMap;
    }

    public boolean hasDuplicateHotelNames() {
        parseHotelDataContainers();
        Set<String> uniqueNames = new HashSet<>();
        for (String name : cachedNames) {
            if (!uniqueNames.add(name)) {
                return true;
            }
        }
        return false;
    
    }
    public void selectFirstHotel() {
        // A comprehensive locator that catches any button on the first item in the results listing
        By firstHotelBtn = By.xpath(
            "(//a[contains(@href, 'hotel') or contains(text(), 'Book') or contains(text(), 'View') or contains(text(), 'Details') or contains(text(), 'Select')])[1]" + 
            " | (//button[contains(@class, 'btn') or @type='submit'])[1]"
        );
        
        try {
            // 1. Explicitly wait for any dynamic page loaders to clear out, then wait for the element
            System.out.println("[Debug Engine] Waiting for hotel results listing card to settle...");
            
            // Using your project's custom utility tracker if applicable, otherwise a standard wait
            org.openqa.selenium.support.ui.WebDriverWait pageWait = new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(15));
            pageWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(firstHotelBtn));
            
            // 2. Perform native click
            getDriver().findElement(firstHotelBtn).click();
            System.out.println("[Debug Engine] Successfully clicked the first hotel listing.");
            
        } catch (Exception e) {
            System.out.println("[Debug Engine] Native element click intercepted or timed out. Injecting DOM selection fallback...");
            
            // 3. Absolute DOM fallback bypasses rendering layout quirks completely
            try {
                org.openqa.selenium.WebElement element = getDriver().findElement(firstHotelBtn);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
                System.out.println("[Debug Engine] DOM selection override completed successfully.");
            } catch (Exception fatalEx) {
                throw new org.openqa.selenium.TimeoutException("Could not resolve or click the hotel selection button container.", fatalEx);
            }
        }}}     
    