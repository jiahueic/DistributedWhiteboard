package message;

import java.util.ArrayList;

public class ComplexTestClass extends Message {
    private ArrayList<TestClass> testClasses;

    public ComplexTestClass(){
        super();
    }

    public ComplexTestClass(ArrayList<TestClass> testClasses){
        super();
        this.testClasses = testClasses;
    }

    public ArrayList<TestClass> getTestClasses() {
        return testClasses;
    }

    public void setTestClasses(ArrayList<TestClass> testClasses) {
        this.testClasses = testClasses;
    }
}
