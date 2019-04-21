package main.service;

public class LambdaResistant <Type>{
    private Type value;
    public LambdaResistant(Type value) {
        this.value = value;
    }

    public Type get() {
        return value;
    }

    public void set(Type value) {
        this.value = value;
    }
}
