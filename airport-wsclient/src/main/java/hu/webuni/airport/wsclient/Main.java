package hu.webuni.airport.wsclient;

import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws Exception {
		AirportXmlWs airportXmlWsImplPort = new AirportXmlWsImplService().getAirportXmlWsImplPort();
		airportXmlWsImplPort.getHistoryById(900L).forEach(historyData -> {
			System.out.format("Name:%s,Revision:%d,RevType:%s%n",historyData.getData().getName(),
					historyData.getRevision(),
					historyData.getRevType().toString());
		});
		
		GetFlightDelay getFlightDelay = new GetFlightDelay();
		getFlightDelay.setArg0(100L);
//		System.out.println(airportXmlWsImplPort.getFlightDelay(getFlightDelay)); //szinkron hívás is lehetséges
		
//		System.out.println(airportXmlWsImplPort.getFlightDelay(100L));
		
		airportXmlWsImplPort.getFlightDelayAsync(getFlightDelay, result -> {
			try {
				System.out.println(result.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		
		for(int i=0;i<6;i++) {
			System.out.println("Client doing something else...");
			Thread.sleep(1000);
		}
	}

}
