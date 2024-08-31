package com.beat.book.gamble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeatTheBookTest {
    @Test
    void playTheGameWithoutBoost() {
        BeatTheBook.Pair input = new BeatTheBook.Pair(-126f, 108f);
        float boost = .5f;
        BeatTheBook.BeatTheBookInput beatTheBookInput = new BeatTheBook.BeatTheBookInput(input, boost, 10000);
        BeatTheBook beatTheBook = new BeatTheBook(beatTheBookInput);
        System.out.println(beatTheBook.playTheGame());
    }
}