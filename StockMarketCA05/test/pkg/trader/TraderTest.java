package pkg.trader;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pkg.exception.StockMarketExpection;
import pkg.market.Market;
import pkg.market.api.IPO;
import pkg.order.BuyOrder;
import pkg.order.Order;
import pkg.order.OrderType;
import pkg.order.SellOrder;
import pkg.util.OrderUtility;

public class TraderTest {
	
	private Market m;
	private Trader trader1;
	private Trader trader2;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
    public void setUp() {

        m = new Market("NASDAQ");
        IPO.enterNewStock(m, "SBUX", "Starbucks Corp.", 92.86);
		IPO.enterNewStock(m, "TWTR", "Twitter Inc.", 47.88);
		IPO.enterNewStock(m, "VSLR", "Vivint Solar", 16.44);
		IPO.enterNewStock(m, "GILD", "Gilead Sciences", 93.33);
		
		trader1 = new Trader("John", 5000.0);
		trader2 = new Trader("Jake", 50.0);
		
		try {
			trader1.buyFromBank(m, "TWTR", 4);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		
    }
	
	@After
	public void tearDown() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testTraderInitialize() {
		Trader trader = new Trader("Joe", 300.0);
		assertTrue(Trader.class.isInstance(trader));
	}

	@Test
	public void testBuyFromBank() {
		try {
			trader1.buyFromBank(m, "SBUX", 4);
		} catch(StockMarketExpection e) {
			e.printStackTrace();
		}
		
		assertEquals(trader1.cashInHand, 4437.04, 0.01);
	}
	
	@Test
	public void testBuyFromBankFailure() {
		try {
			trader2.buyFromBank(m, "SBUX", 2);
			fail("Expected StockMarketExpection here");
		} catch (StockMarketExpection e) {
			// StockMarketExpection caught
		}
	}

	@Test
	public void testPlaceNewSellOrder() {
		
		//for (Order i : trader1.position)
		    //System.out.println("Order: " + i.getStockSymbol().toString() + " Quantitiy: " + i.getSize());
		try {
			trader1.placeNewOrder(m, "TWTR", 2, 97.0, OrderType.SELL);
		} catch(StockMarketExpection e) {
			e.printStackTrace();
		}
		Order order = new SellOrder("TWTR", 2, 97.0, trader1);
		
		assertTrue(OrderUtility.isAlreadyPresent(trader1.ordersPlaced, order));
		
	}
	
	@Test
	public void testPlaceNewBuyOrder() {

		try {
			trader1.placeNewOrder(m, "SBUX", 2, 99.0, OrderType.BUY);
		} catch(StockMarketExpection e) {
			e.printStackTrace();
		}
		Order order = new BuyOrder("SBUX", 2, 99.0, trader1);
		
		assertTrue(OrderUtility.isAlreadyPresent(trader1.ordersPlaced, order));
		
	}
	
	@Test
	public void testPlaceNewBuyOrderNotEnoughCash() {
		
		try {
			trader2.placeNewOrder(m, "TWTR", 2, 97.0, OrderType.BUY);
			fail("Expected StockMarketExpection here");
		} catch(StockMarketExpection e) {
			// StockMarketExpection caught
		}
		Order order = new SellOrder("TWTR", 2, 97.0, trader1);
		
	}
	
	@Test
	public void testPlaceNewOrderExistingOrder() {
		
		try {
			trader1.placeNewOrder(m, "TWTR", 2, 97.0, OrderType.SELL);
		} catch(StockMarketExpection e) {
			e.printStackTrace();
		}
		try {
			trader1.placeNewOrder(m, "TWTR", 2, 97.0, OrderType.SELL);
			fail("Expected StockMarketExpection here");
		} catch(StockMarketExpection e) {
			// StockMarketExpection caught
		}
		
	}
	
	@Test
	public void testPlaceNewOrderDoesNotOwn() {
		
		try {
			trader1.placeNewOrder(m, "GILD", 2, 97.0, OrderType.SELL);
			fail("Expected StockMarketExpection here");
		} catch(StockMarketExpection e) {
			// StockMarketExpection caught
		}
		
	}
	
	@Test
	public void testPlaceNewOrderDoesNotOwnEnough() {
		
		try {
			trader1.placeNewOrder(m, "TWTR", 10, 97.0, OrderType.SELL);
			fail("Expected StockMarketExpection here");
		} catch(StockMarketExpection e) {
			// StockMarketExpection caught
		}
		
	}

	@Test
	public void testPrintTrader() {
		try {
			trader1.placeNewOrder(m, "SBUX", 2, 40.0, OrderType.BUY);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		trader1.printTrader();
		
		String outputString = "Trader Name: John\n";
		outputString += "=====================\n";
		outputString += "Cash: 4808.48\n";
		outputString += "Stocks Owned: \n";
		outputString += "TWTR\n";
		outputString += "Stocks Desired: \n";
		outputString += "Stock: SBUX $40.0 x 2 (Buy)\n";
		outputString += "+++++++++++++++++++++\n";
		outputString += "+++++++++++++++++++++\n";
		
		assertEquals(outputString, outContent.toString());
		
		//fail("Not yet implemented");
	}

	@Test
	public void testTradePerformedBuyOrder() {
		try {
			trader1.placeNewOrder(m, "VSLR", 2, 14.20, OrderType.BUY);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		
		Order order = new BuyOrder("VSLR", 2, 14.20, trader1);
		try {
			trader1.tradePerformed(order, 95.0);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTradePerformedBuyOrderNotPresent() {
		
		Order order = new BuyOrder("VSLR", 2, 14.20, trader1);
		try {
			trader1.tradePerformed(order, 95.0);
			fail("Expected StockMarketExpection here");
		} catch (StockMarketExpection e) {
			// StockMarketExpection caught
		}
		
	}
	
	@Test
	public void testTradePerformedSellOrder() {
		try {
			trader1.placeNewOrder(m, "TWTR", 2, 14.20, OrderType.SELL);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		Order order = new SellOrder("TWTR", 2, 14.20, trader1);
		try {
			trader1.tradePerformed(order, 95.0);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
	}

}
