package practice;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

public class CampaignSystem {

	@Test
	public void createcCampaign() throws Exception {
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

		//Click on vendors
		WebElement more = driver.findElement(By.xpath("//a[.='More']"));
		Actions a=new Actions(driver);
		a.moveToElement(more).perform();
		driver.findElement(By.name("Vendors")).click();

		//create a vendor
		driver.findElement(By.xpath("//img[@title='Create Vendor...']")).click();
		//vendor name
		String str=RandomString.make(3);
		String ven="vend"+str;
		driver.findElement(By.name("vendorname")).sendKeys(ven);
		//vendor email
		driver.findElement(By.id("email")).sendKeys("abc@gmail.com");
		//category
		driver.findElement(By.id("category")).sendKeys("Marketing");
		//phone
		driver.findElement(By.id("phone")).sendKeys("9876543210");
		//website
		driver.findElement(By.xpath("//input[@name='website']")).sendKeys("www."+ven+".com");
		//address information
		//street
		driver.findElement(By.xpath("//textarea[@name='street']")).sendKeys("abc123 street");
		//city
		driver.findElement(By.id("city")).sendKeys("Bengaluru");
		//postal code
		driver.findElement(By.id("postalcode")).sendKeys("345678");
		//PObox
		driver.findElement(By.id("pobox")).sendKeys("123456");
		//state
		driver.findElement(By.id("state")).sendKeys("Karnataka");
		//country
		driver.findElement(By.id("country")).sendKeys("India");
		//description
		driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("Testing description");
		//click on save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		WebElement check = driver.findElement(By.xpath("//span[contains(text(),'"+ven+" -  Vendor Information')]"));
		if(check.isDisplayed()) {
			System.out.println("Vendor is created");
		}
		else {
			System.out.println("Vendor is not created");
		}




		//create a product
		driver.findElement(By.xpath("//a[.='Products']/ancestor::td[@class='tabUnSelected']")).click();
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		String pro="prod"+str;
		driver.findElement(By.name("productname")).sendKeys(pro);
		
		
		 //sales start date
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
		
		//product category
		WebElement prod = driver.findElement(By.name("productcategory"));
		prod.click();
		Select s=new Select(prod);
		s.selectByValue("Hardware");
	
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
		 

		//add vendor name
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		String pwh = driver.getWindowHandle();
		Set<String> allwh = driver.getWindowHandles();
		for(String wh:allwh)
		{
			driver.switchTo().window(wh);
		}

		driver.findElement(By.id("search_txt")).sendKeys(ven);
		WebElement in = driver.findElement(By.xpath("//select[@class='txtBox']"));
		in.click();
		Select s1=new Select(in);
		s1.selectByValue("vendorname");

		driver.findElement(By.xpath("//input[@class='crmbutton small create']")).click();
		driver.findElement(By.xpath("//a[.='"+ven+"']")).click();
		//to save
		driver.switchTo().window(pwh);
		driver.findElement(By.xpath("//input[@class='crmbutton small save']")).click();
		//verify if product is created
		WebElement prodCheck = driver.findElement(By.xpath("//span[contains(text(),'Updated today')]"));
		if(prodCheck.isDisplayed()) {
			System.out.println("Product is created");
		}
		else {
			System.out.println("Product is not created");
		}



		//create a campaign
		WebElement more1 = driver.findElement(By.xpath("//a[.='More']"));
		a.moveToElement(more1).perform();
		driver.findElement(By.name("Campaigns")).click();
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();
		//campaign name
		String camp="camp"+str;
		driver.findElement(By.name("campaignname")).sendKeys(camp);
		//campaign type
		WebElement type = driver.findElement(By.name("campaigntype"));
		type.click();
		Select s2=new Select(type);
		s2.selectByValue("Advertisement");
		//target audience
		driver.findElement(By.id("targetaudience")).sendKeys("Kids");
		//sponsor
		driver.findElement(By.id("sponsor")).sendKeys("Nestle");
		//num sent
		driver.findElement(By.id("numsent")).sendKeys("75");
		//add product
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		String pwh1 = driver.getWindowHandle();
		Set<String> allwh1 = driver.getWindowHandles();
		for(String wh1:allwh1)
		{
			driver.switchTo().window(wh1);
		}

		driver.findElement(By.id("search_txt")).sendKeys(pro);
		WebElement in1 = driver.findElement(By.xpath("//select[@name='search_field']"));
		in1.click();
		Select s3=new Select(in1);
		s3.selectByValue("productname");
		driver.findElement(By.xpath("//input[@name='search']")).click();
		driver.findElement(By.xpath("//a[.='"+pro+"']")).click();
		//back to parent window
		driver.switchTo().window(pwh1);
		driver.findElement(By.id("targetsize")).sendKeys("500");
		//budget cost
		WebElement bcost = driver.findElement(By.name("budgetcost"));
		bcost.clear();
		bcost.sendKeys("50000");
		//expected response
		WebElement res = driver.findElement(By.name("expectedresponse"));
		Select s4=new Select(res);
		s4.selectByValue("Good");
		//expected sales count
		driver.findElement(By.id("expectedsalescount")).sendKeys("5000");
		//Expected Response Count
		driver.findElement(By.id("expectedresponsecount")).sendKeys("4000");
		//actual cost
		WebElement acost = driver.findElement(By.name("actualcost"));
		acost.clear();
		acost.sendKeys("47900");
		//expected revenue
		WebElement rev = driver.findElement(By.name("expectedrevenue"));
		rev.clear();
		rev.sendKeys("100000");
		//description
		driver.findElement(By.name("description")).sendKeys("Testing description");
		//save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		//verify campaign is created
		WebElement campCheck = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		String text = campCheck.getText();
		if(text.contains(camp)) {
			System.out.println("Campaign is created and verified: PASS");
		}
		else {
			System.out.println("Campaign is not created: FAIL");
		}
        //logout of the application
		WebElement logout = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		a.moveToElement(logout).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		driver.close();
		System.out.println("Campaign Completed");
		System.out.println("Testscript passed");

	}

}
