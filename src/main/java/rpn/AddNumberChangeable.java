package rpn;

import java.util.List;

/**
 * undoes an Add Number
 */
public class AddNumberChangeable implements Changeable {

	@Override
	public void undo(List<Double> ev) {
		ev.remove(ev.size() - 1);
	}
	
}
