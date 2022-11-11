package hu.webuni.student.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.student.aspect.RetryCall;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.wsclient.CentralXmlWsImplService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CentralService {

	private Random random = new Random();

	@RetryCall
	public int getNumFreeSemestersForStudent(long cid) {
		int rnd = random.nextInt(0, 2);
		if (rnd == 0) {
			throw new RuntimeException("Central Education Service timed out.");
		} else {
			return new CentralXmlWsImplService()
					.getCentralXmlWsImplPort()
					.getFreeSemesterCount(cid);
		}
	}

}
