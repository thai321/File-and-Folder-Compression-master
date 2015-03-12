import java.util.*;

public class FileFreqWordsIterator implements Iterator<String>{

	private String inputFile;    //input file
	private String outputFile;	// output file
	// private BinaryNode myRoot;   // root of the Huffman tree
	// private ArrayList<BinaryNode> nodes;   // nodes for contructing Huffman tree

	private ArrayList<String> iter;    // store words and character for iterator in order
	// private ArrayList<BinaryNode> words;  // store the words as nodes
	private HashMap<String, Integer> table;   
	private HashMap<String, Integer> maxWords; // keep n max words

	private HashMap<String, String> codeTable;  // key is word bit string, and value is codemap
												// for output encoding
	private String minKey;		// get the minimun freq key in maxWords
	private int counter;	// counter for n freq max words
	private int n;			// input n freq max words
	private int j;      // for interator start at 0
	
// ########################### For Iterator only ##########################################	
	public FileFreqWordsIterator(String inputFile, String n) {
		construct(n, inputFile);
		constructTable();
       	reOrder();
		initIterator();
	}
// ########################### For OutPut Encoding ##########################################
	public void construct(String n, String inputFile) {	
		this.n = Integer.parseInt(n);
		if(this.n >= 0) {
			this.inputFile = inputFile;
			minKey = "";
			counter = 0;
			iter = new ArrayList<String>();
			table = new HashMap<String, Integer>();
			maxWords =new HashMap<String,Integer>(this.n);
		}
		else {
			System.err.print("n must be positive");
		}
	}
// ########################### MAIN ##########################################
	// public static void main(String[] args) {
	// 	FileFreqWordsIterator thai = new FileFreqWordsIterator(args[0], args[1], args[2],args[3]);
	// }

//--------------Freq Iterator-------------Begin--------------------------------------------------
	public void initIterator(){
		j = 0;
	}
	@Override
    public boolean hasNext() {
        return j < iter.size();
    }
    @Override
    public String next() {
      	return iter.get(j++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("FileCharIterator does not delete from files.");
    }
//--------------Freq Iterator-------------End--------------------------------------------------

//----------Construct Table and Maxwords, also iter----------Begin------------------
	public void	constructTable() {  // construct Table and MaxWords, also iter
		FileCharIterator myIter = new FileCharIterator(inputFile);
		String s = "";
		String temp = "";
		while(myIter.hasNext()) {
			temp = myIter.next();  // s = 01100001 --> a
			if(!myIter.hasNext()) {
				iter.add(s);
				iter.add(temp);
				keepTrackTable(s);
				updateMaxWord(s);
				s= "";
			}
			else if(temp.equals("00100000")) { //space
				if(!s.equals("")) {  //words
					iter.add(s);
					keepTrackTable(s);
				}
				updateMaxWord(s);
				keepTrackTable(temp);
				iter.add(temp);
				s = "";
				
			}
			else if(temp.equals("00001010")) {
				if(!s.equals("")) { //words
					iter.add(s);
					keepTrackTable(s);
				}
				updateMaxWord(s);
				keepTrackTable(temp);
				iter.add(temp);
				s = "";
			}
			else {  // other characters
				s += temp;
			}
		}
		System.out.println("counter = " + this.counter);
		System.out.println("this.n  = " + n);
		if(this.counter < this.n) {
			System.out.println("hererere");
			System.err.println("There are fewer words in the file than specified by the command line arguments");
			System.exit(1);
		}
	}
	
	public String getMinKey() { // get the mininum key in the MaxWords
		int min;
		boolean flag = false;
		for(String key: maxWords.keySet()) {
			if(!maxWords.containsKey(minKey)) {
				flag = true;
				min = maxWords.get(key);
				minKey = key;
			}
			else {
				min = maxWords.get(minKey);
			}

			if(!minKey.equals(key) && maxWords.get(key) < min){
				minKey = key;
				min = maxWords.get(minKey);
			}
		}
		return minKey;
	}

	public boolean canPut(String key) { // true if able to put the key in intoMaxWords
		if(this.n > 0) {				// false otherwise
			getMinKey();
			if(table.get(key) <= maxWords.get(minKey)) {
				return false;
			}
			return true;
		}
		return false;
	}

	public void keepTrackTable(String s) { // keep track and update table
    	if(table.containsKey(s)) {
    		table.put(s, table.get(s) + 1);
    	}
    	else {
    		table.put(s, 1);
    	}
    }

    public void updateMaxWord(String s){ // keep track and update table
    	if(s.length() >= 16) {
    		if(this.counter < this.n) {
    			maxWords.put(s, 1);
	    		counter++;
	    		if(minKey.equals("")) {
	    			minKey = s;
	    		}
    		}
	    	else if(maxWords.containsKey(s)) {
	    		maxWords.put(s, maxWords.get(s) +1);
	    	}
	    	else if(canPut(s)) {
				if(this.counter >= this.n) {
					minKey = getMinKey();
					maxWords.remove(minKey);
					minKey = getMinKey();
					maxWords.put(s, table.get(s));
				}
				else {
					maxWords.put(s, table.get(s));
				}
			}
	    }
    }

//----------Construct Table and Maxwords, also iter----------end------------------
// re-order the iter for iterator and split the words (length more than 2)
    public void reOrder() {   // if only if words not in the maxWords
    	ArrayList<String> list = new ArrayList<String>();
    	for(int i = 0; i < iter.size(); i++) {
    		String temp = iter.get(i);
    		if(!maxWords.containsKey(temp)) {
    			for(int k = 0; k < temp.length(); k +=8) {
    				list.add(temp.substring(k, k+8));
    			}
    		}
    		else {
    			list.add(temp);
    		}
    	}
    	iter = list;
    }
/*
    public void updateTable() {  //  update the table (hashMap), split words with length more than 2
								// into characters if only if words in table are not in maxWords
		HashMap<String, Integer> temp = new HashMap<String,Integer>();
		Iterator <Map.Entry<String, Integer>> iterator = table.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Integer> entry = iterator.next();
			String key = entry.getKey();
			int val = entry.getValue();
			if(!maxWords.containsKey(key)) {
				if(key.length() > 8) {
					for(int i = 0; i < key.length(); i += 8) {
						String a = key.substring(i,i+8);
						if(temp.containsKey(a)) {
							temp.put(a, temp.get(a) + 1);
						}
						else {
							temp.put(a,1);
						}
					}
				}
				else{
					temp.put(key,val);
				}
			}
		}
		table = temp;
	}

	public void makeList() {  // make nodes of MaxWords and table before contruct Huffman Tree
		for(String temp: maxWords.keySet()) {
			words.add(new BinaryNode(temp,maxWords.get(temp)));
		}
		for(String temp: table.keySet()) {
			nodes.add(new BinaryNode(temp,table.get(temp)));
		}
		Collections.sort(nodes, new FileFreqWordsIterator.Freq());
		if(words.size() != 0) {
			Collections.sort(words, new FileFreqWordsIterator.Freq());
		}
	}

	public void huffManConstruct() {  // construct Huffman tree
		if(nodes.size() > 0 ) {
			if(words.size() != 0 && nodes.size() != 0) {
				myRoot = myRoot.huffManConstruct(nodes, words);
			}
			else {
				myRoot = myRoot.huffManConstruct(nodes, words);
			}
			createTable();
		}	
	}								

	public void createTable(){    // create table for encoding output
			if(myRoot != null) {
				myRoot.createTable("", codeTable);
			}
		}

//--------------Encoding2 output------------Begin-------------------------------------------
	public void writeTable() {
		try {
			FileWriter f = new FileWriter(outputFile);
			Iterator <Map.Entry<String, String>> iterator = codeTable.entrySet().iterator();
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
			System.out.println("Can't write to the file");
		}

		FileCharIterator myIter = new FileCharIterator(inputFile);
		FileOutputHelper young = new FileOutputHelper();
		StringBuilder bits = new StringBuilder();
		boolean flag = false;
		int count = 0;
		while(myIter.hasNext()) {
			if(!flag) {
				String temp = codeTable.get(myIter.next());
				count += temp.length();
				bits.append(temp);
			}

			if(!myIter.hasNext()) {
				String temp = codeTable.get("EOF");
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
//--------------Encoding2 output------------End-------------------------------------------


///--------Comparator--------begin-----------------------------------------
	public static class Freq implements Comparator<BinaryNode> {
        @Override
        public int compare(BinaryNode node1, BinaryNode node2) {
            return node2.freq - node1.freq;
        }
    }
///--------Comparator--------end-----------------------------------------

//-------------Print Stuff ---------------begin-----------for testing---------------
	public void printNodes(ArrayList<BinaryNode> nodes){
		System.out.println("size = " + nodes.size());
		for(BinaryNode temp: nodes){
			System.out.println("symbol = " + temp.word + ", freq = " + temp.freq);
		}
		System.out.println();
	}

	public void printTable() {
		System.out.println("Table: ");
		for(String temp: table.keySet()) {
			System.out.println("Key = "+ temp + ", value = " + table.get(temp));
		}

		System.out.println("\nmaxWords: ");
		for(String temp: maxWords.keySet()) {
			System.out.println("Key = "+ temp + ", value = " + maxWords.get(temp));
		}

		System.out.println("\nCodeTable: ");
		for(String temp: codeTable.keySet()) {
			System.out.println("Key = "+ temp + ", value = " + codeTable.get(temp));
		}

	}

	public void print() {
			if(myRoot != null) {
				myRoot.print(0);
			}	
	}

	public void printOrder(ArrayList<String> iter) {
		System.out.println("Print iter:");
		for(String a: iter) {
			System.out.println(a);
		}
		System.out.println();
	}
//----------------Print Stuff ---------------begin---------------------------

// ######################BINARY NODE##########START#############################
	public class BinaryNode {
		public String symbol;
		public String word;
		public int freq;
		public BinaryNode parent;
		public BinaryNode left; // zero
		public BinaryNode right; // one


		public BinaryNode(){
			symbol = "*";
			freq = 0;
			parent = null;
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
			//create Huffman tree given 2 arraysList, nodes and words
		public BinaryNode huffManConstruct(ArrayList<BinaryNode> nodes, ArrayList<BinaryNode> words) {
			if(nodes.size() == 1 && words.size() == 0) {   
				return nodes.get(0);
			}
			else if(nodes.size() == 1) {
				BinaryNode temp = nodes.get(0);
				words.add(temp);
				nodes.clear();
				return huffManConstruct(words, nodes);
			}
			else {
				BinaryNode temp = new BinaryNode(nodes.get(nodes.size()-2), nodes.get(nodes.size()-1));
				nodes.remove(nodes.size()-1);
				nodes.remove(nodes.size()-1);
				nodes.add(temp);
				Collections.sort(nodes, new FileFreqWordsIterator.Freq());
				return huffManConstruct(nodes, words);
			}
		}

		public void createTable(String s, HashMap<String,String> table) {  // create table from Huffman tree
			if(left == null && right == null ) {							// with bits string and code map
				table.put(symbol, s);
			}
			if(left != null) {
				left.createTable(s + "0", table);
			}
			if(right != null) {
				right.createTable(s + "1", table);
			}
		}

//-------------Print Stuff ---------------begin-----------for testing---------------
		private void print(int indent) {	
            if(right != null && left != null) {
                right.print(indent + 2);
                println(word, indent);
                left.print(indent + 2);
            }
            else if(left != null) {
                left.print(indent + 2);
                println(word, indent);
            }
            else if(right != null) {
                right.print(indent + 2);
                println(word, indent);
            }
            else {
                println(word, indent);
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
//-------------Print Stuff ---------------end-----------for testing---------------
*/
// ######################BINARY NODE##########START#############################
}// END HUFFMAN CLASS

