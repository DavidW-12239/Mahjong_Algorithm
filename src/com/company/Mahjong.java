package com.company;

import java.util.Arrays;

/**
 * @author Yawei Xi
 * @date 2018-10-9
 */
class MahjongCore {

    /**
     Different types of cards
     */
    private static final int[] ALL_CARDS = new int[]{
            11, 12, 13, 14, 15, 16, 17, 18, 19,
            21, 22, 23, 24, 25, 26, 27, 28, 29,
            31, 32, 33, 34, 35, 36, 37, 38, 39,
            50, 60, 70, 80, 90, 100, 110
    };

    /**
     * check the wining
     *
     * @param cards
     * @return win (true), not win (false)
     */
    public static boolean isHu(int[] cards) {
        if (null == cards) {
            return false;
        }
        // cards count must be 2, 8, 11 or 14
        if (cards.length != 2 && cards.length != 5 && cards.length != 8 && cards.length != 11 && cards.length != 14) {
            return false;
        }
        // take the Jiangs out
        int[] js = getJiangs(cards);
        if (null == js || js.length <= 0) {
            return false;
        }

        for (int j : js) {
            int[] tempCards = Arrays.copyOf(cards, cards.length);
            tempCards = removeOne(tempCards, j);
            tempCards = removeOne(tempCards, j);
            Arrays.sort(tempCards);
            // remove all ke
            tempCards = removeAllKe(tempCards);
            if (tempCards.length <= 0) {
                return true;
            }

            // removeAllShun
            tempCards = removeAllShun(tempCards);
            if (tempCards.length <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * get all the Jiang
     *

     */
    private static int[] getJiangs(int[] cards) {
        int[] res = new int[0];
        if (null != cards && cards.length > 1) {
            for (int i = 0; i < cards.length - 1; i++) {
                if (cards[i] == cards[i + 1]) {
                    res = add(res, cards[i]);
                    i++;
                }
            }
        }
        return res;
    }

    /**
     * remove all ke
     *
     * @param cards
     */
    private static int[] removeAllKe(int[] cards) {
        for (int i = 0; i < cards.length - 2; i++) {
            if (cards[i] == cards[i + 1] && cards[i] == cards[i + 2]) {
                cards = removeOne(cards, cards[i]);
                cards = removeOne(cards, cards[i]);
                cards = removeOne(cards, cards[i]);
            }
        }
        return cards;
    }

    /**
     * remove all shun
     *
     * @param cards
     */
    private static int[] removeAllShun(int[] cards) {
        int[] res = Arrays.copyOf(cards, cards.length);
        for (int i = 0; i < cards.length - 2; i++) {
            if (cards[i] + 1 == cards[i + 1] && cards[i + 1] + 1 == cards[i + 2]) {
                res = removeOne(res, cards[i]);
                res = removeOne(res, cards[i + 1]);
                res = removeOne(res, cards[i + 2]);
                i += 2;
            }
        }
        return res;
    }

    /**
     * get card without suit
     *
     * @param card
     * @return card without suit
     */
    private static int getCardWithoutSuit(int card) {
        return card % 10;
    }

    /**
     * all card to the cards
     *
     * @param cards
     * @param card
     * @return cards after adding
     */
    private static int[] add(int[] cards, int card) {
        int[] res = new int[cards.length + 1];
        System.arraycopy(cards, 0, res, 0, cards.length);
        res[res.length - 1] = card;
        return res;
    }

    /**
     * remove one card from the cards
     *
     * @param cards
     * @param card  
     * @return
     */
    private static int[] removeOne(int[] cards, int card) {
        if (null == cards || cards.length <= 0) {
            return cards;
        }
        Arrays.sort(cards);
        int index = Arrays.binarySearch(cards, card);
        if (index >= 0) {
            int[] res = new int[cards.length - 1];
            int j = 0;
            for (int i = 0; i < cards.length; i++) {
                if (i != index) {
                    res[j++] = cards[i];
                }
            }
            return res;
        }
        return cards;
    }

}
