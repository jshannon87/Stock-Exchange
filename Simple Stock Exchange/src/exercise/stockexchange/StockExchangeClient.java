package exercise.stockexchange;

import  java.util.Scanner;

public class StockExchangeClient 
{
	private static Scanner scanner = new Scanner(System.in);
	private static int actionMain;
	
	private static final int SET_OFFER                       = 1;
	private static final int SET_BID                         = 2;
	private static final int GET_DIVEDEND_YIELD_AND_PE_RATIO = 3;
	private static final int GET_VOLUME_WEIGHTED_STOCK_PRICE = 4;
	private static final int GET_GBCE_INDEX                  = 5;
	private static final int GET_SHARE_PRICE                 = 6;
	private static final int GET_OFFER_AND_BID_LISTS         = 7;
	
	public static void main(String[] args)
	{
		boolean bContinue;
	    System.out.println("Welcome to the Super Simple Stock Exchange");
	    System.out.println("\nAll stocks have an initial value of £5.00");
	    System.out.println("********************************************");
	    
	    while (true)
	    {
	    	bContinue = true;
	    	System.out.println("\nPerform one of the actions below by entering the appropriate value "
	    			+ "from 1 to 7.\nStock symbols are TEA, JOE, ALE, POP and GIN.\nEnter -1 to cancel action and return to "
	    			+ "this menu during execution of the program.");
	    	
	    	System.out.println("\n[1] Make Offer for given stock\n"
	    			+ "[2] Make Bid for given stock\n"
	    			+ "[3] Calculate Dividend Yield and P/E Ratio for given stock\n"
	    			+ "[4] Calculate the Volume Weighted Stock Price for a given stock based on trades in the last 15 minutes\n"
	    			+ "[5] Calculate the GBCE All Share Index using the Geometric Mean of all stocks\n"
	    			+ "[6] Get the current share price for all stocks\n"
	    			+ "[7] Get the current offer and bid lists for a stock");
	    	
	    	System.out.println("\nAction ");
	    	
	    	if(scanner.hasNextInt())
	    	{
	    	   actionMain = scanner.nextInt();
	    	   if (!(actionMain >= 1) || !(actionMain <= 7))
	    	   {
	    		   bContinue = false;
	    	   }
	    	}
	    	else
	    	{
	    		bContinue = false;
	    	}
	    	
	    	if(!bContinue)
	    	{
	    		System.out.println("\nYou must enter a value from 1 to 7");
	    		scanner.nextLine();
	    		continue; // Go back to the start
	    	}
	    	
	    	scanner.nextLine();
	    	switch (actionMain)
	    	{
	    	case SET_OFFER:
	    	case SET_BID:
	            onMakeTradeRequest();
	    		break;
	    	case GET_DIVEDEND_YIELD_AND_PE_RATIO:
	    		onGetDividendYieldAndPeRation();
	    		break;
	    	case GET_VOLUME_WEIGHTED_STOCK_PRICE:
	    		onGetVolumeWeightedStockPrice();
	    		break;
	    	case GET_GBCE_INDEX:
	    		onGetGbceAllShareIndex();
	    		break;
	    	case GET_SHARE_PRICE:
	    		onGetCurrentSharePrices();
	    		break;
	    	case GET_OFFER_AND_BID_LISTS:
	    		onGetCurrentOfferAndBidLists();
	    		break;
	    	default:
	    		System.out.println("The Super Simple Stock Exchange has got a problem!!");
	    	}
	    }
	}
	
    private static void onMakeTradeRequest()
    {
    	boolean invalidParameters = false;
    	boolean success = false;
    	String stockSymbol;
    	int numShares;
    	double price;
    	Scanner lineTokenizer;
    	
    	String action;
    	String format;
    	if (actionMain == SET_OFFER)
    	{
    	    action = "offer";
    	    format = "OFFER_PRICE";
    	}
    	else
    	{
    		action = "bid";
    		format = "BID_PRICE";
    	}
    	
    	while(!success)
    	{
    		if(invalidParameters)
    		{
    			System.out.println("\nInvalid parameters entered");
    		}
    		
    		System.out.printf("\nEnter the Stock Symbol, number of shares and %s price in the following format\n"
    			    + "STOCK_SYMBOL SHARES %s (eg TEA 100 4.56)\n", action, format);
    	
    	    lineTokenizer = new Scanner(scanner.nextLine());
    	
            if(lineTokenizer.hasNext())
            {
        	    stockSymbol = lineTokenizer.next();
        	    
        	    // Check if -1 was entered
        	    if(returnToMainMenu(stockSymbol))
        	    {
        	    	lineTokenizer.close();
        	    	return;
        	    }
            }
            else
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(lineTokenizer.hasNextInt())
            {
        	    numShares = lineTokenizer.nextInt();
            }
            else
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(lineTokenizer.hasNextDouble())
            {
        	    price = lineTokenizer.nextDouble();
            }
            else
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(lineTokenizer.hasNext()) // No extra parameters
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
        
            if (actionMain == SET_OFFER)
            {
                if(!StockExchange.STOCKEXCHANGE.setAsk(stockSymbol, numShares, price))
                {
                	invalidParameters = true;
            	    lineTokenizer.close();
            	    continue;
                }
            }
            else // must be 2
            {
                if(!StockExchange.STOCKEXCHANGE.setBid(stockSymbol, numShares, price))
                {
                	invalidParameters = true;
            	    lineTokenizer.close();
            	    continue;
                }
            }
            success = true;
            lineTokenizer.close();
    	}
    }
    
    private static void onGetVolumeWeightedStockPrice()
    {
    	boolean success = false;
    	boolean invalidParameters = false;
    	String stockSymbol;
    	
    	while(!success)
    	{
    		
    		if(invalidParameters)
    		{
    			System.out.println("\nInvalid parameters entered");
    		}
    		
    	    System.out.println("\nEnter the Stock Symbol");
    	
    	    Scanner lineTokenizer = new Scanner(scanner.nextLine());
    	
            if(lineTokenizer.hasNext())
            {
        	    stockSymbol = lineTokenizer.next();
        	    
        	    // Check if -1 was entered
        	    if(returnToMainMenu(stockSymbol))
        	    {
        	    	lineTokenizer.close();
        	    	return;
        	    }
            }
            else
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(lineTokenizer.hasNext()) // no extra parameters
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(((int) StockExchange.STOCKEXCHANGE.getVolumeWeightedStockPrice(stockSymbol)) < 0)
            {
        	    invalidParameters = true;
    	        lineTokenizer.close();
    	        continue;
            }
            success = true;
            lineTokenizer.close();
    	}
    }
    
    private static void onGetGbceAllShareIndex()
    {
    	StockExchange.STOCKEXCHANGE.getGeometricMean();
    }
    
    private static void onGetDividendYieldAndPeRation()
    {
    	boolean success = false;
    	int action;
    	String stockSymbol;
    	
    	while(!success)
    	{
    	    System.out.println("\nEnter the appropriate value to perform action"
    			    + "\n[1] Perform calculation based on actual stock price\n"
    			    + "[2] Perform calculation based on abritary price");
    	
    	    Scanner lineTokenizer = new Scanner(scanner.nextLine());
    	
            if(lineTokenizer.hasNextInt())
            {
        	    action = lineTokenizer.nextInt();
        	    
        	    // Check if -1 was entered
        	    if(action == -1)
        	    {
        	    	lineTokenizer.close();
        	    	return;
        	    }
            }
            else
            {
            	System.out.println("\nValue must be between 1 and 2");
        	    lineTokenizer.close();
        	    continue;
            }
            if(lineTokenizer.hasNext())// no extra parameters
            {
            	System.out.println("\nOnly enter the value 1 or 2");
        	    lineTokenizer.close();
        	    continue;
            }
            if(action == 1)
            {
            	boolean bStockSymbol =false;
            	while(!bStockSymbol)
            	{
            	    System.out.println("\nEnter the stock symbol");
            	    Scanner stockTokenizer = new Scanner(scanner.nextLine());
                    if(stockTokenizer.hasNext())
                    {
                	    stockSymbol = stockTokenizer.next();
                	    
                	    // Check if -1 was entered
                	    if(returnToMainMenu(stockSymbol))
                	    {
                	    	stockTokenizer.close();
                	    	return;
                	    }
                    }
                    else
                    {
                    	System.out.println("\nYou must enter a stock symbol");
                	    stockTokenizer.close();
                	    continue;
                    }
                    if(stockTokenizer.hasNext())
                    {
                    	System.out.println("\nOnly enter the stock symbol");
                	    stockTokenizer.close();
                	    continue;
                    }
                    if(((int) StockExchange.STOCKEXCHANGE.getDividendYield(stockSymbol)) < 0 | ((int) StockExchange.STOCKEXCHANGE.getPeRatio(stockSymbol)) < 0)
                    {
                	    stockTokenizer.close();
                	    continue;
                    }
                    stockTokenizer.close();
                    bStockSymbol = true;
            	}
            }
            else if(action == 2)
            {
            	boolean bStockAndPriceSymbol = false;
            	double price;
          
            	while(!bStockAndPriceSymbol)
            	{
            	    System.out.println("\nEnter the stock symbol and price in the format STOCK_SYMBOL PRICE (eg TEA 4.56)");
        	        Scanner stockTokenizer = new Scanner(scanner.nextLine());
                    if(stockTokenizer.hasNext())
                    {
            	        stockSymbol = stockTokenizer.next();
            	        
                	    // Check if -1 was entered
                	    if(returnToMainMenu(stockSymbol))
                	    {
                	    	stockTokenizer.close();
                	    	return;
                	    }
                    }
                    else
                    {
                    	System.out.println("\nYou must enter a stock symbol");
            	        stockTokenizer.close();
            	        continue;
                    }
                    if(stockTokenizer.hasNextDouble())
                    {
                        price = stockTokenizer.nextDouble();
                    }
                    else
                    {
                    	System.out.println("\nInvalid parameters");
            	        stockTokenizer.close();
            	        continue;
                    }
                    if(stockTokenizer.hasNext())// no extra parameters
                    {
                    	System.out.println("\nOnly enter the stock symbol and price");
                	    stockTokenizer.close();
                	    continue;
                    }
                    if(((int) StockExchange.STOCKEXCHANGE.getDividendYield(stockSymbol, price)) < 0 | ((int) StockExchange.STOCKEXCHANGE.getPeRatio(stockSymbol)) < 0)
                    {
            	        stockTokenizer.close();
            	        continue;
                    }
                    stockTokenizer.close();
                    bStockAndPriceSymbol = true;
            	}
            }
            else
            {
            	System.out.println("\nValue must be between 1 and 2");
        	    lineTokenizer.close();
        	    continue;
            }
            success = true;
            lineTokenizer.close();
    	}
    }
    
    private static void onGetCurrentSharePrices()
    {
    	StockExchange.STOCKEXCHANGE.getCurrentStockPrices();
    }
    
    private static void onGetCurrentOfferAndBidLists()
    {
    	boolean success = false;
    	boolean invalidParameters = false;
    	String stockSymbol;
    	
    	while(!success)
    	{
    		
    		if(invalidParameters)
    		{
    			System.out.println("\nInvalid parameters entered");
    		}
    		
    	    System.out.println("\nEnter the Stock Symbol");
    	
    	    Scanner lineTokenizer = new Scanner(scanner.nextLine());
    	
            if(lineTokenizer.hasNext())
            {
        	    stockSymbol = lineTokenizer.next();
        	    
        	    // Check if -1 was entered
        	    if(returnToMainMenu(stockSymbol))
        	    {
        	    	lineTokenizer.close();
        	    	return;
        	    }
            }
            else
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(lineTokenizer.hasNext()) // no extra parameters
            {
            	invalidParameters = true;
        	    lineTokenizer.close();
        	    continue;
            }
            if(!StockExchange.STOCKEXCHANGE.getOfferAndBidLists(stockSymbol))
            {
        	    invalidParameters = true;
    	        lineTokenizer.close();
    	        continue;
            }
            success = true;
            lineTokenizer.close();
    	}
    }
    
    public static boolean returnToMainMenu(String command)
    {
    	if(isInteger(command))
    	{
    		if(Integer.parseInt(command) == -1)
    		{
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public static boolean isInteger(String string)
    {
    	if(string.isEmpty())
    		return false;
    	
    	for(int i = 0; i < string.length(); i++)
    	{
    		if((i == 0) && (string.charAt(i) == '-'))
    		{
    			if(string.length() == 1)
    			{
    				return false;
    			}
    			else
    			{
    				continue;
    			}
    		}
    		if (Character.digit(string.charAt(i), 10) < 0)
    		{
    			return false;
    		}
    	}
    	return true;
    }
}

