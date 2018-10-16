package rpn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RPNCalculator extends ArrayList<String> {
	
	boolean isFirstExecut = false;
	
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

	List<Changeable> undos = new ArrayList<Changeable>();
	
	public void exec(String expr) {
		List<String> array = Arrays.asList(expr.trim().split("[ \t]+"));
		exec(array, expr);
	}
	
	public void exec(List<String> array, String expr) {
		if (isFirstExecut) {
			isFirstExecut = false;
			eval(new ArrayList<>(), new ArrayList<>(array), expr);
		} else {
			eval(new ArrayList<>(this), new ArrayList<>(array), expr);
		}
	}

	public void eval(List<String> ev, List<String> res, String expr) {
		
		if (res.size() == 0){
			this.clear();
			this.addAll(ev);
			return;
		}
		
		String t = res.remove(0);
		
		if (isDouble(t)) {
			ev.add(t);
			undos.add(new AddNumberChangeable());
		} else if (t.equals(CLEAR)) {
			ev.clear();
			undos.add(new AddNumberChangeable());
		} else if (t.equals(SQRT)) {
			String y = ev.remove(ev.size() - 1);
			res.add(Math.sqrt(Double.parseDouble(y))+"");
			undos.add(new UniaryChangeable(y));
		} else if ( BINARYOPERAIONS.contains(t) && ev.size() < 2) {
			System.out.println("operator "+t+" (position: TODO): insucient parameters"); // TODO position error
			eval(ev, new ArrayList<>(), expr);
			return;
		} else if (BINARYOPERAIONS.contains(t)) {

			String yStr = ev.remove(ev.size() - 1);
			String xStr = ev.remove(ev.size() - 1);
			
			double y = Double.parseDouble(yStr);
			double x = Double.parseDouble(xStr);
			
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
			undos.add(new BinaryChangeable(xStr, yStr));
			ev.add(result+"");
		} else if (UNDO.equals(t)) {
			if (undos.size() == 0) {
				System.out.println("cannot undo - skipping");
			}
			Changeable undo = undos.remove(undos.size() - 1);
			undo.undo(ev);
		} else {
			System.out.println("Not supported! Ignoring: "+t);
		}
		
		eval(ev, res, expr);
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
	
}