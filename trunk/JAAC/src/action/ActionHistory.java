package action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ActionHistory implements Executor {

	private ArrayList<IAction> _history = new ArrayList<IAction>();

	private int _placeHolder = -1;

	private Executor _next;

	private Transaction _openTransaction;

	public void add(Executor executor) {
		_next = executor;
	}

	public boolean atEnd() {
		return _placeHolder == _history.size() - 1;
	}

	public boolean atStart() {
		return _placeHolder == -1;
	}

	public void closeTransaction() {
		_history.add(_openTransaction);
		_placeHolder++;
		_openTransaction = null;

	}

	public Object execute(Action action) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException {

		if (_placeHolder < _history.size() - 1)
			for (int i = _history.size() - 1; i > _placeHolder; i--)
				_history.remove(i);

		if (_openTransaction != null)
			_openTransaction.add(action);
		else {
			_history.add(action);
			_placeHolder++;
		}

		if (_next != null)
			return _next.execute(action);
		return action.execute();

	}

	public int length() {
		return _history.size();
	}

	public void redo() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		if (_placeHolder < _history.size() - 1)
			_history.get(++_placeHolder).execute();
	}

	public String toString() {
		return _history.toString();
	}

	public void undo() throws IllegalArgumentException, SecurityException,
			ClassNotFoundException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		if (_placeHolder >= 0)
			_history.get(_placeHolder--).undo();
	}

	public void openTransaction() {
		_openTransaction = new Transaction();
	}

}