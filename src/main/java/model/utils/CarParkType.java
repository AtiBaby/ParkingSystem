package model.utils;

/**
 * Created by holhosa on 2017.09.20..
 *
 * Ha módosítjuk, akkor a resourcebundle-t is alakítani kell!!!
 */
public enum CarParkType {

    OUTDOOR_CAR_PARK("Outdoor car park", "Szabadtéri parkoló"),
    PARKING_HOUSE("Parking garage", "Parkolóház"),
    UNDERGROUND_GARAGE("Underground garage", "Mélygarázs");

    private String nameEN;
    private String nameHU;

    CarParkType(String nameEN, String nameHU) {
        this.nameEN = nameEN;
        this.nameHU = nameHU;
    }
}
