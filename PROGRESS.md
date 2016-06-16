# Progress #

### Day 1


- Niet genoeg gevonden voor oorspronkelijke idee. Geen databases met veel data en 3D visualisatie wordt te moeilijk
- Nieuw idee uitgewerkt : SaveTheNight app
- Op zoek gegaan naar mogelijkheden om toepassing te maken. 
- schetsen gemaakt voor basic design. 
- gevonden : custom search (news) API - google VOICE (call) - code (sms) - hardcoded questions. 


### Day 2
- Check Bing News API: makkelijk te gebruiken JSON response en beter een news API dan een algemene search engine. NEE deze gaat per category en niet per locatie. 
- Check : http://feeds.nos.nl/nosjournaal RSS feed. Returns xml. Maak news Object met titel, link en discription. 
- Vergeet per locatie, te ingewikkeld in combinatie met nieuws app. 
- Vragen API niet gevonden. Hard coden en swipen naar volgende vraag. Met een + opslaan in favorieten
- inlezen over design in android
- maken van een activity + scherm met een namaak incoming call met objects en buttons
- Verzenden van een sms permissie: <uses-permission android:name="android.permission.SEND_SMS"> gebruik smsManager
- sms versturen kan niet. smsManager gebruiken om naar sms app te gaan. 
- Opslaan in database is makkelijkste met allemaal columns.
- gebruik van integers bij een object van question, zodat je gewoon een array kan exporteren met alleen integers om de ListView van favourites te vullen. 
- Alle objects en activities en classes weergegeven in UML. 

### Day 3
- verder lezen over design
- Checken of telefoon op stil staat, dan geen geluid maar vibraten. 
- Update database: 2 tabellen, 1 van 101 rijen met de questions id, string en boolean. En een tweede tabel met de settings. Boolean wordt gebruikt voor opslaan van favourites. 
- Design nu in android style geschetst. Duidelijk voorbeeld. 
- denk aan audio! beter focussen op alleen ringtone, misschien ophalen uit de telefoon zelf. Importeren en toestaan in manifest!
- denk aan internet toegang in manifest. 

### Day 4
- begonnen met het maken van een prototype. Alle activiteiten en alle schermen. 
- geen gebruik maken van ImageButtons, maar ImageViews met een onClick functie in de activiteit. Op deze manier blijven de plaatjes makkelijker bewerkbaar.'
- gekozen voor een zwart incoming call scherm want het makkelijkste na te maken. On click functie in activity voor het middelste telefoon icoontje. De rest opgevuld met textViews en Imageviews. Nog toe te voegen geluid. 
- Nog linken en voorbeeld runnen! Picturen aangepast en git opgeschoond.

### Day 5
- asynctask file toegevoegd
- httprequestmanager toegevoegd
- objecten nieuws en vragen gemaakt
- DB helper toegevoegd, nog to do: implementatie en CRUD methods 

### Day 6
- als  de fake call wordt geïnitialiseerd moet het screen niet gelocked worden tijdens de 30 sec wacht tijd. 
- voor news feed gebruik maken van AynsTask, RSSfeedParser en functies in NewsItem Object. Koppeling moet nog beter! Nadenken over weergave in html string of ArrayList met Items, niet duidelijk nog. 
- Row_layout file voor news Listview aangemaakt. Misschien standaard ArrayAdapter gebruiken. 

### Day 7
- It's something... nog maar 1 title van newsItem. Bij het runnen wordt niet de volgende start tag bereikt. 
- zorgen dat bij scherm draaien er geen request wordt gestuurd, of zet orientatie vast (sta geen landscape toe)
- clear adapter bij het verlaten van de activity. (finish()?)

### Day 8
-nieuwe structuur voor de vragen: geen object maar meer queries naar de database.
- adapter voor de questions favourites ListView gemaakt. 
- invullen van de random vragen via de oncreate en setText op het TextView: TODO maak een onClick, onSwipe voor de random vragen. 
- mogelijkheid voor sms misschien wel aanwezig: toevoegen aan manifest en dan nummer en text ingeven uit de database. TODO: eerst database helemaal werkend krijgen, denk aan de settings activity. 
Misschien geen extra activiteit nodig voor  het verzenden. 
- parsen werkt nog niet.

### Day 9
- database voor questions is helemaal functioneel. 
- TODO: meteen updaten als ListView wordt gevuld met favourite questions en het verwijderen van favourite questions uit de lijst moet mogelijk zijn. (misschien on long click, of on swipe (met een pop-up)
- next button voor de volgende vraag. 
- fake call wacht tijd geïmplementeerd. Ook wordt de screen niet meer gelocked (behalve voor force door een button)
- bezig geweest met invoeren van contact gegevens en het opslaan van de sms. nog niet werkend. Wel zijn alle functies die nodig zijn nu in de database gestopt. Nog niet helemaal werkend. 

### Day 10 
- presentation day
- contact toegevoegd aan telefoon, was een probleem
- mogelijk om naar contacten te gaan

### Day 11
- contacten worden uitgelezen
- geselecteerd contact wordt correct opgeslagen in DB
- permission check geïmplementeerd 
- mogelijk om opnieuw een contact te selecteren te selecteren. 
- sms activiteit verwijdert, vervangen door een Toast
- bij fake call binnenhalen van ringtone
- rekening houden met silent mode, vibrate mode of ringtone
- TODO: on backbutton press, ook stoppen met de ringtone! 
- button om sms op te slaan is enabled = false wanneer er niets is ingevuld.
- TextView toegevoegd om opgeslagen sms weer te geven
- hide keyboard als het sms is opgeslagen. 

### Day 12
- werken aan parsen newsfeed. 
- TODO error handling!!! 
- parsing soort van werkend

### Day 13
- werkende newsfeed parser
- title is clickable en gaat naar de link
- nieuwe icons (google android)
- cleaned start scherm
- todolist gemaakt voor bugs en nieuwe feature (swipe textview)

### Day 14
- implemented swipe voor TextView van de questions
- fixed bug niet meteen updatende ListView
- Internet connectie check
- maximum lengte edittext voor sms
- edittext tekst opslaan in sharedpreferences zodat je verder kan typen tot je hem saved. Dan wordt de edittext weer leeg. 
- bug fix nog een keer om permissie vragen bij contacten en sms versturen 
- zorgen dat je maar 1 call tegelijk mag doen: boolean in shared pref aanpassen en ophalen. 