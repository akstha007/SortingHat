package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

/**
 * Created by Uchiha Ashuke on 5/3/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private PrefManager prefManager;
    private String[] arrQuote = {
            "\"After all this time?\" \"Always\" said Snape.",
            "Dark times lie ahead of us and there will be a time when we must choose between what is right and what is easy.",
            "We’ve all got both light and dark inside us. What matters is the part we choose to act on. That’s who we really are.",
            "Things we lose have a way of coming back to us in the end, if not always in the way we expect.",
            "While we may come from different places and speak in different tongues, our hearts beat as one.",
            "Books! And cleverness! There are more important things — friendship and bravery and — oh Harry — be careful!",
            "Oh, to be young and to feel love’s keen sting.",
            "Differences of habit and language are nothing at all if our aims are identical and our hearts are open.",
            "It is impossible to manufacture or imitate love.",
            "I’d just been thinking, if you had died, you’d have been welcome to share my toilet.",
            "Just because you have the emotional range of a teaspoon doesn’t mean we all have.",
            "When you have seen as much of life as I have, you will not underestimate the power of obsessive love.",
            "Where your treasure is, there your heart will be also.",
            "We could all have been killed - or worse, expelled.",
            "There are some things you can’t share without ending up liking each other, and knocking out a twelve-foot mountain troll is one of them.",
            "It’s wingardium leviOsa, not leviosAH.",
            "Until the very end.",
            "I enjoyed the meetings, too. It was like having friends.",
            "We are only as strong as we are united, as weak as we are divided.",
            "It takes a great deal of bravery to stand up to our enemies, but just as much to stand up to our friends.",
            "We’re with you whatever happens.",
            "It does not do to dwell on dreams and forget to live.",
            "Of course it is happening inside your head, Harry, but why on earth should that mean that it is not real?",
            "But you know, happiness can be found even in the darkest of times, if one one only remembers to turn on the light.",
            "You sort of start thinking anything’s possible if you’ve got enough nerve.",
            "All was well.",
            "Nothing like a nighttime stroll to give you ideas.",
            "For in dreams we enter a world that is entirely our own. Let them swim in the deepest ocean or glide over the highest cloud.",
            "What’s comin’ will come, an’ we’ll meet it when it does.",
            "Chaos reigned.",
            "I solemnly swear that I am up to no good.",
            "We did it, we bashed them, wee Potter’s the one, and Voldy’s gone moldy, so now let’s have fun!",
            "Ah, music. A magic beyond all we do here!",
            "Have a biscuit, Potter.",
            "Hearing voices no one else can hear isn’t a good sign, even in the wizarding world.",
            "Time is making fools of us again.",
            "Nobody’s ever asked me to a party before, as a friend. Is that why you dyed your eyebrow, for the party? Should I do mine too?",
            "One can never have enough socks.",
            "Let us step out into the night and pursue that flighty temptress, adventure.",
            "Don’t let the Muggles get you down.",
            "Mischief managed.",
            "You’re a wizard, Harry.",
            "I am what I am, an’ I’m not ashamed. ‘Never be ashamed,’ my ol’ dad used ter say, ‘there’s some who’ll hold it against you, but they’re not worth botherin’ with.",
            "There is no need to call me Sir, Professor.",
            "It is our choices, Harry, that show what we truly are, far more than our abilities.",
            "This pain is part of being human … the fact that you can feel pain like this is your greatest strength.",
            "The best of us sometimes eat our words.",
            "I didn’t put my name in that cup, I don’t want eternal glory.",
            "Working hard is important. But there is something that matters even more, believing in yourself.",
            "It matters not what someone is born, but what they grow to be.",
            "Why spiders? Why couldn't it be \"follow the butterflies\"?",
            "Mr. and Mrs. Dursley, of number four Privet Drive, were proud to say that they were perfectly normal, thank you very much.",
            "\"I am not worried, Harry,\" said Dumbledore, his voice a little stronger despite the freezing water. \"I am with you.\"",
            "Don’t Pity the dead Harry, Pity the living, and those who live without love.",
            "I’m sorry, Professor. I must not tell lies.",
            "Wit beyond measure is man’s greatest treasure.",
            "The mind is not a book to be opened at will, and examined at leisure.",
            "Your soul is whole and completely yours, Harry",
            "You dare use my own spell against me Potter? Yes, I am the Half-Blood prince.",
            "I don't go looking for trouble, trouble usually finds me.",
            "It was the most beautiful magic. Wondrous to behold.",
            "I love magic.",
            "You’re just as sane as I am.",
            "We are all human, aren’t we? Every human life is worth the same, and worth saving.",
            "The world isn’t split into good people and Death Eaters. We’ve all got both light and dark inside us. What matters is the part we choose to act on. That’s who we really are.",
            "You know, Minister, I disagree with Dumbledore on may counts ... but you cannot deny he's got style.",
            "Mr. Moony presents his compliments to Professor Snape, and begs him to keep his abnormally large nose out of other people's business.",
            "Why are they all staring?\" demanded Albus as he and Rose craned around to look at the other students. \"Don’t let it worry you\" said Ron. \"It’s me. I’m extremely famous.\"",
            "...She was crying,\" Harry continued heavily. \"Oh,\" said Ron, his smile faded slightly. \"Are you that bad at kissing?\"",
            "\"I’m not going to be murdered,\" Harry said out loud. \"That’s the spirit, dear,\" said his mirror sleepily.",
            "And it’s Johnson, Johnson with the Quaffle, what a player that girl is, I’ve been saying it for years but she still won’t go out with me",
            "Always."
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        /*Calendar now = Calendar.getInstance();
        */

        prefManager = new PrefManager(context);
        int index = prefManager.getPoints("QuoteTimeLaunch");
        index = (index + 1) % arrQuote.length;

        prefManager.setPoints("QuoteTimeLaunch", index);
        //prefManager.setStringValue("TodaysQuote", arrQuote[index].trim());

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle(context.getResources().getString(R.string.message_box_title))
                        .setContentText(arrQuote[index].trim());
        Intent resultIntent = new Intent(context, QuoteActivity.class);
        resultIntent.putExtra("Quote", arrQuote[index].trim());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        int color = ContextCompat.getColor(context, R.color.orange);
        mBuilder.setColor(color);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

        /*int dayOfWeek = now.get(Calendar.DATE);
        if(dayOfWeek != 1 && dayOfWeek != 7) {

        }*/
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_notification_hat : R.drawable.ic_launcher;
    }
}