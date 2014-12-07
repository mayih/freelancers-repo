package il.bruzn.freelancers.Module;

/**
 * Created by Yair on 02/12/2014.
 */
public interface CRUD<T> {

	// Create
	public T	add(T entry);

	// Read
	public T[]	selectAll();
	public T	selectById(int Id);
	public T[]	selectBy(String field, String value);

	// Update
	public void	update(T entry);

	//Delete
	public void delete(T entry);
}
