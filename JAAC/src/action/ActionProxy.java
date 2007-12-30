package action;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import action.Action;
import action.Executor;
import action.Undoable;

public class ActionProxy implements InvocationHandler {

	private Executor _executor;

	private Object _object;

	public ActionProxy(Object object) {
		_object = object;
	}

	public Object invoke(Object wrapper, Method method, Object[] arguments)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException, NoSuchMethodException {

		if (method.isAnnotationPresent(Undoable.class)) {

			return _executor.execute(new Action(_object, method, arguments));

		}
		return method.invoke(_object, arguments);

	}

	public void setActionHandler(Executor actionHandler) {
		_executor = actionHandler;
	}
}
