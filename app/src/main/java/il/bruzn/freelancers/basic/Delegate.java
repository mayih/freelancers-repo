package il.bruzn.freelancers.basic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Yair on 27/12/2014.
 */
public class Delegate<typeReturn> {
	private Object _instance;
	private Method _method;
	private Object[] _arguments;

	// Contructors ---
	public Delegate(Object instance, Method method, Object... arguments) {
		_method = method;
		_instance = instance;
		_arguments = arguments;
	}
	public Delegate(Object instance, String methodName, Object... arguments) throws NoSuchMethodException {
		_instance = instance;

		Class[] argumentsClass = new Class[arguments.length];
		for (int i = 0; i < arguments.length; i++)
			argumentsClass[i] = arguments[i].getClass();
		_method = instance.getClass().getMethod(methodName, argumentsClass);

		_arguments = arguments;
	}

	// Execute method ---
	public typeReturn execute() throws InvocationTargetException, IllegalAccessException {
		return (typeReturn) _method.invoke(_instance, _arguments);
	}

	// Getters & setters ---
	public Object getInstance() {
		return _instance;
	}
	public Method getMethod() {
		return _method;
	}
	public Object[] getArguments() {
		return _arguments;
	}

	public Delegate setInstance(Object instance) {
		_instance = instance;
		return this;
	}
	public Delegate setMethod(Method method) {
		_method = method;
		return this;
	}
	public Delegate setArguments(Object[] arguments) {
		_arguments = arguments;
		return this;
	}
}
