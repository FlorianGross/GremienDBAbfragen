import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static boolean run = true;

    public static void main(String[] args) {
        while (run) {
            try {
                System.out.println("Geben Sie die Bezeichnung des Gremiums an: (Bsp.: Öffentlichkeitsarbeit) \n Eine auflistung aller Gremien-Bezeichnung finden sie mit der Eingabe: All");
                Scanner scan = new Scanner(System.in);
                String gremienBezeichnung = scan.next();
                if (gremienBezeichnung.equals("All")) {
                    printAllGremienBezeichnungen();
                } else {
                    int gremienID = getSitzungFromGremienBezeichnung(gremienBezeichnung);
                    if (gremienID != -1) {
                        System.out.println("ID des Gesuchten Gremiums: " + gremienID);
                        ArrayList<Sitzung> SitzungsList = getAllSitzungnWithID(gremienID);
                        int input = -1;
                        try {
                            input = scan.nextInt();
                        } catch (InputMismatchException e) {
                            run = false;
                        }
                        try {
                            System.out.println(generateProtocol(SitzungsList.get(input - 1)));
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Falsche Eingabe:" + e);
                        }
                    } else {
                        System.out.println("Kein Gremium mit der Bezeichnung gefunden");
                    }
                }
            } catch (Exception e) {
                System.out.println("Fehler bei ihrer Eingabe:" + e);
            }
        }
    }

    private static void printAllGremienBezeichnungen() throws SQLException {
        String sqlStr = "select * FROM GREMIUM";
        ResultSet rs = getResultSetFromQuary(sqlStr);
        System.out.println("Alle Bezeichnungen: ");
        while (rs.next()) {
            System.out.print(rs.getString("BEZEICHNUNG") + ", ");
        }
        System.out.println();
    }

    private static ArrayList<Sitzung> getAllSitzungnWithID(int gremienId) throws SQLException {
        String sqlStr = "select * FROM SITZUNGEN";
        ResultSet rs = getResultSetFromQuary(sqlStr);
        ArrayList<Sitzung> output = new ArrayList<>();
        System.out.println("Wählen sie die ID des Gremiums aus:\nID - Beginn");
        int i = 1;
        while (rs.next()) {
            if (rs.getInt("GREMIUM") == gremienId) {
                System.out.println(i + " - " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("BEGINN")));
                i++;
                output.add(new Sitzung(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("BEGINN"))));
            }
        }
        return output;
    }

    private static Connection getDriverConnection() throws SQLException {
        return DriverManager.getConnection(getDriverDBInfo(), "DABS_14", "DABS_14");
    }

    private static String getDriverDBInfo() {
        return "jdbc:oracle:thin:@localhost:10111:namib";
    }

    static ResultSet getResultSetFromQuary(String quary) throws SQLException {
        Statement stmtKurse = getDriverConnection().createStatement();
        return stmtKurse.executeQuery(quary);
    }

    private static int getSitzungFromGremienBezeichnung(String gremienBezeichnung) throws SQLException {
        int output = -1;
        String sqlStr = "select ID, BEZEICHNUNG from GREMIUM";
        ResultSet rs = getResultSetFromQuary(sqlStr);
        while (rs.next()) {
            if (rs.getString("BEZEICHNUNG").equals(gremienBezeichnung)) {
                output = rs.getInt("ID");
            }
        }
        return output;
    }

    public static String generateProtocol(Sitzung currentSitzung) throws SQLException {
        run = false;
        return "Result - Finished \n" + currentSitzung;
    }
}

class Sitzung {
    int ID, gremium;
    String name;
    boolean oeffentlich;
    Timestamp beginn, ende;
    String ort, strasse, PLZ, HausNR;

    public Sitzung(String beginn) throws SQLException {
        String sqlStr = "select ID, NAMEN, GREMIUM, OEFFENTLICH, BEGINN, ENDE, ORT, STRASSE, PLZ, HAUSNR from Sitzungen where BEGINN = TO_DATE('" + beginn + "', 'YYYY-MM-DD hh24:mi:ss')";
        ResultSet rs = Main.getResultSetFromQuary(sqlStr);
        rs.next();
        this.ID = rs.getInt("ID");
        this.name = rs.getString("NAMEN");
        this.gremium = rs.getInt("GREMIUM");
        this.oeffentlich = rs.getBoolean("oeffentlich");
        this.beginn = rs.getTimestamp("BEGINN");
        this.ende = rs.getTimestamp("ENDE");
        this.ort = rs.getString("ORT");
        this.strasse = rs.getString("STRASSE");
        this.PLZ = rs.getString("PLZ");
        this.HausNR = rs.getString("HAUSNR");
    }

    @Override
    public String toString() {
        return "Sitzung{" +
                "ID=" + ID +
                ", gremium=" + gremium +
                ", oeffentlich=" + oeffentlich +
                ", beginn=" + beginn +
                ", ende=" + ende +
                ", ort='" + ort + '\'' +
                ", strasse='" + strasse + '\'' +
                ", PLZ='" + PLZ + '\'' +
                ", HausNR='" + HausNR + '\'' +
                '}';
    }
}

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
