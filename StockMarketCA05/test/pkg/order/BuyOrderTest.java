package pkg.order;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pkg.exception.StockMarketExpection;
import pkg.trader.Trader;

public class BuyOrderTest {

	private Trader trader;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
    public void setUp() {
		trader = new Trader("Joe", 400.0);
		
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void tearDown() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testPrintOrder() {
		BuyOrder order = new BuyOrder("TWTR", 30, 98.76, trader);
		order.printOrder();
		
		assertEquals("Stock: TWTR $98.76 x 30 (Buy)\n", outContent.toString());

	}

	@Test
	public void testBuyOrderStringIntDoubleTrader() {
		BuyOrder order = new BuyOrder("SBUX", 10, 33.33, trader);
		
		assertEquals(order.stockSymbol, "SBUX");
		assertEquals(order.size, 10);
		assertEquals(order.price, 33.33, .01);
		assertEquals(order.trader, trader);
	}

	@Test
	public void testBuyMarketOrder() {
		try {
			BuyOrder order = new BuyOrder("SBUX", 10, true, trader);
			assertEquals(order.stockSymbol, "SBUX");
			assertEquals(order.size, 10);
			assertEquals(order.price, 0.0, .0001);
			assertTrue(order.isMarketOrder);
			assertEquals(order.trader, trader);
			
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBuyMarketOrderFalse() {
		try {
			@SuppressWarnings("unused")
			BuyOrder order = new BuyOrder("SBUX", 10, false, trader);
			fail("Expected StockMarketExpection here");
		} catch (StockMarketExpection e) {
			// StockMarketExpection caught
		}
		
	}

}
