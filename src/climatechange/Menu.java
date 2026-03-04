package climatechange;
import java.util.Scanner;
public class Menu {
	private static Scanner scnr = new Scanner(System.in);
	public static void printOptions() {
		wrapText("left","Available options",
		"  impact (i) - Answer some questions to calculate your carbon footprint",
		"  project (p) - get a projection of various climate related events",
		"  equity (e) - learn about how inequitable climate change is",
		"  join (j) - get a list of local groups for couseling, action or information",
		"  environmental effects (f) - learn about climate change and ",
		"  how it affected some environments",
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
	public static void personalImpact() {
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
		String input;
		String[] groups = {"Snohomish Conservation District","Adopt a Stream Foundation"};
		String[] descriptions = {
				"""
				Snohomish Conservation District’s mission is to work cooperatively with others
				to promote and encourage conservation and responsible use of natural resources.""",
				"""
				The Adopt A Stream Foundation (AASF) a 501(c)3 non-profit organization was established
				to ensure that Pacific Northwest streams continue to provide healthy spawning and rearing
				habitat for salmon, trout, steelhead, and other wildlife. While serving our growing
				population by providing clean drinking water, and beautiful places for rest and relaxation.
				"""
		};
		String[] links = {"https://snohomishcd.org/","https://www.streamkeeper.org/"};
		
		wrapText("center","Here are some local and online groups you can join to help");
		wrapText("left",groups);
		while (true) {
			input = scnr.nextLine().trim();
			
		}
		
		
	}
		public static void environmentalEffects() {
	        //effects of climate change of trees, animals, choose from menu options to get more information. 
//	     Scanner input = new Scanner(System.in);
	        // Array of ecosystem types
	        String[] ecosystems = {
	            "Forest",
	            "Ocean",
	            "Desert",
	            "Grassland",
	            "Tundra",
	            "Wetlands"
	        };
	       //Array of what is affected in each Ecosystem. 
	       String[] affectedComponents = {
	                "Tree die-off, increased wildfires, habitat loss for animals",
	                "Coral bleaching, rising sea levels, marine species migration",
	                "Water scarcity, species extinction, extreme heat impact",
	                "Loss of grazing species, drought damage, soil degradation",
	                "Melting permafrost, declining polar wildlife populations",
	                "Flooding, loss of bird habitats, water contamination"
	            };
	       int choice = 0;
	       while(choice != ecosystems.length + 1) {
	           System.out.println("\n=== You are in Environmental Effects ===");
	           System.out.println("Select an ecosystem to learn more:");
	           for(int i = 0; i < ecosystems.length;i++) {
	               System.out.println((i+1) + "." + ecosystems[i]);
	           }
	           System.out.println((ecosystems.length + 1) + ". Exit");
	           System.out.print("Enter your choice: ");
	           choice = scnr.nextInt();

	           if(choice >=1 && choice <= ecosystems.length) {
	               System.out.println("\nEffects on " + ecosystems[choice - 1] + ":");
	                System.out.println(affectedComponents[choice - 1]);
	           }
	           else if(choice == ecosystems.length + 1) {
	                System.out.println("Exiting Environmental Effects...");
	                break;
	           }
	           else {
	                System.out.println("Invalid choice. Please try again.");
	            }
	       }
	       System.out.println("here");
//	       input.close();
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input;
		
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
				personalImpact();
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
			else if (input.equalsIgnoreCase("effects") || input.equalsIgnoreCase("f")) {
				environmentalEffects();
			}
	
		}
		}
		
	}

