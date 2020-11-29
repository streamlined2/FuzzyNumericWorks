import java.util.function.Predicate;

public final class Palindrome<T extends Number> implements Predicate<T> {

	@Override
	public boolean test(T number) {
		String s=number.toString();
		for(int left=0, right=s.length()-1; left<=right; left++, right--) {//scan string representation right/left from start/end
			if(s.charAt(left)!=s.charAt(right)) return false;//stop on first occasion of different characters pair
		}
		return true;//otherwise all pairs have identical characters on both sides
	}

}
