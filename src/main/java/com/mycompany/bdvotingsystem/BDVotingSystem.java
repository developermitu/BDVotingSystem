/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bdvotingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

abstract class Voter {
    private String nid;
    private String password;
    private boolean voted;

    public Voter(String nid, String password) {
        this.nid = nid;
        this.password = password;
        this.voted = false;
    }

    public String getNid() {
        return nid;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public abstract void vote();

    public abstract String getCandidateName();
}

class MurgiMarkaVoter extends Voter {
    public MurgiMarkaVoter(String nid, String password) {
        super(nid, password);
    }

    @Override
    public void vote() {
        System.out.println("You voted for Murgi Marka.");
    }

    @Override
    public String getCandidateName() {
        return "Murgi Marka";
    }
}

class FanMarkaVoter extends Voter {
    public FanMarkaVoter(String nid, String password) {
        super(nid, password);
    }

    @Override
    public void vote() {
        System.out.println("You voted for Fan Marka.");
    }

    @Override
    public String getCandidateName() {
        return "Fan Marka";
    }
}

class BiddutMarkaVoter extends Voter {
    public BiddutMarkaVoter(String nid, String password) {
        super(nid, password);
    }

    @Override
    public void vote() {
        System.out.println("You voted for Biddut Marka.");
    }

    @Override
    public String getCandidateName() {
        return "Biddut Marka";
    }
}

class VotingSystem {
    private List<Voter> voters;
    private int totalVoteCount;
    private Map<String, Integer> candidateVotes;

    public VotingSystem() {
        this.voters = new ArrayList<>();
        this.totalVoteCount = 0;
        this.candidateVotes = new HashMap<>();
    }

    public void initializeVoters() {
        voters.add(new MurgiMarkaVoter("8260356244", "mitu"));
        voters.add(new FanMarkaVoter("1111000022", "2"));
        voters.add(new BiddutMarkaVoter("1111000033", "3"));
        voters.add(new MurgiMarkaVoter("1111000044", "4"));
        voters.add(new FanMarkaVoter("1111000055", "test"));
    }

    public boolean login(String nid, String password) {
        for (Voter voter : voters) {
            if (voter.getNid().equals(nid) && voter.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void vote(String nid) {
        Voter voter = null;
        for (Voter v : voters) {
            if (v.getNid().equals(nid)) {
                voter = v;
                break;
            }
        }
        if (voter != null && !voter.hasVoted()) {
            System.out.println("Select your candidate:");
            System.out.println("1. Murgi Marka");
            System.out.println("2. Fan Marka");
            System.out.println("3. Biddut Marka");
            Scanner scanner = new Scanner(System.in);
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
                return;
            }
            switch (choice) {
                case 1:
                    voter.vote();
                    updateVoteCount("Murgi Marka");
                    break;
                case 2:
                    voter.vote();
                    updateVoteCount("Fan Marka");
                    break;
                case 3:
                    voter.vote();
                    updateVoteCount("Biddut Marka");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            voter.setVoted(true);
            totalVoteCount++;
            System.out.println("Vote recorded successfully.");
        } else {
            System.out.println("Invalid NID or you have already voted.");
        }
    }

    private void updateVoteCount(String candidateName) {
        if (candidateVotes.containsKey(candidateName)) {
            int count = candidateVotes.get(candidateName);
            candidateVotes.put(candidateName, count + 1);
        } else {
            candidateVotes.put(candidateName, 1);
        }
    }

    public void printVoteCount() {
        System.out.println("Total vote count: " + totalVoteCount);
        System.out.println("Vote count for each candidate:");
        for (String candidate : candidateVotes.keySet()) {
            int count = candidateVotes.get(candidate);
            System.out.println(candidate + ": " + count);
        }
    }
}
public class BDVotingSystem {

    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();
        votingSystem.initializeVoters();
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        String currentNid = "";

        System.out.println("-------------------------------");
        System.out.println("  Bangladesh Voting System");
        System.out.println("-------------------------------");

        while (true) {
            if (!loggedIn) {
                System.out.println("\n1. Login");
            } else {
                System.out.println("\n1. Logout");
            }
            System.out.println("2. Vote");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (!loggedIn) {
                        System.out.print("Enter NID: ");
                        String nid = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        if (votingSystem.login(nid, password)) {
                            loggedIn = true;
                            currentNid = nid;
                            System.out.println("Login successful.");
                        } else {
                            System.out.println("Invalid NID or password. Please try again.");
                        }
                    } else {
                        loggedIn = false;
                        currentNid = "";
                        System.out.println("Logged out successfully.");
                    }
                    break;

                case 2:
                    if (!loggedIn) {
                        System.out.println("You must be logged in to vote.");
                        break;
                    }
                    votingSystem.vote(currentNid);
                    break;

                case 3:
                    System.out.println("\nVote count:");
                    votingSystem.printVoteCount();
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
