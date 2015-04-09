2015-01-20

La presentazione di Diego Torres Milano (dir /doc) contiene esempi del tipo di test che non servono a nulla... tipo testare l'allineamento degli elementi nella GUI.  La sua spiegazione del TDD evita di dire la cosa piu' importante, che e' una strategia per fare design emergente.  Non una strategia di testing.


2014-10-17

OK! Non e' venerdi' 13.

2014-09-12

Carlo e Matteo insieme.

Esaminiamo il problema proposto da Carlo Pescio qui: http://www.aspectroid.com/

Facciamo una Spike.  La API di Android e' veramente facile da usare.  Ogni touchEvent contiene un certo numero di "puntatori" (dita.)  Ad ogni evento successivo ci dice dove si sono spostate quelle dita.

Facciamo partire il progetto vero.  Imbarazzo: come lo testiamo?

 - tentativo 1: scriviamo un oggetto 'App' che non dipende da Android e ragioniamo in maniera astratta, per poi collegarlo al sistema Android.  Siamo arrivati a buon punto.

 - tentativo 2: testiamo la activity, cercando di fare emergere i comportamenti dalla simulazione di un tocco.  Come facciamo a sviluppare il primo passo?  E come faccio a fare un test unitario, dato che qualsiasi cosa io provi a testare all'interno del modulo AndroidDependent fa partire il download del codice sul dispositivo?



2014-08-13

TODO: rename "app" module to something else


2014-08-12

Riprendo l'esercizio UnitDoctor

 Dopo avere un failing end2end test, creo il modulo UnitDoctorCore
 Sviluppo l'oggetto UnitDoctor in stile presenter-first alla JBrains
 Poi devo sviluppare la view... e si torna al modulo app con un integration test

Che cosa ho imparato:

  - se fai TextView.setText, l'evento onKey non scatta
  - potresti farlo scattare con sendKeys, ma e' impestatissimo perche' fai
    molta fatica a controllare quale field ha il focus (anzi non ci sono riuscito)
  - ho risolto usando TextView.setOnTextChangedListener.

  - Usare la MainActivity come "main" funziona benissimo

  - genymotion alternativa come emulatore piu' veloce

Che cosa manca da fare:

  - supportare F to C
  - supportare C to F, cm to m
  - salvare lo stato dell'applicazione quando viene fermata, recuperarlo nella onCreate
  - convertire metri in ft + in??
  - convertire gradi centesimali in ore, min, secondi
  - convertire ore o minuti in giorni
  - mostrare come gli oggetti rimuovono la catena di IF


2014-08-04

Studiato il libro di Rainsberger.
+ Use Activity.onCreate as main
+ Wrap android Views in object that implement the MVC View interface
- rather useless o/w



2014-08-01

Added score to Lights Out



2014-07-24 TDD Compound Interest

3 pomodori.  Perso parecchio tempo in problemucci.

Non si capisce bene il valore aggiunto del test, dato che non ci sono molte variazioni da provare in questa applicazione.

Che cosa ho imparato?

 - variazioni:
  - tipi di calcolo: l'app percentage ... fornisce un certo numero di diversi calcolatori
 - mi serve un puzzle dove e' il controller a creare la gui ad ogni stadio


2014-07-21

 Tried a spike for the whole game.

 What I learned

  - Moving from portrait to landscape loses the state of the game.
  - When this happens, a new instance of the activity is created!
  - This creates a problem of who knows about whom.  The controller needs the view, the view needs the controller.

  - A good AT is one for the real use case, not a simplified one.  It's hard to generalize if the AT is for a single button.

  - The GridView works well for structuring the buttons. It needs a ListAdapter that creates the items for the elements in the grid.  This is a "model" for the grid.  It probably deserves the status of "view" for the purposes of the controller.

  - It's easy to get confused about which project contains what.  It's best to have a very clear naming convention.


2014-07-21 Spike: tempconv

 Riesco ad avere la spike funzionante in 23 minuti.

 La cosa curiosa e' che il cambio di orientamento del display NON distrugge lo stato dell'applicazione.  Vai a capire.





2014-07-13

What I learned

 - can't assign 0,1,2 as IDs in the layout xml. You must create views programmatically for that.
 - it's difficult to write tests to the LighsOut controller unless you separate the game status in a separate object.

Next steps

 - try a spike: a horizontal linear layout with 4 buttons.  When you click a button, its id (0,1,2,3) should appear as a toast message.
 - try again with a simpler exercise (average annual growth)
 - reboot the exercise


