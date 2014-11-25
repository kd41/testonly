package ee.test.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Program {
    public final static double EPSILON = 0.0000001d;
    
    public static void main(String[] args) {
        
        roundExaple();
        
//        BigDecimal a = new BigDecimal("1.0");
//        BigDecimal b = new BigDecimal("1.00");
//        System.out.println(a.equals(b));
//        System.out.println(a.compareTo(b));
        
//        BigDecimal[] amountInTournamentCurrency = tournament.getBaseCurrencyCode().equals(results.getCurrency()) ?
//                new BigDecimal[] { new BigDecimal(results.getBetAmount()), new BigDecimal(results.getWinAmount()) } :
//                getBetAndWinInTournamentCurrency(tournament, results);
//        final BigDecimal betInTournamentCurrency = amountInTournamentCurrency[0];
//        final BigDecimal winInTournamentCurrency = amountInTournamentCurrency[1];
//        final BigDecimal pointsToAdd = tournament.getBetPoints(betInTournamentCurrency);
//        int gameRoundsToAdd = 1;
//        logger.debug("Adding " + pointsToAdd + " points and " + gameRoundsToAdd + " games to tournament " + tournament.getCode() + " player " + username);
//        addPoints(con, pointsToAdd, gameRoundsToAdd, tournament.getCode(), username);
//        statisticsService.addReport(new StatisticsEntity(tournament.getCode(), username, results.getGameCode(), betInTournamentCurrency, winInTournamentCurrency));

        for(BigDecimal d : getBetAndWinInTournamentCurrency())
        System.out.println(d);
}

    private static BigDecimal[] getBetAndWinInTournamentCurrency() {
        BigDecimal rateToBase = new BigDecimal(2);
        BigDecimal betInCurrency = new BigDecimal(4);
        BigDecimal winInCurrency = new BigDecimal(6);
        return new BigDecimal[] { rateToBase.multiply(betInCurrency), rateToBase.multiply(winInCurrency) };
    }
    
    private static void roundExaple() {
        BigDecimal initialBet = new BigDecimal(3);
        int initialPoints = 8;
        BigDecimal initialRate = new BigDecimal(initialPoints).divide(initialBet, 16, RoundingMode.FLOOR);
      System.out.println("initialRate: " + initialRate + ", doubleValue: " + initialRate.doubleValue());
      System.out.println("initialRate: " + floor(initialRate.doubleValue()));
    }
    
    public static long floor(double val) {
        if (Math.abs(val - Math.round(val)) < EPSILON) {
            return (long)Math.round(val);
        }
        return (long)(val);
    }
    
    public static double round(double val, int prec) {
        double pow = Math.pow(10, prec);
        return floor(val*pow)/pow;
    }

}
