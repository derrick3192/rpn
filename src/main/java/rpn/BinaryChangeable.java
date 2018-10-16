package rpn;

import java.util.List;

public class BinaryChangeable implements Changeable {

	List<String> ev; 
	String element1;
	String element2;
	
	public BinaryChangeable(List<String> ev, String element1, String element2) {
		super();
		this.ev = ev;
		this.element1 = element1;
		this.element2 = element2;
	}

	@Override
	public void undo() {
		ev.remove(ev.size() - 1);
		ev.add(element1);
		ev.add(element2);
	}
	
}
