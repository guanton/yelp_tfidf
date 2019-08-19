# yelp_tfidf
tf-idf (term frequency–inverse document frequency) is a widely used metric for large datasets in order to determine the most relevant keywords. 
My program takes in a dataset from Yelp (attached), and it determines the top 30 keywords pertaining to each business in the dataset by using
the tf-idf metric. Since the dataset is quite large, my main method outputs this data among other criteria, for the top 10 businesses only (the ones with the most characters in their reviews). 


Simply modify the method "filtertop10()" if you'd like to see the data from a different number of businesses from the dataset.
Also note that you must modify the comparator class "SortByReviewCharCount" to alter the order of the businesses to be outputted. By default, it prioritizes businesses with the most number of characters in their reviews. Later on, I will make this a more user-friendly search engine with GUI, but for now, it's just backend programming that parses and prints the desired data.

Finally, IDEs such as intelliJ restrict the file size of attached .txt files. My code will run on much larger datasets, but these cannot be dragged directly into intelliJ.

To make this project, I made use of:

- Comparators
- Standard parsing techniques
- Linked Hash Maps, regular Hash Maps, and map conversions
- PriorityQueues (for efficiency)
- Input Streams from a file

