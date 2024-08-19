package com.epam.rd.autocode.startegy.cards;

import java.util.*;

public class CardDealingStrategies {

//    TreeMap<String, List<Card>> cardsStack = new TreeMap<>();

    private static void DealPerPlayer(Deck deck, int players, TreeMap<String, List<Card>> cardStack, int cardsCount) {
        for(int i = 0; i < cardsCount; i++){
            for(int j = 0; j < players; j++){
                List<Card> cards = cardStack.getOrDefault("Player" + " " + (j + 1), new ArrayList<>());
                cards.add(deck.dealCard());
                cardStack.put("Player" + " " + (j + 1), cards);
            }
        }
    }

    public static CardDealingStrategy texasHoldemCardDealingStrategy() {

//        throw new UnsupportedOperationException();
        return new CardDealingStrategy() {

            TreeMap<String, List<Card>> cardStack = new TreeMap<>();

            @Override
            public Map<String, List<Card>> dealStacks(Deck deck, int players) {

                if(!cardStack.isEmpty()){
                    cardStack.clear();
                }

                DealPerPlayer(deck, players, cardStack,2);

                List<Card> additional = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                       additional.add(deck.dealCard());
                }
                cardStack.put("Community", additional);

                List<Card> rest = new ArrayList<>();
                while(deck.size() > 0){
                    rest.add(deck.dealCard());
                }
                cardStack.put("Remaining", rest);

                return cardStack;
            }
        };
    }

    public static CardDealingStrategy classicPokerCardDealingStrategy() {

//        throw new UnsupportedOperationException();
        return new CardDealingStrategy() {

            TreeMap<String, List<Card>> cardStack = new TreeMap<>();

            @Override
            public Map<String, List<Card>> dealStacks(Deck deck, int players) {
                if(!cardStack.isEmpty()){
                    cardStack.clear();
                }
                DealPerPlayer(deck, players, cardStack, 5);
                cardStack.put("Remaining", new LinkedList<>(deck.restCards()));

                return cardStack;
            }
        };
    }

    public static CardDealingStrategy bridgeCardDealingStrategy(){


//        throw new UnsupportedOperationException();

        TreeMap<String, List<Card>> cardStack = new TreeMap<>();

        return  new CardDealingStrategy() {
            @Override
            public Map<String, List<Card>> dealStacks(Deck deck, int players) {
                DealPerPlayer(deck, players, cardStack, 13);
                return cardStack;
            }
        };
    }

    public static CardDealingStrategy foolCardDealingStrategy(){

//        throw new UnsupportedOperationException();

        TreeMap<String, List<Card>> cardStack = new TreeMap<>();

        return  new CardDealingStrategy() {
            @Override
            public Map<String, List<Card>> dealStacks(Deck deck, int players) {

                if(!cardStack.isEmpty()){
                    cardStack.clear();
                }

                DealPerPlayer(deck, players, cardStack, 6);
                cardStack.put("Trump card", List.of(deck.dealCard()));
                List<Card> rest = new ArrayList<>();
                while(deck.size() > 0){
                    rest.add(deck.dealCard());
                }
                cardStack.put("Remaining", rest);
                return cardStack;
            }
        };
    }

}
