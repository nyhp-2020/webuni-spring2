package hu.webuni.airport.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class DelayService {
	
	private Random random = new Random();
	
	public int getDelay(long flightId) {
		System.out.println("getDelay called");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//		}
		return random.nextInt(0,1800);
	}

}
