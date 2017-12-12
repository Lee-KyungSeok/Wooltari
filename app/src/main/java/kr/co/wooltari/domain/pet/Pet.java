package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-08.
 */

public class Pet {
    // @SerialzedName : JSON으로 serialize 될 때 매칭되는 이름을 명시하는 목적으로 사용되는 field 마킹 어노테이션이다.
    // @Expose : object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략해 준다. (serialize, deserialize 를 정의하여 때에 따라 사용할 수 있다.)
    @Expose(serialize = false, deserialize = true)
    private Owner owner;
    @Expose
    private int pk;
    @Expose
    private boolean is_active;
    @Expose
    private String species;
    @Expose
    private String breeds;
    @Expose
    private String name;
    @Expose
    private String birth_date;
    @Expose
    private String gender;
    @Expose
    private String identified_number;
    @Expose
    private boolean is_neutering;
    @Expose
    private String body_color;
    @Expose
    private String ages;

    // 나중에 프로필을 보여줄 때
    @Expose(serialize = false, deserialize = true)
    private String profileUrl;

    public String getBody_color() {
        return body_color;
    }

    public void setBody_color(String body_color) {
        this.body_color = body_color;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIs_neutering() {
        return is_neutering;
    }

    public void setIs_neutering(boolean is_neutering) {
        this.is_neutering = is_neutering;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreeds() {
        return breeds;
    }

    public void setBreeds(String breeds) {
        this.breeds = breeds;
    }

    public String getIdentified_number() {
        return identified_number;
    }

    public void setIdentified_number(String identified_number) {
        this.identified_number = identified_number;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    @Override
    public String toString() {
        return "ClassPojo [body_color = " + body_color + ", birth_date = " + birth_date + ", species = " + species + ", is_active = " + is_active + ", name = " + name + ", is_neutering = " + is_neutering + ", owner = " + owner + ", gender = " + gender + ", breeds = " + breeds + ", identified_number = " + identified_number + ", pk = " + pk + "]";
    }
}
