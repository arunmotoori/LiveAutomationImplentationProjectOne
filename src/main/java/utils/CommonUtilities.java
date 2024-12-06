package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class CommonUtilities {
	
	public static String generateBrandNewEmail() {
		
		Date date = new Date();
		String dateString = date.toString();
		String dateStringWithoutSpaces = dateString.replaceAll("\\s","");
		String dateStringWithoutSpacesAndColons = dateStringWithoutSpaces.replaceAll("\\:","");
		String brandNewEmail = dateStringWithoutSpacesAndColons+"@gmail.com";
		return brandNewEmail;
		
	}
	
	public static boolean compareTwoScreenshots(String actualImagePath,String expectedImagePath) throws IOException {
		
		BufferedImage bufferedActualImage = ImageIO.read(new File(actualImagePath));
		BufferedImage bufferedExpectedImage = ImageIO.read(new File(expectedImagePath));
		ImageDiffer differ = new ImageDiffer();
		ImageDiff imageDiff = differ.makeDiff(bufferedExpectedImage, bufferedActualImage);
		return imageDiff.hasDiff();
	}
	
	

}
