package rpn;

import java.util.List;

public class BinaryChangeable implements Changeable {

	String element1;
	String element2;
	
	public BinaryChangeable(String element1, String element2) {
		super();
		this.element1 = element1;
		this.element2 = element2;
	}

	@Override
	public void undo(List<String> ev) {
		ev.remove(ev.size() - 1);
		ev.add(element1);
		ev.add(element2);
	}
	
}
