package ru.akirakozov.sd.refactoring.servlet.query;

public class UnknownCommandQuery extends Query<Void> {

    public void executeQuery() { }

    public void initRespond() {
        re.addLine("Unknown command.");
    }

    public void putResultInRespond() { }

}
