package pkg.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import pkg.market.Market;

public class OrderBook {
	Market m;
	HashMap<String, ArrayList<Order>> buyOrders;
	HashMap<String, ArrayList<Order>> sellOrders;

	public OrderBook(Market m) {
		this.m = m;
		buyOrders = new HashMap<String, ArrayList<Order>>();
		sellOrders = new HashMap<String, ArrayList<Order>>();
	}

	public void addToOrderBook(Order order) {
		// Populate the buyOrders and sellOrders data structures, whichever
		// appropriate
		if (order instanceof BuyOrder) {
			// get arraylist of orders for stock
			ArrayList<Order> orders;
			if (buyOrders.containsKey(order.getStockSymbol())) {
				orders = buyOrders.get(order.getStockSymbol());
			} else { // if none exists, create a new arraylist
				orders = new ArrayList<Order>();
			}
			
			// add order to orders arraylist for stock then update buyOrders structure
			orders.add(order);
			buyOrders.put(order.getStockSymbol(), orders);
		} else {
			// get arraylist of orders for stock
			ArrayList<Order> orders;
			if (sellOrders.containsKey(order.getStockSymbol())) {
				orders = sellOrders.get(order.getStockSymbol());
			} else { // if none exists, create a new arraylist
				orders = new ArrayList<Order>();
			}
			
			// add order to orders arraylist for stock then update sellOrders structure
			orders.add(order);
			sellOrders.put(order.getStockSymbol(), orders);
		}
		
	}

	public void trade() {
		// Complete the trading.
		// 1. Follow and create the orderbook data representation (see spec)
		// 2. Find the matching price
		// 3. Update the stocks price in the market using the PriceSetter.
		// Note that PriceSetter follows the Observer pattern. Use the pattern.
		// 4. Remove the traded orders from the orderbook
		// 5. Delegate to trader that the trade has been made, so that the
		// trader's orders can be placed to his possession (a trader's position
		// is the stocks he owns)
		// (Add other methods as necessary)
		
		for (String stock : sellOrders.keySet()) {
			if (buyOrders.containsKey(stock)) {
				ArrayList<Order> buying = buyOrders.get(stock);
				ArrayList<Order> selling = sellOrders.get(stock);
				TreeMap<Double, ArrayList<Order>> sortedOrders = new TreeMap<Double, ArrayList<Order>>();
				ArrayList<Order> orders;
				
				// populate sortedOrders with both buy and sell orders for each price
				for (Order buyOrder : buying) {
					// get arraylist of orders for price
					if (sortedOrders.containsKey(buyOrder.getPrice())) {
						orders = sortedOrders.get(buyOrder.getPrice());
					} else { // if none exists, create a new arraylist
						orders = new ArrayList<Order>();
					}
					orders.add(buyOrder);
					sortedOrders.put(buyOrder.getPrice(), orders);
				}
				for (Order sellOrder : selling) {
					// get arraylist of orders for price
					if (sortedOrders.containsKey(sellOrder.getPrice())) {
						orders = sortedOrders.get(sellOrder.getPrice());
					} else { // if none exists, create a new arraylist
						orders = new ArrayList<Order>();
					}
					orders.add(sellOrder);
					sortedOrders.put(sellOrder.getPrice(), orders);
				}
				
				// construct cumulative least favorably price list
				
				// find matching price
				

			}
		}
	}

}
