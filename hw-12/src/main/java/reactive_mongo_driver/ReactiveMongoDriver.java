package reactive_mongo_driver;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;
import static reactive_mongo_driver.Constants.*;


public class ReactiveMongoDriver {
    public static MongoClient client = createMongoClient();
    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    public static Success createUser(User user) {
        return client
                .getDatabase(DATABASE)
                .getCollection(USERS_COLLECTION)
                .insertOne(user.getDocument())
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .toBlocking()
                .single();
    }

    public static Success createProduct(Product product) {
        return client
                .getDatabase(DATABASE)
                .getCollection(PRODUCTS_COLLECTION)
                .insertOne(product.getDocument())
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .toBlocking()
                .single();
    }

    public static Observable<String> getUsers() {
        return client
                .getDatabase(DATABASE)
                .getCollection(USERS_COLLECTION)
                .find()
                .toObservable()
                .map(d -> new User(d).toString())
                .reduce((u1, u2) -> u1 + ", " + u2);
    }

    public static Observable<String> getProducts(Integer id) {
        return client
                .getDatabase(DATABASE)
                .getCollection(PRODUCTS_COLLECTION)
                .find()
                .toObservable()
                .map(document -> new Product(document).toString(findUser(id).getCurrency()))
                .reduce((product1, product2) -> product1 + ", " + product2);
    }

    public static User findUser(Integer id) {
        return client
                .getDatabase(DATABASE)
                .getCollection(USERS_COLLECTION)
                .find(eq(ID_FIELD, id))
                .first()
                .map(User::new)
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .toBlocking()
                .single();
    }
}
