import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

final class ArrayEntry <T extends Comparable<? super T>> {
	private int index;
	private T value;
	
	public ArrayEntry(final int index, final T entry) {
		this.index=index;
		this.value=entry;
	}
	
	public int getIndex() {
		return index;
	}
	
	public T getValue() {
		return value;
	}
	
	public void shiftLeft(int nextIndex) {
		if(nextIndex<=index) index--;
	}
	
}

public final class Mathematics {
	
	public final static <T extends Comparable<? super T>> ArrayEntry<T> getMinExtremum(final List<T> list) {
		return getExtremum(list, Comparator.naturalOrder(), 0);
	}
	
	public final static <T extends Comparable<? super T>> ArrayEntry<T> getMaxExtremum(final List<T> list) {
		return getExtremum(list, Comparator.reverseOrder(), 0);
	}
	
	public final static <T extends Comparable<? super T>> ArrayEntry<T> getExtremum(
			  final List<T> list, final Comparator<? super T> comp) {
		return getExtremum(list, comp, 0);
	}
	
	public final static <T extends Comparable<? super T>> ArrayEntry<T> getExtremum(
			  final List<T> list, final Comparator<? super T> comp, final int startIndex) {
		if(list.size()<=startIndex) throw new RuntimeException(String.format("parameter list must have at least %d item(s)",startIndex)); 
		
		int resultIndex=startIndex;
		T result=list.get(startIndex);
		for(ListIterator<T> i=list.listIterator(startIndex+1);i.hasNext();) {
			final int valueIndex=i.nextIndex();
			final T value=i.next();
			if(comp.compare(value, result)<0) {
				resultIndex=valueIndex;
				result=value;
			}
		} 
		return new ArrayEntry<T>(resultIndex, result); 
	}  
	 
	public final static <T extends Comparable<? super T>> T getMinimum(final List<? extends T> list) {
		if(list.isEmpty()) throw new RuntimeException("parameter list must have at least one item");
		T min=list.get(0);
		for(ListIterator<? extends T> i=list.listIterator(1);i.hasNext();) {
			T value=i.next();
			min=min.compareTo(value)<0?min:value;
		}
		return min;
	}
	
	public final static <T extends Comparable<? super T>> T getMaximum(final List<? extends T> list) {
		if(list.isEmpty()) throw new RuntimeException("parameter list must have at least one item");
		T max=list.get(0);
		for(ListIterator<? extends T> i=list.listIterator(1);i.hasNext();) {
			T value=i.next();
			max=max.compareTo(value)>0?max:value;
		}
		return max;
	}
	
	/**
	 * The method returns sorted set of lucky numbers according to article {@link https://en.wikipedia.org/wiki/Lucky_number}
	 * 
	 * @param <T> type of resulting numbers
	 * @param lastOne last lucky number in result set would be less or equal to this parameter
	 * @return list of lucky numbers up to lastOne parameter
	 * 	 */
	public final static <T extends Integer> SortedSet<T> getLuckyNumbers(final int lastOne){
		if(lastOne<=0) throw new RuntimeException("at least one number should be included in lucky numbers sequence");
		
		final boolean[] sieve=new boolean[lastOne+1];//sieve for lucky numbers 
		Arrays.fill(sieve, true);//consider all numbers initially
		
		int period=2;//every number distanced 'period' steps further should be struck off 
		
		do {
			final int pace=period-1;
			
			for(int index=1;;) {//'index' refers to inspected number in sieve
				for(int step=pace;index<=lastOne && step>0;index++) 
						if(sieve[index]) step--;//skip next 'pace' marked numbers
				
				while(index<=lastOne && !sieve[index]) index++;//skip all numbers that were stricken out earlier 
				
				if(index<=lastOne) sieve[index++]=false;//strike out the number and move right to next position
				else break;
			}
	
			//find first still non-stricken number
			do{ period++; } while(period<=lastOne && !sieve[period]);

		}while(period<=lastOne);
		
		//compose result set
		SortedSet<T> result=new TreeSet<T>();
		for(int value=1;value<=lastOne;value++) {
			if(sieve[value]) {
				result.add((T) Integer.valueOf(value));
			}
		}
		return result;
	}
	
	public final static <T extends Integer> List<T> getFibonacciNumberList(List<T> input){
		List<BigInteger> fbNumbers=getFibonacciNumberList(BigInteger.valueOf(getMaximum(input)));
		List<T> result=new LinkedList<T>();
		for(T item:input) {
			if(fbNumbers.contains(BigInteger.valueOf(item))) {
				result.add(item);
			}
		}
		return result;
	}
	
	public final static List<BigInteger> getFibonacciNumberList(final BigInteger upperLimit) {
		final List<BigInteger> list=new LinkedList<BigInteger>();
		if(upperLimit.compareTo(BigInteger.ZERO)>=0) {
			BigInteger first=BigInteger.ZERO;
			list.add(first);
			if(upperLimit.compareTo(BigInteger.ONE)>=0) {
				BigInteger second=BigInteger.ONE;
				list.add(second);
				if(upperLimit.compareTo(BigInteger.TWO)>=0) {
					BigInteger next;
					do{
						next=first.add(second);
						if(next.compareTo(upperLimit)<=0) {
							list.add(next);//add new value to resulting list and
							first=second;//save values for next iteration
							second=next;					
						}else break;
					}while(true);
				}
			}
		}else throw new RuntimeException("negative upperLimit parameter is inappropriate");
		return list;
	}
	
	public final static <T extends Integer> List<T> getPrimes(final int lastNumber){
		if(lastNumber<=0) throw new RuntimeException("size parameter for getPrimes should be cardinal number");
		
		final int size=lastNumber+1;
		boolean[] marks=new boolean[size];
		//initialize sieve
		for(int k=0;k<size;k++) marks[k]=false;
		
		int probe=2;
		do{
			//strike out composite numbers
			for(int k=2*probe;k<size;k+=probe){
				marks[k]=true;
			}
			// find out next prime divisor
			do{
				probe++;
			}while(probe<size && marks[probe]);
		}while(probe<size);

		List<T> primes=new ArrayList<T>();
		for(int k=2;k<size;k++){
			if(!marks[k]){
				primes.add((T)Integer.valueOf(k));
			}
		}
		return primes;
	}
	
	public final static <T extends Integer> BigInteger getGCD(final List<T> source) {
		List<T> list=new LinkedList<T>();//source.stream().filter(x->x>0).collect(Collectors.toList())
		for(T item:source) if(item.intValue()>1) 	list.add(item);
		if(list.size()<2) throw new RuntimeException("at least two numbers must be included in parameter list to compute GCD");
		
		BigInteger gcd=null;
		do {
			ArrayEntry<T> minEntry=getMinExtremum(list);
			T min=minEntry.getValue();
			for(ListIterator<T> i=list.listIterator();i.hasNext();) {
				int nextIndex=i.nextIndex();
				T value=i.next();
				if(!value.equals(min)) {
					T remainder=(T)Integer.valueOf((value.intValue()%min));
					if(remainder.equals(0)) {
						i.remove();
						minEntry.shiftLeft(nextIndex);
					}else {
						i.set(remainder);						
					}
				}else if(minEntry.getIndex()!=nextIndex) {
					i.remove();
					minEntry.shiftLeft(nextIndex);
				}
			}
		}while(list.size()>1);
		if(list.size()==1) {
			gcd=BigInteger.valueOf(list.get(0).longValue());
		}
		if(gcd==null) throw new RuntimeException(String.format("can't figure out GCD for %s",list));
		return gcd;
	}
	
	public final static <T extends Integer> BigInteger getProduct(final List<T> list) {
		BigInteger product=BigInteger.ONE;
		for(T item:list){
			product=product.multiply(BigInteger.valueOf(item.longValue()));
		}
		return product;
	}

	public final static <T extends Integer> BigInteger getLCM(final List<T> list) {
		final BigInteger product=getProduct(list);
		final BigInteger gcd=getGCD(list);
		return product.divide(BigInteger.valueOf(gcd.longValue()));
	}

}
