package dao.mapper;

import static entity.Sex.FEMALE;
import static entity.Sex.MALE;

import entity.Sex;

public class SexMapper {
    public Sex mapFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        return switch (stringValue) {
            case "MALE" -> MALE;
            case "FEMALE" -> FEMALE;
            default -> throw new IllegalArgumentException("Unknown Sex value " + stringValue);
        };
    }

    public String mapToDatabaseColumn(Sex sex) {
        if (sex == null) {
            return null;
        }
        return switch (sex) {
            case MALE -> "MALE";
            case FEMALE -> "FEMALE";
        };
    }
}
