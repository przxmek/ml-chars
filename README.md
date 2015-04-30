# Raport

Jako metrykę zdecydowałem się używać odległości w postaci sumarycznej
różnicy koloru wszystkich pikseli (czarny lub biały) między dwoma obrazkami.
To znaczy, że gdy obrazek A ma piksel (x, y) czarny, a obrazek B ma
piksel (x, y) biały lub nie ma piksela (x, y) to różnica wynosi 1, a wpp 0.
Taka metryka daje dosyć dobre przybliżenie "stopnia różnicy" dwóch obrazków,
a dla przedstawionych danych jest także wystarczaja ze względu na złożoność
czasową.

Wybrana metoda klastrowania to **Farthest First**, jej zaletą jest szybkość
działąnia i skuteczność klastrowania (obrazki w poszczególnych klastrach są
faktycznie podobne), jednak wymaga określenia liczby klastrów, co powoduje,
że przy określeniu zbyt niskiej liczby klastrów, poszczególne klastry
zawierają różne litery, a przy określeniu zbyt wysokiej liczby klastrów
jedna litera może znaleźć się w kilku klastrach.
