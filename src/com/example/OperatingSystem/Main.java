package com.example.OperatingSystem;


import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<Job> job1, job2, job3, job4, job5, job6;
        String file1 = "testdata1.txt";
        String file2 = "testdata2.txt";
        String file3 = "testdata3.txt";
        try {
            job1 = parseFile(file1);
            job2 = parseFile(file2);
            job3 = parseFile(file3);
            job4 = parseFile(file1);
            job5 = parseFile(file2);
            job6 = parseFile(file3);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return;
        }
        System.out.println("____________________________________________________");
        System.out.println("FILE READ: " + file1);
        System.out.println("====================================================");
        System.out.println("===============FIRST COME FIRST SERVE===============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        FCFS(job1);
        System.out.println("====================================================");
        System.out.println("=================SHORTEST JOB FIRST=================");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        SJF(job1);
        System.out.println("====================================================");
        System.out.println("============ROUND-ROBIN (Time Slice = 3)============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        RR(job1, 3);
        System.out.println("====================================================");
        System.out.println("============ROUND-ROBIN (Time Slice = 5)============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        RR(job4, 5);
        System.out.println("____________________________________________________");
        System.out.println("FILE READ: " + file2);
        System.out.println("====================================================");
        System.out.println("===============FIRST COME FIRST SERVE===============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        FCFS(job2);
        System.out.println("====================================================");
        System.out.println("=================SHORTEST JOB FIRST=================");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        SJF(job2);
        System.out.println("====================================================");
        System.out.println("============ROUND-ROBIN (Time Slice = 3)============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        RR(job2, 3);
        System.out.println("====================================================");
        System.out.println("============ROUND-ROBIN (Time Slice = 5)============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        RR(job5, 5);
        System.out.println("____________________________________________________");
        System.out.println("FILE READ: " + file3);
        System.out.println("====================================================");
        System.out.println("===============FIRST COME FIRST SERVE===============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        FCFS(job3);
        System.out.println("====================================================");
        System.out.println("=================SHORTEST JOB FIRST=================");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        SJF(job3);
        System.out.println("====================================================");
        System.out.println("============ROUND-ROBIN (Time Slice = 3)============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        RR(job3, 3);
        System.out.println("====================================================");
        System.out.println("============ROUND-ROBIN (Time Slice = 5)============");
        System.out.println("====================================================");
        System.out.println("\nJob Name\tStart\t End");
        System.out.println("----------------------------------------------------");
        RR(job6, 5);
    }

    private static ArrayList<Job> parseFile(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        ArrayList<Job> jobs = new ArrayList<>();
        int cost;
        String name;
        while (sc.hasNextLine()) {
            name = sc.nextLine();
            cost = Integer.parseInt(sc.nextLine());
            jobs.add(new Job(name, cost));
        }
        return jobs;
    }

    private static double calcAverage(int[] turnaround, List<Job> jobList) {
        if (turnaround.length == 0) return 0;
        double sum = 0;
        System.out.println();
        for (int i = 0; i < turnaround.length; i++) {
            sum += turnaround[i];
            System.out.println("Job " + jobList.get(i).getName() + " completed at " + turnaround[i]);
        }
        System.out.println();
        return sum / turnaround.length;
    }

    private static void FCFS(List<Job> jobList) {
        int time = 0;
        int[] turnaround = new int[jobList.size()];
        for (int i = 0; i < jobList.size(); i++) {
            int started = time;
            time += jobList.get(i).getCost();
            turnaround[i] = time;
            System.out.println(jobList.get(i).getName() + "\t\t" + started + "\t" + time + "\t Completed " + jobList.get(i).getName() + " at " + time);
        }
        printTime(calcAverage(turnaround, jobList));
    }

    private static void SJF(List<Job> jobList) {
        ArrayList<Job> sortedJobList = new ArrayList<>(jobList);
        Collections.sort(sortedJobList);
        FCFS(sortedJobList);
    }

    private static void RR(List<Job> jobList, int quantum) {
        int completed = 0;
        int time = 0;
        int[] turnaround = new int[jobList.size()];
        Job currentJob;
        while(completed < jobList.size()) {
            for (int i = 0; i < jobList.size(); i++) {
                currentJob = jobList.get(i);
                boolean jobCompleted = false;
                if (currentJob.getCost() > 0) {
                    System.out.print(currentJob.getName() + "\t\t" + time);
                    int decrement = currentJob.decrement(quantum);
                    time += quantum;
                    if (decrement <= 0) {
                        completed++;
                        time += decrement;
                        turnaround[i] = time;
                        jobCompleted = true;
                    }
                    System.out.print("\t" + time + (jobCompleted ? "\tCompleted " + currentJob.getName() + " at " + time : "") + "\n");
                }
            }
        }
        printTime(calcAverage(turnaround, jobList));
    }

    private static void printTime(double time) {
        System.out.println("Average Turnaround Time: " + new DecimalFormat("#.##").format(time) + "\n\n");
    }
}