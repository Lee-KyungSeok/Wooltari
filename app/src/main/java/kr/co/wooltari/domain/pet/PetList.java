package kr.co.wooltari.domain.pet;

/**
 * Created by Kyung on 2017-12-09.
 */

public class PetList {
    private String count;
    private Pet[] results;
    private String previous;
    private String next;

    public Pet[] getResults() {
        return results;
    }

    public void setResults(Pet[] results) {
        this.results = results;
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
        return "ClassPojo [results = " + results + ", previous = " + previous + ", count = " + count + ", next = " + next + "]";
    }
}
