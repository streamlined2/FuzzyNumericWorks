import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectionSortCollector<T extends Comparable<? super T>> extends SortCollector<T> {

	public SelectionSortCollector(final Comparator<T> comparator){
		super(comparator);
	}
	
	@Override
	public List<T> getSorted(List<T> list) {
		List<T> sorted=new ArrayList<T>(list);
		for(int startIndex=0;startIndex<sorted.size()-1;startIndex++) {
			ArrayEntry<T> ex=Mathematics.getExtremum(sorted, getComparator(), startIndex);
			T firstItem=sorted.get(startIndex);
			sorted.set(startIndex, ex.getValue());
			sorted.set(ex.getIndex(), firstItem);
		}
		return sorted;
	}

}
