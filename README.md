# Конвертер в текстовую графику

![](pics/preview.png)

| Класс / Интерфейс      | Для чего? |
| ----------- | ----------- |
| `BadImageSizeException`      | Класс исключения, которое вы будете выбрасывать       |
| `TextColorSchema`   | Интерфейс цветовой схемы, который вы будете реализовывать        |
| `TextGraphicsConverter`   | Интерфейс конвертера картинок, который вы будете реализовывать        |
| `GServer`   | Готовый класс сервера, который будет использовать ваш конвертер; вам туда лезть не нужно        |
| `Main`   | Запуск приложения. В нём запускается сервер, также в нём можно будет конвертировать картинки в текстовые файлы без сервера        |

Пример работы конвертера, результат работы которого выводим в консоль:
```java
        String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);
```

В итоге мы видим такой результат, где более тёмные участки заменяются на более "жирные" символы, а светлые на более незаметные символы:

![](pics/simple-test-demo.jpeg)

Общая схема работы метода `convert` будет соответствовать следующей последовательности действий (они подробнее описаны ниже):
1. Скачиваем картинку по URL;
2. Менеджеру могли выставить максимально допустимое соотношение сторон (ширины и высоты); если оно слишком большое, то конвертация не делается и выбрасывается исключение;
3. При конвертации мы будем менять каждый пиксель на символ: чем пиксель темнее, тем «жирнее» символ, который мы подставим. Менеджеру могли выставить максимальные ширину и высоту итоговой картинки, при этом если исходная картинка больше, то нам надо уменьшить её размер, соблюдая пропорции;
4. Превращаем цветное изображение в чёрно-белое (чтобы мы смотрели только на интенсивность цвета, а не подбирали для красного одни символы, для зелёного другие и т. п);
1. Перебираем все пиксели изображения, спрашивая у них степень белого (число от 0 до 255, где 0 — это чёрный, а 255 — это светлый). В зависимости от этого числа выбираем символ из заранее подготовленного набора;
1. Собираем все полученные символы в единую строку, отдаём как результат конвертации.

Пример сконвертированной картинки:

![gg](https://user-images.githubusercontent.com/98952360/195162389-d0f3ec84-00aa-4f2a-b086-ea7495ebeb0f.png)


