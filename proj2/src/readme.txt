Stephen Sweeney cs61bl-ok
Carlos Flores cs61bl-oj
Quoc Thai Nguyen Truong cs61bl-on


//////test purpose//////

-------------------------------------------PART ONE BEGIN---------------------------------------------
(HuffmanEncoding.java)

test Constructor: We used this test to ensure that the correct spelling of the arguments are 
					passed into the function and catch an error message if an incorrect input is passed through.
					
testgetFreq: In this test we test create five text files; is to make sure that the frequency for each character is correct.A 
				hashmap "freq" is created to store each character in the string the frequency that the character occurs in the text file.
	
	-->test0.txt: tests an empty file to make sure that the hash map "freq" is empty.
	
	-->test1.txt: has only one character "a" (01100001) this enabled us to know that we are able to take in a character and give it a 
					frequency of 1, and returns null for any other character, and therefore the hashmap "freq" is not empty.
	
	-->test3.txt: has only a single space "00100000" this allowed us to texst to see if we were able to take in a space and give it a 
					frequency of 1, and therefore the hashmap "freq" is not empty.
	
	-->test4.txt: has only a single new line "00001010", this allows us to test to see that we can record a new line as a character 
					with a frequency of 1 in the "freq" hashmap.
	
	-->test2: tests the characters "a b c a b a\n". This allowed us that the frequency in "freq" hashmap has 3 for a, 2 for b, 1 for c, 
					and 1 for \n "new line."
	
	-->test5: we put in "it was the best of time , it was the worst of time".  We want to make sure that each character has the correct
	 				frequency contained in the hashmap "freq."

testTable: This is used to test to make sure that the hash map "table" is being generated correctly with the bit string including the "EOF" as a key and the
 				corresponding value is a codemap from Huffman trees.Also to check to see that the value of the bitstring is not equal to null.
	
	-->test0.txt: is an empty file, test to make sure that the table is empty

	-->test1.txt: has only a single character "a" (01100001), to check to see that the value of the bitstring is not equal to null.

	-->test3.txt: has only a single space "00100000", check to see that the value of the bitstring is not equal to null.

	-->test4.txt: has only a single new line "00001010", check to see that the value of the bitstring is not equal to null.
	
	-->test2.txt: tests the characters "a b c a b a\n", check to see that the values of each bitstring character is not equal to null.

	-->test5.txt: we put in "it was the best of tiem, it was the worst of time", check to see that the values of each bitstring character 
					is not equal to null.

testHuffmanTree: used to make sure the code map gets the right bitstring in the table from the Huffman Tree that we generate. We make 
					a method "checktree" in the HuffmanEncoding class which takes in a codemap string, this method returns the bitstring constructed by the
 					iteration of the tree.

	-->test5.txt:"it was the best of tiem, it was the worst of time", we create a table with a key as a bitstring and the corresponding 
					value as the codemap that we get from the Huffman Trees. we used the method "checktrees" to pass in the values codemap in the table 
					to make sure that we get the original bitstring back. We also check if the key "bitstring" is actually in the table prior to calling
	 				the method "checktrees".

testDecoding: We encode a txt file, then decode the same file. To check to see that they contain the same characters by using two 
				FileCharIterators
--------------------------------------PART ONE END--------------------------------------------------------





--------------------------------------PART TWO BEGIN-------------------------------------------------------
(FileFreqWordsIteratorTest.java)

testConstructor:We used this test to ensure that the correct spelling of the arguments are 
					passed into the function and catch an error message if an incorrect input is passed through. 
					Make sure that it takes in four arguments

testgetFreq: In this test we test create five text files; is to make sure that the frequency for each character is correct.A 
				hashmap "freq" is created to store each character in the string the frequency that the character occurs in the text file.
	
	-->test0.txt: tests an empty file to make sure that the hash map "freq" is empty with the fourth argument being zero
	
	-->test1.txt: has only one character "a" (01100001) this enabled us to know that we are able to take in a character and give it a 
					frequency of 1, and returns null for any other character, and therefore the hashmap "freq" is not empty. Fourth 
					argument "args[3]" is zero.
	
	-->test3.txt: has only a single space "00100000" this allowed us to texst to see if we were able to take in a space and give it a 
					frequency of 1, and therefore the hashmap "freq" is not empty.Fourth argument "args[3]" is zero.
	
	-->test4.txt: has only a single new line "00001010", this allows us to test to see that we can record a new line as a character 
					with a frequency of 1 in the "freq" hashmap. Fourth argument "args[3]" is zero.
	
	-->test2: tests the characters "a b c a b a\n". This allowed us that the frequency in "freq" hashmap has 3 for a, 2 for b, 1 for c, 
					and 1 for \n "new line." Fourth argument "args[3]" is zero.
	
	-->test5: we put in "it was the best of time , it was the worst of time".  We want to make sure that each character has the correct
	 				frequency contained in the hashmap "freq." Fourth argument "args[3]" is zero.

	-->test5: we put in "it was the best of time , it was the worst of time".  We want to make sure that each character(8 bits) and each word(more than
					8 bits) have the correct frequency contained in the hashmap "freq." Fourth argument "args[3]" is "4".

testTable: This is used to test to make sure that the hash map "table" is being generated correctly with the bit string including the "EOF" as a key and the
 				corresponding value is a codemap from Huffman trees.Also to check to see that the value of the bitstring is not equal to null.
	
	-->test0.txt: is an empty file, test to make sure that the table is empty.Fourth 
					argument "args[3]" is zero.

	-->test1.txt: has only a single character "a" (01100001), to check to see that the value of the bitstring is not equal to null.Fourth 
					argument "args[3]" is zero.


	-->test3.txt: has only a single space "00100000", check to see that the value of the bitstring is not equal to null.Fourth 
					argument "args[3]" is zero.

	-->test4.txt: has only a single new line "00001010", check to see that the value of the bitstring is not equal to null.Fourth 
					argument "args[3]" is zero.
	
	-->test2.txt: tests the characters "a b c a b a\n", check to see that the values of each bitstring character is not equal to null. Fourth 
					argument "args[3]" is zero.


	-->test5.txt: we put in "it was the best of tiem, it was the worst of time", check to see that the values of each bitstring character 
					is not equal to null. Fourth argument "args[3]" is zero.

	-->test5.txt: we put in "it was the best of tiem, it was the worst of time", check to see that the values of each bitstring character 
					is not equal to null. Fourth argument "args[3]" is "4".

	-->test5.txt: we put in "it was the best of tiem, it was the worst of time", check to see that the values of each bitstring character 
					is not equal to null. Fourth argument "args[3]" is "7".

testHuffmanTree: used to make sure the code map gets the right bitstring in the table from the Huffman Tree that we generate. We make 
					a method "checktree" in the HuffmanEncoding class which takes in a codemap string, this method returns the bitstring constructed by the
 					iteration of the tree. 

	-->test5.txt:"it was the best of tiem, it was the worst of time", we create a table with a key as a bitstring and the corresponding 
					value as the codemap that we get from the Huffman Trees. we used the method "checktrees" to pass in the values codemap in the table 
					to make sure that we get the original bitstring back. We also check if the key "bitstring" is actually in the table prior to calling
	 				the method "checktrees". Fourth argument "args[3]" is "0". Make sure that this executes exactly like "encode".

	-->test5.txt:"it was the best of tiem, it was the worst of time", we create a table with a key as a bitstring (charcter and words) and the corresponding 
					value as the codemap that we get from the Huffman Trees. we used the method "checktrees" to pass in the values codemap in the table 
					to make sure that we get the original bitstring back. We also check if the key "bitstring" is actually in the table prior to calling
	 				the method "checktrees". Fourth argument "args[3]" is "4". Make sure that this executes exactly like "encode".

testDecoding: We encode a txt file, then decode the same file. To check to see that they contain the same characters by using two 
				FileCharIterators


--------------------------------------PART THREE BEGIN-------------------------------------------------------
(Zipper.java)

Zip
->testconstructor: makes sure that the constructor works properly

-->testFileName: here we try to zip an empty folder and ensure that it is still empty, then we 	
				create a linked list that is going to contain all of the files in the folder. make 	sure that the zipper file contains all of the tested items in the
					correct folders and directories.

-->testEncodeField: this is used to make sure that given a directoy with an arbitratry number of files within it, the directory and files are all encoded correctly


UNZIP

-->testoutfile: here we make sure that given a zipped file, we are able to create "unzip" all of the the files within.

--------------------------------------PART THREE END-------------------------------------------------------


////////////Contributions/////////

We worked together for the most part.  We discussed most of the implementations before we started writing. We designed the project together but worked separately for 
some parts of it.  Part 1 was done by the three of us and mostly written by Thai, and debugged by Carlos and Stephen. Part two was written by Thai and most of the debugging
done by Stephen.  Part three was written by Carlos and debugging by Thai.  Test files were written by Stephen and Thai (part1), and Carlos (part3). 


/////////////RunTime//////////////

Decoding = the run time is Linear time.
		explain: first what we do is that we make a codemap table by going through the bitstring table, and that take n time, n is the total frequency
		of characters '0', '1', ',', and  '\n' (newline).Next the FileCharIterator object goes through the machine readable code and that take m time, m is 
		the total frequency of all machine readable code. Checking through the "if/else" statement takes c time. The running time for this is m + n + c which
		is linear time.

Encoding = the run time is in quadratic time O(m^2 + n), n is the total bytes in the file, and m is a unique character or word in the file.
		explain: it takes O(n) to iterate through each file, where n is the number bytes in each file, then we created m nodes from a hashmap that cotained 
		the character or word in bit strings as keys and the frequency as value; this operation is in O(m). Then we constructed the Huffman trees from the 
		nodes we had, which were represented by m. The construction of the tree is in O(m^2) because we used ArrayList.sort(quicksort) to sort the array 
		containing each node which takes mlog(m) + (m-1)log(m-1) + ... + 1log(1) = m^2 + mlog(m). Then we constructed the table for m code map by going through
		the Huffman trees, each operation is in log(m), so m operations are in mlog(m). Then we wrote the table to the file which is in O(m). Then we write the contents
		under the table by reading through the text file each byte at a time, which is in O(n) where n is the number of bytes. 
		The total running time is n + m + m^2 + mlog(m) + m + n, which is in O(m^2 + n).




	
















	
