package rpn;

import java.util.List;

public class UniaryChangeable implements Changeable {
	
	List<Double> ev; 
	Double element1;
	
	public UniaryChangeable(Double element1) {
		super();
		this.element1 = element1;
	}

	@Override
	public void undo(List<Double> ev) {
		ev.remove(ev.size() - 1);
		ev.add(element1);
	}
}
