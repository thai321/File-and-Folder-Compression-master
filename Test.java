import java.util.*;
import java.io.*;
import java.lang.StringBuilder;

public class Test {
	public static void main(String[] args) {
		String s = Integer.toBinaryString('“').toString();
		System.out.println(s);

		// try {
		// 	String current = "";
  //       	BufferedReader in = new BufferedReader(new FileReader("test3.txt"));
  //   		while((current = in.readLine()) != null) {
  //   			System.out.println("yes");
  //   		}   	
  	
  //       }catch (IOException e) {
		// 	System.out.println("No file contain");
		// }
	}
}

// I have the same problem too, I'm running on the lab computer, I tried different computers but I can't fix it.

// 1st :quotation marks.   “Barkley, Barkley, where are you?”  != "Barkley, Barkley, where are you?"  .dec = 8221 = 10000000011101 (bin) > 8 bits

// 2nd: apostrophe  .  I don’t know   != I don't know  . Dec = 8217 = 10000000011001 (bin) more than 8 bits

// 3rd:  the dash    .  voices—all != voices-all  . Dec = 8212  = 10000000010100 (bin) which more than 8 bits

// since I use writeBinStrToFile method to in FileOutputHelper file , it only read each character and get 8 bits for each.

// How do I fix this problem?

