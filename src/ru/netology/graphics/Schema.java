package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchema;

public class Schema implements TextColorSchema {
    char[] chars;

    public Schema(char[] chars) {
        this.chars = chars;
    }

    public Schema() {
        this.chars = new char[]{'#', '$', '@', '%', '*', '+', '-'};
    }

    @Override
    public char convert(int color) {
        return chars[Math.round(color* (chars.length - 1) / 255)];
    }
}
