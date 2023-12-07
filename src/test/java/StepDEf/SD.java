package StepDEf;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
public class SD {
		WebDriver driver;
		private ClientApi clientApi;
	    private String targetWebsiteURL = "https://ginandjuice.shop/"; // Replace with the actual website URL.
	    public static Proxy proxy;
	    public static ApiResponse apiResponse;
	    public static final String zapAddress = "127.0.0.1";
	    public static final int zapPort = 8084;
	    public static final String apiKey = "c0kn6rmr26kfem1caklac9n78t";
	    @Given("I have the {string} website URL")
	    public void i_have_the_website_url(String string) {
	    	 clientApi = new ClientApi(zapAddress, zapPort, apiKey);
	         proxy = new Proxy().setSslProxy(zapAddress + ":" + zapPort).setHttpProxy(zapAddress + ":" + zapPort);
	    	System.setProperty("webdriver.chrome.driver", "/Users/ashwi/eclipse-workspace/Vajro/ChromeDriver/chromedriver"); 
	        ChromeOptions chromeOptions = new ChromeOptions();
	        chromeOptions.addArguments("--remote-allow-origins=*");
	        chromeOptions.setProxy(proxy);
	        chromeOptions.setAcceptInsecureCerts(true);
	        driver = new ChromeDriver(chromeOptions);
	        driver.get(targetWebsiteURL);
	    }
	    @When("I run a passive security scan on the website")
	    public void i_run_a_passive_security_scan_on_the_website() throws InterruptedException {
	   	 
	        try {
	            apiResponse = clientApi.pscan.recordsToScan();
	            String tempVal = ((ApiResponseElement) apiResponse).getValue();
	            while (!tempVal.equals("0")) {
	            	System.out.println("passive scan is in progress");
	            	Thread.sleep(5000);
	                apiResponse = clientApi.pscan.recordsToScan();
	                tempVal = ((ApiResponseElement) apiResponse).getValue();
	            }
	            System.out.println("passive scan is completed");
	        } catch (ClientApiException e) {
	            e.printStackTrace();
	        }
	    }
	        
	    @Then("I should receive a security report for {string}")
	    public void i_should_receive_a_security_report_for(String targetURL) {
	    	if(clientApi !=null);
	        String title = "Security Report";
	        String template = "traditional-html";
	        String description = "Security report";
	        String reportFilename = "security_report.html";
	        String targetFolder = System.getProperty("user.dir"); // Set the directory to save the report
	       try
	       {
	    	   ApiResponse response =clientApi.reports.generate(title, template, null, description, null, null, null, null, null, reportFilename, null, null, targetFolder);
	    	   System.out.println("ZAP report generated at location:" + response.toString());
	       }
	       catch(ClientApiException e) {
	    	   e.printStackTrace();
	       }
	       
	       
	        driver.quit();
	    }
}  
	    