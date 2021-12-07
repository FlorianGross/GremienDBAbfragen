class Antraege {
    int id;
    String titel;
    String antragstext;
    boolean angenommen;
    int jastimmen;
    int neinStimmen;
    int enthaltungen;

    public Antraege(int id, String titel, String antragstext, boolean angenommen, int jastimmen, int neinStimmen, int enthaltungen) {
        this.id = id;
        this.titel = titel;
        this.antragstext = antragstext;
        this.angenommen = angenommen;
        this.jastimmen = jastimmen;
        this.neinStimmen = neinStimmen;
        this.enthaltungen = enthaltungen;
    }
}
