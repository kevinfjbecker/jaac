package action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface IAction {

	public abstract Object execute() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException;

	public abstract Object[] getArguments();

	public abstract Method getMethod();

	public abstract Object getTarget();

	public abstract Object undo() throws ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	public abstract boolean wasExecuted();

}