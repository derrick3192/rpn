package rpn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RPNCalculator extends ArrayList<String> {
	
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
	
	public void exec(String expr) {
		List<String> array = Arrays.asList(expr.trim().split("[ \t]+"));
		exec(array, expr);
	}
	
	public void exec(List<String> array, String expr) {
		this.addAll(array);
		eval(new ArrayList<>(), this, expr);
	}
	

	// if false all operations have been completed
	public void eval(List<String> ev, List<String> res, String expr) {
		
		List<String> checkPoint = new ArrayList<String>(ev);
		
		if (res.size() == 0){
			this.clear();
			this.addAll(ev);
			return;
		}
		
		String t = res.remove(0);
		
		if (isDouble(t)) {
			ev.add(t);
		} else if (t.equals(CLEAR)) {
			ev.clear();
		} else if (t.equals(SQRT)) {
			double y = Double.parseDouble(ev.remove(ev.size() - 1));
			res.add(Math.sqrt(y)+"");
		} else if ( BINARYOPERAIONS.contains(t) && ev.size() < 2) {
			System.out.println("operator "+t+" (position: TODO): insucient parameters"); // TODO position error
			eval(ev, new ArrayList<>(), expr);
			return;
		} else if (BINARYOPERAIONS.contains(t)) {

			double y = Double.parseDouble(ev.remove(ev.size() - 1));
			double x = Double.parseDouble(ev.remove(ev.size() - 1));
			
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
			
			ev.add(result+"");
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