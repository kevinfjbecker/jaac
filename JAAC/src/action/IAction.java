package action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * IAction defines the base behavior of an action: to be excuted, undone and for
 * method and target.
 * 
 */
public interface IAction {

	/**
	 * 
	 * Invokes the underlying method. All of the exceptions that can be thrown
	 * by the Method class are propogated up from IAction.
	 * 
	 * @return Object The wrapped return type of the underlying method.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public abstract Object execute() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * Returns the argument from the original method call.
	 * 
	 * @return Object[] Each of the arguments that were passed to the proxy
	 *         wrapper in the method call.
	 */
	public abstract Object[] getArguments();

	/**
	 * 
	 * Returns the method obeject contained by this IAction
	 * 
	 * @return Method The method object associated with this IAction.
	 */
	public abstract Method getMethod();

	/**
	 * 
	 * Returns the target of the method invocation.
	 * 
	 * @return Object The inner object, wrapped by the proxy, which is the targe
	 *         of the method invocation.
	 */
	public abstract Object getTarget();

	/**
	 * 
	 * Invokes the undo method as defined by the Undoable annotaion associate
	 * with underlying Method. All of the exceptions that can be thrown by the
	 * Method class are propogated up from IAction. In addition all of the
	 * exceptions, which can be thrown be getmethod() from the java.lang.Class,
	 * are also passed up from IAction.
	 * 
	 * @return Object The wrapped return type of the underlying method.
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public abstract Object undo() throws ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	/**
	 * Indicates whether this action has been executed. Actions that have never
	 * been executed and those, which have been undone should return false.
	 * Executed and re-executed actions should return true.
	 * 
	 * @return boolean Indicates whether this action has been executed
	 */
	public abstract boolean wasExecuted();

}