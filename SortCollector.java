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
	
	private Comparator<T> comparator;
	
	public SortCollector() {
		this.comparator=comparator;
	}

	@Override
	public Supplier<List<T>> supplier() {
		return LinkedList<T>::new;
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return List<T>::add;
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (left, right) -> { left.addAll(right); return left;};
	}

	@Override
	public Function<List<T>, List<T>> finisher() {
		return this::getSorted;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

	@Override
	public abstract List<T> getSorted(List<T> list);

}
