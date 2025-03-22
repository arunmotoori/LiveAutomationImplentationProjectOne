package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class CommonUtilities {
	
	public static final int MIN_TIME = 3;
	public static final int AVERAGE_TIME = 10;
	public static final int MAX_TIME = 30;

	public static Properties loadPropertiesFile() {

		Properties prop = new Properties();
		try {
			FileReader fr = new FileReader(
					System.getProperty("user.dir") + "\\src\\test\\resources\\projectdata.properties");
			prop.load(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;

	}
	
	public static Object[][] getTestData(MyXLSReader xls_received, String testName, String sheetName) {

		MyXLSReader xls = xls_received;

		String testCaseName = testName;

		String testDataSheet = sheetName;

		int testStartRowNumber = 1;

		while (!(xls.getCellData(testDataSheet, 1, testStartRowNumber).equals(testCaseName))) {

			testStartRowNumber++;

		}

		int columnStartRowNumber = testStartRowNumber + 1;
		int dataStartRowNumber = testStartRowNumber + 2;

		int rows = 0;
		while (!(xls.getCellData(testDataSheet, 1, dataStartRowNumber + rows).equals(""))) {

			rows++;

		}

		// Total number of columns in the required test
		int columns = 1;

		while (!(xls.getCellData(testDataSheet, columns, columnStartRowNumber).equals(""))) {

			columns++;

		}

		Object[][] obj = new Object[rows][1];

		HashMap<String, String> map = null;

		// Reading the data in the test
		for (int i = 0, row = dataStartRowNumber; row < dataStartRowNumber + rows; row++, i++) {

			map = new HashMap<String, String>();

			for (@SuppressWarnings("unused")
			int j = 0, column = 1; column < columns; column++, j++) {

				String key = xls.getCellData(testDataSheet, column, columnStartRowNumber);

				String value = xls.getCellData(testDataSheet, column, row);

				map.put(key, value);

			}

			obj[i][0] = map;

		}

		return obj;

	}
	
	public static ExtentReports getExtentReports() {
		
		ExtentReports extentReport = new ExtentReports();
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\Reports\\ExtentReport.html");
		ExtentSparkReporterConfig sparkReporterConfig = sparkReporter.config();
		sparkReporterConfig.setReportName("TutorialsNinja Results");
		sparkReporterConfig.setDocumentTitle("TN Report");
		
		extentReport.attachReporter(sparkReporter);
		extentReport.setSystemInfo("Username",System.getProperty("user.name"));
		extentReport.setSystemInfo("Seleium Version","4.27.0");
		extentReport.setSystemInfo("OS",System.getProperty("os.name"));
		extentReport.setSystemInfo("Java Version",System.getProperty("java.version"));
		
		return extentReport;
		
	}
	
	public static void waitForSeconds(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties storePropertiesFile(Properties prop) {
		try {
			FileWriter fw = new FileWriter(
					System.getProperty("user.dir") + "\\src\\test\\resources\\projectdata.properties");
			prop.store(fw,"Updated Properties file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	public static int convertToInteger(String text) {
		return Integer.parseInt(text);
	}
	
	public static boolean areItemsInListAreInAscendingOrder(List<String> list) {
		List<String> sortedList = list;
		Collections.sort(sortedList);
		return list.equals(sortedList);
	}

	public static String generateBrandNewEmail() {

		Date date = new Date();
		String dateString = date.toString();
		String dateStringWithoutSpaces = dateString.replaceAll("\\s", "");
		String dateStringWithoutSpacesAndColons = dateStringWithoutSpaces.replaceAll("\\:", "");
		String brandNewEmail = dateStringWithoutSpacesAndColons + "@gmail.com";
		return brandNewEmail;

	}

	public static boolean compareTwoScreenshots(String actualImagePath, String expectedImagePath)  {

		BufferedImage bufferedActualImage = null;
		try {
			bufferedActualImage = ImageIO.read(new File(actualImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage bufferedExpectedImage = null;
		try {
			bufferedExpectedImage = ImageIO.read(new File(expectedImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageDiffer differ = new ImageDiffer();
		ImageDiff imageDiff = differ.makeDiff(bufferedExpectedImage, bufferedActualImage);
		return imageDiff.hasDiff();
	}

	public static void takeScreenshot(WebDriver driver, String screenshotPath) {

		TakesScreenshot ts = (TakesScreenshot) driver;

		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);

		try {
			FileHandler.copy(srcScreenshot, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTextFromMessage(Message message) throws Exception {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("text/html")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		StringBuilder result = new StringBuilder();
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result.append(bodyPart.getContent());
			} else if (bodyPart.isMimeType("text/html")) {
				result.append(bodyPart.getContent());
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
			}
		}
		return result.toString();
	}

}
