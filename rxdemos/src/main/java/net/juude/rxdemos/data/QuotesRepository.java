package net.juude.rxdemos.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juude on 16/6/27.
 */
public class QuotesRepository {
    public static List<QuoteItem> getQuotes() {
        QuoteItem item = new QuoteItem();
        item.author = "Da Vinci";
        item.quote = "Life is pretty simple: You do some stuff. Most fails. Some works. You do more of what works. If it works big, others quickly copy it. Then you do something else. The trick is the doing something else.";
        ArrayList<QuoteItem> list = new ArrayList<>();
        list.add(item);
        return list;
    }
}
