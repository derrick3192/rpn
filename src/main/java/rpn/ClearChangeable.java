package rpn;

import java.util.List;

public class ClearChangeable implements Changeable {

	List<String> ev; 
	List<String> backup;

	public ClearChangeable(List<String> ev, List<String> backup) {
		super();
		this.ev = ev;
		this.backup = backup;
	}

	@Override
	public void undo() {
		ev.addAll(backup);
	}
}

