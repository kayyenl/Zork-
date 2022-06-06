import java.util.List;

class Troll implements Items {
    private static final int maxstate = 5;
    private final int state;
    private static final String itemname = "Troll";
    private static final ImList<String> textlist = 
        ImList.of(List.<String>of("Troll lurks in the shadows.",
        "Troll is getting hungry.","Troll is VERY hungry.",
        "Troll is SUPER HUNGRY and is about to ATTACK!",
        "Troll attacks!"));

    Troll() {
        this.state = 0;
    }

    public boolean getPickupState() {
        return false;
    }

    public Items changeToPickup() {
        return this;
    }

    public int checkState() {
        return this.maxstate;
    }

    public int getState() {
        return this.state;
    }

    Troll(int prevstate) {
        if (prevstate < maxstate - 1) {
            this.state = prevstate + 1;
        } else {
            this.state = prevstate;
        }
    }

    public Items stateChange() {
        return new Troll(this.state);
    }

    public String getName() {
        return this.itemname;
    }

    public String identify() {
        return this.itemname;
    }

    public String toString() {
        return textlist.get(this.state);
    }




}
