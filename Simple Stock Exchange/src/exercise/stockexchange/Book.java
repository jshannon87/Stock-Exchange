package exercise.stockexchange;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Book 
{
	
	private double bidPrice;
	private double askPrice;
	
	private Stock  stock;
	
	private List<BidOrder> buyList =  new ArrayList<BidOrder>();
	private List<AskOrder> sellList = new ArrayList<AskOrder>();
	
	public Book(Stock lStock)
	{
		stock = lStock;
	}

	public void addAsk(AskOrder askOrder)
	{
		sellList.add(askOrder);
		Collections.sort(sellList);
		if (sellList.get(0) == askOrder)
		{
			setAskPrice(askOrder.getPrice());
		}
		findMatch();
	}
	
	public void addBid(BidOrder bidOrder)
	{
		buyList.add(bidOrder);
		Collections.sort(buyList);
		if (buyList.get(0) == bidOrder)
		{
			setBidPrice(bidOrder.getPrice());
		}
		findMatch();
	}
	
	private void setBidPrice(double lBidPrice)
	{
		bidPrice = lBidPrice;
	}
	
	private void setAskPrice(double lAskPrice)
	{
		askPrice = lAskPrice;
	}
	
	private double getBidPrice()
	{
		return bidPrice;
	}
	
	private double getAskPrice()
	{
		return askPrice;
	}
	
	private void findMatch()
	{
		boolean morePossibleMatches = true;
		
		while(morePossibleMatches)
		{
		    if ((!buyList.isEmpty()) && (!sellList.isEmpty()))
		    {
			    if (getBidPrice() >= getAskPrice())
			    {
				    Order sell = sellList.get(0);
				    Order buy = buyList.get(0);
				    if (sell.getStock() == buy.getStock())
				    {
					    int stockExhanged = sell.getStock();
					    double tradePrice = sell.getPrice();
					    sellList.remove(0);
					    buyList.remove(0);
					    if (!sellList.isEmpty())
					    {
					        setAskPrice(sellList.get(0).getPrice());
					    }
					    if (!buyList.isEmpty())
					    {
					        setBidPrice(buyList.get(0).getPrice());
					    }
					    else
					    {
					    	setBidPrice(0);
					    }
					    Order order = new Order(stock.getSymbol(),stockExhanged,tradePrice, new Date());
					    stock.tradeExchanged(order);
					    StockExchange.STOCKEXCHANGE.setMatch(order);
					    morePossibleMatches = false;
				    }
				    else if (sell.getStock() > buy.getStock())
				    {
					    int stockExhanged = buy.getStock();
					    sell.reduceStock(stockExhanged);
					    buyList.remove(0);
					    if (!buyList.isEmpty())
					    {
					        setBidPrice(buyList.get(0).getPrice());
					    }
					    else
					    {
					    	setBidPrice(0);
					    }
					    Order order = new Order(stock.getSymbol(),stockExhanged,sell.getPrice(), new Date());
					    stock.tradeExchanged(order);
					    StockExchange.STOCKEXCHANGE.setMatch(order);
				    }
				    else if (sell.getStock() < buy.getStock())
				    {
					    double tradePrice = sell.getPrice();
					    int stockExhanged = sell.getStock();
					    buy.reduceStock(stockExhanged);
					    sellList.remove(0);
					    if (!sellList.isEmpty())
					    {
					        setAskPrice(sellList.get(0).getPrice());
				        }
					    Order order = new Order(stock.getSymbol(),stockExhanged,tradePrice, new Date());
					    stock.tradeExchanged(order);
					    StockExchange.STOCKEXCHANGE.setMatch(order);
				    }
			    } // end getBidPrice() >= getAskPrice()
			    else
			    {
			    	morePossibleMatches = false;
			    }
		    } // end (buyList.size()) > 0 && (sellList.size() > 0)
		    else
		    {
		    	morePossibleMatches = false;
		    }
		} // end while
	}
	
	public void getOfferAndBidLists()
	{
		if(sellList.isEmpty())
		{
		    System.out.println("\nNo Current Offers");
		}
		else
		{
		    System.out.println("\n" + stock.getSymbol() + " - Offers\nShares       Price      Date");
		    for(Order order : sellList)
		    {
			    System.out.println(order);
		    }
		}
		
		if(buyList.isEmpty())
		{
		    System.out.println("\nNo Current Bids");
		}
		else
		{
		    System.out.println("\n" + stock.getSymbol() + " - Bids\nShares       Price      Date");
		    for(Order order : buyList)
		    {
			    System.out.println(order);
		    }
		}
	}
}
