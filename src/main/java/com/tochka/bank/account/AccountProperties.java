package com.tochka.bank.account;

public class AccountProperties {

    private final int defaultAccountAmount;
    private final double transferCommission;

    public AccountProperties(int defaultAmount, double transferCommission) {
        this.defaultAccountAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAccountAmount() {
        return defaultAccountAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }
}
