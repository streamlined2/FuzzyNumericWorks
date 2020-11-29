import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class QuickSortCollector<T extends Comparable<? super T>> extends SortCollector<T> {
	
	private final Stack<Range> postponed;

	public QuickSortCollector(final Comparator<? super T> comparator) {
		super(comparator);
		postponed=new Stack<Range>();
	}
	
	private static class Stack<V extends Range> {

		private final List<V> list=new LinkedList<V>();

		public void push(V item) {
			if(item.length()>=2) {
				list.add(0,item);				
			}
		}
		
		public V pop() {
			if(list.isEmpty()) throw new RuntimeException("logic error: range stack shouldn't be empty");
			return list.remove(0);
		}
		
		public boolean isEmpty() {
			return list.isEmpty();
		}
		
	}
	
	private static class Range {//immutable class for range
		
		private final int left, right;
		
		public Range(final int left, final int right){
			this.left=left;
			this.right=right;
		}
		
		public int getLeft(){
			return left;
		}
		
		public int getRight(){
			return right;
		}
		
		public int length() {
			return right-left+1;
		}
		
	}
	
	private void splitRange(final List<T> result){
		
			final Range range=postponed.pop();//take another range from stack
		
			//select divisor
			final int divisorIndex = (range.getLeft()+range.getRight())/2;
			T divisor=result.get(divisorIndex);
			
			int leftCandidate=range.getLeft(), rightCandidate=range.getRight();
			do{
			
				//seek for greater value in left subrange
				while(leftCandidate<=rightCandidate && getComparator().compare(result.get(leftCandidate),divisor)<=0) leftCandidate++;
				
				//seek for lesser value in right subrange
				while(rightCandidate>=leftCandidate && getComparator().compare(result.get(rightCandidate),divisor)>0) rightCandidate--;
				
				//swap them so that lesser value be placed left and greater value be placed right
				if(leftCandidate<rightCandidate) {
					swap(result, leftCandidate, rightCandidate);
					leftCandidate++; rightCandidate--;//step further right/left
				}
			
			}while(leftCandidate<=rightCandidate);
			
			//save pair of subranges for next iteration
			if(range.getRight()==leftCandidate-1) {//all numbers less or equal to divisor
				swap(result,divisorIndex,range.getRight());//move divisor to the rightmost position and thus exclude it from next range to process
				postponed.push(new Range(range.getLeft(),range.getRight()-1));//save rest to process later
			}else {
				postponed.push(new Range(range.getLeft(),leftCandidate-1));//range actually was split, save left part
			}

			if(range.getLeft()==leftCandidate) {//do same check for the right part
				swap(result,divisorIndex,range.getLeft());
				postponed.push(new Range(range.getLeft()+1,range.getRight()));								
			}else {
				postponed.push(new Range(leftCandidate,range.getRight()));								
			}

	}

	private void swap(final List<T> result, int first, int second) {
		T loc=result.get(first);
		result.set(first,result.get(second));
		result.set(second,loc);
	}

	@Override
	public List<T> getSorted(List<T> list) {
		List<T> result=new ArrayList<T>(list);
		
		postponed.push(new Range(0,result.size()-1));//very first range encompasses all the list to be sorted
		do {
			splitRange(result);//fetch range and split it in two
		}while(!postponed.isEmpty());//should be at least one range left in stack to proceed

		return result;
	}
	
}
