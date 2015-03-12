import static org.junit.Assert.*;

import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;

import org.junit.Test;


public class ZipperTest {

	@Test
	public void testConstructor() {
		Zipper thai;

		
		thai = new Zipper("zipper", "Folder1", "output");
		// Folder1 contains Kaleidoscope.txt'
		
	}
	
	@Test
	public void testFileNames() {
		Zipper thai;
		LinkedList<String> fileNames = new LinkedList<String>();

		
		thai = new Zipper("zipper", "Folder1_1", "output");
		// Folder1_1 contains Kaleidoscope.txt , Folder2_1 contains lastquestion.txt

		fileNames = thai.z.getfileNames();
		assertTrue(!fileNames.isEmpty());
		assertTrue(fileNames.get(0).equals("Folder1_1"));
		assertTrue(fileNames.get(1).equals("Folder1_1/Kaleidoscope.txt"));

			
		thai = new Zipper("zipper", "Folder2", "output");
		// Folder1_1 contains Kaleidoscope.txt Folder,  

		fileNames = thai.z.getfileNames();
		assertTrue(!fileNames.isEmpty());
		assertTrue(fileNames.get(0).equals("Folder2"));
		assertTrue(fileNames.get(1).equals("Folder2/Folder2_1"));
		assertTrue(fileNames.get(2).equals("Folder2/Folder2_1/lastquestion.txt"));
		assertTrue(fileNames.get(3).equals("Folder2/Kaleidoscope.txt"));
		
		
	}
	
	@Test
	public void testEncodedFiles() {
		Zipper thai;
	
		Hashtable<String, String> files = new Hashtable<String, String>();
		
		
		 thai = new Zipper("zipper", "Folder1_2", "output");
//		 Folder1_1 contains Kaleidoscope.txt

		files = thai.z.getEncodedFiles();
		assertTrue(!files.isEmpty());
		assertTrue(files.get("Folder1_2/Kaleidoscope.txt").equals("Folder1_2/Kaleidoscope.txt.huffman"));

			
		 thai = new Zipper("zipper", "Folder2_2", "output");
//		  Folder2_2 contains Kaleidoscope.txt and "Folder

		 files = thai.z.getEncodedFiles();
		 assertTrue(!files.isEmpty());
		 assertTrue(files.get("Folder2_2/Kaleidoscope.txt").equals("Folder2_2/Kaleidoscope.txt.huffman"));
		 assertTrue(files.get("Folder2_2/Folder2_1/lastquestion.txt").equals("Folder2_2/Folder2_1/lastquestion.txt.huffman"));
		
	}
	
	@Test
	public void testUnzip() {
		Zipper thai;
		thai = new Zipper("zipper", "Folder5", "blah.zipper");
		File file = new File("blah.zipper");
		thai = new Zipper("unzipper","blah.zipper", "output2");
        thai = new Zipper("zipper", "output2/Folder5", "output3");
		LinkedList<String> fileNames = new LinkedList<String>();
		fileNames = thai.z.getfileNames();
		assertTrue(!fileNames.isEmpty());
		
		assertTrue(fileNames.get(0).equals("output2/Folder5"));
		assertTrue(fileNames.get(1).equals("output2/Folder5/Kaleidoscope.txt"));
		assertTrue(fileNames.get(2).equals("output2/Folder5/TheAdventuresOfSherlockHolmes.txt"));
		
		Hashtable<String, String> files = new Hashtable<String, String>();
		files = thai.z.getEncodedFiles();
		assertTrue(files.get("output2/Folder5/Kaleidoscope.txt").equals("output2/Folder5/Kaleidoscope.txt.huffman"));
		assertTrue(files.get("output2/Folder5/TheAdventuresOfSherlockHolmes.txt").equals("output2/Folder5/TheAdventuresOfSherlockHolmes.txt.huffman"));
		
        file.delete();
	}
	
}