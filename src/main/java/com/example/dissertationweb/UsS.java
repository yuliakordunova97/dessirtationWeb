package com.example.dissertationweb;

import java.util.*;
import java.util.stream.Collectors;

public class UsS {
    int number;
    int storyPoint;
    List<UsS> previousStories;

    public UsS(int number, int storyPoint) {
        this.number = number;
        this.storyPoint = storyPoint;
        this.previousStories = new ArrayList<>();
    }


    public UsS() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(int storyPoint) {
        this.storyPoint = storyPoint;
    }

    public List<UsS> getPreviousStories() {
        return previousStories;
    }

    public void setPreviousStories(List<UsS> previousStories) {
        this.previousStories = previousStories;
    }


    @Override
    public String toString() {
        return "UserStory{" +
                "number=" + number +
                ", storyPoint=" + storyPoint +
                ", previousStories=" + previousStories +
                '}';
    }

    public String getPreviousStoriesString() {
        // Перетворюємо список в рядок за допомогою toString()
        return previousStories != null ? previousStories.toString() : "";
    }

    public void setPreviousStoriesStr(String previousStoriesString, List<UsS> u) {
        if (previousStoriesString != null && !previousStoriesString.isBlank()) {
            List<UsS> previousStoriesList = Arrays.stream(previousStoriesString.split(","))
                    .map(Integer::parseInt)
                    .map(number -> {
                        UsS previousUsS = new UsS();
                        if (number == 0){
                            previousUsS.number = 0;
                            previousUsS.storyPoint = 0;
                        }else {
                            for (UsS usS: u){
                                if (number == usS.getNumber()) previousUsS = usS;
                            }
                        }
                        return previousUsS;
                    })
                    .collect(Collectors.toList());
            this.setPreviousStories(previousStoriesList);
        } else {
            System.err.println("!!!!!!!!!Треба доробити! !!!!!");
        }
    }




    //simplelog4j slf4j loombok log.
}
