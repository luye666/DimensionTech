package com.zl.tech.aspect;

import android.text.TextUtils;
import android.util.Log;
import com.zl.tech.di.annotation.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import java.lang.reflect.Array;


/**
 * Created by Administrator on 2019/5/29
 * <p>
 * Logger打印的切面
 */
@Aspect
public class LoggerAspect {
    @Around("execution(@com.zl.tech.di.annotation.Logger * *(..)) && @annotation(log)")
    public Object logger(ProceedingJoinPoint joinPoint, Logger log) {


        Object result = null;
        try {
            long start = System.currentTimeMillis();
//            获取返回值
            result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            Object[] args = joinPoint.getArgs();

//            获取方法签名
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Class[] parameterTypes = methodSignature.getParameterTypes();
            String[] parameterNames = methodSignature.getParameterNames();

            StringBuilder builder = new StringBuilder();

//            判断是不是数组
            Class<?> aClass = args[0].getClass();
            if (aClass.isArray()) {
                Class<?> componentType = aClass.getComponentType();
                builder.append("Array of: " + componentType+" ");
                builder.append("Length: " + Array.getLength(args[0])+"\n");

                for (int i = 0; i < Array.getLength(args[0]); i++) {
                    builder.append(Array.get(args[0],i) + " ");
                }
            }else {
                builder.append(parameterTypes[0].getSimpleName());
                builder.append(" ");
                builder.append(parameterNames[0]);
                builder.append(" = ");
                builder.append(args[0]);
                for (int i = 1; i < parameterTypes.length; i++) {
                    builder.append(", ");
                    builder.append(parameterTypes[i].getSimpleName());
                    builder.append(" ");
                    builder.append(parameterNames[i]);
                    builder.append(" = ");
                    builder.append(args[i]);
                }
            }




            // 根据方法签名 获得返回类型
            String type = methodSignature.getReturnType().getSimpleName();


            /**
             * 获得源码文件位置信息 拼接日志支持可跳转格式的字符串
             */
            SourceLocation sourceLocation = joinPoint.getSourceLocation();
            int lineNumber = sourceLocation.getLine();
            String fileName = sourceLocation.getFileName();
            String fullClassName = methodSignature.getDeclaringTypeName();
            String methodName = methodSignature.getMethod().getName();

            StringBuilder content = new StringBuilder(" \n");
            content.append("╔═════════════════════════════════════════════════════════════════════\n");
            content.append("║ 当前线程: ");
            content.append(Thread.currentThread().getName());
            content.append("\n");
            content.append("╟─────────────────────────────────────────────────────────────────────\n");
            content.append("║ 函数: ");
            content.append(fullClassName);
            content.append(".");
            content.append(methodName);
            content.append("(");
            content.append(fileName);
            content.append(":");
            content.append(lineNumber);
            content.append(")\n");
            content.append("║ 参数: ");
            content.append(builder);
            content.append("\n");
            content.append("╟─────────────────────────────────────────────────────────────────────\n");
            content.append("║ 返回: ");
            content.append(type);
            content.append(" ");
            content.append(TextUtils.equals(type.toLowerCase(), "void") ? "" : result);
            content.append("\n");
            content.append("║ 耗时: ");
            content.append(end - start);
            content.append(" ms\n");
            content.append("╚═════════════════════════════════════════════════════════════════════");


            Log.println(log.value(), methodSignature.getDeclaringType().getSimpleName(), content
                    .toString());


        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
