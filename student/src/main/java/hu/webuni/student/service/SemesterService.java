package hu.webuni.student.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import hu.webuni.student.aspect.RetryCall;

@Service
//@RetryCall
public class SemesterService {
	
	private Random random = new Random();
	
	@RetryCall
	public int getUsedFreeSemesters(long cid) {
		throwExceptionOrNot();
		return 0;
	}
	
	private void throwExceptionOrNot() {
		if(random.nextInt(0,100) > 50)
			throw new SemesterException();
	}
	
	
	
}
