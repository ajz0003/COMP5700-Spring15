package pkg.order;

/**
 * BuyOrder class for CA05
 * Modified on: 4/2/15
 * 
 * Jared Brown & Jonathan Hart (Pair Programming)
 */

import pkg.exception.StockMarketExpection;
import pkg.trader.Trader;

public class BuyOrder extends Order {

	public BuyOrder(String stockSymbol, int size, double price, Trader trader) {
		// Create a new buy order
		this.stockSymbol = stockSymbol;
		this.size = size;
		this.price = price;
		this.trader = trader;
		this.orderNumber = Order.getNextOrderNumber();
	}

	public BuyOrder(String stockSymbol, int size, boolean isMarketOrder,
			Trader trader) throws StockMarketExpection {
		// Create a new buy order which is a market order
		this.stockSymbol = stockSymbol;
		this.size = size;
		this.trader = trader;
		
		// Set the price to 0.0, Set isMarketOrder attribute to true
		this.price = 0.0;
		this.isMarketOrder = true;
		
		// If this is called with isMarketOrder == false, throw an exception
		// that an order has been placed without a valid price.
		if (!isMarketOrder) {
			throw new StockMarketExpection("BuyOrder placed without a valid price.");
		}
		this.orderNumber = Order.getNextOrderNumber();
	}

	public void printOrder() {
		System.out.println("Stock: " + stockSymbol + " $" + price + " x "
				+ size + " (Buy)");
	}

}
