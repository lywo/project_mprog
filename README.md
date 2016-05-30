# project mprog : neuro imaging app 
Het maken van mijn eigen app. Onderdeel van het afsluitende vak Programmeerproject van de minor programmeren. 
Lydia Wolfs
Std nmb 10338217



- *Welk probleem wordt opgelost voor de gebruiker?* 
De app heeft een educatie doel. Deze app maakt het voor studenten makkelijk om snel een voorbeeld te vinden in een 3d weergave van het menselijke brein. Vaak beschikken vooral studenten niet over voorbeelden wanneer zij aan het studeren zijn. Een app met specifiek studie doeleinden en daarbij behoren features maakt het voor studenten makkelijker om de menselijke anatomie van de hersenen te begrijpen.  


- *Welke features zijn beschikbaar om het probleem op te lossen?*
Het is ook mogelijk om in te zoomen op een afbeeldingen. Het favoriet maken van een bepaalde oriëntatie en het opslaan van notes, maakt het studeren makkelijker. 
Er is een makkelijk uit klapbaar keuze menu voor de verschillende datasets.Terug naar het hoofdmenu door gebruik van de back button. 



- *Een schets van een overzicht van hoe de app eruit gaat zien. Het basic design en alle schermen.*




- *Welke datasets en data sources zijn er nodig en hoe wordt de data aangepast naar een goed format voor de app?*  Datasetnames moeten worden opgeslagen om de namen makkelijk in een keuzemenu weer te geven, waarna een HTTP request wordt gestuurd naar de 3dbar API. 



- *Welke losse onderdelen van de applicatie kunnen worden onderscheiden en hoe moeten deze samenwerken?* Basis scherm met keuze tussen verschillende datasets (hersengebieden of whole brain). Deze brengt de gebruiker naar een volgend scherm waarin de structure kan worden gekozen. Hierna wordt de gebruiker naar een nieuw scherm gestuurd waarin de x3d afbeelding is weergegeven. Het is mogelijk om request op te slaan en daar eigen notes toe te voegen.



- *Welke externe onderdelen (API's) zijn nodig om de features mogelijk te maken?*
**3dbar**: Om de 3d file te krijgen van een bepaalde database. Gebruik de functie: getPreviewReconstruction
Return a lightweight model of a given structure from selected dataset as an x3d mesh. **freewrl** : om de x3d weer te geven in een android app. 



- *Noem technische problemen of limitaties optreden tijdens het ontwerpen van de applicatie.Welke oplossingen zijn er om dit op te lossen.*
als de losse API's niet blijken te werken, overstappen naar een API die alleen plaatjes binnen haalt, zoals de Allen Brain Atlas API of de BRAINMAPS API.  




- *Een review van vergelijkbare applicaties*
**3D Brain**: 
**Brain Tutor 3D**:
**VR Human Brain**:
Allen missen interactie met de gebruiker. Het toevoegen van eigen notities en het opslaan van bepaalde oriëntatie moet het makkelijker maken voor een student om zijn eigen struikel punten makkelijker terug te vinden. 




- *minimum viable product (MVP)*
aan de hand van een keuze menu worden brain images getoond aan de user die kunnen worden opgeslagen en waar notes bij kunnen worden opgeslagen. 