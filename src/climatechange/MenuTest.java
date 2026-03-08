package climatechange;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MenuTest {
//	Menu menu = new Menu();
	private static final int TEST_COUNT = 10;
	private static PrintStream originalOut;
	private static ByteArrayOutputStream capturedOutput;
	public static void startOutputCapture() {
		originalOut = System.out;
		capturedOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(capturedOutput));
	}
	public static String stopOutputCapture() {
		System.setOut(originalOut);
		return capturedOutput.toString();
	}
	// Test immediate exit in main menu
	public static TestResult immediateExitTest() {
		startOutputCapture();
		Scanner testScnr  = new Scanner("test\nx\n");		
		Menu.runMenu(testScnr);
		String output = stopOutputCapture();
		boolean passed = output.contains("Thanks for using the app, Goodbye test!");
		String errorMsg = passed ? "" : "Did not find the goodbye message.";
		return new TestResult("Immediate Exit Test",passed,errorMsg);
		
	}
	// Test main menu for a variety of bad inputs
	public static TestResult garbageInputsMainMenu() {
	
		startOutputCapture();
		
		Scanner testScnr  = new Scanner("test\n1\n@\naeogij\n+\n{}\nx\n");		
		Menu.runMenu(testScnr);
		String output = stopOutputCapture();
		int originalLength = output.length();
		int targetLength = output.replace("Invalid","").length();
		
		// 5 invalid inputs results in five instances of Invalid in the output
		// difference in length should equal 7 * number of bad inputs
		boolean passed = (originalLength - targetLength == 35);
		String errorMsg = passed ? "" : "Did not reject all garbage inputs";
		return new TestResult("Garbage Inputs Test",passed,errorMsg); 
		
	}
	// end to end test of personalImpact method
	public static TestResult personalImpactTest() {
		
		startOutputCapture();
		Scanner testScnr  = new Scanner(
				"test\n"
				+ "s\n"
				+ "50\n"
				+ "50\n"
				+ "50\n"
				+ "8000\n"
				+ "2\n"
				+ "3\n"
				+ "y\n"
				+ "n\n"
				+ "x\n");		
		Menu.personalImpact(testScnr);
		String output = stopOutputCapture();
		System.out.println(output);
		boolean passed = output.contains("Thanks for using the app, Goodbye test!");
		String errorMsg = passed ? "" : "Did not find the goodbye message.";
		return new TestResult("Immediate Exit Test",passed,errorMsg);
		
	}
	public static void main(String[] args) {
		int totalPassed = 0;
		Menu.wrapText("left","Running Unit Tests");
		TestResult[] testResults = new TestResult[TEST_COUNT];
		testResults[0] = immediateExitTest();
		testResults[1] = garbageInputsMainMenu();
		testResults[2] = personalImpactTest();
		for (TestResult result : testResults) {
			result.print();
			if (result.isPassed()) {
				totalPassed++;
			}
		}
		Menu.wrapText("left","Test Results",
				"Total Tests: " + testResults.length + 
				" | Passed: " + totalPassed  +
				" | Failed: " + (testResults.length - totalPassed
				));

	}
	
}