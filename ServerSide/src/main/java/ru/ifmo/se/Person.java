package ru.ifmo.se;

import ru.ifmo.se.annotations.JORM;
import ru.ifmo.se.annotations.JORMIgnoredColumn;
import ru.ifmo.se.enums.State;
import ru.ifmo.se.exceptions.NotEnoughMoneyException;
import ru.ifmo.se.exceptions.TooMuchMoneyException;

import java.awt.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@JORM
public abstract class Person implements Serializable, Comparable {
    private String name;
    private String last_name;
    private int age;
    private int steps_from_door = (int) (Math.random()*100);
    private int x;
    private int y;
    @JORMIgnoredColumn
    private List<GeneralClothes> generalClothes = new ArrayList<>();
    @JORMIgnoredColumn
    private List<Shoes> shoes = new ArrayList<>();
    @JORMIgnoredColumn
    private List<Accessories> accessories = new ArrayList<>();
    private State state;
    private Color color;
    private ZonedDateTime time;

    public Person(String name) {
        time = ZonedDateTime.now();
        this.name = name;
        this.setState();
        getSteps_from_door();
        set_X_Y();
    }

    public Person() {
        time = ZonedDateTime.now();
        name = "Stranger";
        this.setState();
        getSteps_from_door();
        set_X_Y();
    }

    public Person(String name, String last_name) {
        time = ZonedDateTime.now();
        this.name = name;
        this.last_name = last_name;
        this.setState();
        getSteps_from_door();
        set_X_Y();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setState() {
        switch ((int) (Math.random()*10)) {
            case 0:
            case 1:
            case 2:
                this.state = State.NEUTRAL;
                break;
            case 3:
            case 4:
            case 5:
                this.state = State.INTERESTED;
                break;
            case 6:
            case 7:
            case 8:
                this.state = State.BORED;
                break;
            case 9:
            case 10:
            default:
                this.state = State.ANGRY;
        }
        color = state.getColor();
    }

    public State getState() {
        return this.state;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void showClothes(List<? extends Clothes> clothes) {
        int i = 1;
        for (Clothes a : clothes) {
            System.out.print(a.toString());
            if (i++ < clothes.size())
                System.out.print(", ");
        }
    }

    public int getSteps_from_door() {
        if (steps_from_door == 0)
            steps_from_door = (int) (Math.random()*100);
        return steps_from_door;
    }

    public void set_X_Y(){
        x = (int)(Math.random()*100+Math.random()*50);
        y = (int) Math.sqrt(Math.abs(steps_from_door*steps_from_door- x*x));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getClothes(List<? extends Clothes> clothes) {
        StringBuilder tempString = new StringBuilder();
        int i = 1;
        for (Clothes a : clothes) {
            tempString.append(a.toString());
            if (i++ < clothes.size())
                tempString.append(", ");
        }
        return tempString.toString();
    }

    public List<Accessories> getAccessories() {
        return accessories;
    }

    public List<GeneralClothes> getGeneralClothes() {
        return generalClothes;
    }

    public List<Shoes> getShoes() {
        return shoes;
    }

    public void addGeneralClothes(GeneralClothes thing, List<? super GeneralClothes> clothes) {
        if (thing == null)
            return;
        clothes.add(thing);
    }

    public void addShoes(Shoes thing, List<? super Shoes> clothes) {
        if (thing == null)
            return;
        clothes.add(thing);
    }

    public void addAccessories(Accessories thing, List<? super Accessories> clothes) {
        if (thing == null)
            return;
        clothes.add(thing);
    }

    public void describe() {
        StringBuilder tempString = new StringBuilder();
        tempString.append(this.toString());
        tempString.append(" is");
        if (this.generalClothes !=null && (this.generalClothes.size() != 0 || this.shoes.size() != 0 || this.accessories.size() != 0)) {
            tempString.append(" wearing ");
            tempString.append(this.getClothes(this.generalClothes));
        }
        if (this.shoes != null && this.shoes.size() != 0) {
            tempString.append(", ");
            tempString.append(this.getClothes(this.shoes));
        }
        if (this.accessories != null && this.accessories.size() != 0) {
            tempString.append(", ");
            tempString.append(this.getClothes(this.accessories));
        }
        tempString.append(" standing in " + this.steps_from_door + " steps from the door");
        if (tempString.length() > this.toString().length())
            tempString.append(".");
        System.out.println(tempString.toString());
    }

    public String description() {
        StringBuilder tempString = new StringBuilder();
        tempString.append(this.toString());
        if (this.generalClothes !=null && (this.generalClothes.size() != 0 || this.shoes.size() != 0 || this.accessories.size() != 0)) {
            tempString.append(" is wearing ");
            tempString.append(this.getClothes(this.generalClothes));
        }
        if (this.shoes != null && this.shoes.size() != 0) {
            tempString.append(", ");
            tempString.append(this.getClothes(this.shoes));
        }
        if (this.accessories != null && this.accessories.size() != 0) {
            tempString.append(", ");
            tempString.append(this.getClothes(this.accessories));
        }
        tempString.append(" standing in " + this.steps_from_door + " steps from the door");
        if (tempString.length() > this.toString().length())
            tempString.append(".");
        return tempString.toString();
    }

    public Bed getBed(double paid) {
        System.out.println(this.toString() + " pays " + paid + " to get a bed set up.");
        Bed bed = new Bed();
        try {
            bed.checkMoney(paid, bed.price);
            System.out.println(new GettingBedding() {
                @Override
                public String giveSomething() {
                    return "Bed is ready.";
                }
            }.giveSomething());
            bed.makeReady();
        } catch (NotEnoughMoneyException e) {
            System.out.println("Not enough money.");
        } catch (TooMuchMoneyException e) {
            System.out.println("Bed is ready, don't forget your change.");
            bed.makeReady();
        } finally {
            return bed;
        }
    }

    public void removeBed(Bed bed) {
        //without if NothingToRemoveException could be thrown
        System.out.println(this.toString() + " wants the bed to be removed.");
        if (bed.isReady()) {
            bed.makeUnready();
            System.out.println("Bed is removed.");
        }
    }

    @Override
    public String toString() {
        if (this.last_name != null)
            return this.name + " " + this.last_name;
        else
            return this.name;
    }

    @Override
    public boolean equals(Object a) {
        if (a == this)
            return true;
        if (a == null || getClass() != a.getClass())
            return false;
        Person other = (Person) a;
        return state == other.state && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Person ob = (Person) o;
        if (this.name != null && this.name.compareTo(ob.getName()) > 0)
            return 1;
        else if (this.name != null && this.name.compareTo(ob.getName()) < 0)
            return -1;
        if (this.last_name != null && this.last_name.compareTo(ob.getLast_name()) > 0)
            return 1;
        else if (this.last_name != null && this.last_name.compareTo(ob.getLast_name()) < 0)
            return -1;
        else
            return 0;
    }
}
