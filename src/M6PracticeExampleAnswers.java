import java.util.*;

public class M6PracticeExampleAnswers {

	public static void main(String[] args) {
		int[] numbers = {2, 1, 6, 3, 5};
		System.out.println(findMissingInt(numbers));
		
		int[] uniqueRandomNumbers = createArrayWithUniqueRandom(5);
		System.out.println(Arrays.toString(uniqueRandomNumbers));
		
		int[] posAndNegNumbers = new int[100];
		Random generator = new Random();
		for(int i=0; i<posAndNegNumbers.length; i++) {
			int randomPosOrNegValue = generator.nextInt(51)-25; // random number in range -25 to 25, inclusive
			if(randomPosOrNegValue!=0) {
				posAndNegNumbers[i] = randomPosOrNegValue;
			} else { // no 0s allowed; try that loop pass again
				i--;
			}
		}
		System.out.println(Arrays.toString(posAndNegNumbers));
		rearrangeArray(posAndNegNumbers);
		System.out.println(Arrays.toString(posAndNegNumbers));
		
	}
	
	// there are other solution approaches
	// this solution uses extra memory to keep track of what numbers have been seen
	// this solution takes advantage of array access being O(1)
	// the solution has two consecutive O(n) loops, so the whole method is O(n)
	public static int findMissingInt(int[] numbers) {
		int range = numbers.length+1;
		boolean[] numbersFound = new boolean[range+1]; // making the array one bigger so indices match the actual number (making it 1-based instead of 0-based)
		
		for(int number : numbers) {
			numbersFound[number] = true;
		}
		for(int i=1; i<numbersFound.length; i++) {
			if(numbersFound[i]==false) {
				return i;
			}
		}
		return -1; // needed here for code to compile; the code will never reach this statement
	}
	
	public static int[] createArrayWithUniqueRandom(int size) {
		int[] array = new int[size];
		int upperRange = 5*size;
		ArrayList<Integer> possibleValues = new ArrayList<Integer>(); 
		
		for(int i=0; i<=upperRange; i++) { // this loop runs 5n times, which is still O(n)
			possibleValues.add(i); // add(T) is O(1)
		}
		Collections.shuffle(possibleValues); // review the API- shuffle is O(n)
		
		for(int i=0; i<array.length; i++) {
			array[i] = possibleValues.get(i); // important that we used ArrayList so that get(int) is O(1)
		}
		return array;
	}
	

	public static void rearrangeArray(int[] numbers) {
		int leftIndex = 0;
		int rightIndex = numbers.length-1;
		
		// this loop moves through the array one time, just from two directions- from the left and from the right
		boolean done = false;
		while(!done) {
			
			int leftValue = numbers[leftIndex];
			// loop as long as the indices have not yet crossed over
			// also keep looping as long as there is a negative number on the left side; negative numbers can stay put
			while(leftValue<0 && leftIndex<numbers.length-1 && leftIndex<rightIndex) { 
				leftIndex++;
				leftValue = numbers[leftIndex];
			}
			// at this point, either the indices have crossed over, the index has gone off the end of the array,
			// or leftValue is a positive number
			
			int rightValue = numbers[rightIndex];
			// loop as long as the indices have not yet crossed over
			// also keep looping as long as there is a positive number on the right side; positive numbers can stay put
			while(rightValue>0 && rightIndex>0 && leftIndex<rightIndex) { 
				rightIndex--;
				rightValue = numbers[rightIndex];
			}
			// at this point, either the indices have crossed over, the index has gone off the end of the array,
			// or rightValue is a negative number
			
			
			// if the indices are valid, swap the negative and positive values
			if(leftIndex<numbers.length && rightIndex>=0) {
				numbers[leftIndex] = rightValue;
				numbers[rightIndex] = leftValue;
				leftIndex++;
				rightIndex--;
			}
			
			// once the indices become invalid or cross over, we are done
			if(leftIndex>=numbers.length || rightIndex<0 || rightIndex<=leftIndex) {
				done = true;
			}

			
		}
	}
}
