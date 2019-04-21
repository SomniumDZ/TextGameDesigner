package main.service;

public class LambdaResistantReference<Type>{
    private Type value;
    public LambdaResistantReference(Type value) {
        this.value = value;
    }

    public Type get() {
        return value;
    }

    public void set(Type value) {
        this.value = value;
    }
}
