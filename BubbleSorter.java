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

public class BubbleSorter <T extends Comparable<? super T>> implements Sorter<List<T>,T>, Collector<T, List<T>, List<T>> {
		
		private Comparator<T> comparator;
		
		public BubbleSorter(Comparator<T> c){
			this.comparator=c;
		}
		
		public List<T> getSorted(List<T> list) {
			return sorted(new ArrayList<T>(list));
		}

		private List<T> sorted(List<T> list) {
			int swapCount;
			do {
				swapCount=0;
				for(int pairStart=0;pairStart<list.size()-1;pairStart++) {
					T first, next;
					if(comparator.compare(first=list.get(pairStart), next=list.get(pairStart+1))>0) {
						list.set(pairStart+1,first);
						list.set(pairStart,next);
						swapCount++;
					}
				}
			}while(swapCount>0);
			return list;
		}
		
		@Override
		public Supplier<List<T>> supplier() {
			return ArrayList<T>::new;
		}

		@Override
		public BiConsumer<List<T>, T> accumulator() {
			return List<T>::add; //( list, item ) -> list.add(item);
		}

		@Override
		public BinaryOperator<List<T>> combiner() {
			return ( left, right )->{ left.addAll(right); return left; };
		}

		@Override
		public Function<List<T>, List<T>> finisher() {
			return this::getSorted; //( source ) -> { return sorted(source); };
		}

		@Override
		public Set<Characteristics> characteristics() {
			return Collections.emptySet();
		}

}
