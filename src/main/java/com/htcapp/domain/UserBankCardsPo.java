package com.htcapp.domain;

public class UserBankCardsPo {
    private String name;
    private String card;
    private String bank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public UserBankCardsPo(String name, String card, String bank) {
        this.name = name;
        this.card = card;
        this.bank = bank;
    }

    public UserBankCardsPo() {

    }
}
