package Twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;



public class TBotDriver {
    public static void main(String[] argv) throws TwitterException {
        ConfigurationBuilder cf = new ConfigurationBuilder();

        cf.setDebugEnabled(true)
                .setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
                .setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
                .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);

        //user stream

        TwitterStream twitterStream = new TwitterStreamFactory(cf.build()).getInstance();

        twitterStream.addListener(new TBotStatusListener());

        long[] idList = {"list with Long type"};

        FilterQuery kwFilterQuery = new FilterQuery();

        kwFilterQuery.follow(idList);

        twitterStream.filter(kwFilterQuery);


    }
}
