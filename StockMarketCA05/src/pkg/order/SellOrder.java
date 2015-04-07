package pkg.order;

import pkg.exception.StockMarketExpection;
import pkg.trader.Trader;

public class SellOrder extends Order {
	public SellOrder(String stockSymbol, int size, double price, Trader trader) {
		// Create a new sell order
		this.stockSymbol = stockSymbol;
		this.size = size;
		this.price = price;
		this.trader = trader;
		this.orderNumber = Order.getNextOrderNumber();
	}

	public SellOrder(String stockSymbol, int size, boolean isMarketOrder,
			Trader trader) throws StockMarketExpection {
		// Create a new sell order which is a market order
		this.stockSymbol = stockSymbol;
		this.size = size;
		this.trader = trader;
		
		// Set the price to 0.0, Set isMarketOrder attribute to true
		this.price = 0.0;
		this.isMarketOrder = true;
		

		// If this is called with isMarketOrder == false, throw an exception
		// that an order has been placed without a valid price.
		if (!isMarketOrder) {
			throw new StockMarketExpection("SellOrder placed without a valid price.");
		}
		this.orderNumber = Order.getNextOrderNumber();
	}

	public void printOrder() {
		System.out.println("Stock: " + stockSymbol + " $" + price + " x "
				+ size + " (Sell)");
	}
}
