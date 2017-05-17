package hello;

public class Greeting {

    private final long id;
    private final boolean result;

    public Greeting(long id, boolean result) {
        this.id = id;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public boolean getResult() {
        return result;
    }
}
