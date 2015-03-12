import java.io.*;
import java.lang.*;
import java.util.*;

public class Zipper {
    public Zip z;

    public static void main (String[] args) {
        Zipper zipper = new Zipper(args[0], args[1], args[2]);
    }

    public Zipper (String func, String input, String target) {
        if (func.equals("zipper")) {
            zip(input, target);
        } else if (func.equals("unzipper")) {
            unzip(input, target);
        }
        else  {
            System.err.println("please enter either \"zipper\" or \"unzip\"");
            System.exit(1);
        }
    }

    public void zip (String inputDirectory, String outputFile) {
        Zip zipDirectory = new Zip(inputDirectory, outputFile);
        z = zipDirectory;
    }

    public void unzip (String zipperFile, String outputDirectory) {
        Unzip unzip = new Unzip(zipperFile, outputDirectory);
    }

    public class Zip { 

        private LinkedList<String> fileNames = new LinkedList<String>(); // filenames of the txt file
        private Hashtable<String, String> encodedFiles = new Hashtable<String, String>();  // key = filename, value = filename.huffman


        public LinkedList<String> getfileNames() {
            return new LinkedList<String>(fileNames);
        }
        public Hashtable<String, String> getEncodedFiles() {
            return new Hashtable<String, String> (encodedFiles);
        }
        public Zip (String inputDirectory, String outputFile) {
            File directory = new File(inputDirectory);
            encodeDirectory(directory);
            outputFileWriter(outputFile);
        }

        public void encodeDirectory (File folder) {
            fileNames.add(folder.toString());
            for (File entry : folder.listFiles()) {
                if (entry.isFile()) {
                    String encodedFileName = new String(entry.toString() + ".huffman");
                    try {
                        encode(entry.toString(), encodedFileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fileNames.add(entry.toString()); 
                    encodedFiles.put(entry.toString(), encodedFileName);
                } else {
                    encodeDirectory(entry);
                }
            }
        }

        public void encode (String inputFile, String outputFile) throws Exception {
            HuffmanEncoding encode = new HuffmanEncoding("encode", inputFile, outputFile);
        }

        public void outputFileWriter (String outputFile) {
            try {
                FileWriter f = new FileWriter(outputFile);
                long totalBytes = 0;
                for (String pathToFile : fileNames) {
                    File path = new File(pathToFile);
                    if (path.isFile()) {
                        File encodedFile = new File(encodedFiles.get(pathToFile));
                        f.write(pathToFile + "," + totalBytes);
                        f.write(System.getProperty("line.separator"));
                        totalBytes += encodedFile.length();
                    } else {
                        f.write(pathToFile + "," + -1);
                        f.write(System.getProperty("line.separator"));
                    }
                }
                f.write(System.getProperty("line.separator"));
                f.close();
            } catch (IOException e) {
                System.out.println("Can't find or create " + outputFile);
            }
            for (String pathToFile : fileNames) {
                String encodedStr = new String(pathToFile + ".huffman");
                File encodedFile = new File(encodedStr);
                if (encodedFile.isFile()) {
                    FileOutputHelper output = new FileOutputHelper();
                    FileCharIterator iter = new FileCharIterator(encodedStr);
                    StringBuilder bits = new StringBuilder();
                    while(iter.hasNext()) {
                        bits.append(iter.next());
                    }
                    output.writeBinStrToFile(bits.toString() + "00001010", outputFile);
                    encodedFile.delete();
                }
            }
        }
    }

    public class Unzip {
        
        private Hashtable<Long, String> byteMappings = new Hashtable<Long, String>();
        private PriorityQueue<Long> byteLength = new PriorityQueue<Long>();
        private LinkedList<String> directories = new LinkedList<String>();
        private String targetDirectory;

        public Unzip (String inputFile, String outputDirectory) {
            this.targetDirectory = outputDirectory;
            File dir = new File(this.targetDirectory);
            if (!dir.exists()) {
                try {
                    dir.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            getMappings(inputFile);
            createDirectories();
            decodeFile(inputFile, outputDirectory);
        }

        public void getMappings (String inputFile) {
            long totalBytes = 0;
            try {
                BufferedReader in = new BufferedReader (new FileReader(inputFile));
                for (String nextLine = in.readLine(); !nextLine.isEmpty(); nextLine = in.readLine()) {
                    String path = new String();
                    String location = new String();
                    boolean found = false;
                    for (int i = 0; i < nextLine.length(); i++) {
                        if (nextLine.charAt(i) == ',') {
                            found = true;
                        } else if (found) {
                            location += nextLine.charAt(i);
                        } else {
                            path += nextLine.charAt(i);
                        }
                    }
                    if (Long.parseLong(location) < 0) {
                       directories.add(path);
                    } else {
                        byteLength.add(Long.parseLong(location));
                        byteMappings.put(Long.parseLong(location), path);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void createDirectories ( ) {
            for (String file : directories) {
                File dir = new File(this.targetDirectory + "/" + file);
                if (!dir.exists()) {
                    try {
                        dir.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void decodeFile (String inputFile, String outputDirectory) {
            FileCharIterator iter = new FileCharIterator(inputFile);
            Iterator<Long> byteIter = byteLength.iterator();
            boolean tableEnds = false;
            boolean skipTable = false;
            String nextBits = new String();
            int count = 0;
            boolean flag = false;
            boolean flag1 = false;
            long nextBitCount = 0;
            long key = byteIter.next();
            while(!flag) {
                if (!skipTable) {
                    nextBits = iter.next();
                    if (nextBits.equals("00001010")) {
                        if (tableEnds) {
                            skipTable = true;
                        }
                        tableEnds = true;
                    } else {
                        tableEnds = false;
                    }
                } else {
                    StringBuilder encodedFile = new StringBuilder();
                    if (byteIter.hasNext()) {
                        nextBitCount = byteIter.next();
                        if (!byteIter.hasNext()) {
                            flag1 = true;
                        }
                        while(count <= nextBitCount) {
                            count++;
                            encodedFile.append(iter.next());
                        }
                        decodeFile(key, encodedFile);
                    } else {
                        while (iter.hasNext()) {
                            encodedFile.append(iter.next());
                        }
                        flag = true;
                        decodeFile(key, encodedFile);
                    }
                    key = nextBitCount;
                }
            }
        }

        public void decodeFile (long key, StringBuilder file) {
            FileOutputHelper output = new FileOutputHelper();
            String toDecode = this.targetDirectory + "/" + byteMappings.get(key) + ".huffman";
            String decoded = this.targetDirectory + "/" + byteMappings.get(key);
            File deleteThis = new File(toDecode);
            output.writeBinStrToFile(file.toString(), toDecode);
            try {
                decode(toDecode, decoded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteThis.delete();
        }

        public void decode (String inputFile, String outputFile) throws Exception {
            HuffmanEncoding decode = new HuffmanEncoding("decode", inputFile, outputFile);
        }
    }
}
