import java.util.List;

class Sword implements Items {
    private static final int maxstate = 1;
    private final int state;
    private final boolean pickup;
    private static final String itemname = "Sword";
    private static final ImList<String> textlist =
        ImList.of(List.<String>of("Sword is shimmering."));

    Sword() {
        this.pickup = false;
        this.state = 0;
    }

    Sword(int currstate, boolean pickup) { //change pickup constructor
        this.pickup = pickup;
        this.state = currstate;
    }

    public boolean getPickupState() {
        return this.pickup;
    }

    public Items changeToPickup() {
        return new Sword(this.state, !this.pickup);
    }

    public int checkState() {
        return this.maxstate;
    }

    public int getState() {
        return this.state;
    }

    Sword(int prevstate, boolean pickup, String indicator) {  //changing state constructor
        this.pickup = pickup;
        if (prevstate < maxstate - 1) {
            this.state = prevstate + 1;
        } else {
            this.state = prevstate;
        }
    }

    public Items stateChange() {
        return new Sword(this.state, this.pickup, "indicator");
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
