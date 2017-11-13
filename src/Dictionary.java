import java.util.ArrayList;

public class Dictionary extends ExtractedList {
	private ArrayList<Entry> dictionary;

	
	public Dictionary(String filePath) {
		super(filePath);
		dictionary = getEntriesFromExtractedList();
	}

	private ArrayList<Entry> getEntriesFromExtractedList() {
		ArrayList<Entry> words = new ArrayList<>();
		
		for (int i = 0; i < extractedList.size(); i++) {
			Entry word = new Entry(extractedList.get(i));
			words.add(word);
		}
		
		return words;
	}

	public Entry get(int i) {
		if(i < dictionary.size()) {
			return dictionary.get(i);
		}
		return null;
	}
	
	public Entry get(String string) {
		for (int i = 0; i < dictionary.size(); i++) {
			Entry currentEntry = dictionary.get(i);
			if(currentEntry.getChinese().equals(string)) {
				return currentEntry;
			}
		}
		return null;
	}
	public int size() {
		return dictionary.size();
	}
	public String toString() {
		String returnString = "";
		for(int i = 0; i < dictionary.size(); i++) {
			returnString += "\n" + dictionary.get(i).toString();
		}
		returnString += "\n" + dictionary.size();
		return returnString;
	}

	public boolean contains(String string) {
		for (int i = 0; i < dictionary.size(); i++) {
			Entry currentEntry = dictionary.get(i);
			if(currentEntry.getChinese().equals(string)) {
				return true;
			}
		}
		return false;
	}

}
