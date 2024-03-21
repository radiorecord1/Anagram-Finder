import java.io.*;
import java.util.Iterator;
/**
 * Capstone Project "AnagramFinder".
 * @author Paul Arevalo pa2658
 * @version 1.0 December 18, 2023
 */

public class AnagramFinder {

    /** Takes a String, sorts its characters lexicographically, and
     * returns a new sorted String.
     * @param s a String that needs its characters sorted.
     * @return a String with its characters sorted in lower case.
     */
    private String insertionSort(String s){
        int len = s.length();
        char[] charArray = new char[len];
        //Puts all characters as lower case characters into an array
        for(int i=0; i<s.length();i++){
            char ch = Character.toLowerCase(s.charAt(i));
            charArray[i] = ch;
        }
        //Sorts the array using insertion sort
        for (int i = 1; i < len; ++i) {
            char current = charArray[i];
            int j = i-1;
            for (; j>= 0 && charArray[j] > current; --j) {
                charArray[j + 1] = charArray[j];
            }
            charArray[j + 1] = current;
        }
        //Builds a new String using the now sorted array
        StringBuilder stringBuilder = new StringBuilder(len);
        for (char c : charArray) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
    /**Takes the target String from the command line arguments and finds the key-value
     * mapping in the map data structure associated with its key. The LinkedList value
     * is then converted into an array, the array is sorted, then its String elements
     * are printed, hence printing the anagrams of the target String.
     * @param input the target String from the command line.
     * @param map the MyMap built by the MapBuild method.
     */
    private void getAnagrams(String input,MyMap<String, MyList<String>> map){
        String key = insertionSort(input);
        MyList<String> value = map.get(key);
        //When map.get() returns null it means that no anagrams are found
        if(value == null){
            System.out.println("No anagrams found.");
            return;
        }
        //Puts all String elements of the LinkedList into an array of Strings
        String[] listArray = new String[value.size()];
        Iterator<String> iterator = value.iterator();
        for(int i=0; i< listArray.length; i++){
            listArray[i] = iterator.next();
        }
        //Sorts the array of Strings
        insertionSort(listArray);
        //Prints each String element while counting how many have been printed
        //Omits printing any duplicates of the target String
        int j = 0;
        for (String anagram : listArray) {
            if(!anagram.equals(input)){
                System.out.println(anagram);
                j++;
            }
        }
        //If zero String elements are printed then no anagrams are found
        if(j == 0){
            System.out.println("No anagrams found.");
        }
    }
    /**Takes an array of Strings and sorts it lexicographically.
     * @param anagrams an array of Strings
     */
    private void insertionSort(String[] anagrams){
        for (int i = 1; i < anagrams.length; ++i) {
            String current = anagrams[i];
            int j = i-1;
            for (; j>= 0 && anagrams[j].compareTo(current)>0; --j) {
                anagrams[j + 1] = anagrams[j];
            }
            anagrams[j + 1] = current;
        }
    }
    /**Builds a MyMap by taking the file named in the command line arguments and reads
     * it line by line. Once a line receives its key, if a mapping of that key is not
     * yet in the MyMap, a LinkedList of Strings is instantiated as its value and the
     * line is added to the list. The key-value pair is then put in the map. If the key
     * already has a mapping in the MyMap, the line is added to that key's value,
     * the LinkedList of Strings.
     * @param file File name from the command line arguments to be read.
     * @param map MyMap either BSTMap, AVLTreeMap, or HashMap instantiated in the
     * main method.
     * @throws IOException if there is an error while reading the file.
     */
    private void MapBuild(String file, MyMap<String, MyList<String>> map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = bufferedReader.readLine();
        //For each line String, creates and puts a new key-value pair in the map or
        //adds the line String to an existing mapping.
        while (line != null) {
            String key = insertionSort(line);
            MyList<String> value = map.get(key);
            //Puts a key-value pair in the map if it does not exist in it
            if (value == null) {
                MyList<String> list = new MyLinkedList<>();
                list.add(line);
                map.put(key, list);
            }
            //Adds line String to an already existing LinkedList
            else {
                value.add(line);
            }
            //Reads next line
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
    }
    /** Validates command line arguments and executes program based on those arguments.
     * @param args from command line
     */
    public static void main(String[] args) {
        //Checks for valid command line arguments
        if(args.length!=3){
            System.err.println("Usage: java AnagramFinder <word> <dictionary file> " +
                    "<bst|avl|hash>");
            System.exit(1);
        }
        File file = new File(args[1]);
        // Check if the file exists
        if (!file.exists()) {
            System.err.println("Error: Cannot open file '" + args[1] + "' for input."
            );
            System.exit(1);
        }
        if(!args[2].equals("bst") && !args[2].equals("avl") && !args[2].equals("hash")){
            System.err.println("Error: Invalid data structure '" + args[2] + "' received.");
            System.exit(1);
        }
        AnagramFinder finder = new AnagramFinder();
        //Based on the command line arguments, instantiates either a BSTMap, AVLTreeMap,
        //or MyHashMap MyMap, builds it by putting in key-value mappings gathered from
        //reading the file, and prints out the anagrams of the target String.
        try {
            if(args[2].equals("bst")) {
                MyMap<String, MyList<String>> bst = new BSTMap<>();
                finder.MapBuild(args[1], bst);
                finder.getAnagrams(args[0], bst);
            }
            if(args[2].equals("avl")){
                MyMap<String, MyList<String>> avl = new AVLTreeMap<>();
                finder.MapBuild(args[1], avl);
                finder.getAnagrams(args[0], avl);
            }
            if(args[2].equals("hash")) {
                MyMap<String, MyList<String>> hash = new MyHashMap<>();
                finder.MapBuild(args[1], hash);
                finder.getAnagrams(args[0], hash);
            }
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred reading '" + args[1] + "'.");
        }
    }

}
