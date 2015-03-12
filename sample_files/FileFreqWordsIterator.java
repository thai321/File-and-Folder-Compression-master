import java.util.*;
import java.io.*;
import java.lang.StringBuilder;


public class FileFreqWordsIterator implements Iterator<String>{

	private String inputFile;
	private String outputFile;
	private ArrayList<BinaryNode> nodes;
	private ArrayList<String> iter;
	private ArrayList<BinaryNode> words;
	private HashMap<String, Integer> table;
	private HashMap<String, Integer> maxWords; // keep n max
	private HashMap<String, String> codeTable;


	private String minKey;
	private int counter;
	private int n;
	private int j;
	private BinaryNode myRoot;
	private static final String EnCODE = "encode2";
	public FileFreqWordsIterator(String encode, String inputFile, String outputFile, String n) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.n = Integer.parseInt(n);
		minKey = "";
		counter = 0;
		j = 0;
		myRoot = new BinaryNode();
		myRoot.symbol = "Root#######################";
		nodes = new ArrayList<BinaryNode>();
		words = new ArrayList<BinaryNode>();
		iter = new ArrayList<String>();
		BinaryNode eof = new BinaryNode("EOF","EOF",1);
		nodes.add(eof);
		table = new HashMap<String, Integer>();
		maxWords =new HashMap<String,Integer>(this.n);
		codeTable =new HashMap<String,String>();

		play();

	}

	public static void main(String[] args) {
		FileFreqWordsIterator thai = new FileFreqWordsIterator(args[0], args[1], args[2],args[3]);
	}
	
	public void print() {
			if(myRoot != null) {
				myRoot.print(0);
			}	
		}

	public void play(){
        try {
        	BufferedReader in = new BufferedReader(new FileReader(inputFile));
        	constructTable(in);
       		reOrder();
        	makeList();
        	// reOrder();
        	huffManConstruct();
        	System.out.println("done");
        	// printTable();
        	writeTable();
        	System.out.println("right done");
        	initIterator();
        	// print();
        }catch (IOException e) {
			System.out.println("No file contain");
		}
	}

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
		boolean flag2= false;
		String remainder = "";
		// String temp = "";
		boolean flag3= false;
		String s = "";
		try{
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String current;
			String save = "";
			int k = 0;
			String z = "";
			while((current = in.readLine()) != null) {
				save = current;
				if(k > 0) {
					bits.append(codeTable.get("00001010"));
					count += codeTable.get("00001010").length();
				}
				for(int i = 0; i < current.length(); i++) {
					z= myIter.next();
					s += convert(Character.toString(current.charAt(i)));

					if(codeTable.containsKey(s)) {
						count += codeTable.get(s).length();
						bits.append(codeTable.get(s));
						s = "";
					}
				}
				k++;
			}
			
			String temp = codeTable.get("EOF");
			count +=temp.length();
			bits.append(temp);
			flag  = true;
			while(count%8 != 0) {
				bits.append("0");
				count++;
			}
			young.writeBinStrToFile(bits.toString(), outputFile);
		}catch(IOException e) {
			System.out.println("Can't write to the file");
		}
	}

//-----------------------------------------------------------------------------

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
//----------------------------------------------------
    public void reOrder() {
    	// int k = 0;
    	// ArrayList<String> temp = new ArrayList<String>();
    	// for(String a: iter) {
    	// 	if(maxWords.containsKey(a)) {
    	// 		temp.add(a);
     //   		}
    	// }
	    // if(!maxWords.isEmpty()) {	
	    // 	for(int i = 0; i < temp.size() - 1; i++) {
	    // 		int current = maxWords.get(temp.get(i));
	    // 			for(int j = i + 1; j < temp.size(); j++) {
	    // 				int next = maxWords.get(temp.get(j));
	    // 				if(current < next) {
	    // 					temp.set(i,temp.get(j));
	    // 					temp.set(j,temp.get(i));
	    // 				}
	    // 			}
	    // 	}
	    // }
    	// for(String a: temp) {
    	// 	iter.remove(a);
    	// 	iter.add(k++,a);
    	// }

    	// int length = iter.size()-n;
    	// k = n;
    	// for(int i = 0; i < length; i++) {
    	// 	String s = iter.get(k);
    	// 	if(s.length() >=2) {
    	// 		iter.remove(s);
    	// 		for(int j = 0; j < s.length(); j++) {
    	// 			String c = Character.toString(s.charAt(j));
    	// 			if(!iter.contains(c)) {
    	// 				iter.add(k++, c);
    	// 			}
    	// 		}
    	// 	}
    	// 	else {
    	// 		k++;
    	// 	}
    	// }
    	printTable();
    	System.out.println("size = " + iter.size());
    	int length = iter.size();
    	ArrayList<String> list = new ArrayList<String>();
    	for(int i = 0; i < length; i++) {
    		String temp = iter.get(i);
    		System.out.println("temp = "+ temp);
    		if(!maxWords.containsKey(temp)) {
    			int k = 0;
    			// iter.remove(i);
    			while(k < temp.length()) {
    				String c = Character.toString(temp.charAt(k));
    				list.add(c);
    				k++;
    			}
    		}
    		else {
    			list.add(temp);
    		}
    	
    	}
    	iter = list;

    }


	public void	constructTable(BufferedReader in) {
		String current = "";
		String space =  " ";// "00100000";
		String z = "";
		int count  = 0;
		boolean flag = false;
		boolean flag2 = false;
		int l = 0;
		try{

			FileCharIterator myIter = new FileCharIterator(inputFile);
			while((current = in.readLine()) != null) {
				// printTable();
				System.out.println("Current =                " + current);
				String[] splited = current.split("\\s");
				flag2 = false;
				flag = false;
				// if(z.equals("001000000")) {
				// 	iter.add(space);
				// }
				if(current.equals("") ) {
					System.out.println("here");
					myIter.next();
					iter.add("\n");
					flag2 = true;
				}
				else if(Character.toString(current.charAt(0)).equals(" ") && splited.length == 0) {
					System.out.println("TTTTTTTTTTTTTTTttt");
					z = myIter.next();
					// iter.add(space);
					while(z.equals("00100000")) {
						System.out.println("GO HERE");
						iter.add(space);
						z = myIter.next();
						// iter.add(space);
					}
					iter.add("\n");
					// System.out.println("z = " + z);
					// z = myIter.next();
					// System.out.println("z = " + z);
					flag2 = true;
				}
				else if(Character.toString(current.charAt(0)).equals(" ")) {
					z = myIter.next();
					while(z.equals("00100000")) {
						System.out.println("GO HERE2222");
						iter.add(space);
						z = myIter.next();
						// System.out.println("Next z = " + z);
						// iter.add(space);
						// while(myIter.hasNext() && !z.equals("0010000")) {
						// 	z = myIter.next();
						// }
					}
					flag = true;
				}
				if(l > 0) {
					if(l == 1) {
						table.put("\n", 1);
					}
					else {
						table.put("\n", table.get("\n"));
					}
				}
				l++;
				if(!flag2) {	
					// String[] splited = current.split("\\s");

					if(splited.length - 1 == 0) {
						if(!iter.contains(space) && splited[0].charAt(0) == ' ') { // 1st time add space;
							table.put(space, splited[0].length());
						}
						else {
							if(!iter.contains(splited[0])) {
								table.put(splited[0],1);
							}
							else {
								table.put(splited[0],table.get(splited[0]) + 1);
							}
						}
					}
					else {
						count += splited.length - 1;
						table.put(space, count);
					}


					int k = 0;
					boolean flag3 = false;
					int counter = 0;
					for(int i = 0; i < splited.length; i++) {
						if(flag3) {
							iter.add(space);
							counter++;
						}
						if(!splited[i].equals("")) {
							iter.add(splited[i]);
							flag3 = true;
							counter +=splited[i].length();
						}
					
						if(!table.containsKey(splited[i])) {
							table.put(splited[i], 1);
							if(counter < this.n && splited[i].length() >= 2){
								maxWords.put(splited[i],1);
								minKey = splited[i];
								counter++;
							}
						}
						else {
							String key = splited[i];
							table.put(key, table.get(key) + 1);
							int value = table.get(key);

							if(counter < n && key.length() >= 2 && !maxWords.containsKey(key)) {
								maxWords.put(key, value);
								counter++;

							}
							else if(maxWords.containsKey(key)) {
								maxWords.put(key, maxWords.get(key) + 1);

							}
							else if(minKey == "") {
								minKey = key;
								maxWords.put(key, value);
							}
							else if(key.length() >= 2 && canPut(key)){
								if(counter >= n) {
									minKey = getMinKey();
									maxWords.remove(minKey);
									minKey = getMinKey();
									maxWords.put(key, value);
								}
								else {
									maxWords.put(key, value);
								}
							}
						}
					}
				// } // flag2	
					// int i = 0;
				if(!flag) { 
					z  = myIter.next();
				}
					int i = 0;
					System.out.println("couter = " + counter);

					if(!z.equals("0000010") && !z.equals("00100000")) {
						while(i < counter) {
							z = myIter.next();
							i++;
						}
						if(z.equals("00001010")) {
								iter.add("\n");
							}
						else if(z.equals("00100000")) {
							System.out.println("spaceOUT");
							while(z.equals("00100000")) {
								System.out.println("spaceIN");
								iter.add(space);
								z = myIter.next();
							}
							if(z.equals("00001010")) {
								iter.add("\n");
							}
						}					
					}
					if(z.equals("00001010")) {
						// z =  myIter.next();
						System.out.println("SDFSDFSDFds");
					}
					System.out.println("z =            "+ z);
				
				}// flage 2
			}	
		}catch (IOException e) {
			System.out.println("No file contain");
		}
	}
	public String getMinKey() {
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

	public boolean canPut(String key) {
		getMinKey();
		if(key.length() >= 2 && table.get(key) <= maxWords.get(minKey)) {
			return false;
		}
		return true;
	}

	public void makeList() {

		updateTable();

		for(String temp: maxWords.keySet()) {
			words.add(new BinaryNode(convert(temp),temp, maxWords.get(temp)));
		}
		for(String temp: table.keySet()) {
			nodes.add(new BinaryNode(convert(temp),temp, table.get(temp)));
		}

		Collections.sort(nodes, new FileFreqWordsIterator.Freq());
		if(words.size() != 0) {
			Collections.sort(words, new FileFreqWordsIterator.Freq());
		}
	}

	public void updateTable() {
		HashMap<String, Integer> temp = new HashMap<String,Integer>();
		Iterator <Map.Entry<String, Integer>> iterator = table.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Integer> entry = iterator.next();
			String key = entry.getKey();
			int val = entry.getValue();
			if(!maxWords.containsKey(key)) {
				if(key.length() > 1) {
					while(val > 0) {
						int i = 0;
						while(i < key.length()) {
							String k = Character.toString(key.charAt(i));
							if(table.containsKey(k) && temp.containsKey(k)) {
								temp.put(k, temp.get(k) + 1);
							}
							else {
								temp.put(k, 1);
							}
							i++;
						}
						val--;
					}
				}
				else{
					temp.put(key,val);
				}
			}
			
		}
		table = temp;

	}
	public static class Freq implements Comparator<BinaryNode> {
        @Override
        public int compare(BinaryNode node1, BinaryNode node2) {
            return node2.freq - node1.freq;
        }
    }

    public String convert(String temp) {
    	if(temp.length() == 1) {
    		return convertToBi(new Integer(temp.charAt(0)));
    	}
    	StringBuilder s = new StringBuilder();
    	int i = 0;
    	while(i < temp.length()) {
    		s.append(convertToBi(new Integer(temp.charAt(i))));
    		i++;
    	}
    	return s.toString();

    }
	public String convertToBi(int index) {		
		String s = Integer.toBinaryString(index).toString();
			while(s.length()%8 != 0) {
					s = "0" + s;
			}
		return s;
	}

	public void huffManConstruct() {
		// Collections.sort(allNodes, new FileFreqWordsIterator.Freq());
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
	public void createTable(){
			if(myRoot != null) {
				myRoot.createTable("", codeTable);
			}
		}

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
	public void printOrder(ArrayList<String> iter) {
		System.out.println("Print iter:");
		for(String a: iter) {
			System.out.println(a);
		}
		System.out.println();
	}


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
		public BinaryNode(String sym,String word, int freq) {
			symbol = sym;
			this.word = word;
			this.freq = freq;
			left = right = null;
		}
		
		public BinaryNode(BinaryNode left, BinaryNode right) {
			this.freq = left.freq + right.freq;
			this.symbol = Integer.toString(this.freq);
			this.left = left;
			this.right = right;
		}

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

		public void printNodes(ArrayList<BinaryNode> nodes){
			System.out.println("size nodes = " + nodes.size());
			for(BinaryNode temp: nodes){
				System.out.println("symbol = " + temp.symbol + ", freq = " + temp.freq);
			}
			System.out.println();
		}

		public void createTable(String s, HashMap<String,String> table) {
			if(left == null && right == null ) {
				table.put(symbol, s);
			}
			if(left != null) {
				left.createTable(s + "0", table);
			}
			if(right != null) {
				right.createTable(s + "1", table);
			}
		}
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
}

