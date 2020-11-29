import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BubbleSorter <T extends Comparable<? super T>> extends SortCollector<T> {
		
		public BubbleSorter(final Comparator<T> c){
			super(c);
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
					if(getComparator().compare(first=list.get(pairStart), next=list.get(pairStart+1))>0) {
						list.set(pairStart+1,first);
						list.set(pairStart,next);
						swapCount++;
					}
				}
			}while(swapCount>0);
			return list;
		}
		
}
