/*
   Jacob Chalissery
   10/27/2023
   
   This problem solves the classic "stable marriage" problem in which equal sized groups of men and women have ranked preferences of the opposite gender which they want to 
   marry, and I pair them in the most "stable" way possible, by using a pattern in which a there is never a downgrade in preference. I use 2D Lists to store the preferences
   for the men and women, a regular ArrayList for the names of men and women, a LinkedList to store the single men, which is what keeps the algorithm going, and a HashMap
   to store the engaged couples.
*/

//import statements
import java.util.*;
import java.io.*;

public class StableMarriage {
   
   //main method
   public static void main(String[] args) throws FileNotFoundException {
      
      //Main scanner to scan the text input file
      Scanner scan = new Scanner(new File("StableMarriageInput2.txt"));
      
      //Lists of the names of men and women
      ArrayList<String> men = new ArrayList<String>();
      ArrayList<String> women = new ArrayList<String>();
      
      //LinkedList of single men
      LinkedList<String> singleMen = new LinkedList<String>();
      
      //HashMap of couples
      Map<String, String> couples = new HashMap<String, String>();
      
      //2D lists of preferences for both genders
      ArrayList<LinkedList<Integer>> menPref = new ArrayList<LinkedList<Integer>>();
      ArrayList<LinkedList<Integer>> womenPref = new ArrayList<LinkedList<Integer>>();
      
      //counter for rows of 2D array for men, must be defined outside loop
      int menCounter = 0;
      //first while loop to store all the names of men, the linked list of single men, and the preferences for men
      while (scan.hasNextLine()) {
         String line = scan.nextLine();         
         if (line.length() > 1) {
            String name = line.substring(0, line.indexOf(":"));
            men.add(name);
            singleMen.add(name);
            
            //second scanner for individual line, to scan for the integers that are stored in the 2D list
            Scanner numScan = new Scanner(line.substring(line.indexOf(":") + 1));
            menPref.add(new LinkedList<Integer>());
            while (numScan.hasNextInt()) {
               menPref.get(menCounter).add(Integer.valueOf(numScan.nextInt()));
            }
         }
         //the loop breaks when it hits the separating new line
         else {
            break;
         }
         //increments row counter on each iteration of the main while loop
         menCounter++;
      }
      
      //counter for rows of 2D array for women, must be defined outside loop
      int womenCounter = 0;
      //second while loop to store the names and preferences for women
      while (scan.hasNextLine()) {
         String line = scan.nextLine();
         String name = line.substring(0, line.indexOf(":"));
         women.add(name);
         
         //smaller scanner for individual line to collect integers and store in 2D list
         Scanner numScan = new Scanner(line.substring(line.indexOf(":") + 1));
         womenPref.add(new LinkedList<Integer>());
         while (numScan.hasNextInt()) {
            womenPref.get(womenCounter).add(Integer.valueOf(numScan.nextInt()));
         }
         //increments row counter
         womenCounter++;
      }
      
      //while loop to run the algorithm, the first while loop keeps the algorithm going as long as there are single men remaining
      while (singleMen.size() > 0) {
         //saves indexes and names of chosen men and women pair for the iteration
         int m = men.indexOf(singleMen.getFirst());
         int w = menPref.get(m).getFirst();
         String wName = women.get(w - 1);
         String mName = men.get(m);
         //if else statement to add or update the map of couples with the new pair
         if (couples.containsKey(wName)) {
            singleMen.addLast(couples.get(wName));
            couples.replace(wName, mName);
         }
         else {
            couples.put(wName, mName);
         }
         //removes the newly paired man from single men list
         singleMen.removeFirst();
         //saves the current row for women to a variable that can be easily accessed later
         LinkedList<Integer> currRow = womenPref.get(w-1);
         //second loop that iterates for each successor q to m in w's list of preferences, and removes w from q's list of preferneces as well as q from w's preferences
         for (int i = currRow.indexOf(Integer.valueOf(m + 1)) + 1; i < currRow.size(); i++) {
            int q = currRow.get(i);
            menPref.get(q - 1).remove(Integer.valueOf(w));
            currRow.remove(i);
            //since currRows size is decreased once when a value is removed from it, i must be decremented so the loop still runs the desired amount of times
            i--;
         }
      }
      
      //prints out all the couples
      for (int i = 0; i < men.size(); i++) {
         System.out.println(couples.get(women.get(i)) + " and " + women.get(i));
      }
   }
}