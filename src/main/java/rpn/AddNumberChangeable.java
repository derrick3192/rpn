package rpn;

import java.util.List;

public class AddNumberChangeable implements Changeable {

	List<String> ev; 
	
	public AddNumberChangeable(List<String> ev) {
		super();
		this.ev = ev;
	}
	
	@Override
	public void undo() {
		ev.remove(ev.size() - 1);
	}
	
}
