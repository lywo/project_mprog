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