package rpn;

import java.util.List;

public class UniaryChangeable implements Changeable {
	
	List<String> ev; 
	String element1;
	
	public UniaryChangeable(List<String> ev, String element1) {
		super();
		this.ev = ev;
		this.element1 = element1;
	}

	@Override
	public void undo() {
		ev.remove(ev.size() - 1);
		ev.add(element1);
	}
}
