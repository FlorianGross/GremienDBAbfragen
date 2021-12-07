import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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

