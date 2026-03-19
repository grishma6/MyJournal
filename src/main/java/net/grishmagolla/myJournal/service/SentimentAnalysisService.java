package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.enums.Sentiment;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public Sentiment getSentiment(String text) {
        if (text == null || text.isBlank()) {
            return Sentiment.ANXIOUS;
        }

        String lowerText = text.toLowerCase();

        if (lowerText.contains("happy") || lowerText.contains("good") || lowerText.contains("great")) {
            return Sentiment.HAPPY;
        } else if (lowerText.contains("sad") || lowerText.contains("bad") || lowerText.contains("upset")) {
            return Sentiment.SAD;
        } else if (lowerText.contains("angry") || lowerText.contains("frustrated")) {
            return Sentiment.ANGRY;
        }

        return Sentiment.ANXIOUS;
    }
}