package hu.webuni.airport.model;

import java.util.Date;

import org.hibernate.envers.RevisionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class HistoryData<T> {
	private T data;
	private RevisionType revType;
	private int revision;
	private Date date;
}
