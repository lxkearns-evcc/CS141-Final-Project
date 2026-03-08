package climatechange;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// Class for testing Menu class
public class MenuTest {
	
	private static final int TEST_COUNT = 7;
	private static PrintStream originalOut;
	private static ByteArrayOutputStream capturedOutput;
	
	// redirect System.out so that it can captured for testing
	public static void startOutputCapture() {
		originalOut = System.out;
		capturedOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(capturedOutput));
	}
	
	// replace custom output location with default one
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
	
	// test personalImpact method calculation for correctness
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
		boolean passed = output.contains("37,636 pounds");
		String errorMsg = passed ? "" : "Calculation did not result in 37,636 pounds";
		return new TestResult("Personal Impact Calculation Test",passed,errorMsg);
	}
	
	// test equity quiz result
	public static TestResult equityQuizTest() {
		
		startOutputCapture();
		Scanner testScnr  = new Scanner(
				"test\n"
				+ "a\n"
				+ "c\n"
				+ "b\n"
				+ "d\n"
				+ "b\n"
				+ "x\n");		
		Menu.equity(testScnr);
		String output = stopOutputCapture();
		boolean passed = output.contains("You got 80.0% of questions correct!");
		String errorMsg = passed ? "" : "Did not get right score on the test, check answer key and questions";
		return new TestResult("Equity Quiz Result Test",passed,errorMsg);
	}
	
	// test group join function
	public static TestResult joinGroupTest() {
		
		startOutputCapture();
		Scanner testScnr  = new Scanner(
				"test\n"
				+ "1\n"
				+ "1\n"
				+ "4\n"
				+ "x\n");		
		Menu.join(testScnr);
		String output = stopOutputCapture();
		boolean passed = output.contains("You have joined Snohomish Conservation District!");
		String errorMsg = passed ? "" : "Did not join the correct group";
		return new TestResult("Join Group Test",passed,errorMsg);
	}
	
	// test projection function
	public static TestResult globalProjectionTest() {
		
		startOutputCapture();
		Scanner testScnr  = new Scanner(
				"test\n"
				+ "1\n"
				+ "6\n");		
		Menu.globalProjection(testScnr);
		String output = stopOutputCapture();
		boolean passed = output.contains("Global mean temperatures are likely to continue at or near record levels");
		String errorMsg = passed ? "" : "Viewed the wrong projection";
		return new TestResult("Global Projection Test",passed,errorMsg);
	}		
	
	// test species function
	public static TestResult speciesEffectsTest() {
		
		startOutputCapture();
		Scanner testScnr  = new Scanner(
				"test\n"
				+ "1\n"
				+ "x\n");		
		Menu.speciesEffects(testScnr);
		String output = stopOutputCapture();
		boolean passed = output.contains("American Pika") && 
				output.contains("Koala") &&
				output.contains("Whitebark Pine");
		String errorMsg = passed ? "" : "Wrong species";
		return new TestResult("Species Effects Test",passed,errorMsg);
	}	
	
	public static void main(String[] args) {
		int totalPassed = 0;
		Menu.usePause = false; // toggle off pausing
		Menu.wrapText("left","Running Unit Tests");
		
		// Store TestResult objects in array
		// Could consider use ArrayList or List so that sizing is more dynamic
		TestResult[] testResults = new TestResult[TEST_COUNT];
		testResults[0] = immediateExitTest();
		testResults[1] = garbageInputsMainMenu();
		testResults[2] = personalImpactTest();
		testResults[3] = equityQuizTest();
		testResults[4] = joinGroupTest();
		testResults[5] = globalProjectionTest();
		testResults[6] = speciesEffectsTest();
		
		for (TestResult result : testResults) {
			result.print();
			if (result.isPassed()) {
				// increment passed tests as they finish
				totalPassed++;
			}
		}
		// Show test results
		Menu.wrapText("left","Test Results",
				"Total Tests: " + testResults.length + 
				" | Passed: " + totalPassed  +
				" | Failed: " + (testResults.length - totalPassed
				));

	}
}
