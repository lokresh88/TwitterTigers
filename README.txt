
DataCollector 
-------------
Simple PHP collector uses a open source Library to collect live tweets / retweets / mentions of a selected list of celebrities. Run in Jinx Cluster & Json to be collected after a number of days.

Input
-----
List of celebrities to follow/monitor.

Output
------
Json files for each celebrity.



InvertedIndex + DataProcessor 
-----------------------------
Inverted Index takes each of the json file collected above and returns the LIst of keywords - will be used a suggestions for our app once a C is selected.

DataProcessor
------------
Converts these raw json into related Celeb Info , their Tweets , suggestion Keywords/C & retweets at level 2 --> MongoDB