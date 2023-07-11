package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Product {

	public static void main(String[] args) throws InterruptedException, Exception {
		/*utilities : property file*/
		String path = "./src/main/resources/Commondata.properties";
		FileInputStream fis= new FileInputStream(path);
		Properties p= new Properties();
		p.load(fis);
		
		
		WebDriver driver=null ;
		//String browser=p.getProperty("browser");
		String browser=System.getProperty("browser", p.getProperty("browser"));
		/* Open Browser*/
		if(browser.equalsIgnoreCase("chrome")) {
			driver= new ChromeDriver();
		}else if(browser.equalsIgnoreCase("edge")) {
			driver=new EdgeDriver();
		}else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//Login to the application
		driver.get(System.getProperty("url", p.getProperty("url")));
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(System.getProperty("username", p.getProperty("username")));
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(System.getProperty("password", p.getProperty("password")));
		driver.findElement(By.xpath("//input[@id='submitButton']")).click();
		
		//Click on Product and create a product by adding vendor
				//click on product tab
				driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Products']")).click();
				//click on create product
				driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
				//Give Product name
				Random s= new Random();
				int str=s.nextInt();
				String productname="CI cover"+str;
				driver.findElement(By.xpath("//input[@name='productname']")).sendKeys(productname);
				//click on productactive check box
				WebElement checkbox = driver.findElement(By.xpath("//input[@name='discontinued']"));
				if (checkbox.isSelected()) {

				}
				else
					checkbox.click();
				//part number: 101
				driver.findElement(By.xpath("//input[@id='productcode']")).sendKeys("101");
				//give sales start date 2023-08-21
				driver.findElement(By.xpath("//img[@id='jscal_trigger_sales_start_date']")).click();
				 String monthyear = driver.findElement(By.xpath("//td[@class='title']")).getText();
				String month = monthyear.split(",")[0].trim();
				String year = monthyear.split(",")[1].trim();
				while( !((month.equalsIgnoreCase("august")) && (year.equalsIgnoreCase("2023")))) {
					driver.findElement(By.xpath("//td[contains(text(),'›')]")).click();
					 monthyear = driver.findElement(By.xpath("//td[@class='title']")).getText();
					 month = monthyear.split(",")[0].trim();
					 year = monthyear.split(",")[1].trim();
				}
				
				//driver.findElement(By.xpath("//img[@id='jscal_trigger_sales_start_date']")).click();
				driver.findElement(By.xpath("//td[normalize-space()='21']")).click();
				//sales end date
				//give sales end date 2023-10-21
				driver.findElement(By.xpath("//img[@id='jscal_trigger_sales_end_date']")).click();
				 String monthyear1 = driver.findElement(By.xpath("(//td[@class='title'])[2]")).getText();
				String month1 = monthyear1.split(",")[0].trim();
				String year1 = monthyear1.split(",")[1].trim();
				while( !((month1.equalsIgnoreCase("october")) && (year1.equalsIgnoreCase("2023")))) {
					driver.findElement(By.xpath("(//td[contains(text(),'›')])[2]")).click();
					 monthyear1 = driver.findElement(By.xpath("(//td[@class='title'])[2]")).getText();
					 month1 = monthyear1.split(",")[0].trim();
					 year1 = monthyear1.split(",")[1].trim();
				}
				
				driver.findElement(By.xpath("(//td[@class='title'])[2]/../../..//td[.='21']")).click();
				Thread.sleep(6000);
				//Select Manufacturer:		AltvetPet Inc.
				driver.findElement(By.xpath("//select[@name='manufacturer']")).click();
				driver.findElement(By.xpath("//select[@name='manufacturer']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
				//select product category : Hardware
				driver.findElement(By.xpath("//select[@name='productcategory']")).click();
				driver.findElement(By.xpath("//select[@name='productcategory']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
				//select vendor name
				// click on add vendor icon
				driver.findElement(By.xpath("//img[@title='Select']")).click();
				//get window handles
				String pwh = driver.getWindowHandle();
				Set<String> allwh = driver.getWindowHandles();

				for (String string : allwh) {
					if(!(pwh.contains(string))) {
						driver.switchTo().window(string);
						driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys("Test134");
						driver.findElement(By.xpath("//input[@name='search']")).click();
						driver.findElement(By.xpath("//a[.='Test134']")).click();
					}
				}
				driver.switchTo().window(pwh);
				//give vendor part number:403
				driver.findElement(By.xpath("//input[@id='vendor_part_no']")).sendKeys("403");
				//give unit price: 10
				driver.findElement(By.xpath("//input[@id='unit_price']")).sendKeys("10");
				//give qty in stock:100
				driver.findElement(By.xpath("//input[@id='qtyinstock']")).sendKeys("100");
				//give qty on demand:20
				driver.findElement(By.xpath("//input[@id='qtyindemand']")).sendKeys("20");
				//click on savebutton
				driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
				//Confirmation Product created
				String confirmproduct = driver.findElement(By.xpath("//span[@class='small']")).getText();
				if (confirmproduct.contains("Updated today")) {
					System.out.println("Created a Product");
				}
				else
					System.out.println("Failed to create a Product");

	}
}
