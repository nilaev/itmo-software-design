package netty_http_server;

import com.mongodb.rx.client.Success;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import reactive_mongo_driver.Product;
import reactive_mongo_driver.ReactiveMongoDriver;
import reactive_mongo_driver.User;
import rx.Observable;

import java.util.*;

import static reactive_mongo_driver.Constants.*;


public class RxNettyHttpServer {
    public static void main(final String[] args) {
        HttpServer
                .newServer(PORT)
                .start((req, resp) -> {
                    Observable<String> response;
                    String command = req.getDecodedPath().substring(1);
                    Map<String, List<String>> queryParam = req.getQueryParameters();
                    switch (command) {
                        case "createUser":
                            response = createUser(queryParam);
                            resp.setStatus(HttpResponseStatus.OK);
                            break;
                        case "createProduct":
                            response = createProduct(queryParam);
                            resp.setStatus(HttpResponseStatus.OK);
                            break;
                        case "getUsers":
                            response = getUsers();
                            resp.setStatus(HttpResponseStatus.OK);
                            break;
                        case "getProducts":
                            response = getProducts(queryParam);
                            resp.setStatus(HttpResponseStatus.OK);
                            break;
                        default:
                            response = Observable.just("Такую команду я не знаю :(");
                            resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                    }
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    public static Observable<String> createUser(Map<String, List<String>> queryParam) {
        ArrayList<String> required = new ArrayList<>(Arrays.asList(ID_FIELD, CURRENCY_FIELD, NAME_FIELD));
        if (isCompleteRequest(queryParam, required)) {
            return Observable.just("Ошибка: Неправильные аттрибуты " +
                    "\nПример правильных аттрибутов: /createUser?id=1&name=nik&currency=rub");
        }

        if (ReactiveMongoDriver.createUser(
                new User(
                        Integer.parseInt(queryParam.get(ID_FIELD).get(0)),
                        queryParam.get(NAME_FIELD).get(0),
                        queryParam.get(CURRENCY_FIELD).get(0)
                )
        ) == Success.SUCCESS) {
            return Observable.just("Успешно");
        } else {
            return Observable.just("Ошибка: Не получилось добавить в базу");
        }
    }

    public static Observable<String> createProduct(Map<String, List<String>> queryParam) {
        ArrayList<String> required = new ArrayList<>(Arrays.asList(ID_FIELD, NAME_FIELD, RUB, EUR, USD));
        if (isCompleteRequest(queryParam, required)) {
            return Observable.just("Ошибка: Неправильные аттрибуты" +
                    "\nПример правильных аттрибутов: /createProduct?id=1&name=chair&rub=80&eur=1&usd=2");
        }

        if (ReactiveMongoDriver.createProduct(
                new Product(
                        Integer.parseInt(queryParam.get(ID_FIELD).get(0)),
                        queryParam.get(NAME_FIELD).get(0),
                        queryParam.get(RUB).get(0),
                        queryParam.get(EUR).get(0),
                        queryParam.get(USD).get(0)
                )
        ) == Success.SUCCESS) {
            return Observable.just("Успешно");
        } else {
            return Observable.just("Ошибка: Не получилось добавить в базу");
        }
    }

    public static Observable<String> getUsers() {
        Observable<String> users = ReactiveMongoDriver.getUsers();
        return Observable.just("{ users = [").concatWith(users).concatWith(Observable.just("]}"));

    }

    public static Observable<String> getProducts(Map<String, List<String>> queryParam) {
        ArrayList<String> needValues = new ArrayList<>(Collections.singletonList(ID_FIELD));
        if (isCompleteRequest(queryParam, needValues)) {
            return Observable.just("Ошибка: Неправильные аттрибуты" +
                    "\nПример правильных аттрибутов: /getProducts?id=1");
        }

        Integer id = Integer.valueOf(queryParam.get(ID_FIELD).get(0));

        Observable<String> products = ReactiveMongoDriver.getProducts(id);
        return Observable.just("{ user_id = " + id + ", products = [").concatWith(products).concatWith(Observable.just("]}"));
    }

    private static boolean isCompleteRequest(Map<String, List<String>> queryParam, List<String> required) {
        for (String value : required) {
            if (!queryParam.containsKey(value)) {
                return true;
            }
        }
        return false;
    }
}
