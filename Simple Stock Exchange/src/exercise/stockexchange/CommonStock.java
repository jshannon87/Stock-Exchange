package exercise.stockexchange;

public class CommonStock extends Stock 
{
	
	public CommonStock(String symbol, int lastDividend, int parValue)
	{
		super(symbol, lastDividend, parValue);
	}
	
	public double getDividendYield()
	{
		return lastDividend / price;
	}
	
	public double getDividendYield(double inputPrice)
	{
		return lastDividend / inputPrice;
	}
}
