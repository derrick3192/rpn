package rpn;

import java.util.ArrayList;
import java.util.List;


/**
 * undoes a clear
 */
public class ClearChangeable implements Changeable {

	List<Double> backup;

	public ClearChangeable(List<Double> ev, List<Double> backup) {
		super();
		this.backup = new ArrayList<>(backup);
	}

	@Override
	public void undo(List<Double> ev) {
		ev.addAll(backup);
	}
}

