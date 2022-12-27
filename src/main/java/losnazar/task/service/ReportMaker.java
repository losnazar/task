package losnazar.task.service;

import java.util.Map;
import losnazar.task.model.CurrencyPrice;

public interface ReportMaker {
    String report(Map<CurrencyPrice.Pair, String> data);
}
