package com.company;

import java.util.Arrays;
import java.util.List;

enum HallType
{
    Simple, Vip, Comfort, Imax, Children
}
public record Hall(HallType type, int capacity, FilmType... supportedTypes)
{
    public boolean CanShow(Film film)
    {
        return (Arrays.stream(supportedTypes).mapToInt(FilmType::get_maskCode).sum()
                & film.type().get_maskCode()) == film.type().get_maskCode();
    }

    @Override
    public String toString() {
        return
                "type: " + type +
                "\ncapacity: " + capacity +
                "\nsupported films: " + Arrays.toString(supportedTypes);
    }
}
