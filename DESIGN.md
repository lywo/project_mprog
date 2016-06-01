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
![](https://www.lucidchart.com/publicSegments/view/b98ca287-1489-4449-8e58-c3ad600b69d9/image.png)


#### Activities & Views
![](https://www.lucidchart.com/publicSegments/view/2a811053-9345-4b78-aa9d-d0fb5b5b6b82/image.png)

## Detail Sketches
to do

## Database format 
Ik ga een database gebruiken met 2 tabellen erin. De Questions Table heeft 101 rijen om allen questions op te slaan met een id, een string en een boolean. De boolean wordt gebruikt om aan te geven of het een favourite is of niet. De Settings table wordt gebruikt om alle instellingen op te slaan. 
Links op de afbeelding staat de indeling van de Questions table weergegeven en rechts de indeling van de Settings table. 

![](https://www.lucidchart.com/publicSegments/view/5293631a-51f8-44a6-a94f-a5cf2665959f/image.png)







