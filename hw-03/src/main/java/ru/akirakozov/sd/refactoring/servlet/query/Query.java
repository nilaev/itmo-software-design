package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.HttpResponse;

public abstract class Query<T> {

    protected HttpResponse re = new HttpResponse();
    protected T queryResult;

    public static Query<?> init(String command) {
        switch (command) {
            case "min":
                return new MinQuery();
            case "max":
                return new MaxQuery();
            case "sum":
                return new SumQuery();
            case "count":
                return new CountQuery();
            default:
                return new UnknownCommandQuery();
        }
    }

    public HttpResponse execute() {
        executeQuery();
        initRespond();
        putResultInRespond();
        return re;
    }

    abstract public void executeQuery();

    abstract public void initRespond();

    abstract public void putResultInRespond();

}
