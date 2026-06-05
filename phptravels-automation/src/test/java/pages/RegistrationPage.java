package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPage extends BasePage {
    
    private final By firstNameField = By.name("first_name");
    private final By lastNameField = By.name("last_name");
    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By cnfpassword    = By.name("confirm_password");
    private final By agreementCheckbox = By.xpath("/html/body/div[2]/div/div/div[2]/form/div[6]/div/div/div");
    private final By submitButton = By.xpath("/html/body/div[2]/div/div/div[2]/form/div[7]/button");
    private final By successMessage = By.xpath("//div[contains(@class,'alert-success')] | //h3[contains(text(),'Welcome')] | //*[contains(text(),'Success')]");

    private final By captchaLabel = By.xpath("//label[contains(text(), 'What is') or contains(text(), 'Security Check')] | //span[contains(text(), '+') or contains(text(), '-')]");
    private final By captchaInputField = By.xpath("//input[contains(@id, 'captcha') or contains(@name, 'captcha') or contains(@placeholder, 'Result') or contains(@class, 'calculate')]");

    public void navigateToRegistrationPage() {
        getDriver().get("https://phptravels.net/signup"); 
    }

    public void enterMandatoryDetails() {
        WebElement initialInput = waitUtil.waitForVisible(firstNameField);
        
        initialInput.clear();
        initialInput.sendKeys("Automation");
        
        type(lastNameField, "Tester");
        type(emailField, "test" + System.currentTimeMillis() + "@travels.com");
        type(passwordField, "Password123");
        type(cnfpassword, "Password123");

        try {
            getDriver().findElement(agreementCheckbox).click();
        } catch (Exception e) {
            WebElement checkboxElement = getDriver().findElement(agreementCheckbox);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkboxElement);
        }

        solveMathCaptchaIfPresent();
    }

    private void solveMathCaptchaIfPresent() {
        try {
            if (getDriver().findElements(captchaLabel).size() > 0) {
                String rawText = getDriver().findElement(captchaLabel).getText();
                System.out.println("[Debug Engine] Detected Captcha Text: " + rawText);

                Pattern pattern = Pattern.compile("(\\d+)\\s*([+\\-*\\/])\\s*(\\d+)");
                Matcher matcher = pattern.matcher(rawText);

                if (matcher.find()) {
                    int num1 = Integer.parseInt(matcher.group(1));
                    String operator = matcher.group(2);
                    int num2 = Integer.parseInt(matcher.group(3));
                    int calculatedResult = 0;

                    if (operator.equals("+")) {
                        calculatedResult = num1 + num2;
                    } else if (operator.equals("-")) {
                        calculatedResult = num1 - num2;
                    } else if (operator.equals("*")) {
                        calculatedResult = num1 * num2;
                    }

                    System.out.println("[Debug Engine] Solved equation: " + num1 + " " + operator + " " + num2 + " = " + calculatedResult);
                    
                    WebElement resultInput = getDriver().findElement(captchaInputField);
                    resultInput.clear();
                    resultInput.sendKeys(String.valueOf(calculatedResult));
                    System.out.println("[Debug Engine] Captcha resolved and filled successfully.");
                }
            }
        } catch (Exception e) {
            System.out.println("[Debug Engine] Note: Captcha verification steps bypassed or field not found: " + e.getMessage());
        }
    }

    public void selectCountryDynamically() {
        System.out.println("[Debug Engine] Country option field skip initialized via Page Object Layer...");
    }

    public void submitRegistration() {
        try {
            org.openqa.selenium.support.ui.WebDriverWait clickWait = new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(5));
            WebElement submitBtn = clickWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(submitButton));
            submitBtn.click();
        } catch (Exception e) {
            System.out.println("[Debug Engine] Standard click blocked. Forcing submission execution track via JavaScript...");
            WebElement submitBtn = getDriver().findElement(submitButton);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", submitBtn);
        }
    }

    public boolean isRegistrationSuccessful() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        return isDisplayed(successMessage);
    }
}