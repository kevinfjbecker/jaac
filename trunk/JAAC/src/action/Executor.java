package action;

import java.lang.reflect.InvocationTargetException;

public interface Executor {

	Object execute(Action action) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException;

}
