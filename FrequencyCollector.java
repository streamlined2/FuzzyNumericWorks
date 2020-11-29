import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FrequencyCollector<T extends Comparable<? super T>> 
	implements Collector<T, Map<T,Integer>, SortedSet<Map.Entry<T,Integer>>> {
	
	private final Comparator<? super Number> comparator;
	
	public FrequencyCollector(final Comparator<? super Number> comparator) {
		this.comparator=comparator;
	}

	@Override
	public Supplier<Map<T, Integer>> supplier() {
		return HashMap<T,Integer>::new;
	}
	
	@Override
	public BiConsumer<Map<T, Integer>, T> accumulator() {
		return (map,key) -> {
			map.put(key, map.getOrDefault(key, 0)+1);
		};
	}

	@Override
	public BinaryOperator<Map<T, Integer>> combiner() {
		return (left,right)->{
			right.forEach((key,value)->{ left.put(key,left.getOrDefault(key, 0)+value); });
			return left;
		};
	}
	
	private class MapValueComparator implements Comparator<Map.Entry<T, Integer>> {
		@Override
		public int compare(Map.Entry<T, Integer> o1, Map.Entry<T, Integer> o2) {
			return comparator.compare(o1.getValue(), o2.getValue());
		}	
	}

	@Override
	public Function<Map<T, Integer>, SortedSet<Map.Entry<T,Integer>>> finisher() {
		return (map)->{
			@SuppressWarnings("serial")
			SortedSet<Map.Entry<T, Integer>> set = new TreeSet<Map.Entry<T, Integer>>(new MapValueComparator()) {
				@Override public String toString() {
					final StringBuilder b=new StringBuilder("[");
					forEach(x->b.append(x.getKey()).append(","));
					if(!isEmpty()) b.deleteCharAt(b.length()-1);
					return b.append("]").toString();
				};
			};
			set.addAll(map.entrySet());
			return set;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

}
