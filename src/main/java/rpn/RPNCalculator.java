package rpn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPNCalculator extends ArrayList<Double> {
	
	private static final long serialVersionUID = 1L;

	public static final String CLEAR = "clear";
	public static final String UNDO = "undo";
	public static final String SQRT = "sqrt";
	public static final List<String> BINARYOPERAIONS = Arrays.asList(new String[]{"+", "-", "*", "/"});
	
	public static final List<String> ALLOPERATIONS = new ArrayList<>();
	static {
		ALLOPERATIONS.add(CLEAR);
		ALLOPERATIONS.add(UNDO);
		ALLOPERATIONS.add(SQRT);
		ALLOPERATIONS.addAll(BINARYOPERAIONS);
	}
	
	public static final List<String> UNIARYOPERATIONS = new ArrayList<>();
	static {
		UNIARYOPERATIONS.add(SQRT);
	}
	
	/** true if the first execute **/
	boolean firstExecut = false;
	
	/** stores the changeables for undo functionality **/
	List<Changeable> undos = new ArrayList<Changeable>();
	
	
	/** exec a String reverse polish notation **/
	public void exec(String expr) {
		List<String> array = Arrays.asList(expr.trim().split("[ \t]+"));
		HashMap<String, Integer> operatorOccurances = new HashMap<String, Integer>();
		ALLOPERATIONS.forEach(o -> operatorOccurances.put(o, 0));
		exec(array, expr, operatorOccurances);
	}
	
	public void exec(List<String> array, String expr, Map<String, Integer> operatorOccurances) {
		eval(new ArrayList<>(this), new ArrayList<>(array), expr, operatorOccurances);
	}

	
	public static int ordinalIndexOf(String str, String substr, int n) {
	    int pos = str.indexOf(substr);
	    while (--n > 0 && pos != -1)
	        pos = str.indexOf(substr, pos + 1);
	    return pos;
	}
	
	public static void printOperatorErrorMessage(String t, String expr, Map<String, Integer> operatorOccurances) {
		int pos = ordinalIndexOf(expr, t, operatorOccurances.get(t)) + 1;
		System.out.println("operator "+t+" (position: "+pos+"): insucient parameters");
	}
	
	/**
	 * @param ev - current state
	 * @param res - symbols to execute
	 * @param expr - expression
	 */
	public void eval(List<Double> ev, List<String> res, String expr, Map<String, Integer> operatorOccurances) {
		
		if (res.size() == 0){
			this.clear();
			this.addAll(ev);
			return;
		}
		
		String t = res.remove(0);
		
		if (ALLOPERATIONS.contains(t)) {
			operatorOccurances.put(t, operatorOccurances.get(t) + 1);
		}
		
		if (isDouble(t)) {
			ev.add(Double.parseDouble(t));
			undos.add(new AddNumberChangeable());
		} else if (t.equals(CLEAR)) {
			ev.clear();
			undos.add(new AddNumberChangeable());
		} else if (UNIARYOPERATIONS.contains(t) && ev.size() < 1 ||
				    BINARYOPERAIONS.contains(t) && ev.size() < 2) {
			printOperatorErrorMessage(t, expr, operatorOccurances);
			eval(ev, new ArrayList<>(), expr, operatorOccurances);
			return;
		} else if (UNIARYOPERATIONS.contains(t)) {
			double y = ev.remove(ev.size() - 1);
			ev.add(Math.sqrt(y));
			undos.add(new UniaryChangeable(y));
		} else if (BINARYOPERAIONS.contains(t)) {

			double y = ev.remove(ev.size() - 1);
			double x = ev.remove(ev.size() - 1);
			
			double result = 0.0;
			switch (t) {
			case "+":
				result = x + y;
				break;
			case "-":
				result = x - y;
				break;
			case "*":
				result = x * y;
				break;
			case "/":
				result = x / y;
				break;
			default:
				throw new RuntimeException("does not support operation");
			}
			undos.add(new BinaryChangeable(x, y));
			ev.add(result);
		} else if (UNDO.equals(t)) {
			if (undos.size() == 0) {
				System.out.println("cannot undo - skipping");
			}
			Changeable undo = undos.remove(undos.size() - 1);
			undo.undo(ev);
		} else {
			System.out.println("Not supported! Ignoring: "+t);
		}
		
		eval(ev, res, expr, operatorOccurances);
	}
	
	public static boolean isDouble(String number) {
		try
		{
		  Double.parseDouble(number);
		  return true;
		}
		catch(NumberFormatException e)
		{
		  return false;
		}
	}
	
	public static void main(String[] args) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		RPNCalculator rpn = new RPNCalculator();
		for (;;) {
			String s = in.readLine();
			if (s == null)
				break;

			rpn.exec(s);
			String rpnStr = rpn.toString();
			rpnStr = rpnStr.substring(1, rpnStr.length()-1);
			System.out.println("stack: "+rpnStr);
		}
	}
	
}