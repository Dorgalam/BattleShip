package com.battleship.FXUI;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionEffects {
    static FadeTransition fadeEffect(Node firstItem) {
        final FadeTransition fadeTo = new FadeTransition(Duration.millis(1000), firstItem);
        fadeTo.setFromValue(0);
        fadeTo.setToValue(1);
        fadeTo.play();
        return fadeTo;
    }
    static FadeTransition fadeEffect(Node firstItem, double from, int time) {
        final FadeTransition fadeTo = new FadeTransition(Duration.millis(time), firstItem);
        fadeTo.setFromValue(from);
        fadeTo.setToValue(1);
        fadeTo.play();
        return fadeTo;
    }
    static FadeTransition fadeEffect(Node firstItem, Node secondItem) {
        final FadeTransition fadeFrom = new FadeTransition(Duration.millis(1000), secondItem);
        fadeFrom.setFromValue(1);
        fadeFrom.setToValue(0);
        fadeFrom.play();
        return fadeEffect(firstItem);
    }

}
