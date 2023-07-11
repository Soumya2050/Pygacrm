package practice;

import java.io.FileInputStream;
import java.io.IOException;
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

import net.bytebuddy.utility.RandomString;

public class OpportunitySystem {

	public static void main(String[] args) throws InterruptedException, IOException {
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

		//create organization
		driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Organizations']")).click();
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		//organization name
		String str=RandomString.make(3);
		String org="org"+str;
		driver.findElement(By.name("accountname")).sendKeys(org);
		//website
		driver.findElement(By.xpath("//input[@name='website']")).sendKeys("www."+org+".com");
		//ticker symbol
		driver.findElement(By.id("tickersymbol")).sendKeys("ORG");
		//employees
		driver.findElement(By.id("employees")).sendKeys("120");
		//industry
		WebElement ind = driver.findElement(By.name("industry"));
		ind.click();
		Select s=new Select(ind);
		s.selectByValue("Food & Beverage");
		//type
		WebElement type = driver.findElement(By.name("accounttype"));
		type.click();
		Select s1=new Select(type);
		s1.selectByValue("Customer");

		//phone
		driver.findElement(By.id("phone")).sendKeys("6789012345");
		//fax
		driver.findElement(By.id("fax")).sendKeys("111-222-3333");
		//email
		driver.findElement(By.id("email1")).sendKeys(org+"@mail.com");
		//address
		driver.findElement(By.name("bill_street")).sendKeys("street1");
		driver.findElement(By.id("bill_pobox")).sendKeys("city post-345261");
		driver.findElement(By.id("bill_city")).sendKeys("Bengaluru");
		driver.findElement(By.id("bill_state")).sendKeys("Karnataka");
		driver.findElement(By.id("bill_code")).sendKeys("345261");
		driver.findElement(By.id("bill_country")).sendKeys("India");
		driver.findElement(By.xpath("//input[@onclick='return copyAddressRight(EditView)']")).click();

		//description
		driver.findElement(By.name("description")).sendKeys("Testing description");
		//save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();


		System.out.println("org created");

		//create a contact
		Thread.sleep(3000);
		driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Contacts']")).click();
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		String lname="lna"+str;
		driver.findElement(By.name("lastname")).sendKeys(lname);
		driver.findElement(By.xpath("//td[normalize-space()='Organization Name']/..//img[@alt='Select']")).click();
		String pwh = driver.getWindowHandle();
		Set<String> allwh = driver.getWindowHandles();
		for(String wh:allwh)
		{
			driver.switchTo().window(wh);
		}
		driver.findElement(By.id("search_txt")).sendKeys(org);
		WebElement in = driver.findElement(By.name("search_field"));
		in.click();
		Select s2=new Select(in);
		s2.selectByVisibleText("Organization Name");
		driver.findElement(By.xpath("//input[@name='search']")).click();
		driver.findElement(By.xpath("//a[.='"+org+"']")).click();
		driver.switchTo().window(pwh);
		driver.findElement(By.xpath("//input[@class='crmbutton small save']")).click();

		WebElement check = driver.findElement(By.xpath("//span[contains(text(),'"+lname+"  -  Contact Information')]"));
		if(check.isDisplayed()) {
			System.out.println("Contact is created");
		}
		else {
			System.out.println("Contact is not created");
		}

		//create opportunity
		Thread.sleep(3000);
		driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Opportunities']")).click();
		driver.findElement(By.xpath("//img[@title='Create Opportunity...']")).click();
		String opp="oppo"+str;
		driver.findElement(By.name("potentialname")).sendKeys(opp);
		WebElement related = driver.findElement(By.id("related_to_type"));
		Select s3=new Select(related);
		s3.selectByValue("Contacts");
		driver.findElement(By.xpath("//input[@id='related_to_display']/../img[@title='Select']")).click();
		String pwh1 = driver.getWindowHandle();
		Set<String> allwh1 = driver.getWindowHandles();
		for(String wh1:allwh1)
		{
			driver.switchTo().window(wh1);
		}
		driver.findElement(By.id("search_txt")).sendKeys(lname);
		WebElement in1 = driver.findElement(By.name("search_field"));
		in1.click();
		Select s4=new Select(in1);
		s4.selectByValue("lastname");
		driver.findElement(By.xpath("//input[@class='crmbutton small create']")).click();
		driver.findElement(By.xpath(" //a[contains(text(),'"+lname+"')]")).click();
		driver.switchTo().window(pwh1);
		driver.findElement(By.xpath("//input[@class='crmbutton small save']")).click();

		WebElement check1 = driver.findElement(By.xpath("//span[contains(text(),'"+opp+" -  Opportunity Information')]"));
		if(check1.isDisplayed()) {
			System.out.println("Opportunity is created");
		}
		else {
			System.out.println("Opportunity is not created");
		}

		//logout of the application
		WebElement logout = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions a=new Actions(driver);
		a.moveToElement(logout).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();



		System.out.println("Testscript passed");


		driver.close();



	}

}
