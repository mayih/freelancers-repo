package il.bruzn.freelancers.Model;

import java.util.ArrayList;

/**
 * Created by Yair on 02/12/2014.
 */
public interface CRUD<T> {

	// Create
	public void	add(T entry);

	// Read
	public T	selectById(int Id);
	public	ArrayList<T> selectAll();
	public	ArrayList<T> selectBy(String field, String value);

	// Update
	public void	update(T entry, int id);

	//Delete
	public void delete(T entry, int id);
}