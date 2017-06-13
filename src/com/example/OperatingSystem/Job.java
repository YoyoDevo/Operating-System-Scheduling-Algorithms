package com.example.OperatingSystem;


public class Job implements Comparable<Job> {
    private String name;
    private int cost;

    public Job(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int decrement(int amount) {
        return this.cost -= amount;
    }

    public int compareTo(Job job) {
        return this.cost - job.cost;
    }

    public String toString() {
        return "(" + this.name + ", " + this.cost + ")";
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }
}