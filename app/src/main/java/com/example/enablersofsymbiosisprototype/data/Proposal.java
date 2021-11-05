package com.example.enablersofsymbiosisprototype.data;

public class Proposal extends Message {
    public enum State {
        NotResponded,
        Approved,
        Rejected
    }

    public int price;
    public State state;
}
