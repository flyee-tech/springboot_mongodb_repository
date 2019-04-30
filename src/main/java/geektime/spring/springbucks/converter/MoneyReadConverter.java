package geektime.spring.springbucks.converter;
import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-04-30
 */
public class MoneyReadConverter implements Converter<Document, Money> {

    @Override
    public Money convert(Document document) {
        Document money = (Document) document.get("money");
        double amount = Double.parseDouble(money.getString("amount"));
        String currency = ((Document) money.get("currency")).getString("code");
        return Money.of(CurrencyUnit.of(currency), amount);
    }

}
