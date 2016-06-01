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