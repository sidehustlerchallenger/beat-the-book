package com.beat.book.gamble;

public class BeatTheBook {

    public BeatTheBookInput beatTheBookInput;

    public BeatTheBook(BeatTheBookInput beatTheBookInput) {
        this.beatTheBookInput = beatTheBookInput;
    }

    public static class BeatTheBookInput {
        public Pair input;
        public Pair ProfitBoostPair;
        public int numberOfIterations;

        public BeatTheBookInput(Pair input, Pair profitBoostPair, int numberOfIterations) {
            this.input = input;
            ProfitBoostPair = profitBoostPair;
            this.numberOfIterations = numberOfIterations;
        }

        @Override
        public String toString() {
            return "BeatTheBookInput{" +
                    "input=" + input +
                    ", ProfitBoostPair=" + ProfitBoostPair +
                    ", numberOfIterations=" + numberOfIterations +
                    '}';
        }
    }

    public class BeatTheBookOutput {
        public Pair originalWinnings;
        public Pair boostWinnings;
        public BeatTheBookOutput(Pair originalWinnings, Pair boostWinnings) {
            this.originalWinnings = originalWinnings;
            this.boostWinnings = boostWinnings;
        }

        @Override
        public String toString() {
            return "BeatTheBookOutput{" +
                    "originalWinnings=" + originalWinnings +
                    ", boostWinnings=" + boostWinnings +
                    '}';
        }
    }

    public static class Pair {
        public float numberOne;
        public float numberTwo;

        public Pair(float numberOne, float numberTwo) {
            this.numberOne = numberOne;
            this.numberTwo = numberTwo;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "numberOne=" + numberOne +
                    ", numberTwo=" + numberTwo +
                    '}';
        }
    }

    public BeatTheBookOutput playTheGame() {
        Pair impliedProbabilityPair = new Pair(findImpliedProbability(beatTheBookInput.input.numberOne), findImpliedProbability(beatTheBookInput.input.numberTwo));
        Pair assumedProbabilityPair = convertToAssumedProbability(impliedProbabilityPair);
        Pair perWin = convertToPerWin(beatTheBookInput.input);
        Pair totalWinnings = new Pair(0f, 0f);
        Pair totalWinningsWithProfitBoost = new Pair(0f, 0f);
        for (int i = 0; i < beatTheBookInput.numberOfIterations; i++) {
            if (Math.random() <= assumedProbabilityPair.numberOne) {
                totalWinnings.numberOne += perWin.numberOne;
                totalWinningsWithProfitBoost.numberOne += perWin.numberOne + convertToBoostWinning(perWin.numberOne);
            } else {
                totalWinnings.numberTwo += perWin.numberTwo;
                totalWinningsWithProfitBoost.numberTwo += perWin.numberTwo + convertToBoostWinning(perWin.numberTwo);
            }
        }
        return new BeatTheBookOutput(
                new Pair(convertToRealWinnings(totalWinnings.numberOne, beatTheBookInput.numberOfIterations),
                        convertToRealWinnings(totalWinnings.numberTwo, beatTheBookInput.numberOfIterations)),
                new Pair(convertToRealWinnings(totalWinningsWithProfitBoost.numberOne, beatTheBookInput.numberOfIterations),
                        convertToRealWinnings(totalWinningsWithProfitBoost.numberTwo, beatTheBookInput.numberOfIterations)));
    }

    private float convertToBoostWinning(float perWin) {
        return (float) ((perWin - 100) * .5);
    }

    private float convertToRealWinnings(float totalWinnings, int numberOfIterations) {
        return totalWinnings / numberOfIterations;
    }

    private Pair convertToPerWin(Pair pair) {
        return new Pair(convertToPerWin(pair.numberOne), convertToPerWin(pair.numberTwo));
    }

    private float convertToPerWin(float single) {
        if (single < 0) {
            return 100 / (Math.abs(single)) * 100 + 100;
        }
        return single + 100;
    }

    private float findImpliedProbability(float number) {
        if (number < 0) {
            return Math.abs(number) / (Math.abs(number) + 100);
        }
        return 100 / (number + 100);
    }

    private Pair convertToAssumedProbability(Pair impliedProbabilityPair) {
        float totalImpliedProbability = impliedProbabilityPair.numberOne + impliedProbabilityPair.numberTwo;
        if (totalImpliedProbability > 1) {
            float reduction = (totalImpliedProbability - 1) / 2;
            return new Pair(impliedProbabilityPair.numberOne - reduction, impliedProbabilityPair.numberTwo - reduction);
        }
        return impliedProbabilityPair;
    }
}
