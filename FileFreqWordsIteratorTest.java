import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.HashMap;


public class FileFreqWordsIteratorTest {

	@Rule
  	public ExpectedException exception = ExpectedException.none();
	
	
	@Test
	public void testConstructor() throws Exception {
//		exception.expectMessage("Please enter either \"encode\" or \"decode\" for 1st argument ");
//		HuffmanEncoding thai = new HuffmanEncoding("encode3","test.txt","output.txt.huffman","0");

		HuffmanEncoding thai2 = new HuffmanEncoding("encode2","test0.txt","output.txt.huffman","0");
	}
	

	@Test
	public void testgetFreq() throws Exception { // testing countword
		HuffmanEncoding thai;
		HashMap<String,Integer> freq = new HashMap<String, Integer>();
		thai = new HuffmanEncoding("encode2","test0.txt","output.txt.huffman", "0");
		freq =  thai.en.getGetFreq();  // test0.txt is empty
		assertTrue(freq.isEmpty());


		thai = new HuffmanEncoding("encode2","test1.txt","output.txt.huffman", "0" );
		freq =  thai.en.getGetFreq(); // test1.txt has only 1 character
		assertTrue(!freq.isEmpty());
		assertEquals((int)freq.get("01100001"), 1);
		assertTrue( freq.get("01100010") == null);

		thai = new HuffmanEncoding("encode2","test3.txt","output.txt.huffman", "0" );
		freq =  thai.en.getGetFreq();  // test3.txt is a single space
		assertTrue(!freq.isEmpty());
		assertEquals((int)freq.get("00100000"), 1 );


		thai = new HuffmanEncoding("encode2","test4.txt","output.txt.huffman", "0" );
		freq =  thai.en.getGetFreq();  // test3.txt is a single newline
		assertTrue(!freq.isEmpty());
		assertEquals((int)freq.get("00001010"), 1);

		thai = new HuffmanEncoding("encode2","test2.txt","output.txt.huffman", "0");
		freq =  thai.en.getGetFreq();  // test2.txt is "a b c a b a\n"

		assertEquals((int)freq.get("01100001"), 3 );
		assertEquals((int)freq.get("01100010"), 2 );
		assertEquals((int)freq.get("01100011"), 1 );
		assertEquals((int)freq.get("00100000"), 5 );
		assertEquals((int)freq.get("00001010"), 1 );


		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman", "0");
		freq =  thai.en.getGetFreq();  // test5.txt is "it was the best of time , it was the worst of time"
		assertTrue(!freq.isEmpty());  // n = 0  args[3]
		
		assertEquals((int)freq.get("01101000") ,2);
		assertEquals((int)freq.get("01100010") ,1);
		assertEquals((int)freq.get("01101101") ,2);
		assertEquals((int)freq.get("01101111") ,3);
		assertEquals((int)freq.get("00100000") ,12);
		assertEquals((int)freq.get("01100110") ,2);
		assertEquals((int)freq.get("01100101") ,5);
		assertEquals((int)freq.get("01100001") ,2);
		assertEquals((int)freq.get("01110010") ,1);
		assertEquals((int)freq.get("01110011") ,4);
		assertEquals((int)freq.get("01110100") ,8);
		assertEquals((int)freq.get("01101001") ,4);
		assertEquals((int)freq.get("01101001") ,4);
		assertEquals((int)freq.get("00101100") ,1);
		assertEquals((int)freq.get("01110111") ,3);

		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman", "4");
		freq =  thai.en.getGetFreq();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!freq.isEmpty());  // n = 0

		assertEquals((int)freq.get("011101110110000101110011"),2);
		assertEquals((int)freq.get("01100010"),1);
		assertEquals((int)freq.get("0110100101110100"),2);
		assertEquals((int)freq.get("01101101"),2);
		assertEquals((int)freq.get("01101111"),1);
		assertEquals((int)freq.get("00100000"),12);
		assertEquals((int)freq.get("01100101"),3);
		assertEquals((int)freq.get("011101000110100001100101"),2);
		assertEquals((int)freq.get("01110010"),1);
		assertEquals((int)freq.get("01110100"),4);
		assertEquals((int)freq.get("01110011"),2);
		assertEquals((int)freq.get("01101001"),2);
		assertEquals((int)freq.get("0110111101100110"),2);
		assertEquals((int)freq.get("00101100"),1);
		assertEquals((int)freq.get("01110111"),1);

	}
	
	
	@Test
	public void testTable() throws Exception {   // testing countword
		HuffmanEncoding thai;
		HashMap<String,String> table = new HashMap<String, String>();

		thai = new HuffmanEncoding("encode2","test0.txt","output.txt.huffman","0");
		table =  thai.en.getTable();  // test0.txt is empty
		assertTrue(table.isEmpty());


		thai = new HuffmanEncoding("encode2","test1.txt","output.txt.huffman","0");
		table =  thai.en.getTable();  // test1.txt is only 1 character a
		assertTrue(!table.isEmpty());
		assertTrue(table.get("01100001") != null);

		thai = new HuffmanEncoding("encode2","test3.txt","output.txt.huffman","0");
		table =  thai.en.getTable();  // test3.txt is a single space
		assertTrue(!table.isEmpty());
		assertTrue(table.get("00100000") != null);

		thai = new HuffmanEncoding("encode2","test4.txt","output.txt.huffman", "0");
		table =  thai.en.getTable();  // test4.txt is a single newline
		assertTrue(!table.isEmpty());
		assertTrue(table.get("00001010") != null);


		thai = new HuffmanEncoding("encode2","test2.txt","output.txt.huffman", "0");
		table =  thai.en.getTable(); // test2.txt is "a b c a b a\n"

		assertTrue(table.get("01100001") != null);
		assertTrue(table.get("01100010") != null);
		assertTrue(table.get("EOF") != null);
		assertTrue(table.get("01100011") != null);
		assertTrue(table.get("00001010") != null);
		assertTrue(table.get("00100000") != null);



		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman","0");
		table =  thai.en.getTable();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!table.isEmpty());  // n = 0
		
		assertTrue(table.get("01101000") != null);
		assertTrue(table.get("01100010") != null);
		assertTrue(table.get("01101101") != null);
		assertTrue(table.get("01101111") != null);
		assertTrue(table.get("00100000") != null);
		assertTrue(table.get("01100110") != null);
		assertTrue(table.get("01100101") != null);
		assertTrue(table.get("EOF") != null);
		assertTrue(table.get("01100001") != null);
		assertTrue(table.get("01110010") != null);
		assertTrue(table.get("01110011") != null);
		assertTrue(table.get("01110100") != null);
		assertTrue(table.get("01101001") != null);
		assertTrue(table.get("01101001") != null);
		assertTrue(table.get("00101100") != null);
		assertTrue(table.get("01110111") != null);


		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman", "4");
		table =  thai.en.getTable();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!table.isEmpty());  // n = 4
		
		assertTrue(table.get("011101110110000101110011") != null);
		assertTrue(table.get("01100010") != null);
		assertTrue(table.get("0110100101110100") != null);
		assertTrue(table.get("01101101") != null);
		assertTrue(table.get("01101111") != null);
		assertTrue(table.get("00100000") != null);
		assertTrue(table.get("01100101") != null);
		assertTrue(table.get("011101000110100001100101") != null);
		assertTrue(table.get("EOF") != null);
		assertTrue(table.get("01110010") != null);
		assertTrue(table.get("01110100") != null);
		assertTrue(table.get("01110011") != null);
		assertTrue(table.get("01101001") != null);
		assertTrue(table.get("0110111101100110") != null);
		assertTrue(table.get("00101100") != null);
		assertTrue(table.get("01110111") != null);


		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman", "7");
		table =  thai.en.getTable();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!table.isEmpty());  // n = 7

		assertTrue(table.get("01110100011010010110110101100101") != null);
		assertTrue(table.get("011101110110000101110011") != null);
		assertTrue(table.get("0111011101101111011100100111001101110100") != null);
		assertTrue(table.get("01100010011001010111001101110100") != null);
		assertTrue(table.get("0110100101110100") != null);
		assertTrue(table.get("011101000110100001100101") != null);
		assertTrue(table.get("EOF") != null);
		assertTrue(table.get("0110111101100110") != null);
		assertTrue(table.get("00101100") != null);

	}

	@Test
	public void testHuffmanTree() throws Exception { // to make sure the code map get the right bits string in the table
		HuffmanEncoding thai;						// go through the tree by the code map (key of table) and get the bits string
													//in the tree to make sure it the same value(bits string) in the table
		HashMap<String,String> table = new HashMap<String, String>();


		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman","0");
		table =  thai.en.getTable();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!table.isEmpty());  // n = 0
		
		assertTrue(table.get("01101000") != null);
		assertTrue(thai.en.checkTree(table.get("01101000")).equals("01101000"));
		
		assertTrue(table.get("01100010") != null);
		assertTrue(thai.en.checkTree(table.get("01100010")).equals("01100010"));

		assertTrue(table.get("01101101") != null);
		assertTrue(thai.en.checkTree(table.get("01101101")).equals("01101101"));

		assertTrue(table.get("01101111") != null);
		assertTrue(thai.en.checkTree(table.get("01101111")).equals("01101111"));

		assertTrue(table.get("00100000") != null);
		assertTrue(thai.en.checkTree(table.get("00100000")).equals("00100000"));

		assertTrue(table.get("01100110") != null);
		assertTrue(thai.en.checkTree(table.get("01100110")).equals("01100110"));

		assertTrue(table.get("01100101") != null);
		assertTrue(thai.en.checkTree(table.get("01100101")).equals("01100101"));

		assertTrue(table.get("EOF") != null);
		assertTrue(thai.en.checkTree(table.get("EOF")).equals("EOF"));

		assertTrue(table.get("01100001") != null);
		assertTrue(thai.en.checkTree(table.get("01100001")).equals("01100001"));

		assertTrue(table.get("01110010") != null);
		assertTrue(thai.en.checkTree(table.get("01110010")).equals("01110010"));

		assertTrue(table.get("01110011") != null);
		assertTrue(thai.en.checkTree(table.get("01110011")).equals("01110011"));

		assertTrue(table.get("01110100") != null);
		assertTrue(thai.en.checkTree(table.get("01110100")).equals("01110100"));

		assertTrue(table.get("01101001") != null);
		assertTrue(thai.en.checkTree(table.get("01101001")).equals("01101001"));

		assertTrue(table.get("01101001") != null);
		assertTrue(thai.en.checkTree(table.get("01101001")).equals("01101001"));

		assertTrue(table.get("00101100") != null);
		assertTrue(thai.en.checkTree(table.get("00101100")).equals("00101100"));

		assertTrue(table.get("01110111") != null);
		assertTrue(thai.en.checkTree(table.get("01110111")).equals("01110111"));

		//---------------------------------------------------------------------------

		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman","4");
		table =  thai.en.getTable();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!table.isEmpty());  // n = 4
		
		assertTrue(table.get("011101110110000101110011") != null);

		assertTrue(table.get("011101110110000101110011") != null);
		assertTrue(thai.en.checkTree(table.get("011101110110000101110011")).equals("011101110110000101110011"));
		
		assertTrue(table.get("01100010") != null);
		assertTrue(thai.en.checkTree(table.get("01100010")).equals("01100010"));

		assertTrue(table.get("0110100101110100") != null);
		assertTrue(thai.en.checkTree(table.get("0110100101110100")).equals("0110100101110100"));

		assertTrue(table.get("01101101") != null);
		assertTrue(thai.en.checkTree(table.get("01101101")).equals("01101101"));

		assertTrue(table.get("01101111") != null);
		assertTrue(thai.en.checkTree(table.get("01101111")).equals("01101111"));

		assertTrue(table.get("00100000") != null);
		assertTrue(thai.en.checkTree(table.get("00100000")).equals("00100000"));

		assertTrue(table.get("01100101") != null);
		assertTrue(thai.en.checkTree(table.get("01100101")).equals("01100101"));

		assertTrue(table.get("011101000110100001100101") != null);
		assertTrue(thai.en.checkTree(table.get("011101000110100001100101")).equals("011101000110100001100101"));

		assertTrue(table.get("EOF") != null);
		assertTrue(thai.en.checkTree(table.get("EOF")).equals("EOF"));

		assertTrue(table.get("01110010") != null);
		assertTrue(thai.en.checkTree(table.get("01110010")).equals("01110010"));

		assertTrue(table.get("01110100") != null);
		assertTrue(thai.en.checkTree(table.get("01110100")).equals("01110100"));

		assertTrue(table.get("01110011") != null);
		assertTrue(thai.en.checkTree(table.get("01110011")).equals("01110011"));

		assertTrue(table.get("01101001") != null);
		assertTrue(thai.en.checkTree(table.get("01101001")).equals("01101001"));

		assertTrue(table.get("0110111101100110") != null);
		assertTrue(thai.en.checkTree(table.get("0110111101100110")).equals("0110111101100110"));

		assertTrue(table.get("00101100") != null);
		assertTrue(thai.en.checkTree(table.get("00101100")).equals("00101100"));

		assertTrue(table.get("01110111") != null);
		assertTrue(thai.en.checkTree(table.get("01110111")).equals("01110111"));
	}
	
	@Test
	public void testDecoding() throws Exception {
		HuffmanEncoding thai, thai2;
		FileCharIterator myIter1 , myIter2;

		thai = new HuffmanEncoding("encode2","test1.txt","testDecoding1.txt.huffman", "0");
		thai2 = new HuffmanEncoding("decode", "testDecoding1.txt.huffman", "testDecoding1.txt");
		myIter1 = new FileCharIterator("test1.txt");   // test1.txt is only a character "a"
		myIter2 = new FileCharIterator("testDecoding1.txt");

		assertTrue(myIter1.hasNext() == true);
		assertTrue(myIter2.hasNext() == true);
		String a = "01100001";
		assertTrue(myIter1.next().equals(a));
		assertTrue(myIter2.next().equals(a));
		assertTrue(myIter1.hasNext() == false);
		assertTrue(myIter1.hasNext() == false);


		thai = new HuffmanEncoding("encode2","test5.txt","testDecoding5.txt.huffman", "4");
		thai2 = new HuffmanEncoding("decode", "testDecoding5.txt.huffman", "testDecoding5.txt");
		// test5.txt is "it was the best of time , it was the worst of time"
		
		myIter1 = new FileCharIterator("test5.txt");
		myIter2 = new FileCharIterator("testDecoding5.txt");

		while(myIter1.hasNext() && myIter2.hasNext()) {
			assertTrue(myIter1.next().equals(myIter2.next()));
		}

	}
}
