import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class InsertSorter<T extends Comparable<? super T>> implements Sorter<List<T>, T>, Collector<T, List<T>, List<T>> {

	private Comparator<T> comparator;
	
	public InsertSorter(Comparator<T> comp) {
		this.comparator=comp;
	}

	@Override
	public List<T> getSorted(List<T> source) {
		return sorted(source);
	}

	private void addItem(List<T> sorted,T item) {
//		if(!sorted.isEmpty()) {
			boolean added=false;
			ListIterator<T> i=sorted.listIterator();
			while(i.hasNext()) {
				T next = i.next();
				if(comparator.compare(next, item)>0) {
					i.previous();
					i.add(item);
					added=true;
					break;
				}
			}
			if(!added) {
				i.add(item);
			}
//		}else {
//			sorted.add(item);
//		}
	}
	
	private List<T> sorted(List<T> source) {
		List<T> result=new LinkedList<T>();
		for(T item:source) {
			addItem(result,item);
		}
		return result;
	}
	
	@Override
	public Supplier<List<T>> supplier() {
		return LinkedList<T>::new;
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return ( list, item ) -> addItem(list, item);
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return ( left, right )->{ left.addAll(right); return left; };
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Set.of(Characteristics.IDENTITY_FINISH);
	}

	@Override
	public Function<List<T>, List<T>> finisher() {
		return null;
	}

}
