package message;

public class TestClass {
    private String name;
    private int id;

    public TestClass(){

    }

    public TestClass(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
