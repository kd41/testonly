package ee.test.list;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class Program {
	private enum GameStage {
        DEAL, FOLD, FLOP, TURN, RIVER;
    }
	
	public static String SEPARATOR = ":";
	
	public static void main(String... args) throws UnknownHostException {

        System.out.println(InetAddress.getLocalHost().getHostName());
        System.out.println(System.getProperty("user.name"));
        
        
        
        System.out.println("GameStage: " + GameStage.DEAL);
		

		List<String> playerCards = new ArrayList<String>(2);
		playerCards.add("H1");
		playerCards.add("H2");
		List<String> flopCards = new ArrayList<String>(5);
		flopCards.add("Q1");
		flopCards.add("Q2");
		flopCards.add("Q3");
		flopCards.add("Q4");
		flopCards.add("Q5");
		for (String string : flopCards) {
            flopCards.remove(0);
        }
		
		List<String> dealerCards = new ArrayList<String>(2);

		System.out.println(getCardsAsString(playerCards, 2));
		System.out.println(getCardsAsString(flopCards, 5));
		System.out.println(getCardsAsString(dealerCards, 2));
	}
	
	private static String getCardsAsString(List<String> cards, int expectedCardCount) {
        StringBuilder sb = new StringBuilder(32);
        int count = 1;
        if (CollectionUtils.isNotEmpty(cards)) {
        	count--;
            for (Iterator<String> iter = cards.iterator();;) {
                count++;
                String card = iter.next();
                sb.append(card);
                if (iter.hasNext()) {
                    sb.append(SEPARATOR);
                } else {
                    break;
                }
            }
        }
        for (int i = 0; i < expectedCardCount - count; i++) {
            sb.append(SEPARATOR);
        }
        return sb.toString();
    }
}
