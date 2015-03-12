import java.util.*;


public class TestSecondPart {
	public TestSecondPart(){
	}

	public static void main(String[] args) {
		FileFreqWordsIterator myIter = new FileFreqWordsIterator(args[0], args[1]);
		myIter.initIterator();
//
		while(myIter.hasNext()) {
			String z= myIter.next();
//			System.out.println("h");
//			if(z.equals("00100000")) {
//				System.out.println("space");
//			}
//			else if(z.equals("00001010")) {
//				System.out.println("line");
//			}
//			else {
//				System.out.println(" z = "+ z);
//			}
			System.out.println(z);
		}
		// String z = ",";
		// int a = (new Integer(z.charAt(0)));
		// System.out.println("value = " + a);

		// BufferedReader in = new BufferedReader(new FileReader("test3.txt"));
		// FileCharIterator myIter = new FileCharIterator("test3.txt");
		// while(myIter.hasNext()) {
		// 	System.out.println(myIter.next());
		// }
		// // /0010000000011101

		System.out.println("end");
	}
}
