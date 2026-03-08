package climatechange;

public class TestResult {
    private String testName;
    private boolean passed;
    private String failureMessage;

    // Constructor
    public TestResult(String testName, boolean passed, String failureMessage) {
        this.testName = testName;
        this.passed = passed;
        this.failureMessage = failureMessage;
    }

    // Getter so we can count passes later
    public boolean isPassed() {
        return passed;
    }

    // A clean way to print this specific result
    public void print() {
        if (passed) {
            System.out.println("[✓] PASS: " + testName);
        } else {
            System.out.println("[X] FAIL: " + testName);
            System.out.println("    Reason: " + failureMessage);
        }
    }
}