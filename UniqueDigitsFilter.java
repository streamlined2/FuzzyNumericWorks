import java.util.function.Predicate;

public class UniqueDigitsFilter<T extends Integer> implements Predicate<T>{

	public boolean test(final T n) {
		final int radix=10;
		final int[] counts=new int[radix];
		int remainder=Math.abs(n);
		int digits=0;
		do {
			final int digit=remainder%radix;
			counts[digit]++;
			remainder=remainder/radix;
			digits++;
		}while(remainder>0);
		
		boolean hasDuplicates=false;
		for(int k=0;!hasDuplicates && k<counts.length;k++) {
			hasDuplicates=(counts[k]>1);
		}
		return digits==3 && !hasDuplicates;
	}

}
