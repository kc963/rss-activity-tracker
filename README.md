# RSS Activity Tracker 
---

### Problem Statement

Given a Dictionary keyed by Company and valued by RSS feed url, write a function that determines which companies had no activity for a given number of days.

RSS feed examples: https://rss.com/popular-rss-feeds/ provides many example companies and their RSS feeds.
---
### Solution Instructions

The solution contains 2 classes:
1. **RSSFeedParser**: contains methods for parsing a feed from the URL and obtaining the publishing date.
2. **ActivityTracker**: contains the getInactiveFeeds method which takes a map containing company name and feed url and an integer days.
These classes are present in the src/main/java directory.

The solution also performs unit testing on the RSSFeedParserClass class and ActivityTracker class. The code for testing is present in the src/test/java directory. This directory contains 2 main test classes and other helper classes files. The 2 main test classes are 
1. **TestRSSFeedParser**
2. **TestActivityTracker**

The helper classes are used for setting up the test cases for each class. The following classes represent the TestCase Entity:
1. **TestCase**: represents a general structure of a test case. It has 3 attributes: name, input and output. Also, it is an abstract class.
2. **GetLastUpdateDateTestCase**: represents the test cases for the `getLastUpdateDate` method in the `RSSFeedParser` class.
3. **GetInactiveFeedsTestCase**: represents the test cases for the `getInactiveFeeds` method in the `ActivityTracker` class.

The remaining helper class - `GenerateFeeds` - is used for generating the XML files which represent the feed. It generates 8 files and they are used to set up the streams when testing the `ActivityTracker` class.

The src/test/resources folder contains some text files and xml files. The test files are necessary for the test files as they contain the details about the test cases. The xml files are used by the `RSSFeedParser` class as inputs during the tests.

#### Assumptions
1. The active status of the company is assessed by using all its feeds and if any of the provided feeds of the company are inactive means that the company is inactive.
2. If input integer representing the limit on days for which the company is inactive is negative, none of the companies are checked for activity and an empty list is returned.
3. In case any case any error is encountered by the program (number of days is negative, cannot access the feed url, date format not supported), the program skips the affected data, shows an error message and, processes the remaining data which is valid. If there is no valid data then an empty list is returned.  

#### Implementation Notes
1. The implementation of the tests uses mocking of the RSS feeds. It is achieved by replacing the steps of connecting to the RSS feed server and establishing streams with the processing of test XML files as streams.
2. The test XML files follow the structure similar to the file provided by the RSS feed.
3. When a test is run, you will see some ERROR messages on the console but these errors would not affect the execution of tests. These messages are just printed by the mocking steps. The error statements are those statements which the mocked method would produce when the test XML file is passed to it as input.
4. The `ActivityTracker` class provides a main method in which a sample map has been created and the `getInactiveFeeds` method has been called. Its purpose is to demonstrate the use of the method. If you wish to obtain the results for a specific input, you will have to use the same technique to run the program.

#### Development Environment Configuration
1. Java version: 9.0.4
2. Apache Maven version: 3.5.4

#### Dependencies (Managed by Maven)
1. JUnit5-jupiter
2. Mockito-2.23.4

### Execution Instructions

Once you have maven installed in your system, open command window on the rss-activity-tracker directory (which contains the `pom.xml` file) and proceed with the commands written below.

**Build the project and install dependencies**: `mvn clean install`

**Run the tests**: `mvn test`
This command runs all the tests present in both the test class.

**Run the main class**: `mvn exec:java -Dexec.mainClass="ActivityTracker"`
This command runs the main class which is `InventoryManager`. The main class contains a dummy order and list of warehouses and it provides them as the input to the getCheapestShipment method of the InventoryAllocator class and displays the results on console.


