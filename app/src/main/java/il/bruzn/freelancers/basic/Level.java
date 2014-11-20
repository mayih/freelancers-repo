package il.bruzn.freelancers.basic;

/**
 * Created by Yair on 20/11/2014.
 */
public enum Level {
	ONE(1), TWO(2) ,THREE(3), FOUR(4), FIVE(5);

	private int _level;
	Level(int level) { _level = level; }
	public int getValue() { return _level; }
}
