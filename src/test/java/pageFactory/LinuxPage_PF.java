package pageFactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinuxPage_PF {
	@FindBy(xpath = "//a[@data-os='linux']")
	WebElement btn_linux;
	
	@FindBy(xpath = "(//div[contains(@class, 'mega-input')])[1]")
	WebElement btn_linuxList;
	
	@FindBy(xpath = "(//div[contains(@class, 'ps--active')])[1]/div[@class='option']")
	List<WebElement> linuxVersionList;
	
	@FindBy(xpath = "(//div[contains(@class, 'ps--active')])[1]/div[contains(@class, 'active')]/following-sibling::div[1]")
	WebElement selectedVersion;
	
	@FindBy(xpath = "(//button[text()=' Download '])[1]")
	WebElement btn_download;
	
	WebDriver driver;
	
	public LinuxPage_PF(WebDriver driver) {
		this.driver = driver;

		PageFactory.initElements(driver, this);
	}
	
	public void selectLinux() {
		btn_linux.click();
		
	}
	
	public void selectLinuxList() {
		btn_linuxList.click();
	}
	
	public List<WebElement> getLinuxVersionList() {
		
		return linuxVersionList;
	}
	
	public WebElement getSelectedVersion() {
		
		return selectedVersion;
	}
	
	public void clickDownload() throws InterruptedException {
		btn_download.click();
	}
}
