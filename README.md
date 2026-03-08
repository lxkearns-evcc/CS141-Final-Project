# Climate Change Information Tool

A Java-based, interactive console application designed to educate users about climate change, assess their personal environmental impact, and connect them with environmental conservation groups.

## 🌍 Overview

This tool provides a personalized, menu-driven experience. As you navigate through the app, it tracks your profile, including your carbon footprint, your score on the climate equity quiz, and the conservation groups you have chosen to "join."

## ✨ Features

* **Dynamic User Dashboard:** Automatically updates and displays your username, quiz scores, carbon footprint, and joined groups at the top of the main menu.
* **Personal Impact Calculator `(i)`:** Estimates your annual carbon footprint in metric tons of CO2e based on your utility bills, travel habits, and recycling practices. It also calculates the number of trees required to offset your emissions.
* **Climate Projections `(p)`:** Provides scientific projections on global temperatures, precipitation patterns, methane emissions, and the thawing cryosphere (citing WMO and UNEP reports).
* **Climate Equity Quiz `(e)`:** A 5-question interactive quiz highlighting the socioeconomic disparities and disproportionate impacts of climate change across different global demographics.
* **Local Action & Groups `(j)`:** Learn about and simulate joining conservation groups like the Snohomish Conservation District, Adopt a Stream Foundation, and Earthwatch.
* **Environmental Effects `(f)`:** Explore how climate change specifically impacts various ecosystems, including forests, oceans, deserts, and wetlands.

## 🛠️ Prerequisites

* **Java Development Kit (JDK):** Version 14 or higher (supports modern Java features like switch expressions and text blocks used in the code).
* A terminal or command prompt to run the application.

## 🚀 How to Run

1. **Save the file:** Ensure the code is saved in a file named `Menu.java` inside a folder named `climatechange`.
2. **Open your terminal:** Navigate to the directory *containing* the `climatechange` folder.
3. **Compile the code:**
```bash
javac climatechange/Menu.java

```


4. **Run the application:**
```bash
java climatechange.Menu

```



## 🎮 Usage Guide

Upon launching the application, you will be prompted to enter your name. After that, you can navigate the application using the following commands:

| Command | Action | Description |
| --- | --- | --- |
| **i** or **impact** | Personal Impact | Answer lifestyle questions to calculate your carbon footprint. |
| **p** or **project** | Projections | Read about future climate projections and statistics. |
| **e** or **equity** | Equity Quiz | Test your knowledge on how climate change affects different demographics. |
| **j** or **join** | Join Groups | View and "join" environmental conservation organizations. |
| **f** or **effects** | Ecosystem Effects | Learn about the consequences of climate change on specific biomes. |
| **h** or **help** | Help | Re-print the main menu and options. |
| **x** or **exit** | Exit | Safely close the application. |

## 🏗️ Code Structure Highlights

* `User` **Class:** Stores the session data, utilizing bitwise operations to efficiently track which groups the user has joined.
* `Group` **Enum:** Safely stores the index and names of the available environmental groups.
* `wrapText()` **Methods:** A custom text-formatting engine that automatically wraps text to fit an 80-character console width, complete with center or left-justified asterisk borders.

---
