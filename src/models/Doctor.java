package models;

public class Doctor {
    private String id;
    private String name;
    private String specialization;
    private String availableDays;   // e.g. "MON,WED,FRI"
    private String availableTime;   // e.g. "10:00-14:00"

    public Doctor(String id, String name, String specialization, String availableDays, String availableTime) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.availableTime = availableTime;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getAvailableDays() { return availableDays; }
    public String getAvailableTime() { return availableTime; }

    @Override
    public String toString() {
        return id + "," + name + "," + specialization + "," + availableDays + "," + availableTime;
    }

    public static Doctor fromString(String line) {
        String[] arr = line.split(",");
        return new Doctor(
                arr[0],
                arr[1],
                arr[2],
                arr[3],
                arr[4]
        );
    }
}
