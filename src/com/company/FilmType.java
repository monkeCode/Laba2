package com.company;

public enum FilmType {
    D2(1), D3(2), D4(4);
    private final int _maskCode;

    FilmType(int code) {
        _maskCode = code;
    }

    public int get_maskCode() {
        return _maskCode;
    }
}
