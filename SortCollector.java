import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public abstract class SortCollector<T extends Comparable<? super T>> implements Sorter<List<T>,T>, Collector<T,List<T>,List<T>> {
	
	private Comparator<? super T> comparator;
	
	public SortCollector(final Comparator<? super T> comparator) {
		this.comparator=comparator;
	}
	
	public Comparator<? super T> getComparator(){
		return comparator;
	}

	@Override
	public Supplier<List<T>> supplier() {
		return LinkedList<T>::new;
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return List<T>::add;//( list, item ) -> list.add(item);
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (left, right) -> { left.addAll(right); return left;};
	}

	@Override
	public Function<List<T>, List<T>> finisher() {
		return this::getSorted; //( source ) -> { return sorted(source); };
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

	@Override
	public abstract List<T> getSorted(List<T> list);

}
