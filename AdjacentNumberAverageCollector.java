import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class AdjacentNumberAverageCollector<T extends Number> implements Collector<T, List<T>, List<T>> {

	@Override
	public Supplier<List<T>> supplier() {
		return LinkedList<T>::new;
	}

	@Override
	public BiConsumer<List<T>,T> accumulator() {
		return List<T>::add;
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return ( left, right ) -> { left.addAll(right); return left; };
	}

	@Override
	public Function<List<T>,List<T>> finisher() {
		return (numbers)->{
			List<T> result=new LinkedList<T>();
			Iterator<T> i=numbers.iterator();
			if(i.hasNext()) {
				T left=i.next();
				if(i.hasNext()) {
					T middle=i.next();
					while(i.hasNext()) {
						T right=i.next();
						long average=(left.longValue()+right.longValue())/2L;
						if(middle.longValue()==average) {
							result.add(middle);
						}
						left=middle;
						middle=right;
					};
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
