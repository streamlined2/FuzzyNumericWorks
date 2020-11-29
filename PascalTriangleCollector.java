import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class DimensionHolder {
	private Integer value;
	
	void setValue(Integer value){
		this.value=value;
	}
	
	Integer getValue() {
		return value;
	}
}

class Triangle {
	private static final Integer ONE=Integer.valueOf(1);
	private static int NUMBER_WIDTH=10;
	
	private Integer[][] pyramid;
	
	private void build() {
		//allocate and initialize top of a triangle
		pyramid[0]=new Integer[1];
		pyramid[0][0]=ONE;
		
		//allocate every next triangle row and compute values 
		for(int row=1;row<pyramid.length;row++) {
			pyramid[row]=new Integer[row+1];
			//extreme positions held by 1
			pyramid[row][0]=ONE;
			pyramid[row][pyramid[row].length-1]=ONE;
			//fill in rest of the row
			for(int k=1;k<pyramid[row].length-1;k++) {
				pyramid[row][k]=pyramid[row-1][k-1]+pyramid[row-1][k];
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder b=new StringBuilder();
		b.append('\n');
		for(int row=0;row<pyramid.length;row++) {
			for(int k=0;k<Math.round((pyramid.length-pyramid[row].length)*NUMBER_WIDTH/2f);k++) b.append(' ');//pad left
			for(int k=0;k<pyramid[row].length;k++) {//output row
				b.append(String.format("%"+NUMBER_WIDTH+"d",pyramid[row][k]));
			}
			b.append("\n");
		}
		return b.toString();
	}
	
	Triangle(DimensionHolder holder){
		if(holder.getValue()<=0) throw new RuntimeException("incorrect triangle dimension");
		pyramid=new Integer[holder.getValue()][];
		build();
	}
	
}

public class PascalTriangleCollector implements Collector<Integer, DimensionHolder, Triangle> {
	
	@Override
	public Supplier<DimensionHolder> supplier() {
		return DimensionHolder::new;
	}

	@Override
	public BiConsumer<DimensionHolder, Integer> accumulator() {
		return DimensionHolder::setValue;
	}

	@Override
	public BinaryOperator<DimensionHolder> combiner() {
		return (x,y)->x;
	}

	@Override
	public Function<DimensionHolder, Triangle> finisher() {
		return (x)->new Triangle(x);
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

}
