package StepDefinitions;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageFactory.LinuxPage_PF;

public class MegaLinuxSteps {
	WebDriver driver = null;
	String projectPath;
	String downloadFilePath;

	@Before
	public void browserSetup() {
		projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/resources/drivers/chromedriver.exe");

		downloadFilePath = projectPath + "\\" + "downloads";

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", downloadFilePath);
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.prompt_for_download", false);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		driver.manage().window().maximize();
	}

	@After
	public void teardown() {
		System.out.println("Driver tear down");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.close();
		driver.quit();
	}

	LinuxPage_PF linux;
	int versionCount;
	List<String> versionNames = new ArrayList<>();

	@Given("user is on Mega desktop page")
	public void user_is_on_mega_desktop_page() {
		driver.navigate().to("https://mega.io/desktop,");
	}

	@When("user select Linux button")
	public void user_select_linux_button() {
		linux = new LinuxPage_PF(driver);

		linux.selectLinux();
	}

	@And("download all versions of Linux version")
	public void download_all_versions_of_linux_version() throws InterruptedException {
		linux.selectLinuxList();
		versionCount = linux.getLinuxVersionList().size();
		List<WebElement> versions = linux.getLinuxVersionList();

		for (int i = 0; i < versionCount; i++) {
			System.out.println(i);
			System.out.println("listSelected");

			if (i == 0) {
				System.out.println("openList");
				System.out.println(versions.get(i).getText());
				versionNames.add(versions.get(i).getText());
				System.out.println("versionName:" + versionNames.get(i));
				versions.get(i).click();
			}
			else {
				linux.selectLinuxList();
				System.out.println("openList");
				System.out.println(linux.getSelectedVersion().getText());
				versionNames.add(linux.getSelectedVersion().getText());
				System.out.println("versionName:" + versionNames.get(i));
				linux.getSelectedVersion().click();
			}

			Thread.sleep(2000);
			System.out.println("download");
			linux.clickDownload();
		}
	}

	@Then("all linux versions are stored in local project directory")
	public void all_linux_versions_are_stored_in_local_project_directory() throws Exception {
		Thread.sleep(6000);
		long count = Files.list(Paths.get(downloadFilePath)).filter(p -> p.toFile().isFile()).count();
		System.out.println("count = " + count);
		System.out.println("versionCount = " + versionCount);
		assertEquals((int) count, versionCount);

		for (String name: versionNames) {
			System.out.println("versionName:" + name);
		}
		
		Set<String> set = new HashSet<String>(versionNames);
		assertEquals(set.size(), versionNames.size());
	}

}
