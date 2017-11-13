import java.util.ArrayList;
import java.util.HashMap;

public class Processor2 {
	private Dictionary dictionary;
	private Dictionary data;

	public Processor2(Dictionary dictionary, Dictionary data) {
		this.dictionary = dictionary;
		this.data = data;
	}
	
	public void process2() {
		algorithm("我们是海棠我棠我们你佛");
	}
	public void process() {
		HashMap<Entry,Integer> result = new HashMap<>();
		for (int i = 0; i < data.size(); i++) {
			Entry currentEntry = data.get(i);
			String sentence = currentEntry.getChinese();
			HashMap<Entry,Integer> currentWords = algorithm(sentence);
			add(result, currentWords);
		}	
		printMap(result);
	}
	
	private void add(HashMap<Entry, Integer> result, HashMap<Entry, Integer> currentWords) {
		for (Entry key : currentWords.keySet()) {
			if (result.containsKey(key)) {
				result.put(key, result.get(key) + 1);
			} else {
				result.put(key, 1);
			}
		}
	}

	public HashMap<Entry,Integer> algorithm(String sentence) {
		HashMap<Entry, Integer> result = new HashMap<>();
		int bigWindow = 3;
		String runningString = "";
		for (int i = 0; i < sentence.length(); i++) {
			for(int k = bigWindow; k > 0; k--) {
				if (i + k <= sentence.length()) {
					String substring = sentence.substring(i, i + k);
					Entry entry = searchFor(substring);
					if (entry != null) {
						if (result.containsKey(entry)) {
							result.put(entry, result.get(entry) + 1);
						} else {
							result.put(entry, 1);
						}
						i += substring.length() - 1;
						
						if (runningString.length() > 0) {
							String[] attributes = {runningString, "", "", "7"};
							Entry hsk7 = new Entry(attributes);
							if (result.containsKey(hsk7)) {
								result.put(hsk7, result.get(hsk7) + 1);
							} else {
								result.put(hsk7, 1);
							}
							System.out.println(hsk7);
							runningString = "";
						}
						
						System.out.println(entry);
						break;
					} else {
						if (k == 1) {
							runningString += substring; 
						}
					}
				}
				
			}
		}
		
		if (runningString.length() > 0) {
			String[] attributes = {runningString, "", "", "7"};
			Entry hsk7 = new Entry(attributes);
			if (result.containsKey(hsk7)) {
				result.put(hsk7, result.get(hsk7) + 1);
			} else {
				result.put(hsk7, 1);
			}
			System.out.println(hsk7);
			runningString = "";
		}
		//printMap(result);
		return result;
	}
	
	private void printMap(HashMap<Entry, Integer> result) {
		int count = 0;
		for (Entry key : result.keySet()) {
			int occ = result.get(key);
			int hsk = key.getLevel();
			if (hsk == 7) {
				System.out.println(occ + " : " + key);
				count++;
			}
			
		}
		
		System.out.println("there are " + count + " distinct words");
	}

	public Entry searchFor(String chinese) {
		for (int i = 0; i < dictionary.size(); i++) {
			if (chinese.equals(dictionary.get(i).getChinese())) {
				return dictionary.get(i);
			}
		}
		return null;
	}
}
