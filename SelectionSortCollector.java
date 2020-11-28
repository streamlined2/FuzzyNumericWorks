import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class SelectionSortCollector<T extends Comparable<? super T>> implements Sorter<List<T>,T>, Collector<T, List<T>, List<T>> {

	private Comparator<T> comparator;
	
	public SelectionSortCollector(final Comparator<T> comparator){
		this.comparator=comparator;
	}
	
	@Override
	public Supplier<List<T>> supplier() {
		return ArrayList<T>::new;
	}

	@Override
	public BiConsumer<List<T>,T> accumulator() {
		return List<T>::add;
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (left, right) -> { left.addAll(right); return left;};
	}

	@Override
	public Function<List<T>,List<T>> finisher() {
		return this::getSorted;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

	@Override
	public List<T> getSorted(List<T> list) {
		List<T> sorted=new ArrayList<T>(list);
		for(int startIndex=0;startIndex<sorted.size()-1;startIndex++) {
			ArrayEntry<T> ex=Mathematics.getExtremum(sorted, comparator, startIndex);
			T firstItem=sorted.get(startIndex);
			sorted.set(startIndex, ex.getValue());
			sorted.set(ex.getIndex(), firstItem);
		}
		return sorted;
	}

}
