package il.bruzn.freelancers.Module.Entities;

/**
 * Created by Yair on 20/11/2014.
 */
public class Opinion {
	private Level _level;
	private boolean _done;
	private String _text;

	/**
	 * Created by Yair on 20/11/2014.
	 */
	public static enum Level {
		ONE(1), TWO(2) ,THREE(3), FOUR(4), FIVE(5);

		private int _level;
		Level(int level) { _level = level; }
		public int getValue() { return _level; }
	}
}
