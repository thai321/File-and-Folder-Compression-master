import java.util.*;
import java.io.*;
import java.lang.StringBuilder;
	
public class HuffmanEncoding {
	public Encoding en; // for testing
	public Decoding de;  // for testing

	public HuffmanEncoding(String func, String inputFile, String outputFile) throws Exception {
		
		if(func.equals("encode")) {	
			encode(inputFile, outputFile, new String("0")); // call encode when func is "encode"
		}
		else if(func.equals("decode")) {
			decode(inputFile, outputFile);  // call encode when func is "decode"
		}
		else {                             // else neither
			throw new Exception("Please enter either \"encode\" or \"decode\" for 1st argument ");
		}
	}

	public HuffmanEncoding(String func, String inputFile, String outputFile, String n) {
		if(func.equals("encode2")) {	
			encode(inputFile, outputFile, n); // call encode when func is "encode2"
		}
		else {                             // else neither
			System.out.println("Please enter either \"encode2\" for 1st argument ");
		}
	}
	
	public void encode(String inputFile, String outputFile, String n) { // for encode if func is "encode"
		Encoding encode = new Encoding(inputFile, outputFile, n);
		this.en = encode;
	}

	public void decode(String inputFile, String outputFile){ // for decode if func is "decode"
		Decoding decode = new Decoding(inputFile, outputFile);
		this.de = decode;
	}

	// public void encode2(String inputFile, String outputFile, String func, int n) {
	// 	Encoding encode2 = new Encoding(inputFile, outputFile, func, n); 
	// }

	public HashMap<String,String> getTable() {
		return new HashMap<String,String>(en.getTable());
	}

//	public HashMap<String,Integer> getGetFreq() {
//		return new HashMap<String,Integer>(de.getGetFreq());
//	} 
// ########################### MAIN ##########################################
	public static void main(String[] args) throws Exception {
		if(args.length == 3) {
			HuffmanEncoding thai = new HuffmanEncoding(args[0], args[1], args[2]);
		}// args[0] = either "encode" or "decode" , args[1] = input file name
												// args[3] = output file name
		else if(args.length == 4) { //args[0] = "encode2", args[1] = input file, args[2] = output file name
			HuffmanEncoding thai = new HuffmanEncoding(args[0], args[1], args[2], args[3]);
		}															// args[3] = n max freq words
		else {
			System.out.println("Invalid argument(s)");
		}
	} 
// ######################ENCODING##########BEGIN#############################
	public class Encoding {
		private BinaryNode myRoot = null;  // root of Huffman tree
		private ArrayList<BinaryNode> nodes; // nodes for contructing the Huffman tree
		private HashMap <String,String> table;  // store the bits string as key , and codemap as value. this create after calling createTable method
		private HashMap <String,Integer> getFreq;  // store the bits string as key, and freq of that character
		private String inputFile;	// input File name 
		private String outputFile;	// output file name
		private String n;
		// public BinaryNode node;

		public Encoding(String inputFile,String outputFile, String n) {
			this.inputFile = inputFile;
			this.outputFile = outputFile;
			this.n = n;
			table = new HashMap<String,String>();
			getFreq = new HashMap<String,Integer>();
			nodes = new ArrayList<BinaryNode>();
			BinaryNode eof = new BinaryNode("EOF",1);
			nodes.add(eof);
			myRoot = new BinaryNode("Root",0);
			outputEncoding();			 
		}

		public void countWord() {   // create and update getFreq, key: bits string, value = # freq of key
			// FileCharIterator myIter = new FileCharIterator(inputFile);
			FileFreqWordsIterator myIter = new FileFreqWordsIterator(inputFile, this.n);
			
			while(myIter.hasNext()) {
				String s = myIter.next();  // s = 01100001 --> a
				if(getFreq.containsKey(s)) {
					getFreq.put(s, getFreq.get(s) + 1);
				}
				else {
					getFreq.put(s, 1);
				}
			}
		}
	
	public HashMap<String,String> getTable() { // for testing
		return new HashMap<String,String>(table);

	}

	public HashMap<String,Integer> getGetFreq() {  // for testing
		return new HashMap<String,Integer>(getFreq);

	} 

	public String checkTree(String codemap) {  //for testing 
		return myRoot.checkTree(codemap);
	}

		
//----------------------------Create Node----------begin-------------------------
		public void createNodes() {   // create nodes for constructing the Huffman tree
			if(!getFreq.isEmpty()) {
				Iterator <Map.Entry<String, Integer>> iterator = getFreq.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, Integer> entry = iterator.next();
					String key = entry.getKey();
					Integer val = entry.getValue();	
					BinaryNode node = new BinaryNode(key,val);
					nodes.add(node);
				}
				Collections.sort(nodes,new HuffmanEncoding.Freq());
			}
		}

//-----------------------Create Nodes-------------end----------------------------------

//-------------------Construct the HuffMan Tree ----------begin------------------
		public void huffManConstruct() {  // construct Huffman tree from nodes (arrayList)
			if(nodes.size() > 1 ) {
				myRoot = myRoot.huffManConstruct(nodes);
				createTable();	// create Table after construct Huffman tree
			}	
		}
//-------------------Construct the HuffMan Tree ----------end------------------
//-------------------Create table (was call from huffManConstruct----------begin------------------
		public void createTable() { // create Table after construct Huffman tree
			if(myRoot != null) {
				myRoot.createTable("", table);
			}
		}
//-------------------Create table (was call from huffManConstruct----------end------------------

//-------------Print Stuff ---------------begin-----------for testing---------------
		public void print() {
			if(myRoot != null) {
				myRoot.print(0);
			}	
		}

		public void printTable() {
			Iterator <Map.Entry<String, String>> iterator = table.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String val = entry.getValue();	
			  	System.out.println("key: " + key + " value: " + val);
			}
		}

		public void printNodes() {
			System.out.println("Size = " + nodes.size());
			for(BinaryNode node: nodes)  {
				System.out.println(node.symbol + ", " + node.freq);
			}
			System.out.println();
		}
//-------------Print Stuff ---------------end-----------for testing---------------
//-------------------------------Output Encoding------------------begin------
		public void outputEncoding() {
			countWord();
	    	createNodes();
	    	huffManConstruct();
	    	if(!table.isEmpty()) {
				try {
					FileWriter f = new FileWriter(outputFile);
					Iterator <Map.Entry<String, String>> iterator = table.entrySet().iterator();
					while(iterator.hasNext()){
						Map.Entry<String, String> entry = iterator.next();
						String key = entry.getKey();
						String val = entry.getValue();
						f.write(key + "," + val);
						f.write(System.getProperty("line.separator"));
					}
					f.write(System.getProperty("line.separator"));
					f.close();
				} catch (IOException e) {
					System.out.println("Can't find or create" + outputFile);
				}
				
				// FileCharIterator myIter = new FileCharIterator(inputFile);
				FileFreqWordsIterator myIter = new FileFreqWordsIterator(inputFile, this.n);
				FileOutputHelper young = new FileOutputHelper();
				StringBuilder bits = new StringBuilder();
				boolean flag = false;
				int count = 0;
				while(myIter.hasNext()) {
					
					if(!flag) {
						String temp = table.get(myIter.next());
						count += temp.length();
						bits.append(temp);
					}

					if(!myIter.hasNext()) {
						String temp = table.get("EOF");
						count +=temp.length();
						bits.append(temp);
						flag  = true;
						while(count%8 != 0) {
							bits.append("0");
							count++;
						}
					}
				}
				young.writeBinStrToFile(bits.toString(), outputFile);
		    }
		}
   }

//----------------Output Encoding------------------End---------------------

///--------Comparator--------begin-----------------------------------------
   public static class Freq implements Comparator<BinaryNode> {
        @Override
        public int compare(BinaryNode node1, BinaryNode node2) {
            return node2.freq - node1.freq;
        }
    }
///--------Comparator--------end-----------------------------------------

// ######################ENCODING##########END#############################

// ######################DECODING##########START#############################
   public class Decoding {
		private BinaryNode root ;
		private HashMap <String,String> table;
		private String outputFile;
		private String inputFile;
		public Decoding(String inputFile, String outputFile){
			this.root = null;
			table = new HashMap<String,String>();
			this.outputFile = outputFile;
			this.inputFile = inputFile;
			output(getCodeMap());
		}

		public StringBuilder getCodeMap() {  //create a table from huffman file and reade all the machine readable code (codemap)
											// return the stringBuilder object that is contains all the codemap('0' and '1') from the machine readable code
			FileCharIterator myIter = new FileCharIterator(inputFile);
			boolean flag = false;
			boolean flag2 = false;
			boolean isKey = false;
			String value = "";
			String key = "";
			String current = "";
			boolean eof = false;
			while(!flag) {
				current = myIter.next();
				if(current.equals("00001010")) {
					current = myIter.next();
					table.put(key,value);
					key = "";
					value = "";
					if(eof) {
						eof = false;
					}
					if(current.equals("00001010")) {
						flag = true;
					}
					isKey = false;
				}
				if(current.equals("00110000")) {
					if(isKey) {
						key += "0";
					}
					else if(!eof){
						value += "0"; 
					}

					if(flag2) {
						current = myIter.next();
					}
				}
				else if(current.equals("00110001")) {
					if(isKey) {
						key += "1";
					}
					else if(!eof){
						value += "1"; 
					}

					if(flag2) {
						current = myIter.next();
					}
				}
				else if( current.equals("00101100")) {
					isKey = true;
				}
				else {
					key  = "";
					value = "EOF";
					eof = true;
				}
			}

			StringBuilder s = new StringBuilder();
			while(myIter.hasNext()) {
				StringBuilder temp = new StringBuilder(myIter.next());
				s.append(temp);
			}
			return s;
		} 

		public void output(StringBuilder codes) { // take the whole bits string (codes) and
			StringBuilder temp = new StringBuilder();   // write it into text
			StringBuilder output = new StringBuilder();
			int i = 0;
			boolean flag = false;
			while(!flag) {
				temp.append(codes.charAt(i++));
				if(table.containsKey(temp.toString())) {	
					if(table.get(temp.toString()).equals("EOF")) {
						FileOutputHelper young = new FileOutputHelper();
						young.writeBinStrToFile(output.toString(), outputFile);
						flag = true;
					}
					if(!flag) {
						output.append(table.get(temp.toString()));
						temp = new StringBuilder();
					}
				}
			}
		}

//-------------Print Stuff ---------------begin-----------for testing---------------
		public void printTable() {
			Iterator <Map.Entry<String, String>> iterator = table.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String val = entry.getValue();	
			  	System.out.println("key: " + key + " value: " + val);
			}
		}
//-------------Print Stuff ---------------end-----------for testing---------------
// ######################DECODING##########END#############################
	}
// ######################BINARY NODE##########START#############################

	public class BinaryNode {
		public String symbol;  // store bits string
		public int freq;    // store the freq of the words or character
		public BinaryNode left; // zero
		public BinaryNode right; // one

		public BinaryNode(){
			symbol = "*";
			freq = 0;
			left = null;
			right = null;
		}

		public BinaryNode(String sym, int freq) {
			symbol = sym;
			this.freq = freq;
			left = right = null;
		}
		
		public BinaryNode(BinaryNode left, BinaryNode right) {
			this.freq = left.freq + right.freq;
			this.symbol = Integer.toString(this.freq);
			this.left = left;
			this.right = right;
		}

		public BinaryNode huffManConstruct(ArrayList<BinaryNode> nodes) { // construct Huffman tree from nodes
			if(nodes.size() == 1) {
				return nodes.get(0);
			}
			else {
				BinaryNode temp = new BinaryNode(nodes.get(nodes.size()-2), nodes.get(nodes.size()-1));
				nodes.remove(nodes.size()-1);
				nodes.remove(nodes.size()-1);
				nodes.add(temp);
				Collections.sort(nodes, new HuffmanEncoding.Freq());
				return huffManConstruct(nodes);
			}
		}

		public void createTable(String s, HashMap<String,String> table) { // create table from Huffman tree
			if(left == null && right == null) {								// with bits string and code map
				table.put(symbol, s);
			}
			if(left != null) {
				left.createTable(s + "0", table);
			}
			if(right != null) {
				right.createTable(s + "1", table);
			}
		}

		public String checkTree(String s) { // for testing
			if(s.length() == 0) {
				return this.symbol;
			}
			else if(s.charAt(0) == '0') {
				return this.left.checkTree(s.substring(1));
			}
			else {
				return this.right.checkTree(s.substring(1));
			}
		}

//-------------Print Stuff ---------------begin-----------for testing---------------
	    private void print(int indent) {
	    	
            if(right != null && left != null) {
                right.print(indent + 2);
                println(symbol, indent);
                left.print(indent + 2);
            }
            else if(left != null) {
                left.print(indent + 2);
                println(symbol, indent);
            }
            else if(right != null) {
                right.print(indent + 2);
                println(symbol, indent);
            }
            else {
                println(symbol, indent);
            }
        }

        private static final String indent1 = "    ";
        private void println (String sym, int indent) {
            for (int k = 0; k < indent; k++) {
                System.out.print(indent1);
            }
            System.out.println(sym);
        }
	}
//-------------Print Stuff ---------------End-----------for testing---------------

// ######################BINARY NODE##########END#############################

} // END HUFFMAN CLASS
