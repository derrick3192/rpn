package rpn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RPNCalculator extends Stack<String> {
	
	private static final long serialVersionUID = 1L;

	public static final String CLEAR = "clear";
	public static final String UNDO = "undo";
	public static final String SQRT = "sqrt";
	public static final List<String> OPERAIONS = Arrays.asList(new String[]{"+", "-", "*", "/"});
	
	
	public static final List<String> ALLOPERATIONS = new ArrayList<>();
	static {
		ALLOPERATIONS.add(CLEAR);
		ALLOPERATIONS.add(UNDO);
		ALLOPERATIONS.add(SQRT);
		ALLOPERATIONS.addAll(OPERAIONS);
	}
	
	public void exec(String expr) {
		List<String> array = Arrays.asList(expr.trim().split("[ \t]+"));
		exec(array);
	}
	
	public boolean handleClear(List<String> array) {
		for (int i = array.size() - 1 ; i >= 0; i--) {
			if (array.get(i).equals(CLEAR)) {
				clear();
				
				int start = i + 1;
				int end = array.size();
				if (end > start) {
					List<String> toExec = array.subList(start, end);
					if (toExec.size() > 0) {
						exec(toExec);
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public void exec(List<String> array) {
		
		if (!handleClear(array)) {
			addAll(array);
		}
		
		if (size() > 1) {
			double t = eval();
			push(t + "");
		}
	}

	public double eval() {
		String tk = pop();

		if (OPERAIONS.contains(tk) && size() < 2) {
			System.out.println("operator "+ tk + "(position: "+peek()+"):" + "insufficient parameters:");
			return Double.parseDouble(pop());
		}
		
		double x, y;
		try {
			x = Double.parseDouble(tk);
		} catch (Exception e) {
			y = eval();

			if (tk.equals("sqrt")) {
				return Math.sqrt(y);
			}
			x = eval();

			switch (tk) {
			case "+":
				return x + y;
			case "-":
				return x - y;
			case "*":
				return x * y;
			case "/":
				return x / y;
			default:
				throw new RuntimeException("does not support operation");
			}
		}
		return x;
	}

	public static void main(String[] args) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		RPNCalculator rpn = new RPNCalculator();
		for (;;) {
			String s = in.readLine();
			if (s == null)
				break;

			rpn.exec(s);
			System.out.println(rpn);
		}

	}
	
}