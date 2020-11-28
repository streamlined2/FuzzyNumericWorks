import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PrimeNumbersCollector<T extends Integer> implements Collector<T, List<T>, List<T>> {

	/*
	 * public PrimeNumbersCollector() { }
	 */	
	
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
		return ( left, right )->{ left.addAll(right); return left; };
	}

	@Override
	public Function<List<T>, List<T>> finisher() {
		return ( numbers ) -> {
			final int size=Mathematics.getMaximum(numbers).intValue();
			List<T> primes=Mathematics.getPrimes(size);
			List<T> result=new LinkedList<T>();
			for(T number:numbers) {
				if(primes.contains(number)) {
					result.add(number);
				}
			}
			return result;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

}
