import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarkovModel {
	
	// Key = prefixes, Value = [ Key = word, Value = appearances after prefix]
	private MyHashMap<String, MyHashMap<String, Integer>> wordMap; 
	// Key = Prefixes, Value = Probability distribution of next word
	private MyHashMap<String, ArrayList<String>> wordMapCache;
	private int degree;
	private Random rng;
	
	public MarkovModel(Random rng, int degree) {
		wordMap = new MyHashMap<>();
		wordMapCache = new MyHashMap<>();
		this.rng = rng;
		this.degree = degree;
	}
	
	
	public int train(String trainingText) {
		
		// split the trainingText with " "
		String[] arrOfText = trainingText.split(" ");
		
		// the size of the array is the word count
		int wordCount = arrOfText.length;
		
		// train the array.
		for ( int i =0; i < wordCount - degree; i++ )
		{
			String currentPrefix = "";
			String nextWord = "";
			for( int j = i; j < i + degree; j++ )
			{
				currentPrefix = currentPrefix + arrOfText[ j ]+ " ";
			}
			nextWord = arrOfText[ i + degree ];
			currentPrefix = currentPrefix.trim();
			
			// update WordMap and WordMapCache
			updateWordMap( currentPrefix, nextWord );
			updateWordMapCache( currentPrefix, nextWord );
		}
		return wordCount;
	}
	
	// Keeps track of how many times the given word has occurred when preceded prefix
	private void updateWordMap(String prefix, String word) {
		int currentValue = 0;
		MyHashMap<String, Integer> newEntry = new MyHashMap<>();
		if( wordMap.containsKey( prefix ) )
		{
			// when contains the prefix, check if the entry contains word
			MyHashMap<String, Integer> currentEntry = wordMap.get( prefix );
			if(wordMap.get( prefix ).containsKey( word ) ) 
			{
				// if the entry contains the word, increment the value by 1
				currentValue = wordMap.get( prefix ).get( word );
				currentValue += 1;	
			}else 
			{
				// if the entry does not contain the word, set the value = 1
				currentValue = 1;
			}
			currentEntry.set( word, currentValue );
			wordMap.set( prefix, currentEntry );
			
		}else 
		{
			// if the wordMap does not contain the prefix
			currentValue = 1;
			newEntry.set(word, currentValue);
			wordMap.set(prefix, newEntry);
		} 
	}
	// Keeps track of the next word of preceded prefix, each value is the list of the words
	// that follows that prefix
	private void updateWordMapCache(String prefix, String word) {
		
		// if wordMapCache contains the prefix, add the word to the list.
		if( wordMapCache.containsKey( prefix ) ) 
		{
			ArrayList<String> currentList = wordMapCache.get(prefix);
			currentList.add(word);
			wordMapCache.set(prefix, currentList);
			
		}else 
		{
			// if wordMapCache does not contain the prefix, create a new arrayList,
			// and add the word to the list.
			ArrayList<String> newList = new ArrayList<>();
			newList.add(word);
			wordMapCache.set(prefix, newList);
		}
	}
	 
	
	public String generateSentence(int wordCount) {
		List<String> prefixes = wordMap.keys();
		int initialPrefixIdx = rng.nextInt(prefixes.size());
		String initialPrefix = prefixes.get(initialPrefixIdx);
		StringBuilder sentence = new StringBuilder(wordCount);
	
		sentence.append(initialPrefix);
		
		String currentPrefix = initialPrefix;
		for (int i = 0; i < wordCount - degree; ++i) {
			String generatedWord = generateWord(currentPrefix);
			
			// not enough training text to generate entire sentence
			if (generatedWord == null) {
				break;
			}
			sentence.append(" " + generatedWord);
			currentPrefix = getNextPrefix(currentPrefix, generatedWord);
		}
		
		return sentence.toString();
	}
	
	/**
	 * Uses the probability distribution of the possible next words to generate
	 * a random word
	 * @param prefix the prefix before the generated words
	 * @return the randomly generated word
	 */
	private String generateWord(String prefix) {
		var counts = wordMap.get(prefix);
		if (counts == null) {
			return null;
		}
		ArrayList<String> possiblePredictions = getPossibleWords(prefix, counts);
		String predictedWord = possiblePredictions.get(rng.nextInt(possiblePredictions.size()));
		possiblePredictions.remove(predictedWord);
		return predictedWord;
	}


	// generate probability distribution of next word for the prefix
	private ArrayList<String> getPossibleWords(String prefix, MyHashMap<String, Integer> counts) {
		
		//if the wordMapCache contain the prefix, return the list
		 if( wordMapCache.containsKey( prefix ) ) 
		 {
			ArrayList<String> currentList = wordMapCache.get(prefix);
			return currentList;
			
		 }else 
		 {
			// if the worldMapCache does not contain the prefix, 
			// generate a new prefix and get the entry, call getPossibleWords() again
			ArrayList<String> list = new ArrayList<>();
			list = (ArrayList<String>) counts.keys();
			return list;
		 }
	}
	
	private String getNextPrefix(String currentPrefix, String generatedWord) {
		return currentPrefix.substring(currentPrefix.indexOf(' ') + 1) + " " + generatedWord;
	}
	
}