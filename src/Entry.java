
public class Entry {
	private String chinese;
	private String pinyin;
	private String definitions;
	private int level;
	
	public Entry(String[] attributes) {
		chinese = attributes[0];
		pinyin = attributes[1];
		definitions = attributes[2];
		if (attributes.length >= 4) {
			level = Integer.parseInt(attributes[3]);
		} else {
			level = -1;
		}
	}
	
	public String toString() {
		return chinese + ", " + pinyin + ", " + definitions + ", " + level;
	}

	public String getChinese() {
		return chinese;
	}

	public int getLevel() {
		return level;
	}
}
