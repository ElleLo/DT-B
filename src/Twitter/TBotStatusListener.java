package Twitter;


import org.apache.commons.lang3.ArrayUtils;
import twitter4j.*;


import java.util.ArrayList;

/**
 * Created by elhj on 27/5/17.
 */
public class TBotStatusListener implements StatusListener{

    String returnStatus;

    public static ArrayList<String> statuses = new ArrayList<String>();


    @Override
    public void onStatus(Status s) {

        long tweetId = s.getId();
        String user = s.getUser().getScreenName();

        long[] userList = {
                "insert twitter IDs here"};


        if (containsTracked(s, userList)){

            if (s.isRetweet()){
                String retweet = "";

                retweet += user + " retweeted: \n";

                //System.out.println("Retweet");

                Status r = s.getRetweetedStatus();
                retweet += "https://twitter.com/" + r.getUser().getScreenName() + "/status/" + r.getId();

                statuses.add(retweet);
            }else{
                //System.out.println("Tweet");
                returnStatus = user + " tweeted: \n https://twitter.com/" + user + "/status/" + tweetId;
                statuses.add(returnStatus);
            }

        }

    }



    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {

    }

    private boolean containsTracked(Status s, long[] userList){
        long retweetUser = s.getUser().getId();
        if (ArrayUtils.contains(userList,retweetUser)){
            return true;
        }

        return false;
    }

}
