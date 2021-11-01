JAVABOMBER

Gioco creato attraverso la libreria libGDX per dispositivi android. Il tema di riferimento è il classico videogame JavaBomber. 

Per l'avvio del gioco eseguire il file situato in "desktop/src/it/uniroma1/metodologie2018/javabomber/desktop/DesktopLauncher.java" .

In "core/src/ClassiMadre" si trovano le classi che hanno il compito di:
    - fornire i metodi necessari per le animazioni dell'entità del gioco;
    - fornire caratteristiche di ogni oggetto all'interno del gioco;
    - fornire caratteristiche dei powerUp all'interno del gioco;
    - fornire caratteristiche dell'entità nemiche;



In "core/src/Scenes" si trovano le classi dedicate alle HUD ossia alle heads-up display che rappresentano le informazioni costantemente visibili durante il gioco. Ad esempio salute rimanente, numero bombe, powerUp attivi, tempo ecc.


In "core/src/Tools" si trovano le classi che hanno il compito di:
    - fornire le variabili costanti visibili da tutte le classi;
    - gestire tutti gli eventi possibili all'interno del gioco;



In "core/src/it/uniroma1/metodologie2018/javabomber" ci sono:
    - le entità che costituiscono il gioco;
    - le interfacce che solo alcune entità ereditano;
    - la creazione del mondo circostante il personaggio principale;
    - il personaggio principale;




Le sprite usate sono all'interno della cartella "android/assets" .
