package climatechange;
import java.util.Scanner;
public class Menu {
	public static void printOptions() {
		wrapText("left","Available options",
		"  impact (i) - Answer some questions to calculate your carbon footprint",
		"  project (p) - get a projection of various climate related events",
		"  equity (e) - learn about how inequitable climate change is",
		"  join (j) - get a list of local groups for couseling, action or information",
		"  help (h) - view options menu",
		"  exit (x) - exit the app");
	}
	public static void wrapText(String justify,String... text) {
		// Wraps text in * chars and centers or left justifies
		// Could consider adding handling for text longer than 80
		String topBottomWrap = String.valueOf("*").repeat(80);
		System.out.println(topBottomWrap);
		for (String t: text) {
			String leftSpaces;
			String rightSpaces;
			int textFill = 78 - t.length();
			if (justify.equals("center")) {
				if (textFill% 2 == 0) {
					leftSpaces = String.valueOf(" ").repeat(textFill/2);
					rightSpaces = leftSpaces;
				}
				else {
					leftSpaces = String.valueOf(" ").repeat(textFill/2);
					rightSpaces = String.valueOf(" ").repeat(textFill/2+1);
				}
				System.out.println("*"+leftSpaces+t+rightSpaces+"*");
			}
			else if (justify.equals("left")) {
				leftSpaces = " ";
				rightSpaces = String.valueOf(" ").repeat(textFill-1);
				System.out.println("*"+leftSpaces+t+rightSpaces+"*");
			}
		}
		
		System.out.println(topBottomWrap+'\n');
	}
	public static void impact() {
		System.out.println("TO DO: add code to ask for user info and calculate impact, tell them how many trees or plankton");
		System.out.println("are required to offest");
	}
	public static void project() {
		System.out.println("TO DO: add projections for various environmental things such as sea level, ocean temp etc.");
	}
	public static void equity() {
		System.out.println("TO DO: highlight things like redlining, inequal burdens, responsible parties not paying the price.");
		System.out.println("Maybe as a quiz or something interactive?");
	}
	public static void join() {
		System.out.println("TO DO: prompt user for the kind of group they want to join and then give a few local or online options");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input;
		Scanner scnr = new Scanner(System.in);
		wrapText("center","Welcome to the climate change information tool",
				"Type 'help' for menu options or 'exit' to quit");
		printOptions();
		while (true) {
			
			System.out.print("\n>");
			input = scnr.nextLine().trim();
			if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("x")) {
				System.out.println("Thanks for using the app, Goodbye!");
				break;
			}
			else if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("h")) {
				printOptions();
			}
			else if (input.equalsIgnoreCase("impact") || input.equalsIgnoreCase("i")) {
				impact();
			}
			else if (input.equalsIgnoreCase("project") || input.equalsIgnoreCase("p")) {
				project();
			}
			else if (input.equalsIgnoreCase("equity") || input.equalsIgnoreCase("e")) {
				equity();
			}
			else if (input.equalsIgnoreCase("join") || input.equalsIgnoreCase("j")) {
				join();
			}
	
		}
		}
		
	}

