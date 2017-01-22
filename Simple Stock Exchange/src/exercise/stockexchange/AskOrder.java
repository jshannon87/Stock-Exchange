package exercise.stockexchange;

import java.util.Date;

public class AskOrder extends Order implements Comparable<Order> 
{

	public AskOrder(int stock, double price, Date timeStamp) 
	{
		super(stock, price, timeStamp);
	}
	
	public AskOrder(String stockSymbol, int stock, double price, Date timeStamp) 
	{
		super(stockSymbol, stock, price, timeStamp);
	}
	
	@Override
	public int compareTo(Order order)
	{
		int price1 = (int)(this.price * StockExchange.SCALEFACTOR);
        int price2 = (int)(order.price * StockExchange.SCALEFACTOR);
        
        if (price1 - price2 != 0)
        {
        	return price1 - price2;
        }
        else
        {
        	return this.timeStamp.compareTo(order.timeStamp);
        }
	}

}
