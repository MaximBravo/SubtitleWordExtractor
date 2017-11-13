
public class SubtitleWordExtractor {
	public static void main(String[] args) {
		Dictionary hskBig = new Dictionary("C:\\Users\\Maxim Bravo\\Desktop\\ChineseExtraction\\hskBig.csv");
		//System.out.println(hskBig.toString());
		
		Dictionary testData = new Dictionary("C:\\Users\\Maxim Bravo\\Desktop\\ChineseExtraction\\test_data.csv");
		//System.out.println(testData.toString());
		
		Dictionary bfb = new Dictionary("C:\\Users\\Maxim Bravo\\Desktop\\ChineseExtraction\\BFB.csv");
		//System.out.println(bfb);
		Processor2 processor = new Processor2(hskBig, bfb);
		processor.process();
	}
}
