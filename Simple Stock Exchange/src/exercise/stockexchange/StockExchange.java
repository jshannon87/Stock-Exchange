package exercise.stockexchange;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public final class StockExchange 
{

	public final static StockExchange STOCKEXCHANGE = new StockExchange();
	
	private final long FIFTEENMINUTESTOMILLISECONDS = 900000;
	
	private List<Stock> stocks    = new ArrayList<Stock>();
	
	private List<Order> matchList = new ArrayList<Order>();
	
	public static int SCALEFACTOR = 10000; // Assumes minimum price increment is 0.0001
	
	private StockExchange()
	{
		// Common Stocks
		stocks.add(new CommonStock("TEA", 0, 100));
		stocks.add(new CommonStock("POP", 8, 100));
		stocks.add(new CommonStock("ALE", 23, 60));
	    stocks.add(new CommonStock("JOE", 13, 250));
		
	    // Preferred Stocks
		stocks.add(new PreferredStock("GIN", 8, 100, 0.02));
	}
	
	public void setMatch(Order order)
	{
		// First check if we can remove any old trades
		Iterator<Order> it = matchList.iterator();
		while(it.hasNext())
		{
			Order oldOrder = it.next();

			Date date = new Date();
			
			if (date.getTime() - oldOrder.getTimeStamp().getTime() > FIFTEENMINUTESTOMILLISECONDS)
			{
				it.remove(); // This order is not required anymore
			}
		}
		
		matchList.add(order);
		displayMatch(order);
	}
	
	public boolean setAsk(String stockSymbol, int stockAmount, double price)
	{
		boolean validStock = false;
		
		if((stockAmount <= 0) || ((int)(price * StockExchange.SCALEFACTOR)) <= 0)
		{
			System.out.println("\nPrice and number of shares must be above 0");
			return validStock;
		}
		
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				Date date = new Date();
				System.out.printf("\nSetting Offer for stock %s at £%.2f for %d shares on %s\n", stock.getSymbol(), price, stockAmount, date);
				AskOrder askOrder = new AskOrder(stock.getSymbol(),stockAmount, price, date);
				stock.setAsk(askOrder);
				break;
			}
		}
		if (!validStock)
		{
			System.out.printf("\n%s is not a valid stock symbol\n", stockSymbol);
		}
		
		return validStock;
	}
	
	public boolean setBid(String stockSymbol, int stockAmount, double price)
	{
		boolean validStock = false;
		
		if((stockAmount <= 0) || ((int)(price * StockExchange.SCALEFACTOR)) <= 0)
		{
			System.out.println("Price and number of shares must be above 0");
			return validStock;
		}
		
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				Date date = new Date();
				System.out.printf("\nSetting Bid for stock %s at £%.2f for %d shares on %s\n", stock.getSymbol(), price, stockAmount, date);
				BidOrder bidOrder = new BidOrder(stock.getSymbol(), stockAmount, price, date);
				stock.setBid(bidOrder);
				break;
			}
		}
		if (!validStock)
		{
			System.out.printf("\n%s is not a valid stock symbol\n", stockSymbol);
		}
		
		return validStock;
	}
	
	public double getVolumeWeightedStockPrice(String stockSymbol)
	{
		boolean validStock = false;
		double volumeWeightedStockPrice = 0;
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				break;
			}
		}
		
		if(validStock)
		{
			double totalMoneyTraded = 0;
			double totalSharesBought = 0;
			
			for (Order order : matchList)
			{
				if (order.getStockSymbol().equals(stockSymbol))
				{
					Date date = new Date();
					if (date.getTime() - order.getTimeStamp().getTime() <= FIFTEENMINUTESTOMILLISECONDS)
					{
					    totalMoneyTraded += order.getPrice() * order.getStock();
					    totalSharesBought += order.getStock();
					}
				}
			}
			if (totalSharesBought > 0)
			{
			    volumeWeightedStockPrice = totalMoneyTraded / totalSharesBought;
			    System.out.printf("\nVolume Weighted Stock Price for stock %s is £%.2f\n", stockSymbol, volumeWeightedStockPrice);
			}
			else
			{
				System.out.printf("\nNo trades have been completed for stock %s in the last 15 minutes\n", stockSymbol);
			}
		}
		else
		{
			System.out.printf("\n%s is not a valid stock symbol\n", stockSymbol);
			return -1;
		}
		return volumeWeightedStockPrice;
	}
	
	public double getGeometricMean()
	{
		double total = 1;
		
		for (Stock stock : stocks)
		{
			total *= stock.getPrice();
		}
		
		double geoMean =  root(total, stocks.size());
		
		System.out.printf("\nThe Geometric mean is %.2f\n", geoMean);
		
		return geoMean;
	}
	
	public double root(double num, double root)
	{
		return Math.pow(num, 1.0 / root);
	}
	
	public double getDividendYield(String stockSymbol) 
	{
		double dividendYield = 0;
		boolean validStock = false;
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				dividendYield = stock.getDividendYield();
				System.out.printf("\nThe Dividend Yield for stock %s is %.2f%%\n", stock.getSymbol(), dividendYield);
				break;
			}
		}
		if (!validStock)
		{
			System.out.printf("\n%s is not a valid stock symbol\n", stockSymbol);
			return -1;
		}
		
		return dividendYield;
	}
	
	public double getDividendYield(String stockSymbol, double inputPrice)
	{
		double dividendYield = 0;
		boolean validStock = false;
		
		if(((int)(inputPrice * StockExchange.SCALEFACTOR)) <= 0)
		{
			System.out.println("Price must be above 0");
			return -1;
		}
		
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				dividendYield = stock.getDividendYield(inputPrice);
				System.out.printf("\nThe Dividend Yield for stock %s is %.2f%%\n", stock.getSymbol(), dividendYield);
				break;
			}
		}
		if (!validStock)
		{
			System.out.printf("\n%s is not a valid stock symbol\n", stockSymbol);
			return -1;
		}
		
		return dividendYield;
	}
	
	public double getPeRatio(String stockSymbol) 
	{
		double peRatio = 0;
		boolean validStock = false;
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				peRatio = stock.getPeRatio();
				System.out.printf("\nThe P/E Ratio for stock %s is %.2f\n", stock.getSymbol(), peRatio);
				break;
			}
		}
		if (!validStock)
		{
			return -1;
		}
		
		return peRatio;
	}
	
	public double getPeRatio(String stockSymbol, double inputPrice)
	{
		double peRatio = 0;
		boolean validStock = false;
		
		if(((int)(inputPrice * StockExchange.SCALEFACTOR)) <= 0)
		{
			System.out.println("Price must be above 0");
			return -1;
		}
		
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				peRatio = stock.getPeRatio(inputPrice);
				System.out.printf("\nThe P/E ratio for stock %s is %.2f\n", stock.getSymbol(), peRatio);
				break;
			}
		}
		if (!validStock)
		{
			return -1;
		}
		
		return peRatio;
	}
	
	public void getCurrentStockPrices()
	{
		System.out.printf("\nStock      Price\n");
		for (Stock stock : stocks)
		{
			System.out.printf("%s        £%.2f\n", stock.getSymbol(), stock.getPrice());
		}
	}
	
	public boolean getOfferAndBidLists(String stockSymbol)
	{
		boolean validStock = false;
		
		for (Stock stock : stocks)
		{
			if (stock.getSymbol().equals(stockSymbol))
			{
				validStock = true;
				stock.getOfferAndBidLists();
				break;
			}
		}
		
		if(!validStock)
		{
			System.out.printf("\n%s is not a valid stock symbol\n", stockSymbol);
		}
		
		return validStock;
	}
	
	private void displayMatch(Order order)
	{
		System.out.printf("\nTrade was made at £%.2f for %d shares on %s for stock %s\n", order.getPrice(),order.getStock(), order.getTimeStamp(), order.getStockSymbol());
	}
}
