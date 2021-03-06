package client;

import java.net.MalformedURLException;
import java.net.URL;

import service.CalculatorService;
import service.Calculator;

public class CalculatorClient {
	
	public static void main(String[] args) {
		URL test = null;
		try {
			test = new URL("http://localhost:8080/calculator?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CalculatorService service = new CalculatorService(test);
		Calculator calculator = service.getCalculatorPort();
		System.out.println("Summe: "+calculator.addValues(8, 2));
		
	}

}
