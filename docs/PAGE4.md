# PAGE 4: Mixins
Sometimes the standard API or Event System isn't enough. If you need to change how a specific game function works (for example, change the formula for calculating battle losses or modify the UI creation logic), you use **Mixins**.

## 1. What is a mixin?
A Mixin is a class in your mod that "injects" its code into an existing class of the game. FLoader uses Javassist to rewrite the game's bytecode at runtime, inserting calls to your Java methods.

## 2. Basic structure
To create a mixin, you need to use two main annotations:

- **@Mixin**: Marks the class as a mixin and specifies the target game class.
- **@Inject**: Marks a method to be injected into a specific game method.

### Important rules
- **Static Methods**: All injected methods in your mixin class must be public static
- **Matching Arguments**: The arguments of your mixin method must exactly match the arguments of the game's method.

## 3. Example
```java
@Mixin(target = "aoc.kingdoms.lukasz.jakowski.AA_Game")
public class ExampleMixin {
    @Inject(method = "create", at = At.RETURN)
    public static void create() {
        System.out.println("ExampleMixin called.");
    }
}
```

## 4. Injection points (At)
- **At.HEAD**: Your code will run at the very beginning of the game method, before any original logic.
- **At.RETURN**: Your code will run at the very end of the method, just before it returns a value.