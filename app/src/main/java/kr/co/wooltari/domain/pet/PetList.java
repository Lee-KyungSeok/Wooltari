package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Kyung on 2017-12-09.
 */

public class PetList {
    @Expose
    private String count;
    @Expose
    private List<Pet> pets;
    @Expose
    private String previous;
    @Expose
    private String next;

    public List<Pet> getResults() {
        return pets;
    }

    public void setResults(List<Pet> results) {
        this.pets = results;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "ClassPojo [results = " + pets + ", previous = " + previous + ", count = " + count + ", next = " + next + "]";
    }
}
