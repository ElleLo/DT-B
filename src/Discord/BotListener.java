package Discord;

import Twitter.TBotStatusListener;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import sun.plugin2.message.Message;
import twitter4j.FilterQuery;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


import java.io.*;
import java.util.*;

public class BotListener extends ListenerAdapter{

    private boolean retrieving = false;
    private UserListFunctions SLF = new UserListFunctions();

    @Override
    public void onReady(ReadyEvent event)
    {
        System.out.println("Status: Online");
        startTwitterBot();
        System.out.println("Retrieving future tweets.");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMessage().getRawContent().equalsIgnoreCase("ping")){
            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " pong").queue();
        }else if(e.getMessage().getRawContent().equalsIgnoreCase("!shrug")){
            e.getChannel().sendMessage("¯\\_(ツ)_/¯").queue();
        }else if (e.getMessage().getRawContent().equalsIgnoreCase("!start")) {

            if (!retrieving){
                e.getChannel().sendMessage("Retrieving tweets.").queue();
                runTimer(e);
                retrieving = true;
            }else{
                e.getChannel().sendMessage("Tweet retrieval already in progress.").queue();

            }

        }else if(e.getMessage().getRawContent().contains("!add")){
            String[] addCommand = e.getMessage().getContent().split(" ");
            Long newUser = Long.parseLong(addCommand[1]);
            System.out.println(newUser);

            SLF.writeSL(newUser);

            e.getChannel().sendMessage("test complete").queue();
        }
    }


    private void startTwitterBot(){
        ConfigurationBuilder cf = new ConfigurationBuilder();

        cf.setDebugEnabled(true)
                .setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
                .setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
                .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);


        TwitterStream twitterStream = new TwitterStreamFactory(cf.build()).getInstance();

        twitterStream.addListener(new TBotStatusListener());

        SLF.readSL();
        SLF.printSL();

        FilterQuery fq = new FilterQuery();

        fq.follow(SLF.getSL());

        twitterStream.filter(fq);

    }

    private void runTimer(MessageReceivedEvent e){

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            int count=0;
            @Override
            public void run() {
                count++;
                while (!TBotStatusListener.statuses.isEmpty()) {
                    e.getChannel().sendMessage(TBotStatusListener.statuses.get(0)).queue();
                    TBotStatusListener.statuses.remove(0);
                }
            }
        }, 0, 20000);
    }


}
