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
			Entry keyReal = get(result, key.getChinese());
			if (keyReal != null) {
				result.put(keyReal, result.get(keyReal) + 1);
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
						
						runningString = addHsk7(sentence, result, runningString);
						
						break;
					} else {
						if (k == 1) {
							runningString += substring.trim(); 
						}
					}
				}
				
			}
		}
		
		addHsk7(sentence, result, runningString);
		return result;
	}

	private String addHsk7(String sentence, HashMap<Entry, Integer> result, String runningString) {
		if (runningString.length() > 0) { //)&& runningString.equals("海")) {
			//System.out.println("{" + runningString + "} " + runningString.length());
			
			Entry key = get(result, runningString);
			if (key != null) {
				result.put(key, result.get(key) + 1);
			} else {
				String[] attributes = {runningString, sentence, "", "8"};
				//System.out.println(attributes[0] + " : " + sentence);
				result.put(new Entry(attributes), 1);
			}
			runningString = "";
		}
		return runningString;
	}
	


	private Entry get(HashMap<Entry, Integer> result, String runningString) {
		//if (runningString.equals("椿")) {
			for (Entry key : result.keySet()) {
				String chinese = key.getChinese();
				if (chinese.equals(runningString)) {
					return key;
				}
			}
		//}
		return null;
	}

	private void printMap(HashMap<Entry, Integer> result) {
		int count = 0;
		for (Entry key : result.keySet()) {
			int occ = result.get(key);
			int hsk = key.getLevel();
			if (hsk == 8 && occ >= 3) {
				System.out.println(occ + " : " + key.getChinese());
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
