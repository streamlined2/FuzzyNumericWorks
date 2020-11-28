import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
	
	public final static <T extends Integer> List<T> getPrimes(final int lastNumber){
		if(lastNumber<=0) throw new RuntimeException("size parameter for getPrimes should be cardinal number");
		
		final int size=lastNumber+1;
		boolean[] marks=new boolean[size];
		//initialize sieve
		for(int k=0;k<size;k++) marks[k]=false; //Arrays.setAll(marks, i->false);
		
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
