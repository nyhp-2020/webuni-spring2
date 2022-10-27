package hu.webuni.airport.service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DelayService {

	private Random random = new Random();
	
//	@Async
	public int getDelay(long flightId) {
//	public CompletableFuture<Integer> getDelay(long flightId) {
		System.out.println("DelayService.getDelay called at thread " + Thread.currentThread().getName());

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		return random.nextInt(0, 1800);
//		return CompletableFuture.completedFuture(random.nextInt(0, 1800));
	}
}