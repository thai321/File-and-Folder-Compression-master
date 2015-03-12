import static org.junit.Assert.*;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.HashMap;


public class HuffmanEncodingTest {

	@Rule
  	public ExpectedException exception = ExpectedException.none();
	
	
	@Test
	public void testConstructor() throws Exception {
		exception.expectMessage("Please enter either \"encode\" or \"decode\" for 1st argument ");
		HuffmanEncoding thai = new HuffmanEncoding("encode3","test.txt","output.txt.huffman");
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


		thai = new HuffmanEncoding("encode2","test5.txt","output.txt.huffman", "4");
		freq =  thai.en.getGetFreq();  // test3.txt is "it was the best of time , it was the worst of time"
		assertTrue(!freq.isEmpty());  // n = 0

		assertEquals(freq.get("011101110110000101110011"),2);
		assertEquals(freq.get("01100010"),1);
		assertEquals(freq.get("0110100101110100"),2);
		assertEquals(freq.get("01101101"),1);
		assertEquals(freq.get("01101111"),1);
		assertEquals(freq.get("00100000"),1);
		assertEquals(freq.get("01100101"),1);
		assertEquals(freq.get("011101000110100001100101"),2);
		assertTrue(freq.get("EOF") != null);
		assertEquals(freq.get("01110010"),1);
		assertEquals(freq.get("01110100"),1);
		assertEquals(freq.get("01110011"),1);
		assertEquals(freq.get("01101001"),1);
		assertEquals(freq.get("0110111101100110"),2);
		assertEquals(freq.get("00101100"),1);
		assertEquals(freq.get("01110111"),1);



		thai = new HuffmanEncoding("encode2","test2.txt","output.txt.huffman", "0");
		freq =  thai.en.getGetFreq();  // test2.txt is "a b c a b a\n"

		assertEquals((int)freq.get("01100001"), 3 );
		assertEquals((int)freq.get("01100010"), 2 );
		assertEquals((int)freq.get("01100011"), 1 );
		assertEquals((int)freq.get("00100000"), 5 );
		assertEquals((int)freq.get("00001010"), 1 );

	}
	
	
	@Test
	public void testTable() throws Exception {   // testing countword
		HuffmanEncoding thai;
		HashMap<String,String> table = new HashMap<String, String>();

		thai = new HuffmanEncoding("encode2","test0.txt","output.txt.huffman");
		table =  thai.en.getTable();  // test0.txt is empty
		assertTrue(table.isEmpty());


		thai = new HuffmanEncoding("encode","test1.txt","output.txt.huffman");
		table =  thai.en.getTable();  // test0.txt is empty
		assertTrue(!table.isEmpty());
		assertTrue(table.get("01100001") != null);

		thai = new HuffmanEncoding("encode","test3.txt","output.txt.huffman");
		table =  thai.en.getTable();  // test3.txt is a single space
		assertTrue(!table.isEmpty());
		assertTrue(table.get("00100000") != null);

		thai = new HuffmanEncoding("encode","test4.txt","output.txt.huffman");
		table =  thai.en.getTable();  // test3.txt is a single newline
		assertTrue(!table.isEmpty());
		assertTrue(table.get("00001010") != null);


		thai = new HuffmanEncoding("encode","test2.txt","output.txt.huffman");
		table =  thai.en.getTable(); // test2.txt is "a b c a b a\n"

		assertTrue(table.get("01100001") != null);
		assertTrue(table.get("01100010") != null);
		assertTrue(table.get("EOF") != null);
		assertTrue(table.get("01100011") != null);
		assertTrue(table.get("00001010") != null);
		assertTrue(table.get("00100000") != null);



		thai = new HuffmanEncoding("encode","test5.txt","output.txt.huffman");
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


		thai = new HuffmanEncoding("encode","test5.txt","output.txt.huffman");
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
	}
	
	@Test
	public void testDecoding() throws Exception {
		HuffmanEncoding thai, thai2;
		thai = new HuffmanEncoding("encode","test5.txt","testDecoding.txt.huffman");
		thai2 = new HuffmanEncoding("decode", "testDecoding.txt.huffman", "testDecoding.txt");
		// test3.txt is "it was the best of time , it was the worst of time"
		
		FileCharIterator myIter1 = new FileCharIterator("test5.txt");
		FileCharIterator myIter2 = new FileCharIterator("testDecoding.txt");

		while(myIter1.hasNext() && myIter2.hasNext()) {
			assertTrue(myIter1.next().equals(myIter2.next()));
		}

	}
}
