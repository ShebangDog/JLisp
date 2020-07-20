package type;

import java.util.HashMap;

public class Symbol extends Atom {
    public final String name;
    public T value;
    public T function;

    private static HashMap<String, Symbol> symbolTable = new HashMap<>();
    public static Symbol symbolT = Symbol.symbol("T");
    public static Symbol symbolQuit = Symbol.symbol("QUIT");

    static {
        symbolT.value = symbolT;
    }

    private Symbol(String name) {
        this.name = name;
    }

    public static Symbol symbol(java.lang.String name) {
        if (symbolTable.get(name) == null) {
            Symbol symbol = new Symbol(name);
            symbolTable.put(name, symbol);
        }
        return symbolTable.get(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}