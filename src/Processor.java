import java.util.ArrayList;
import java.util.HashMap;

public class Processor {
	private Dictionary dictionary;
	private Dictionary data;

	private int nonHskWords;
	public Processor(Dictionary dictionary, Dictionary data) {
		this.dictionary = dictionary;
		this.data = data;
	}
	
	public void process2() {
		HashMap<Entry, Integer> result = new HashMap<>();
		for (int i = 0; i < data.size(); i++) {
			Entry currentEntry = data.get(i);
			String sentence = currentEntry.getChinese();
			//System.out.println("sentence: " + sentence);
			ArrayList<Entry> currentWords = algorithm(sentence);
			//addNonDictionaryItems(currentWords, sentence);
			addListToMap(result, currentWords);
		}
		int count = 0;
		for (Entry key : result.keySet()) {
			int value = result.get(key);
			String word =  key.getChinese();
			if (key.getLevel() <= 7 && value >= 1) {
				System.out.println(value + " : " + key);
				count+=value*word.length();
			}
		}
		System.out.println(count);
		//generateStatTable();
	}

	private void addNonDictionaryItems(ArrayList<Entry> currentWords, String sentence) {
		ArrayList<String> notInDictionary = new ArrayList<String>();
		int longestWord = 0;
		for (int i = 0; i < sentence.length(); i++) {
			//System.out.println(i);
			for (int j = 0; j < currentWords.size(); j++) {
				String currentWord = currentWords.get(j).getChinese();
				if(currentWord.length() > longestWord) {
					longestWord = currentWord.length();
				}
				//System.out.println("***" + i);
				if (i + currentWord.length() <= sentence.length()) {
					//System.out.println((sentence.substring(i, i + currentWord.length())));
					if(sentence.substring(i, i + currentWord.length()).equals(currentWord)) {
						if(i != 0) {
							notInDictionary.add(sentence.substring(0, i));
							System.out.println(sentence.substring(0, i));
						}
						//System.out.println(i);
						sentence = sentence.substring(i + currentWord.length(), sentence.length());
						//System.out.println("trim: " + sentence);
						i = -1;
						//System.out.println(i);
						break;
					}
				}
			}
			
		}
	}

	public void process() {
		addNonDictionaryItems(algorithm("我们是我们现在海棠我佛我们"), "我们是我们现在海棠我佛我们");
	}


	private void addListToMap(HashMap<Entry, Integer> result, ArrayList<Entry> currentWords) {
		for (int i = 0; i < currentWords.size(); i++) {
			Entry currentWord = currentWords.get(i);
			if (result.containsKey(currentWord)) {
				result.put(currentWord, result.get(currentWord) + 1);
			} else {
				result.put(currentWord, 1);
			}
		}
	}

	private ArrayList<Entry> algorithm(String sentence) {
		return siftThroughParts(sentence);
	}
	

	private ArrayList<Entry> siftThroughParts(String sentence) {
		ArrayList<Entry> siftedParts = new ArrayList<Entry>();
		ArrayList<String> alreadySeenCharacters = new ArrayList<String>();
		ArrayList<String> parts = getParts(sentence.trim());
		ArrayList<String> inDictionary = new ArrayList<String>();
		for (int i = 0; i < parts.size(); i++) {
			if(dictionary.contains(parts.get(i))) {
				boolean add = true;
				Entry word = dictionary.get(parts.get(i));
				inDictionary.add(parts.get(i));
				//System.out.println(parts.get(i));
				if (!has(alreadySeenCharacters, word.getChinese())) {
					siftedParts.add(word);
					//System.out.println(word);
					for (int k = 0; k < word.getChinese().length(); k++) {
						String currentchar = "" + word.getChinese().charAt(k);
						if (!has(alreadySeenCharacters, currentchar)) {
							alreadySeenCharacters.add(currentchar);
						}
					}
				}
			}
		}
		
//		ArrayList<String> notInDictionary = new ArrayList<String>();
//		for (int i = 0; i < parts.size(); i++) {
//			boolean add = true;
//			for (int j = 0; j < inDictionary.size(); j++) {
//				if (parts.get(i).equals(inDictionary.get(j))) {
//					add = false;
//					break;
//				}
//			}
//			if (add) {
//				notInDictionary.add(parts.get(i));
//				//System.out.println(parts.get(i));
//			}
//		}
//		
//		ArrayList<String> hsk7 = new ArrayList<String>();
//		for (int i = 0; i < notInDictionary.size(); i++) {
//			boolean add = true;
//			for (int j = 0; j < inDictionary.size(); j++) {
//				if (contains(notInDictionary.get(i), inDictionary.get(j))) {
//					add = false;
//				}
//			}
//			if (add) {
//				hsk7.add(notInDictionary.get(i));
//				//System.out.println(notInDictionary.get(i));
//			}
//		}
//		
//		ArrayList<String> siftedHsk7 = new ArrayList<String>();
//		for(int i = 0; i < hsk7.size(); i++) {
//			boolean add = true;
//			for (int j = 0; j < siftedHsk7.size(); j++) {
//				if (siftedHsk7.get(j).contains(hsk7.get(i))) {
//					add = false;
//					break;
//				}
//			}
//			if (add) {
//				siftedHsk7.add(hsk7.get(i));
//				//System.out.println(hsk7.get(i));
//			}
//		}
//		
//		for (int i = 0; i < siftedHsk7.size(); i++) {
//			String[] attributes = {siftedHsk7.get(i), "N/A", "N/A", "7"};
//			Entry word = new Entry(attributes);
//			siftedParts.add(word);
//		}
		return siftedParts;
	}
	
	
	private boolean contains(String a, String b) {
		return a.contains(b) || b.contains(a);
	}

	private boolean has(ArrayList<String> list, String word) {
		for(int i = 0; i < list.size(); i++) {
			if(word.equals(list.get(i))) {
				return true;
			}
		}
		return false;
	}
	private ArrayList<String> getParts(String sentence) {
		ArrayList<String> parts = new ArrayList<String>();
		for (int i = sentence.length() - 1; i >= 0; i--) {
			for (int j = 0; j < sentence.length(); j++) {
				if (j + i < sentence.length()) {
					String part = sentence.substring(j, j + i + 1);
					parts.add(part);
					//System.out.println(part);
				} else {
					break;
				}
			}
		}
		return parts;
	}
}
