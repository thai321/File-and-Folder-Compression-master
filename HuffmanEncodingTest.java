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
		thai = new HuffmanEncoding("encode","test0.txt","output.txt.huffman");
		freq =  thai.en.getGetFreq();
		assertTrue(freq.isEmpty());





		thai = new HuffmanEncoding("encode","test2.txt","output.txt.huffman");
		freq =  thai.en.getGetFreq();

		assertEquals((int)freq.get("01100001"), 3 );
		assertEquals((int)freq.get("01100010"), 2 );
		assertEquals((int)freq.get("01100011"), 1 );
		assertEquals((int)freq.get("00100000"), 5 );
		assertEquals((int)freq.get("00001010"), 1 );
	}
	
	@Test
	public void testTable() throws Exception {   // testing countword
		HuffmanEncoding thai;
		HashMap<String,String> freq = new HashMap<String, String>();
		thai = new HuffmanEncoding("encode","test2.txt","output.txt.huffman");
		freq =  thai.en.getTable();

		assertTrue(freq.get("01100001") != null);
		assertTrue(freq.get("01100010") != null);
		assertTrue(freq.get("01100011") != null);
	}

//	@Test
	
}















