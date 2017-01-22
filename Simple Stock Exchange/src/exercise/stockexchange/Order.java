package exercise.stockexchange;

import java.util.Date;

public class Order 
{
	
	private   String stockSymbol;
	private   int    stock;
	protected double price;
    protected Date   timeStamp;
	
	public Order (int lStock, double lPrice, Date lTimeStamp)
	{
		stockSymbol = "";
		stock = lStock;
		price = lPrice;
	    timeStamp = lTimeStamp;
	}
    
	public Order (String lStockSymbol, int lStock, double lPrice, Date lTimeStamp)
	{
		stockSymbol = lStockSymbol;
		stock = lStock;
		price = lPrice;
	    timeStamp = lTimeStamp;
	}
	
	public String getStockSymbol()
	{
		return stockSymbol;
	}
	
	public int getStock()
	{
		return stock;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public Date getTimeStamp()
	{
		return timeStamp;
	}
	
	public void reduceStock(int stockExchanged)
	{
		stock -= stockExchanged;
	}
	
	public String toString()
	{
		return String.format("%-13d£%-10.2f%s" , stock, price, timeStamp);
	}
}
