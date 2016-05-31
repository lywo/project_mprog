# Design #

## Data 
 
### Van buitenaf
- Data vragen hard coden in een ArrayList<String>: http://www.rsdnation.com/psychopathic/blog/101-unique-questions-ask-first-date. Om de beurt 1 laten zien.
Favorieten opslaan in een andere ArrayList<String> voor constant ListView
- nieuws: NOS news RSS feed http://feeds.nos.nl/nosjournaal

### Opslaan
- SQLiteDatabase: opslaan vragen / favoriete vragen / contact/ text sms
- contact selection emergency sms gebruik permission in manifest:
<uses-permission android:name= "android.permission.READ_CONTACTS"/>
Gebruik functies/intents voor ophalen contacten. Wacht op resultaat en roep functie voor contactSelection aan. 

## Overzicht Structuur

#### Objects
![](https://www.lucidchart.com/publicSegments/view/446a4f91-a6c2-4a45-9961-a1828136a253/image.png)


#### Activities & Views
![](https://www.lucidchart.com/publicSegments/view/74e4dc91-4762-448b-be27-a7cd3f897dd2/image.png)
**ADD GO OUT FOR CONTACTS**

## Detail Sketches
to do

## Database format 
to do tabel maken met columns 







