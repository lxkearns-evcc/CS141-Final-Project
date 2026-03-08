package climatechange;
import java.util.Scanner;
public class Menu {
	private static User user;
	private static final int POUNDS_PER_METRIC_TONNE = 2205;
	private static final int POUNDS_PER_TREE = 2205;
	private static final String END = String.valueOf("_").repeat(79);
	public static boolean usePause = true; // disable pauses if running unit tests
	// Local groups for join()
	private static enum Group {
		SCD(0, "Snohomish Conservation District"),
		ASF(1, "Adopt a Stream Foundation"),
		EAW(2, "Earth Watch");

		private final int index;
		private final String name;

		private Group(int index, String name) {
			this.index = index;
			this.name = name;
		}
	}
	
	// User information
	private static class User {
		public String username;
		public double percentage = -1;
		// Tracker for groups joined in bits by order of Group.index
		// from least significant to most significant
		public int groups = 0;
		public double totalCBFP = -1d;
		
		public User(String username) {
			this.username = username;
		}
		
		// Prints the user header
		public void header() {
			String[] headerText = new String[4];
			// Username
			headerText[0] = "User: " + this.username;
			
			// Equity
			if (this.percentage == -1) {
				headerText[1] = "Try out our equity quiz!";
			}
			else {
				headerText[1] = "Quiz score: " + this.percentage + "%";
			}
			
			// Impact
			if (this.totalCBFP == -1d) {
				headerText[2] = "Try out our carbon footprint calculator!";
			}
			else {
				headerText[2] = "Your carbon footprint is " +
								String.format("%,.1f", this.totalCBFP / POUNDS_PER_METRIC_TONNE) + " Mt CO2e per year.";
			}
			
			// Join
			if (this.groups == 0) {
				headerText[3] = "No groups joined.";
			} else {
				headerText[3] = "Groups joined:" + "";
				int i = this.groups;
				for (Group g: Group.values()) {
					if (i == 0) break;
					if ((i % 2) == 1) {
						headerText[3] += "\n " + g.name;
					}
					i >>= 1;
				}
			}
			
			wrapText("left", headerText);
		}
	}
	
	// Support method for wrapText()
	// Allows handling of texts longer than 75 characters
	private static void wrapTextBody(String justify, String t) {
		if (t.contains("\n")) {
			int i = t.indexOf('\n');
			wrapTextBody(justify, t.substring(0, i));
			wrapTextBody(justify, t.substring(i+1));
			return;
		}
		if (t.length() > 75) {
			wrapTextBody(justify, t.substring(0, 75));
			wrapTextBody(justify, t.substring(75).trim());
			return;
		}
		
		String leftSpaces;
		String rightSpaces;
		
		int textFill = 78 - t.length();
		switch (justify) {
			case "center":
				leftSpaces = String.valueOf(" ").repeat(textFill / 2);
				
				if (textFill % 2 == 0) {
					rightSpaces = String.valueOf(" ").repeat((textFill / 2) - 1);
				} else {
					rightSpaces = leftSpaces;
				}
				
				System.out.println("*" + leftSpaces + t + rightSpaces + "*");
				break;
			case "left":
				leftSpaces = " ";
				rightSpaces = String.valueOf(" ").repeat(textFill - 2);
				System.out.println("*" + leftSpaces + t + rightSpaces + "*");
				break;
		}
	}
	
	// Give user some time to read results
	private static void pause() {
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	// Wraps text in * chars and centers or left justifies
	public static void wrapText(String justify, String... text) {
		String topBottomWrap = String.valueOf("* ").repeat(39) + "*";
		System.out.println(topBottomWrap);
		for (String t: text) {
			wrapTextBody(justify, t);
		}
		
		System.out.println(topBottomWrap + '\n');
	}
	
	// Prints the main menu options
	public static void printOptions() {
		user.header();
		
		wrapText(
			"left",
			"Available options",
			"  (i) impact        - Answer some questions to calculate your carbon",
			"                      footprint",
			"  (p) project       - Get a projection of various climate related events",
			"  (e) equity        - Learn about how inequitable climate change is",
			"  (j) join          - Get a list of local groups for couseling, action or",
			"                      information",
			"  (s) species 		 - Learn about animals that are affected by climate change",
			"  (h) help          - View options menu",
			"  (x) exit          - Exit the app"
		);
	}
	

	// Carbon footprint calculator
	public static void personalImpact(Scanner scnr) {
		String input;
		final double TOO_BIG = 1E9;
		double electric = 0, gas = 0, oil = 0;
		double carMileage = 0;
		int shortFlight = 0, longFlight = 0;
		boolean paperRecycle = false, metalRecycle = false;
		String[] questions = {
			"What is your monthly electric bill (in USD)?",
			"What is your monthly gas bill (in USD)?",
			"What is your monthly oil bill (in USD)?",
			"What is the total yearly mileage on your car (in miles)?",
			"How many flights have you taken in the past year that are 4 hours long or less?",
			"How many flights have you taken in the past year that are more than 4 hours long?",
			"Do you recycle newspaper? (y)es or (n)o",
			"Do you recycle aluminum and tin? (y)es or (n)o"
		};
		
		wrapText("center", "Personal Impact", "Carbon Footprint Calculator");
		System.out.println("""
		Welcome to the carbon footprint calculator!
		Answer eight questions about your lifestyle and see your personal carbon footprint.
		Type (s) to start or (x) to exit.""");
		
		boolean loop = true;
		while (loop) {
			System.out.print("\n> ");
			input = scnr.next().toLowerCase();
			scnr.nextLine(); // Clears buffer
			switch (input) {
				case "s", "start":
					loop = false; // Loop exit point
					break;
				case "x", "exit":
					System.out.println("Exiting Personal Impact...\n");
					printOptions();
					return;
				default:
					System.out.println("Invalid input.\n\nType (s) to start or (x) to exit");
			}
		}
		
		for (int i = 0; i < questions.length; i++) {
			do {
				loop = false; // Error reset, prevents looping when input is valid
				System.out.println("\nType (b) to go back or (x) to exit.\n");
				System.out.println((i + 1) + ". " + questions[i]);
				System.out.print("> ");
				if ((i >= 0) && (i < 3)) {
					System.out.print("$"); // Adds USD unit for questions 1-3
				}
				input = scnr.next().toLowerCase();
				scnr.nextLine(); // Clears buffer
				

				// Variable assignment for each response
				// Checks input to prevent exceptions
				// Exits loop with continue

				switch (i) {
					case 0:
						if (input.matches("^[0-9.]+$")) {
							electric = Double.valueOf(input);
							if (electric > TOO_BIG) {
								electric = 0;
								break;
							}
							continue;
						}
						break;
					case 1:
						if (input.matches("^[0-9.]+$")) {
							gas = Double.valueOf(input);
							if (gas > TOO_BIG) {
								gas = 0;
								break;
							}
							continue;
						}
						break;
					case 2:
						if (input.matches("^[0-9.]+$")) {
							oil = Double.valueOf(input);
							if (oil > TOO_BIG) {
								oil = 0;
								break;
							}
							continue;
						}
						break;
					case 3:
						if (input.matches("^[0-9.]+$")) {
							carMileage = Double.valueOf(input);
							if (carMileage > TOO_BIG) {
								carMileage = 0;
								break;
							}
							continue;
						}
						break;
					case 4:
						if (input.matches("^\\d+$") && (Double.valueOf(input) < TOO_BIG)) {
							shortFlight = Integer.parseInt(input);
							continue;
						}
						break;
					case 5:
						if (input.matches("^\\d+$") && (Double.valueOf(input) < TOO_BIG)) {
							longFlight = Integer.parseInt(input);
							continue;
						}
						break;
					case 6:
						if (input.matches("^y(es)?$")) {
							paperRecycle = true;
							continue;
						}
						else if (input.matches("^no?$")) {
							paperRecycle = false;
							continue;
						}
						break;
					case 7:
						if (input.matches("^y(es)?$")) {
							metalRecycle = true;
							continue;
						}
						else if (input.matches("^no?$")) {
							metalRecycle = false;
							continue;
						}
						break;
				}
				
				// Responsible for invalid input, returning to previous questions,
				// or returning to main menu
				switch (input) {
					case "b", "back":
						// Case for no previous question, reiterates loop for valid response
						if (i == 0) {
							System.out.println("You are on the first question.");
						}
						// Go back one question, reiterates loop with previous index
						else {
							i--;
						}
						loop = true;
						continue;
					case "x", "exit":
						// Returns to main menu
						System.out.println("Exiting Personal Impact...\n");
						printOptions();
						return;
					default:
						// Invalid input, reiterates while loop for valid response
						System.out.println("Invalid input.");
						loop = true;
						continue;
				}
			} while (loop);
			
		}
		
		// Carbon footprint formula
		user.totalCBFP = (electric * 105) + (gas * 105) + (oil * 105) + (carMileage * 0.79) +
						 (shortFlight * 1100) + (longFlight * 4400);
		if (!paperRecycle) user.totalCBFP += 184;
		if (!metalRecycle) user.totalCBFP += 166;
		
		System.out.println("");
		wrapText(
			"center",
			"Your total carbon footprint is " + String.format("%,.0f", user.totalCBFP) + " pounds or ",
			String.format("%,.1f", user.totalCBFP / POUNDS_PER_METRIC_TONNE) + " metric tons of carbon-dioxide equivalant per year.",
			"An average of " + String.format("%,.0f", user.totalCBFP / POUNDS_PER_TREE) + " trees planted are required to offset this emission."
		);
		if (usePause) pause();
		printOptions();
	}
	
	// Future projections of global effects of climate change
	public static void globalProjection(Scanner scnr) {
		String input;
		int inputInt;
		String[] categories = {
			"Global Mean Temperatures",
			"Regional Temperatures",
			"Precipitation",
			"Methane Emissions",
			"Thawing Cryosphere"
		};
		String[] description = {
			"""
			Global mean temperatures are likely to continue at or near record levels in
			the five-year period 2025-2029. The annually averaged global mean near-surface
			temperature for each year between 2025 and 2029 is predicted to be between
			1.2°C and 1.9°C higher than the average over the years 1850-1900.
			
			It is likely (86% chance) that global mean near-surface temperature will exceed
			1.5°C above the 1850-1900 average levels for at least one year between 2025 and
			2029. It is also likely (70% chance) that the 2025-2029 five-year mean will
			exceed 1.5°C above the 1850-1900 average.
			
			It is likely (80% chance) that at least one year between 2025 and 2029 will be
			warmer than the warmest year on record (currently 2024) and although
			exceptionally unlikely, there is now also a chance (1%) of at least one year
			exceeding 2°C of warming in the next five years.
			
			Long-term warming (averaged over decades) remains below 1.5°C.
			
			Source: WMO Global Annual to Decadal Climate Update 2025-2029
			""",
			"""
			The five-year average temperature in the Niño 3.4 region relative to the whole
			tropics indicates mixed or mainly neutral ENSO conditions in this period.
			
			The average Arctic temperature anomaly over the next five extended winters
			(November to March), relative to the recent climatological normal (the average
			of the years 1991-2020), is predicted to be 2.4°C, more than three and a half
			times as large as the anomaly in global mean temperature.
			
			Predictions of Arctic sea-ice for March 2025-2029 suggest further reductions in
			sea-ice concentration in the Barents Sea, Bering Sea, and Sea of Okhotsk.
			
			Source: WMO Global Annual to Decadal Climate Update 2025-2029
			""",
			"""
			Predicted precipitation patterns for May-September 2025-2029, relative to the
			1991-2020 average, suggest anomalously wet conditions in the Sahel, northern
			Europe, Alaska and northern Siberia, and anomalously dry conditions for this
			season over the Amazon.
			
			Recent years, apart from 2023, in the South Asian region have been anomalously
			wet and the forecast suggests this will continue for the 2025-2029 period. This
			may not be the case for all individual seasons in this period.
			
			Source: WMO Global Annual to Decadal Climate Update 2025-2029
			""",
			"""
			Methane’s atmospheric concentrations have more than doubled since
			pre-industrial times. According to new modelling, global anthropogenic
			emissions of methane reached approximately 352 million tonnes (Mt) per year in
			2020 and, under current legislation, are projected to continue rising, reaching
			369 Mt per year in 2030, 5% above 2020. These rising global emissions would
			contribute to almost 24,000 additional premature deaths and 2.5 Mt of crop
			losses annually by 2030, relative to 2020, and would commit the world to an
			additional 0.025°C of warming by 2050. The economic damage of these impacts
			could reach US$43 billion per year in 2030. The largest increases are forecast
			for the agricultural and waste sectors, driven by projected larger livestock
			herds and higher waste generation due to expanding populations and economic
			growth.
			
			Without additional action, methane emissions from waste are projected to
			increase by 13% by 2030 and 56% by 2050, compared with 2020 levels. This rise
			is driven by population and economic growth, along with expanded waste and
			wastewater collection, which provides significant health and environmental
			benefits but also increases methane emissions unless targeted mitigation
			measures are implemented in parallel. Even with current pledges in the latest
			NDCs and MAPs, annual emissions from the sector are expected to grow by 3 Mt
			in 2030 compared with 2020.
			
			Source: UNEP Global Methane Status Report 2025
			""",
			"""
			Between 1994 and 2017, 28 trillion tonnes of ice vanished from the Earth’s
			cryosphere and, compared to the 1990s, the overall rate of ice loss increased
			by 57% over those 24 years. Projections to 2100 suggest that the number of the
			world’s glaciers will be halved, even if temperature increase could be limited
			to +1.5°C. Glaciers are no longer shrinking – they are disappearing. Similarly,
			recent estimates suggest that 24 to 69 per cent of the planet’s near-surface
			permafrost will thaw by 2100.
			
			Source: UNEP Frontiers 2025: The Weight of Time
			"""
		};
		
		wrapText("center", "Climate Projections");
		while (true) {
			System.out.println("Select a projection category to learn more:");
			for (int i = 1; i <= categories.length; i++) {
				System.out.println(i + ". " + categories[i - 1]);
			}
			System.out.println("6. EXIT");
		
			System.out.print("\n> ");
			input = scnr.next().toLowerCase();
			scnr.nextLine(); // Clears buffer
			System.out.print("\n");
			
			switch (input) {
				case "1","2","3","4","5":
					inputInt = Integer.parseInt(input);
					wrapText("center", categories[inputInt - 1]);
					System.out.println(description[inputInt - 1] + END + "\n");
					break;
				case "6":
				case "x":
				case "exit":
					System.out.println("Exiting Climate Projections...");
					printOptions();
					return;
				default:
					System.out.println("Invalid input.");
			}
		}
	}
	
	// Equity method
	// Shares information about the inequity of climate change using a short quiz
	public static void equity(Scanner scnr) {

		String[] questions = {
			"""
			1. What demographic is responsible for the largest share of global greenhouse gas emissions?	
			   (a) The lowest income 50%, globally
			   (b) The wealthiest 10%, globally
			   (c) Indigenous peoples
			   (d) Middle-income households in developing nations
			""",
			"""
			2. What area is most affected by rising sea levels despite having historically minimal carbon footprints?
			   (a) Coastal cities in the US and Europe
			   (b) Landlocked nations
			   (c) Small island developing states
			   (d) Nations closest to the north and south poles
			""",
			"""
			3. Racism has made minorities and the poor bear a higher cost for climate change"
			   (a) True
			   (b) False			
			""",
			"""
			4. In many urban areas, which neighborhoods generally experience the highest temperatures due to the urban heat island effect?
			   (a) Suburbs
			   (b) Wealthy areas in the city
			   (c) Commercial districts
			   (d) Historically marginalized and lower-income neighborhoods				
			""",
			"""
			5. How do changing weather patterns disproportionately affect smallholder farmers in the Global South?
			   (a) They benefit from longer growing seasons and increased crop yields
			   (b) They rely on rain rather than irrigation thus changing weather reduces crop yields
			   (c) They are able to purchase modern equipment and seeds which offsets any loss in production
			   (d) They recieve subsidies from wealthy nations to offest their lower yields
			"""
			
		};
		String[] answers = { "a", "c", "a", "d", "b" };
		int correct = 0;
		String response;
		

		wrapText(
			"center",
			"Welcome to the climate change equity quiz (5 questions)!",
			"Answer with (a) (b) (c) or (d). Press x to exit."
		);

		//Did not test thoroughly test this loop, only used lowercase letters
		for (int i = 0; i < 5; i++) {

			// Loop until a valid answer is given
			boolean invalid = true;
			do {
				System.out.print(questions[i] + "> ");
				response = scnr.next().toLowerCase();

				scnr.nextLine(); // Clears buffer
				
				switch (response) {
					case "d":
					case "c":
						if (i == 2) {
							System.out.println("Invalid answer.");
							System.out.println("Please answer with (a) or (b).\\n");
							break;
						}
					case "b":
					case "a":
						System.out.println("Your answer: " + response + ".");
						if (response.equals(answers[i])) {
							correct++;
							System.out.println("Correct!");
						}
						else {
							System.out.println("Incorrect, the right answer was " + answers[i] + ".\n");
						}
						invalid = false;
						break;
					case "x":
						printOptions();
						return;
					default:
						System.out.println("Invalid answer.");
						if (i == 2) {
							System.out.println("Please answer with (a) or (b).\n");
						}
						else {
							System.out.println("Please answer with (a) (b) (c) or (d).\n");
						}
				}
			} while (invalid);

		}

		user.percentage = 100.0 * (correct/5.0);
		System.out.println("You got " + user.percentage + "% of questions correct!\n");
		if (usePause) pause();
		printOptions();
	}

	// Join groups method
	// This shows a few local and online conservation groups and simulates joining
	public static void join(Scanner scnr) {

		String input;
		Group group;
		String[] groups = {
			"1. Snohomish Conservation District",
			"2. Adopt a Stream Foundation",
			"3. Earth Watch",
			"4. EXIT"
		};
		String[] descriptions = {
			"""
			Snohomish Conservation District’s mission is to work cooperatively with others
			to promote and encourage conservation and responsible use of natural resources.""",
			"""
			The Adopt A Stream Foundation (AASF) a 501(c)3 non-profit organization was established
			to ensure that Pacific Northwest streams continue to provide healthy spawning and rearing
			habitat for salmon, trout, steelhead, and other wildlife. While serving our growing
			population by providing clean drinking water, and beautiful places for rest and relaxation.
			""",
			"""
			Earthwatch connects people with scientists to conserve our planet. 
			Our expeditions put you at the center of research and conservation worldwide. 
			Are you ready to experience nature as you help protect it?
			"""
		};
		String[] links = {
			"https://snohomishcd.org/",
			"https://www.streamkeeper.org/",
			"https://earthwatch.org/"
		};
		
		wrapText(
			"center",
			"Here are some local and online groups you can join to help",
			"Type the number and hit enter to learn more"
		);
		
		// Join loop
		while (true) {
			
			for (String g: groups) {
				System.out.println(g);
			}
			System.out.print("\n> ");
			input = scnr.next().toLowerCase();
			scnr.nextLine(); // Clears buffer
			// Get group Enum based on input

			switch (input) {
				case "1":
					group = Group.SCD;
					break;
				case "2":
					group = Group.ASF;
					break;
				case "3":
					group = Group.EAW;
					break;
				case "4":
				case "exit":
				case "x":
					System.out.print("\n");
					System.out.println("Exiting Groups...");
					printOptions();
					return;
				default:
					System.out.println("Invalid option.");
					continue;
			}
			
			System.out.print("\n");
			wrapText("center", group.name);
			System.out.println(descriptions[group.index] + "\n\n" + links[group.index] + '\n');
			
			while (true) {
				System.out.println("Type (1) to join or leave, or (0) to return.");
				System.out.print("> ");
				input = scnr.next().toLowerCase();
				scnr.nextLine(); // Clears buffer
				
				// Valid responses, exits loop with break
				if ("1".equals(input)) {
					// Get bit at current index
					int bit = 1 << group.index;
					// Bit flip
					user.groups ^= bit;
					// Bit check for output response
					if ((user.groups & bit) == 0) {
						System.out.println("You have left " + group.name +".\n");
					} else {
						System.out.println("You have joined " + group.name +"!\n");
					}
					break;
				}
				else if ("0".equals(input)) {
					break;
				}
				
				// Invalid response, loop continues
				System.out.println("Invalid option.");
			}
			
		}
	}
	

	// Species in specific biomes that are heavily effected by climate change
	public static void speciesEffects(Scanner scnr) {
		String input;
		int inputInt;
        // Array of ecosystems types
	    String[] ecosystems = {
	        "Forest",
	        "Ocean",
	        "Desert",
	        "Grassland",
	        "Tundra",
	        "Wetlands"
	    };
	    String[] affectedSpecies = {
				"""
						American Pika (Ochotona princeps): These small mammals are highly sensitive to
						heat and can die if exposed to temperatures above 77°F (25°C). As their alpine
						forest habitats warm, they are forced to migrate further up mountain slopes.
						Eventually, they risk running out of elevation, leading to localized extinctions.""",

				"""
						Koala (Phascolarctos cinereus): Elevated atmospheric CO2 levels reduce the
						protein and nutrient content of eucalyptus leaves, their nearly exclusive food
						source, leading to malnutrition. Additionally, the increasing frequency of
						extreme, climate-driven bushfires directly destroys their populations and
						habitats.""",

				"""
						Whitebark Pine (Pinus albicaulis): Historically, harsh winters kept mountain
						pine beetle populations in check. Warmer winters now fail to kill off these
						pests, allowing them to multiply rapidly and decimate these high-elevation
						trees, which are crucial for snow retention and forest ecosystem stability.""",

				"""
						Staghorn Coral (Acropora cervicornis): Ocean warming causes coral to expel
						their food-producing symbiotic algae (bleaching). Simultaneously, the ocean
						absorbs excess CO2, lowering its pH. This acidification strips the water of
						the carbonate ions the coral needs to build and maintain its calcium carbonate
						skeleton.""",

				"""
						Antarctic Krill (Euphausia superba): Krill rely on the undersides of sea ice
						to shelter and feed their larvae. The rapid decline of winter sea ice
						drastically reduces their reproductive success and survival rates, threatening
						the entire marine food web (including whales and penguins) that depends on them.""",

				"""
						Loggerhead Sea Turtle (Caretta caretta): Sea turtles rely on temperature-
						dependent sex determination; the temperature of the sand incubates the eggs
						and determines the hatchlings' sex. Unusually hot sand produces overwhelmingly
						female populations, skewing demographics. Furthermore, sea-level rise and
						severe storms erode their critical nesting beaches.""",

				"""
						Joshua Tree (Yucca brevifolia): Prolonged droughts reduce the survival rates
						of vulnerable seedlings. Furthermore, warming temperatures are shrinking the
						habitable range of the yucca moth—the tree's sole pollinator—severely
						threatening the plant's ability to reproduce.""",

				"""
						Desert Tortoise (Gopherus agassizii): Prolonged, severe droughts reduce the
						bloom of specific spring wildflowers these tortoises need to eat to build up
						water and fat reserves, leading to widespread dehydration and starvation.""",

				"""
						Saguaro Cactus (Carnegiea gigantea): Changing precipitation patterns encourage
						the spread of invasive, highly flammable buffelgrass. This grass introduces
						intense, fast-moving wildfires to the ecosystem. Because saguaros did not
						evolve with fire, they possess no natural defenses and are easily killed by
						the blazes.""",

				"""
						Monarch Butterfly (Danaus plexippus): Monarchs rely on environmental
						temperature cues to trigger their migrations. Erratic weather disrupts this
						timeline, while extreme heat and droughts destroy milkweed—the exclusive food
						source for their caterpillars—along their migration routes.""",

				"""
						Saiga Antelope (Saiga tatarica): Anomalous heat waves combined with high
						humidity events have been shown to trigger naturally occurring, normally
						harmless respiratory bacteria in the antelopes' tonsils to become lethal,
						causing sudden mass die-offs of hundreds of thousands of animals.""",

				"""
						Greater Sage-Grouse (Centrocercus urophasianus): Warming temperatures favor
						the aggressive spread of invasive cheatgrass. This outcompetes native flora
						and drastically increases the frequency of wildfires, destroying the
						slow-growing sagebrush these birds absolutely require for cover and food.""",

				"""
						Polar Bear (Ursus maritimus): Polar bears rely almost exclusively on sea ice
						as a platform to hunt seals. Because the ice is freezing later in the fall and
						melting earlier in the spring, their hunting season is severely shortened,
						causing starvation and reproductive failure.""",

				"""
						Arctic Fox (Vulpes lagopus): As the tundra warms, it is slowly giving way to
						boreal scrublands. This allows the larger, more aggressive red fox to expand
						its range northward, outcompeting and directly preying upon the Arctic fox.""",

				"""
						Caribou (Rangifer tarandus): Warmer winter temperatures are causing rain to
						fall instead of snow. When temperatures drop again, this rain freezes into a
						rock-hard layer of solid ice over the tundra, preventing caribou from digging
						through to reach the lichens they need to survive the winter.""",

				"""
						Bog Turtle (Glyptemys muhlenbergii): Altered hydrology threatens this highly
						specialized species. Intense, sudden storms can flood and drown their shallow
						nests, while prolonged droughts dry up the muddy, spring-fed bogs they rely
						on to regulate their temperature and forage.""",

				"""
						Saltmarsh Sparrow (Ammospiza caudacuta): These birds nest exclusively in
						coastal tidal marshes just inches above the high-tide line. Accelerating
						sea-level rise increases the frequency of exceptionally high spring tides,
						which completely flood their nests and drown the chicks before they can learn
						to fly.""",

				"""
						Mangrove Trees (Rhizophora spp.): While mangroves naturally build land by
						trapping sediment in their roots, the current, accelerating rate of sea-level
						rise in some coastal regions is outpacing their ability to accumulate soil.
						This eventually inundates and drowns their specialized root systems.""" };

	    wrapText("center", "Welcome to Species Effects");
    			
	    while (true) {
			System.out.println("Select an ecosystem to learn some animals that are adversely impacted\n" +
	    "by climate change (enter a number)");
			for (int i = 1; i <= ecosystems.length; i++) {
				System.out.println(i + ". " + ecosystems[i - 1]);
			}
			System.out.println("7. EXIT");
		
			System.out.print("\n> ");
			input = scnr.next().toLowerCase();
			scnr.nextLine(); // Clears buffer
			System.out.print("\n");
			
			switch (input) {
				case "1","2","3","4","5","6":
					
					inputInt = Integer.parseInt(input) -1;
				System.out.println(input+ " " + inputInt);
					wrapText("center", ecosystems[inputInt]);
					// each ecosystem has 3 species, loop to get all of them
					// not the cleanest thing
					for (int j = inputInt*3; j < inputInt*3 + 3; j++) {
						System.out.println(affectedSpecies[j] + "\n" + END);
						}
					break;
				case "7":
				case "x":
				case "exit":
					System.out.println("Exiting Climate Projections...");
					printOptions();
					return;
				default:
					System.out.println("Invalid input.");
			}
		}
	    
    }
	
	public static void main(String[] args) {
		// use main as a wrapper to facilitate unit testing
		Scanner scnr = new Scanner(System.in);
		runMenu(scnr);
		
	}
	
	public static void runMenu(Scanner scnr) {
		String input;
		
		wrapText(
			"center",
			"Welcome to the climate change information tool",
			"Type 'help' for menu options or 'exit' to quit"
		);
		
		System.out.print("Please enter your name: ");
		user = new User(scnr.nextLine().trim());
		System.out.print("\n");
		
		printOptions();
		
		// Main menu loop
		while (true) {
			System.out.print("> ");
			input = scnr.nextLine().trim().toLowerCase();

			switch (input) {
				case "i", "impact":
					personalImpact(scnr);
					break;
				case "p", "project":
					globalProjection(scnr);
					break;
				case "e", "equity":
					equity(scnr);
					break;
				case "j", "join":
					join(scnr);
					break;
				case "s", "species":
					speciesEffects(scnr);
					break;
				case "h", "help":
					printOptions();
					break;
				case "x", "exit":
					System.out.println("Thanks for using the app, Goodbye " + user.username + "!");
					scnr.close();
					return;
				default:
					System.out.println("Invalid choice. Please try again.\n");
			}
		}
	}		
}
