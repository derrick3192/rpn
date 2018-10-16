package rpn;

import java.util.ArrayList;
import java.util.List;

public class ClearChangeable implements Changeable {

	List<String> backup;

	public ClearChangeable(List<String> ev, List<String> backup) {
		super();
		this.backup = new ArrayList<>(backup);
	}

	@Override
	public void undo(List<String> ev) {
		ev.addAll(backup);
	}
}

