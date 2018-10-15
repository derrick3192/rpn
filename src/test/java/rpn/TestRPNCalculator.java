package rpn;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestRPNCalculator {

	RPNCalculator calculator;
	
	@Before
	public void init() {
		calculator = new RPNCalculator();
	}
	
	public static void assertEqualCollections(Stack<String> expected, List<Object> actual) {
		if (expected.size() != actual.size()) fail();
		
		for (int i = 0; i < expected.size(); i++) {
			String exp = expected.get(i).toString();
			String act = actual.get(i).toString();
			
			if (RPNCalculator.ALLOPERATIONS.contains(exp) || RPNCalculator.ALLOPERATIONS.contains(act)) {
				assertEquals(exp, act);
			} else {
				double expD = Double.parseDouble(exp);
				double actD = Double.parseDouble(act);
				assertEquals(expD, actD, 0.00000001);
			}
			
			
		}
	}
	
	@Test
	public void example1() {
		calculator.exec("5 2");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{5, 2}));
	}
	
	@Test
	public void example2() {
		calculator.exec("2 sqrt");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{1.4142135623}));
		calculator.exec("clear 9 sqrt");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{3}));
	}
	
	@Test
	public void example3() {
		calculator.exec("5 2 -");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{3}));
		calculator.exec("3 -");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{0}));
		calculator.exec("clear");
		assertEquals(0, calculator.size());
	}
	
	@Ignore
	@Test
	public void example4() {
		calculator.exec("5 4 3 2");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{5,4,3,2}));
		calculator.exec("undo undo *");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{20}));
		calculator.exec("5 *");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{100}));
		calculator.exec("undo");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{20,5}));
	}
	
	@Test
	public void example5() {
		calculator.exec("7 12 2 /");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{7, 6}));
		calculator.exec("*");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{42}));
		calculator.exec("4 /");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{10.5}));
	}
	
	@Test
	public void example6() {
		calculator.exec("1 2 3 4 5");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{1, 2, 3, 4, 5}));
		calculator.exec("*");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{1, 2, 3, 20}));
		calculator.exec("clear 3 4 -");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{-1}));
	}
	
	@Test
	public void example7() {
		calculator.exec("1 2 3 4 5");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{1, 2, 3, 4, 5}));
		calculator.exec("* * * *");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{120}));
	}
	
	
	
	//1 2 3 * 5 + * * 6 5
	//1 6 5 + * * 6 5
	//1 11 * * 6 5
	//11 * 6 5
	//@Ignore
	@Test
	public void example8() {
		calculator.exec("1 2 3 * 5 + * * 6 5");
		assertEqualCollections(calculator, Arrays.asList(new Object[]{11}));
	}
}
