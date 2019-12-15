# Yelp Search Engine (parsing from JSON)
I had previously implemented a basic Yelp Search Engine that parsed .txt files that I obtained from the Yelp website (https://www.yelp.com/dataset/download). However, I was not satisfied with the speed of my program (it would take a few seconds to return results on a relatively small dataset of 20mb). I realized that the vast majority of the time spent was on parsing these orignal Yelp .txt files, which contain every word of every review of every business in the file. Therefore, it would be much more efficient to search  from a different file that only stores the vital information, and that is sorted in an effective manner.

My idea is to keep a json file that stores the following information for every business in the original Yelp dataset: 1) Business name, 2) Business ID, 3) Address, 4) a map where the keys are the words present in the reviews and the values are the corresponding tf-idf scores

The key difference is 4). Parsing from the original dataset required going through all of the businesses once in order to generate the tf and idf scores related to the query, followed by a run through over all businesses to determine which ones have the highest tf-idf values. This second run through is necessary because it is impossible to keep a running total of tf-idf values without approximating the scores. The idf tally may grow after having calculated the tf-idf value for a given business (I did try this technique in earlier commits). Even by using efficient data structures like heaps, the search times were much too slow. However, to get around this, I can store tf-idf scores in a JSON file, and I would only have to parse this once. Moreover, parsing this JSON file should be much quicker since I would avoid having to double parse repeated words, and because I can sort the words alphabetically so that the search time is logarithmic rather than linear.

https://www.yelp.com/dataset/download


