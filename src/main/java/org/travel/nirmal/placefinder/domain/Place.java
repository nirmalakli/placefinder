package org.travel.nirmal.placefinder.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Place implements Serializable {
    private final String name;
    private final String type; // TODO: Promote type to an enum
    private final Coordinate coordinate;

    public Place(String name, String type, Coordinate coordinate) {
        this.name = name;
        this.type = type;
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(name, place.name) &&
                Objects.equals(type, place.type) &&
                Objects.equals(coordinate, place.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, coordinate);
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
