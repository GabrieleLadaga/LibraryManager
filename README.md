# Personal Library Manager
## Descrizione del Progetto
*Personal Library Manager* è un'applicazione Java per organizzare e gestire la propria collezione di libri. Il software permette di catalogare volumi, aggiungere dettagli come titolo, autore, ISBN, genere, valutazione e stato di lettura, con funzionalità complete di inserimento, modifica e rimozione.
## Contesto Accademico
*Personal Library Manager* è un progetto sviluppato per il corso di *Ingegneria del Software* presso l'Università della Calabria, con l'obiettivo di applicare i principi di *Progettazione Software*, *Design Pattern* e *Testing*.
## Funzionalità Principali
- Gestione Catalogo:
  - Aggiunta/Modifica/Rimozione Libri;
  - Dettagli completi per ogni volume (Titolo, Autore, ISBN, Genere, Valutazione tra 1 e 5, Stato di Lettura).
- Visualizzazione:
  - Vista Tabellare dei Libri.
- Ricerca, Filtri e Ordinamento:
  - Ricerca per Titolo e Ricerca Avanzata (con tutti i parametri);
  - Filtri per: Titolo, Autore, Genere, Valutazione, Stato di Lettura;
  - Ordinamento per: Titolo, Autore, ISBN, Genere, Valutazione, Stato di Lettura;
- Persistenza:
  - Il Salvataggio e il caricamento dei dati viene fatto su un semplice Database SQLite.
## Design Pattern e Tecnologie Usate
- Design Pattern:
  - *Builder* per la creazione degli oggetti Book;
  - *Command* per l'esecuzione di operazioni di aggiunta, rimozione e modifica di oggetti Book;
  - *Strategy* per operazioni di Filtro, Ordinamento e Ricerca;
  - *Singleton* per avere una sola istanza della connessione al Database;
  - *Observer* per notificare all'interfaccia quando avvengono delle modifiche allo stato della libreria;
  - *Facade* per separare la richiesta di un operazione da chi la esegue.
- Testing:
  - Svolto tramite tecnologia *JUnit*.