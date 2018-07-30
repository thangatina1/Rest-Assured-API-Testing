package testExtentReport;

import extentReport.ExtentReportBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass2 extends ExtentReportBase{

	@Test
	public void functionality2Test1()
	{
	    test = extent.createTest("functionality2Test1");
	    Assert.assertTrue(1 > 0);
	}

	@Test
	public void functionality2Test2()
	{
	    test = extent.createTest("functionality2Test2");
	    Assert.assertEquals("Google", "goo");
	}

	@Test
	public void functionality2Test3()
	{
	    test = extent.createTest("functionality2Test3");
	    Assert.assertNotEquals("Google", "Google");
	}
}
