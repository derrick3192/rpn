package rpn;

import java.util.List;


public interface Changeable {

	/**
	 * Undoes an action
	 */
	public void undo(List<Double> ev);

}
