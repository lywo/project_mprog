# project mprog : save the night!
Het maken van mijn eigen app. Onderdeel van het afsluitende vak Programmeerproject van de minor programmeren.
 
Lydia Wolfs

Student nummer: 10338217

- *Welk probleem wordt opgelost voor de gebruiker?* 
 Als de gebruiker een hele slechte date heeft geeft deze app de oplossing in meerdere richtingen: 2 opties om de date nog de goede kant op te sturen via suggesties en 2 om de app zo snel mogelijk af te breken. 


- *Welke features zijn beschikbaar om het probleem op te lossen?*
Het implementeren van een fake call. Het laten sturen van een ready to go emergency sms naar vantevoren gekozen nummer uit je telefoonboek. Hierdoor hoef je niet ongemakkelijk nog een sms te typen en een contactpersoon te kiezen. Het ophalen van lokaal news voor een interessant gespreksonderwerp. Een voorstel voor een interessante eerste date vraag om je date een beetje los te krijgen. 



- *Een schets van een overzicht van hoe de app eruit gaat zien. Het basic design en alle schermen.*
![](https://github.com/lywo/project_mprog/blob/master/doc/IMG_3180.JPG?raw=true)

![](https://github.com/lywo/project_mprog/blob/master/doc/IMG_3181.JPG?raw=true)

![](https://github.com/lywo/project_mprog/blob/master/doc/IMG_3182.JPG?raw=true)

![](https://github.com/lywo/project_mprog/blob/master/doc/IMG_3183.JPG?raw=true)


- *Welke datasets en data sources zijn er nodig en hoe wordt de data aangepast naar een goed format voor de app?*  
Dataset met news, een dataset met date vragen, een dataset met telefoonnummers die je binnenhaalt vanaf je eigen telefoon. Online opgeslagen text voor je emergency sms in SQLite database. 



- *Welke losse onderdelen van de applicatie kunnen worden onderscheiden en hoe moeten deze samenwerken?* Begin scherm waarin keuze wordt gemaakt tussen instellingen of de hulp opties. Tweede scherm met of instellingen (die weer moeten worden gelinked aan de hulp opties) of de 4 hulp opties (icons). De 4 hulp opties hebben ook ieder een eigen scherm. Alleen de emergency sms moet weer terug komen bij de 4 hulp opties. Terug gaan is altijd mogelijk via back button. Bij het afsluiten van de app komt de gebruiker weer bij het begin scherm. 



- *Welke externe onderdelen (API's) zijn nodig om de features mogelijk te maken?*
**news API/google news** deze moet gebruiken maken van de locatie (via *GPS*) van de gebruiker om het recente nieuws op te halen. 
**question API** mogelijk een API die vragen opzoekt voor het daten. Anders hard coden. 
**gmail VOICE** mogelijkheid om via gmail account je eigen nummer te bellen voor de emergency call.



- *Noem technische problemen of limitaties optreden tijdens het ontwerpen van de applicatie.Welke oplossingen zijn er om dit op te lossen.*
Als het niet lukt om GPS te gebruiken of om lokaal nieuws op te halen, dit een standaard ingegeven locatie of standaard website front page zijn (via instellingen) en dan wordt altijd dezelfde (geupdate) query uitgevoerd. 
Als het niet lukt om een echte call uit te voeren via GMAIL VOICE kan er ook een plaatje full screen worden weergegeven die lijkt op een incoming call screen en deze wordt vergezeld met een ringtone. Of eventueel een animatie, en geen stilstaande afbeelding. 
Als het niet lukt om een echte sms te sturen, moet dit ook worden gefacked met behulp van een afbeelding en een geluidje. 


- *Een review van vergelijkbare applicaties*

**fake a call free** "Fake an incoming call and get away from any situation!
The original and still the best, and free!
*Autofill from contacts
*Select your own ringtones
*Record 'voice on other end'
*Schedule fake calls
Fake-A-Call at your next meeting, class, or awkward date!" 

Hierbij kan een timer worden gezet en een belletje worden nagemaakt met contactenlijst en een nep stem aan de andere kant. 

**My First Date Questions**: Deze app heeft 101 vragen gehardcoded en in een list view gezet. Je kan erdoor heen scrollen en zo je date verder helpen. Ook kan je favorieten opslaan, want het terugzoeken makkelijker maakt. 



- *minimum viable product (MVP)*
Een app vanuit een begin scherm 

1) van een standaard locatie nieuws ophaalt 

2) gehardcode vragen random 1 voor 1 laat zien door op een knop te drukken.

3) een standaard android incoming call screenshot displayed met bijpassen geluid.  

4) een geluidje van een incoming sms afspeelt. 
