package com.gitlab.rmarzec.framework.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n========================================");
        System.out.println("START: " + result.getMethod().getMethodName());
        System.out.println("========================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASS: " + result.getMethod().getMethodName());
        System.out.println("Execution time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
        System.out.println("========================================\n");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("FAIL: " + result.getMethod().getMethodName());
        System.out.println("Error: " + result.getThrowable().getMessage());
        System.out.println("========================================\n");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIP: " + result.getMethod().getMethodName());
        System.out.println("========================================\n");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n========================================");
        System.out.println("STARTING TESTS: " + context.getName());
        System.out.println("========================================");
    }
}