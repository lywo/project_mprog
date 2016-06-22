# Report #

 
![](./doc/screenshot-resize.png)

#### Description
When an user of the app has a really bad date, but wants to save their night, this app has 4 different solutions. There are 2 possibilities to get out of the date and 2 to help the date forward. By user of the four icon in the screen centre, any of there can be put in action. 


#### Features
This app knows 4 features: 

1. By clicking on the newspaper-icon on the main screen, the current news is loaded into a new screen. At least you have something to talk about know. If you want the full article, just click on the title of the news item. 
2. Is the user ready for some more spicy conversation, then the questionmark-icon can be pressed. This loads 101 dating questions, showed 1 by 1 on the screen. If an user likes a question, it can be saved and showed in a list on the bottom of the screen by pressing on the star-button. Swiping the question to the left shows the previous questions and swiping to the right shows the next question.
3. The fake call: when the user presses the phone-icon, a new screen is initialized after a short delay. The app addapts to the user volume settings and plays a ringtone, vibrates or stays quite during the fake call. This is the perfect time to lose this horrible date of yours!
4. Via the settings-icon in the top right of the app, a user can select a contact from the contact list and type an emergency sms previous to the date. By pressing the sms-icon on the main screen, the sms will automatically be send, so your hero can come to recue you. 

## Technical Description##
####Overview Activities
![](./doc/activities-final-uml.png)

#### Overview connections Objects & classes
![](./doc/files-final-uml.png)

## Challenges during development ##
In an ideal world I would have made an English and a Dutch version. Now everything is in English exept the news, which is in Dutch. I could not find an API for date questions, so they are hardcoded and not translatable. If I had more time I would have put more time in investigation website API's to get local news. There is no API for whole Netherlands, Europe or world to get local news via GPS information. 

During the development I changes some of my other technical designs. First it looked impossible to send an previously typed en saved sms to a chosen contact without leaving the app. But I found a class: smsManager that does this for you. This meant the smsActivity and screen were not necessary anymore. 
I also dropped some of my objects (like Question Object) and put more of my functionality in the Database (10 functions with queries, instead of 3). This made the technical design a lot more clear and clean. Loosing the question Object was possible when I started using a primairy integer as key and a boolean to save whether a question is favourite or not. There was no permanent ArrayList with favourite quesitons needed anymore, just a temporary one to fill the ListView
For the fake call, there are easy ways to get information from the phone to get user settings on notification volume via an AudioManager Class and GetSystemService(). Next was playing the users set ringtone or vibrating the phone. The user's ringtone was found via a RingtoneManager class and played by a Mediaplayer class. Vibrating the phone needed a permission but could easily be activated via Vibrator.vibrate(). To make it more real, an delay was implemented after the phone-icon was pressed via a Handler class and .postdelayed(). 
Another permission was needed to select a contacts from the contacts app on the phone. 

- onSwipeListener met een niet random question

- xml parser problems 

## Argument (defend decisions) ##

