package main.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Printed
@Interceptor
public class PrintInterceptor 
{

	@AroundInvoke
	public Object printMethodName(InvocationContext ctx) throws Exception
	{
		System.out.println("Intercepting method " + ctx.getMethod().getName() + " of class " + ctx.getTarget().getClass());
		return ctx.proceed();
	}
		
}
