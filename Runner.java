import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Runner {
	
	private static final int COUNT=10;
	
	private static final int UPPER_LIMIT = 120;

	private static List<Integer> getList(){
		Scanner scanner=new Scanner(System.in);
		List<Integer> list=new ArrayList<>(COUNT);
		
		System.out.printf("Please enter %d integers: ",COUNT);
		for(int k=0;k<COUNT;k++) {
			list.add(scanner.nextInt());
		}
		
		scanner.close();
		return list;
	}
	

	public static void main(String... args) {
		
		List<Integer> list=getList();
		System.out.printf("You entered list of integers: %s%n",list.toString());

		System.out.printf("List of even integers: %s%n",list.stream().filter(n->n%2==0).collect(Collectors.toList()));
		System.out.printf("List of odd integers: %s%n",list.stream().filter(n->n%2!=0).collect(Collectors.toList()));

		System.out.printf("Maximum: %d%n",list.stream().max(Comparator.naturalOrder()).orElse(0));
		System.out.printf("Minimum: %d%n",list.stream().min(Comparator.naturalOrder()).orElse(0));
		
		System.out.printf("Maximum: %d%n",list.stream().collect(Collectors.maxBy(Comparator.naturalOrder())).orElse(0));
		System.out.printf("Minimum: %d%n",list.stream().collect(Collectors.minBy(Comparator.naturalOrder())).orElse(0));

		System.out.printf("List of integers that are divided by 3 or 9: %s%n",list.stream().filter(n->(n%3==0)||(n%9==0)).collect(Collectors.toList()));
		System.out.printf("List of integers that are divided by 5 and by 7: %s%n",list.stream().filter(n->(n%5==0)&&(n%7==0)).collect(Collectors.toList()));

		System.out.printf("Reversely sorted list: %s%n", list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
		//System.out.printf("Reversely bubblesorted list: %s%n", new BubbleSorter<Integer>(list,Comparator.reverseOrder()).getSorted().toString());
		
		System.out. printf(
				"Reversely sorted list by custom bubblesort collector: %s%n", 
				list.stream().collect(new BubbleSorter<Integer>(Comparator.reverseOrder())));
		 
		System.out. printf(
				"Reversely sorted list by custom insertstream collector: %s%n", 
				list.stream().collect(new InsertSorter<Integer>(Comparator.reverseOrder())));
		/*
		 * List<Integer> sorted=new
		 * InsertSorter<Integer>(list,Comparator.reverseOrder()).getSorted();
		 * System.out.println(sorted);
		 */	
		
		System.out.printf("Custom filtered stream: %s%n", list.stream().filter(new UniqueDigitsFilter<Integer>()::test).collect(Collectors.toList()));
	
		System.out.printf("Minimum %d, Greatest common divisor %d and Least common multiple %d%n", 
					Mathematics.getMinimum(list),
					Mathematics.getGCD(list),
					Mathematics.getLCM(list));
	
		System.out.printf("List of prime numbers up to %d: %s%n", UPPER_LIMIT, Mathematics.getPrimes(UPPER_LIMIT));
		System.out.printf("You entered such prime numbers: %s%n", list.stream().collect(new PrimeNumbersCollector<Integer>()));
		
		System.out.printf("Sorted list in ascending order (selection sort algorithm): %s%n",list.stream().collect(new SelectionSortCollector<Integer>(Comparator.naturalOrder())));
		System.out.printf("Sorted list in descending order (selection sort algorithm): %s%n",list.stream().collect(new SelectionSortCollector<Integer>(Comparator.reverseOrder())));
		
		System.out.printf("Sorted list in ascending order (quick sort algorithm): %s%n",list.stream().collect(new QuickSortCollector<Integer>(Comparator.naturalOrder())));
		System.out.printf("Sorted list in descending order (quick sort algorithm): %s%n",list.stream().collect(new QuickSortCollector<Integer>(Comparator.reverseOrder())));
		
		System.out.printf("Numbers ordered by frequency: %s%n", list.stream().collect(new FrequencyCollector<Integer>(Collections.reverseOrder())));
		
		System.out.printf("Set of lucky numbers up to %d: %s%n",UPPER_LIMIT,Mathematics.getLuckyNumbers(UPPER_LIMIT));
		
		System.out.printf("Set of Fibonacci numbers up to %d: %s%n", UPPER_LIMIT,Mathematics.getFibonacciNumberList(BigInteger.valueOf(UPPER_LIMIT)));
		System.out.printf("Set of Fibonacci numbers from input: %s%n", Mathematics.getFibonacciNumberList(list));
		
		System.out.printf("List of palindromes from input: %s%n", list.stream().filter(new Palindrome<Integer>()::test).collect(Collectors.toList()));
		
		System.out.printf("List of averages of adjacent values: %s%n", list.stream().collect(new AdjacentNumberAverageCollector<Integer>()));
		
		System.out.printf("Period of decimal fraction for first pair of positive adjacent numbers: %s%n", 
				list.stream().filter(x->(x>0 && Math.round(x)==x)).limit(2).collect(new DecimalFractionPeriodCollector<Integer>()));
		
	}

}
