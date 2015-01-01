package il.bruzn.freelancers.basic;

/**
 * Created by Yair on 31/12/2014.
 */
public interface ToRun<typeToReturn> {
	public typeToReturn run(Object... parameters);
}
