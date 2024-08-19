package com.epam.rd.autocode.factory.plot;

class PlotFactories {

    public PlotFactory classicDisneyPlotFactory(Character hero, Character beloved, Character villain) {
//        throw new UnsupportedOperationException();
        return () -> new Plot() {
            @Override
            public String toString() {
                return hero.name() + " is great. " +
                        hero.name() + " and " + beloved.name() + " love each other. " +
                        villain.name() + " interferes with their happiness but loses in the end.";
            }
        };
    }

    public PlotFactory contemporaryDisneyPlotFactory(Character hero, EpicCrisis epicCrisis, Character funnyFriend) {
//        throw new UnsupportedOperationException();
        return () -> new Plot() {
            @Override
            public String toString() {
                return hero.name() + " feels a bit awkward and uncomfortable. But personal issues fades, when a big trouble comes - " +
                        epicCrisis.name() + ". " +
                        hero.name() + " stands up against it, but with no success at first.But putting self together and help of friends, including spectacular funny " +
                        funnyFriend.name() +
                        " restore the spirit and " + hero.name() + " overcomes the crisis and gains gratitude and respect";
            }
        };
    }

    public PlotFactory marvelPlotFactory(Character[] heroes, EpicCrisis epicCrisis, Character villain) {
//        throw new UnsupportedOperationException();
        return () -> new Plot() {
            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                for (Character hero : heroes) {
                    builder.append("brave ").append(hero.name()).append(", ");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.deleteCharAt(builder.length() - 1);
                builder.append(" on guard.");

                return epicCrisis.name() + " threatens the world." +
                        " But " + builder + " So, no way that intrigues of " +
                        villain.name() + " overcome the willpower of inflexible heroes";
            }
        };
    }
}
