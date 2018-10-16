package rpn;

import java.util.List;

public class AddNumberChangeable implements Changeable {

	@Override
	public void undo(List<String> ev) {
		ev.remove(ev.size() - 1);
	}
	
}
