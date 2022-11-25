package hu.webuni.student.ws;

import lombok.Data;

@Data
public class SendFreeSemestersMessage {
	private long id;
	private int FreeSemesters;
}
