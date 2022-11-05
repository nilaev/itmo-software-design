package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.DB;

import java.util.Optional;

public class SumQuery extends Query<Optional<Long>> {

    public void executeQuery() {
        queryResult = DB.executeSQLQuery(DB.CALC_SUM, DB::extractLong);
    }

    public void initRespond() {
        re.addLine("Summary price: ");
    }

    public void putResultInRespond() {
        queryResult.ifPresent(re::addLine);
    }

}