package il.bruzn.freelancers.basic;

/**
 * Created by Yair on 06/01/2015.
 */
public class Sleep {
	public static long sleep(long millisencond){
		try {
			Thread.sleep(millisencond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}
}
