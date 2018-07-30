package extentReport;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportBase {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;


	@BeforeTest
	public void setUp()
	{
	    //where we need to generate the report
	    htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/MyReport.html");
	    extent = new ExtentReports();
	    extent.attachReporter(htmlReporter);

	    // Set our document title, theme etc..
	    htmlReporter.config().setDocumentTitle("My Test Report");
	    htmlReporter.config().setReportName("Test Report");
	    htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	    htmlReporter.config().setTheme(Theme.STANDARD); 

	}

	@AfterMethod
	public void getResult(ITestResult result)
	{
		int resultStatus = result.getStatus();
		System.out.println(resultStatus);
	    if(result.getStatus()==ITestResult.FAILURE)
	    {
	        test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + "Test Case failed due to below issues", ExtentColor.RED));
	        test.fail(result.getThrowable());
	    }

	    else if(result.getStatus()==ITestResult.SUCCESS)
	    {
	        test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + "Test Case Passed", ExtentColor.GREEN));
	    }

	    else
	    {
	        test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + "Test Case skipped", ExtentColor.YELLOW));
	    }

	}

	@AfterSuite
	public void tearDown()
	{
	    extent.flush();
	}
}
