package rpn;

import java.util.List;

/**
 * undoes Binary Operation
 */
public class BinaryChangeable implements Changeable {

	Double element1;
	Double element2;
	
	public BinaryChangeable(Double element1, Double element2) {
		super();
		this.element1 = element1;
		this.element2 = element2;
	}

	@Override
	public void undo(List<Double> ev) {
		ev.remove(ev.size() - 1);
		ev.add(element1);
		ev.add(element2);
	}
	
}
