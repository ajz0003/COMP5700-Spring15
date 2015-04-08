package pkg.order;

/**
 * OrderBookTest class for CA05
 * Created on: 4/7/15
 * 
 * Jared Brown & Jonathan Hart (Pair Programming)
 */

import static org.junit.Assert.*;

import org.junit.Test;

import pkg.exception.StockMarketExpection;
import pkg.market.Market;
import pkg.market.api.IPO;
import pkg.trader.Trader;

public class OrderBookTest {

	@Test
	public void testOrderBook() {
		Market m = new Market("DOW");
		OrderBook orderbook = new OrderBook(m);
		
		assertTrue(OrderBook.class.isInstance(orderbook));
	}

	@Test
	public void testAddBuyToOrderBook() {
		Trader trader = new Trader("Joe", 400.0);
		BuyOrder order = new BuyOrder("TWTR", 3, 98.76, trader);
		
		Market m = new Market("DOW");
		OrderBook orderbook = new OrderBook(m);
		
		orderbook.addToOrderBook(order);
		
		assertTrue(orderbook.buyOrders.containsKey(order.getStockSymbol()));
	}
	
	@Test
	public void testAddBuyToOrderBookTwice() {
		Trader trader = new Trader("Joe", 400.0);
		BuyOrder order = new BuyOrder("TWTR", 3, 98.76, trader);
		
		Market m = new Market("DOW");
		OrderBook orderbook = new OrderBook(m);
		
		orderbook.addToOrderBook(order);
		
		Trader trader2 = new Trader("Jake", 200.0);
		BuyOrder order2 = new BuyOrder("TWTR", 1, 98.76, trader2);
		
		orderbook.addToOrderBook(order2);
		
		assertTrue(orderbook.buyOrders.containsKey(order.getStockSymbol()));
	}
	
	@Test
	public void testAddSellToOrderBook() {
		Market m = new Market("DOW");
		IPO.enterNewStock(m, "TWTR", "Twitter Inc.", 47.88);
		OrderBook orderbook = new OrderBook(m);
		
		Trader trader = new Trader("Joe", 40000.0);
		try {
			trader.buyFromBank(m, "TWTR", 20);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		SellOrder order = new SellOrder("TWTR", 2, 120.33, trader);
		
		orderbook.addToOrderBook(order);
		
		assertTrue(orderbook.sellOrders.containsKey(order.getStockSymbol()));
	}
	
	@Test
	public void testAddSellToOrderBookTwice() {
		Market m = new Market("DOW");
		IPO.enterNewStock(m, "TWTR", "Twitter Inc.", 47.88);
		OrderBook orderbook = new OrderBook(m);
		
		Trader trader = new Trader("Joe", 40000.0);
		try {
			trader.buyFromBank(m, "TWTR", 20);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		SellOrder order = new SellOrder("TWTR", 2, 120.33, trader);
		
		orderbook.addToOrderBook(order);
		
		Trader trader2 = new Trader("Jake", 2000.0);
		try {
			trader2.buyFromBank(m, "TWTR", 1);
		} catch (StockMarketExpection e) {
			e.printStackTrace();
		}
		
		SellOrder order2 = new SellOrder("TWTR", 1, 180.22, trader);
		
		orderbook.addToOrderBook(order2);
		
		assertTrue(orderbook.sellOrders.containsKey(order.getStockSymbol()));
	}

}
