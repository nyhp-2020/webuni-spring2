package hu.webuni.student.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {
	
	@Pointcut("@annotation(hu.webuni.student.aspect.RetryCall) || @within(hu.webuni.student.aspect.RetryCall)")
	public void annotationRetryCall() {}

//	@Around("execution(* hu.webuni.student.service.SemesterService.getUsedFreeSemesters(..))")
	@Around("hu.webuni.student.aspect.RetryAspect.annotationRetryCall()")
	public Object retryWhenException(ProceedingJoinPoint joinPoint) throws Throwable {
		Exception ex = null;
		int maxret = 5;
		int waitmillis=500;
		for (int i = 1; i <= maxret; i++) {
			try {
				return joinPoint.proceed();
//				return;

			} catch (Exception e) {
				ex = e;
				System.out.println("ex" + i);
				Thread.sleep(waitmillis);
			}
		}
		throw ex;
	}

}
