interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {
    public void fly() {
        System.out.println("Duck is flying...");
    }

    public void swim() {
        System.out.println("Duck is swimming...");
    }
}

public class MultipleInheritanceExample {
    public static void main(String[] args) {
        Duck myDuck = new Duck();
        myDuck.fly();
        myDuck.swim();
    }
}
