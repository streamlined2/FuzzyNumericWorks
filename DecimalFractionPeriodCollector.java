import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class DecimalFractionPeriodCollector<T extends Integer> implements Collector<T, List<T>, StringBuilder> {

	@Override
	public Supplier<List<T>> supplier() {
		return LinkedList::new;
	}

	@Override
	public BiConsumer<List<T>,T> accumulator() {
		return List::add;
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (x,y)->{ x.addAll(y); return x;};
	}

	@Override
	public Set<Collector.Characteristics> characteristics() {
		return Collections.emptySet();
	}

	@Override
	public Function<List<T>,StringBuilder> finisher() {	
		return (source)->{
			if(source.size()<2) throw new RuntimeException("at least two numbers should be provided to compute decimal fraction period");
			
			List<T> digits=new LinkedList<T>();
			List<T> remainders=new ArrayList(50);

			int dividend=source.get(0).intValue();
			int divisor=source.get(1).intValue();
			if(dividend>divisor) {
				dividend=dividend%divisor;
			}
			int remainder;
			do {
				digits.add((T)Integer.valueOf(Integer.divideUnsigned(dividend, divisor)));
				remainder=Integer.remainderUnsigned(dividend,divisor);
				if(remainders.contains(remainder)) break;//stop if same remainder already has occurred
				remainders.add((T)Integer.valueOf(remainder));
				dividend=remainder*10;
			}while(dividend>0);
			
			int startIndex=remainders.indexOf(remainder);
			StringBuilder fraction=new StringBuilder();
			if(dividend>0 && startIndex>=0) {
				fraction.append(digits.subList(startIndex+1, digits.size()));
			}
			return fraction;
		};
	}

}
