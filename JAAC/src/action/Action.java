package action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Action implements IAction {

	private Object[] arguments;

	private boolean executed;

	private Method method;

	private Object returnValue;

	private Object target;

	public Action(Object target, Method method, Object[] arguments) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	public Object execute() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		executed = true;
		return (returnValue = method.invoke(target, arguments));
	}

	public Object[] getArguments(){
		return arguments;
	}
	
	public Method getMethod() {
		return method;
	}

	public Object getTarget() {
		return target;
	}

	public String toString() {
		String s = "";
		s += method.getName() + " (";
		for (int i = 0; i < arguments.length; i++)
			if (i > 0)
				s += ", " + arguments[i];
			else
				s += " " + arguments[i];
		s += " ) : " + returnValue;
		return s;
	}

	public Object undo() throws ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		if (wasExecuted()) {

			Undoable u = method.getAnnotation(Undoable.class);

			Class[] ptypes = method.getParameterTypes();
			Class rtype = method.getReturnType();

			Object[] undoArgs = new Object[u.args().length];
			Class[] undoArgTypes = new Class[u.args().length];

			for (int i = 0; i < undoArgs.length; i++)
				if (u.args()[i].equals("R")) {
					undoArgs[i] = returnValue;
					undoArgTypes[i] = rtype;
				} else {
					undoArgs[i] = arguments[Integer.parseInt(u.args()[i])];
					undoArgTypes[i] = ptypes[Integer.parseInt(u.args()[i])];
				}

			return target.getClass().getMethod(u.undoMethod(), undoArgTypes)
					.invoke(target, undoArgs);

		}

		return null;

	}

	public boolean wasExecuted() {
		return executed;
	}

}
