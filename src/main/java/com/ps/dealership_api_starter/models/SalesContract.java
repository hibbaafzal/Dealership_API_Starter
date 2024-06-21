package com.ps.dealership_api_starter.models;

public class SalesContract {
    private int contractId;
    private String contractDate;
    private String customerName;
    private String customerEmail;
    private int vin;
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private double totalPrice;
    private String financeOption;
    private double monthlyPayment;

    public SalesContract() {
    }

    public SalesContract(int contractId, String contractDate, String customerName, String customerEmail, int vin, double salesTax, double recordingFee, double processingFee, double totalPrice, String financeOption, double monthlyPayment) {
        this.contractId = contractId;
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vin = vin;
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.totalPrice = totalPrice;
        this.financeOption = financeOption;
        this.monthlyPayment = monthlyPayment;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFinanceOption() {
        return financeOption;
    }

    public void setFinanceOption(String financeOption) {
        this.financeOption = financeOption;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
}
