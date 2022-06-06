import java.util.List;

class Candle implements Items {
    private static final int maxstate = 4;
    private final int state;
    private static final String itemname = "Candle";
    private static final ImList<String> textlist = 
        ImList.of(List.<String>of("Candle flickers.",
        "Candle is getting shorter.", "Candle is about to burn out.",
        "Candle has burned out."));

    Candle() {
        this.state = 0;
    }

    public boolean getPickupState() {
        return false;
    }

    public int checkState() {
        return this.maxstate;
    }

    public int getState() {
        return this.state;
    }

    public Items changeToPickup() {
        return this;
    }

    Candle(int prevstate) {
        if (prevstate < maxstate - 1) {
            this.state = prevstate + 1;
        } else {
            this.state = prevstate;
        }
    }

    public Items stateChange() {
        return new Candle(this.state);
    }

    public String getName() {
        return this.itemname;
    }

    public String identify() {
        return String.format("%s", this.itemname);
    }

    public String toString() {
        return textlist.get(this.state);
    }
}
