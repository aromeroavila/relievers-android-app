package arao.relieversapp.model;

public class Room {

    private String id;
    private String name;
    private long time;
    private boolean available;
    private boolean inQuarantine;

    public Room(boolean available, String id, boolean inQuarantine, String name, long time) {
        this.available = available;
        this.id = id;
        this.inQuarantine = inQuarantine;
        this.name = name;
        this.time = time;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isInQuarantine() {
        return inQuarantine;
    }

    public void setInQuarantine(boolean inQuarantine) {
        this.inQuarantine = inQuarantine;
    }
}
