package action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Transaction implements IAction {

	private ArrayList<IAction> _actions = new ArrayList<IAction>();

	public void add(IAction action){
		_actions.add(action);
	}
	
	public Object execute() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		for (IAction action : _actions)
			action.execute();

		return null;
	}

	public Object[] getArguments() {
		return null;
	}

	public Method getMethod() {
		return null;
	}

	public Object getTarget() {
		return null;
	}

	public Object undo() throws ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		for (int i = _actions.size() -1; i >= 0; i--)
			_actions.get(i).undo();
		
		return null;
	}

	public boolean wasExecuted() {
		return true;
	}

}
