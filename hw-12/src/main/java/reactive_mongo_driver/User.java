package reactive_mongo_driver;

import org.bson.Document;

import static reactive_mongo_driver.Constants.*;


public class User {
    private final int id;
    private final String name;
    private final String currency;

    public User(Document doc) {
        this(doc.getInteger(ID_FIELD), doc.getString(NAME_FIELD), doc.getString(CURRENCY_FIELD));
    }

    public User(int id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public Document getDocument() {
        return new Document(ID_FIELD, id).append(NAME_FIELD, name).append(CURRENCY_FIELD, currency);
    }

    public String getCurrency() {
        return this.currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", currency='" + currency + "'" +
                "}";
    }
}
