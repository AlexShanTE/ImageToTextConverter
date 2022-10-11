package ru.netology.graphics;

import ru.netology.graphics.image.*;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        char[] charsArr = new char[]{'▐', '▓', '▒', '░', '†', '+'};
        Schema schema = new Schema();
        Schema schema2 = new Schema(charsArr);
        TextGraphicsConverter converter = new Converter(); // Создайте тут объект вашего класса конвертера
//        converter.setTextColorSchema(schema);
//        converter.setTextColorSchema(schema2);
        GServer server = new GServer(converter); // Создаём объект сервера
        server.start();// Запускаем
//        String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
//        String imgTxt = converter.convert(url);
//        System.out.println(imgTxt);
    }
}
