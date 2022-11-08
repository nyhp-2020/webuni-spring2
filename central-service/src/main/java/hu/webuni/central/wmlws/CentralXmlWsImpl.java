package hu.webuni.central.wmlws;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CentralXmlWsImpl implements CentralXmlWs {
	
	private Random random = new Random();

	@Override
	public int getFreeSemesterCount(long cid) {
		return random.nextInt(0,10);
	}

}
