package server;

import javax.xml.ws.Endpoint;
import service.Calculator;

public class CalculatorServer {
	public static void main(String args[]) {
		Calculator server = new Calculator();
		Endpoint endpoint = Endpoint.publish(
				"http://localhost:8080/calculator", server);
	}
}