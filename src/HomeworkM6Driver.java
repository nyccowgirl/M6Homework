import java.util.*;

public class HomeworkM6Driver {

	// I recommend changing these to smaller numbers to start with!
	// Then once you think you have a good solution, you can increase them for more robust testing.
	public static final int LIST_SIZE = 100;
	public static final int MINIMUM_SINGLE_DUPLICATES = 10; // minimum number of numbers on the list that will appear twice (have a "single duplicate")
	public static final int MINIMUM_DOUBLE_DUPLICATES = 5;  // minimum number of numbers on the list that will appear three times (have a "duplicate duplicate")
	
	public static void main(String[] args) {
		
		// set up an Integer list that is guaranteed to have some single and double duplicates
		List<Integer> numbers = new ArrayList<>();
		Random generator = new Random();
		int max = 5 * LIST_SIZE;
		int min = -1 * max;
		int randomRange = max - min + 1;
		
		// add the first round of numbers
		int numberOfNumbersToAdd = LIST_SIZE- MINIMUM_SINGLE_DUPLICATES - MINIMUM_DOUBLE_DUPLICATES;
		numbers.add(min); // adding the min and max number to help test for array out of bounds errors
		numbers.add(min);
		numbers.add(max);
		numbers.add(max);
		numbers.add(0);  // adding 0 as a special test case
		numbers.add(0); 
		numberOfNumbersToAdd-=6;
		for(int i=0; i<numberOfNumbersToAdd; i++) {
			numbers.add(generator.nextInt(randomRange) + min);			
		}
		
		// add duplicate numbers
		Collections.shuffle(numbers);
		for(int i=0; i<MINIMUM_SINGLE_DUPLICATES; i++) {
			numbers.add(numbers.get(i));
		}
		for(int i=0; i<MINIMUM_DOUBLE_DUPLICATES; i++) {
			numbers.add(numbers.get(i));
		}
		
		// print the list sorted (might help with testing)
		Collections.sort(numbers);
		System.out.println("The original list:");
		System.out.println(numbers);
		
		// reshuffle the list
		Collections.shuffle(numbers);
		
		// the results should be the same for both methods
		System.out.println("\nThe duplicate lists from both methods- these should match!");
		List<Integer> duplicateListFromBad = findDuplicatesBad(numbers);
		Collections.sort(duplicateListFromBad);
		System.out.println("Duplicate list from bad method:  \t" + duplicateListFromBad);
		
		List<Integer> duplicateListFromLinear = findDuplicatesLinear(numbers);
		Collections.sort(duplicateListFromLinear);
		System.out.println("Duplicate list from linear method:\t" + duplicateListFromLinear);
		
		boolean match = duplicateListFromBad.equals(duplicateListFromLinear);
		if(match) {
			System.out.println("\nThe lists match. Test passed!");
		} else {
			System.out.println("\n***Test failed: The lists do not match.");
		}
	}
	
	public static List<Integer> findDuplicatesBad(List<Integer> numbers) {
		List<Integer> duplicateList = new ArrayList<Integer>();
		
		// this loop is O(n)- it iterates over the whole list
		for(int i=0; i<numbers.size(); i++) {
			int numberEvaluating = numbers.get(i);
			boolean duplicateFound = false;
			
			// this loop starts at i+1 and goes to the end of the list OR until a duplicate is found
			for(int j=i+1; j<numbers.size() && !duplicateFound; j++) {
				int numberChecking = numbers.get(j);
				
				// we find a duplicate that hasn't yet been put on the list
				if(numberEvaluating==numberChecking && !duplicateList.contains(numberEvaluating)) {
					duplicateFound = true; 
					
					// after a duplicate is found, we won't go back to the previous loop- 
					// this loop below finishes checking the rest of the list and puts all copies of the duplicate on the list
					for(int k=j; k<numbers.size(); k++) {
						if(numberChecking==Integer.valueOf(numbers.get(k))) {
							duplicateList.add(numberChecking);
						}
					}
				}
			}
		}
		return duplicateList;
	}
	
	public static List<Integer> findDuplicatesLinear(List<Integer> numbers) {
		HashMap<Integer, Integer> seen = new HashMap<>();
		List<Integer> duplicateList = new ArrayList<Integer>();

		// Using hash map
		seen.put(numbers.get(0), 1);					// Put first item in table

		for (int i = 1; i < numbers.size(); i++) {

			if (seen.get(numbers.get(i)) != null) {		// If number is in hashmap, then add to duplicateList
				duplicateList.add(numbers.get(i));
			} else if ((i + 1) < numbers.size()) {		// Otherwise, add the number to the hashmap
				seen.put(numbers.get(i), 1);
			}
		}

		return duplicateList;
	}
}
