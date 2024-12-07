package com.kraemer;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface MyAiTriagedReview {

    @UserMessage("""
                Your task is to process the review delimited by ---.
                Apply a sentiment analysis to the review to determine if it is positive or negative, considering various languages.

                For example:
                - "I love your bank, you are the best!" is a 'POSITIVE' review
                - "J'adore votre banque" is a 'POSITIVE' review
                - "I hate your bank, you are the worst!" is a 'NEGATIVE' review

                Respond with a JSON document containing:
                - the 'evaluation' key set to 'POSITIVE' if the review is positive, 'NEGATIVE' otherwise
                - the 'message' key set to a message thanking or apologizing to the customer. These messages must be polite and match the review's language.

                ---
                {review}
                ---
            """)
    TriagedReview triage(String review);
}