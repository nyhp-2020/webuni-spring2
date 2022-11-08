package hu.webuni.central.wmlws;

import javax.jws.WebService;

@WebService
public interface CentralXmlWs {
	public int getFreeSemesterCount(long cid);
}
