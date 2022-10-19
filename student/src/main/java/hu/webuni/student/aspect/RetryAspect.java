package hu.webuni.student.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

	@Around("execution(* hu.webuni.student.service.SemesterService.getUsedFreeSemesters(..))")
	public void retryWhenException(ProceedingJoinPoint joinPoint) throws Throwable {
		Exception ex = null;
		int maxret = 5;
		int waitmillis=500;
		for (int i = 1; i <= maxret; i++) {
			try {
				joinPoint.proceed();
				return;

			} catch (Exception e) {
				ex = e;
				System.out.println("ex" + i);
				Thread.sleep(waitmillis);
			}
		}
		throw ex;
	}

}
