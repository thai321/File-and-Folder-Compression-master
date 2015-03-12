import java.util.*;

public class FileFreqWordsIterator implements Iterator<String>{

	private String inputFile;    //input file
	private String outputFile;	// output file
	private ArrayList<String> iter;    // store words and character for iterator in order
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
		if(this.counter < this.n) {
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
    		if(this.counter < this.n && !maxWords.containsKey(s)) {
    			maxWords.put(s, 1);
	    		this.counter++;
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
// ######################BINARY NODE##########START#############################
}// END HUFFMAN CLASS

