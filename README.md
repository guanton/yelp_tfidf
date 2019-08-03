# yelp_tfidf
tf-idf (term frequency–inverse document frequency) is a widely used metric for large datasets in order to determine the most relevant keywords. 
My program takes in a dataset from Yelp (attached), and it determines the top 30 keywords pertaining to each business in the dataset by using
the tf-idf metric. Since the dataset is quite large, my main method outputs this data for the top 10 businesses only (the ones with the most
characters in their reviews). 


Simply modify the method "filtertop10()" if you'd like to see the data from a different number of businesses from the dataset.
Also note that you must modify the comparator "SortByReviewCharCount" to alter the order of the businesses to be outputted.


