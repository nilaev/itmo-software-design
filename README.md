# Условия домашних заданий по Software Design

## [Лабораторная 1.](https://github.com/nilaev/itmo-software-design/tree/main/hw1)
Цель: получить практический опыт применения динамических проверок в коде (assertions).

Необходимо реализовать структуры данных LRUCache на хешмапе и двусвязном списке. При реализации необходимо самостоятельно продумать 
возможные проверки pre/post-условий и инвариантов класса. Придуманные проверки необходимо добавить в код реализации в виде assertions. 
Класс необходимо покрыть тестами.

Указания:
- использовать LinkedHashMap напрямую нельзя
- задание можно сдавать по почте, в теме письма указать [SD-TASK]


## [Лабораторная 2.](https://github.com/nilaev/itmo-software-design/tree/main/hw2)
Цель: получить практический опыт реализация модульных тестов и тестов, использующих mock-объекты.

Необходимо реализовать компонент, который будет вычислять частоту появления твитов с
определенным хештегом за последние несколько часов. Для выполнения лабораторной
необходимо использовать twitter api (https://dev.twitter.com/rest/public/search) или api любой
другой социальной сети (например, vk не требует авторизации).
На входе компонент должен принимать:
- хештег, по которому будет идти поиск
- N - число часов, за которые необходимо построить диаграмму твитов (1 <= N <= 24)
На выходе нужно выдать массив из N целых чисел - каждое число в массиве определяет число
твитов в соответствующий час.

Указания:
- при выполнении лабораторной рекомендуется применять SOLID-принципы;
- код должен быть покрыт тестами (в том числе mock-тестами и тестами с StubServer)

Примеры из лекции:
https://github.com/akirakozov/example-apps/tree/master/java/mock-example

## [Лабораторная 3](https://github.com/nilaev/itmo-software-design/tree/main/hw3)
Цель: получить практический опыт применения техник рефакторинга.

Скачайте приложение: https://github.com/akirakozov/software-design/tree/master/java/refactoring

Приложение представляет собой простой web-server, который хранит информацию о товарах и
их цене. Поддержаны такие методы:
- http://localhost:8081/get-products - посмотреть все товары в базе
- http://localhost:8081/add-product?name=iphone6&price=300 - добавить новый товар
- http://localhost:8081/query?command=sum - выполнить некоторый запрос с данными в базе

Необходимо отрефакторить этот код (логика методов не должна измениться), например:
- убрать дублирование
- выделить отдельный слой работы с БД
- выделить отдельный слой формирования html-ответа
- и тд

Указание:
- задание нужно сдавать через e-mail, в заголовке письма указать “[SD-TASK]”
- проект перенести к себе github.com
- сначала добавить тесты (отдельными комитами)
- каждый отдельный рефакторинг делать отдельным комитом
- без истории изменений и тестов баллы буду сниматься
