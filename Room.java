import java.util.function.Function;
import java.util.Optional;

public class Room {
   
    private final ImList<Items> itemlist;
    private final String roomname;
    private static final int trollindicator = 5;
    private static final int swordindicator = 1;
    private final Optional<Room> prevroom;


    Room(String name) {
        this.roomname = name;
        this.itemlist = ImList.of();
        this.prevroom = Optional.<Room>empty();
    }   

    Room(String name, ImList<Items> itemlist, Optional<Room> prevroom) {
        this.roomname = name;
        this.itemlist = itemlist;
        this.prevroom = prevroom;
    }

    Room back() {
        Room resultroom = this.prevroom.map(x -> x).orElse(this);
        if (this.isSwordPickup()) {
            return resultroom.add((new Sword()).changeToPickup()).tick();
        }
        return this.prevroom.map(x -> x.tick()).orElse(this);
    }

    Room(String name, Room room) { //modified backroom function just for L5
        this.roomname = name;
        this.itemlist = room.provideItems();
        this.prevroom = Optional.<Room>of(room);
    }

    String getName() {
        return this.roomname;
    }

    ImList<Items> provideItems() {
        return this.itemlist;
    }

    Room add(Items item) {
        ImList<Items> placeholder = ImList.<Items>of();
        placeholder = placeholder.addAll(this.itemlist);
        placeholder = placeholder.add(item);
        return new Room(this.roomname, placeholder, this.prevroom); 
    }

    Room go(Function<Room, Room> func) {
        Room standby = func.apply(this); //called only once, solved the 2 output bug
        if (this.isSwordPickup()) {
            String newname = standby.getName();
            ImList<Items> newitems = standby.provideItems();
            Room roombuilder = new Room(roomname).add(new Sword().changeToPickup());
            ImList<Items> tempsword = roombuilder.provideItems();
            tempsword = tempsword.addAll(newitems);
            return new Room(newname, tempsword, Optional.<Room>of(this).map(x -> x.removeSword())); 
        }
        return new Room(standby.getName(),
            standby.provideItems(), Optional.<Room>of(this));
    }

    Room tick(Function<Room, Room> func) {
        return func.apply(this).tick();
    }

    Room tick() {
        ImList<Items> placeholder = ImList.<Items>of();
        placeholder = placeholder.addAll(this.itemlist);
        for (int i = 0; i < placeholder.size(); i++) {
            placeholder = placeholder.set(i, placeholder.get(i).stateChange()); 
        }
        return new Room(this.roomname, placeholder, this.prevroom); //modded, shld be ok
    }

    boolean isTroll() {
        for (int i = 0; i < itemlist.size(); i++) {
            if (itemlist.get(i).checkState() == this.trollindicator) {
                return true;
            }
        }
        return false;
    }

    boolean isSword() {
        for (int i = 0; i < itemlist.size(); i++) {
            if (itemlist.get(i).checkState() == this.swordindicator) {
                return true;
            }
        }
        return false;
    }

    Room killTroll() {
        ImList<Items> placeholder = ImList.<Items>of();
        placeholder = placeholder.addAll(this.itemlist);
        for (int i = 0; i < placeholder.size(); i++) {
            if (placeholder.get(i).checkState() == this.trollindicator) {
                placeholder = placeholder.remove(i).second();
            }
        }
        return new Room(this.roomname, placeholder, this.prevroom); //modded, shld be ok
    }

    Room pickSword() {
        ImList<Items> placeholder = ImList.<Items>of();
        placeholder = placeholder.addAll(this.itemlist);
        for (int i = 0; i < itemlist.size(); i++) {
            if (itemlist.get(i).checkState() == this.swordindicator) {
                placeholder = placeholder.set(i, placeholder.get(i).changeToPickup());
            }
        }
        return new Room(this.roomname, placeholder, this.prevroom); //modded, shld be ok
    }

    Room droppingSword() {
        ImList<Items> placeholder = ImList.<Items>of();
        placeholder = placeholder.addAll(this.itemlist);
        for (int i = 0; i < itemlist.size(); i++) {
            if (itemlist.get(i).checkState() == this.swordindicator) {
                placeholder = placeholder.set(i, placeholder.get(i).changeToPickup());
            }
        }
        return new Room(this.roomname, placeholder, this.prevroom); //modded, shld be ok
    }

    Room removeSword() {
        ImList<Items> placeholder = ImList.<Items>of();
        placeholder = placeholder.addAll(this.itemlist);
        for (int i = 0; i < placeholder.size(); i++) {
            if (placeholder.get(i).checkState() == this.swordindicator) {
                placeholder = placeholder.remove(i).second();
            }
        }
        return new Room(this.roomname, placeholder, this.prevroom);
    }

    boolean isSwordPickup() {
        for (int i = 0; i < itemlist.size(); i++) {
            if (itemlist.get(i).getPickupState() == true) {
                return true;
            }
        }
        return false;
    }

    private String concatter() {
        String result = "";
        for (int i = 0; i < itemlist.size(); i++) {
            result += "\n";
            result += itemlist.get(i).toString();
        }
        return result;
    }

    public String toString() {
        return String.format("@%s%s", this.roomname, this.concatter());
    }
    
}
