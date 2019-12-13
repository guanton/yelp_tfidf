# yelp_tfidf
tf-idf (term frequency–inverse document frequency) is a widely used metric for large datasets in order to determine the most relevant keywords. 
My program takes in a dataset from Yelp, and initially, my project was to determines the top 30 keywords pertaining to each business in the dataset by using the tf-idf metric. I began by sorting the businesses with respect to the length of their reviews (in characters) and printing them out. 

In Dec 2020, I have begun working on making it into a simple search engine that accepts a query and that outputs the 10 most relevant restaurants pertaining to the keywords in the query. I have tried two approaches so far: tf-idf and frequency ratios (I divide the number of times a keyword shows up in a business' reviews and divide it by the total number of words in the reviews). So far, I have a functioning back-end in my YelpAnalysis class. To search the database, you can enter a query into the argument of txttoString in my main method, and you will see 10 businesses with tf-idf scores and other vital information printed out systematically. I will also implement a GUI for my search engine.

Finally, IDEs such as intelliJ restrict the file size of attached .txt files. My code will run on much larger datasets, but these cannot be dragged directly into intelliJ. I will try to get my program to run on larger datasets and perhaps use JSON to make the search more efficient, because currently, the algorithm goes through every review of every business in the dataset.

To make this project, I made use of:

- Comparators
- Standard parsing techniques
- Linked Hash Maps, regular Hash Maps, and map conversions
- PriorityQueues and Guava's MinMaxPriorityQueue
- Input Streams from a file

