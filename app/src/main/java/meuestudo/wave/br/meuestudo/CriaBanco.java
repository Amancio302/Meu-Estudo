package meuestudo.wave.br.meuestudo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class CriaBanco extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "meuestudo.db";
    public static final String Materia = "Materia";
    public static final String idMateria = "idMateria";
    public static final String nomeMateria = "nomeMateria";
    public static final String meta = "meta";
    public static final String Nota = "Nota";
    public static final String idNota = "_idNota";
    public static final String Materia_idMateria = "Materia_idMateria";
    public static final String nota = "nota";
    public static final String Evento = "Evento";
    public static final String idEvento = "_idEvento";
    public static final String titulo = "titulo";
    public static final String descricao = "descricao";
    public static final String date = "data";
    public static final String concluido = "concluido";
    public static final String cor = "cor";
    public static final String notaTotal = "notaTotal";
    public static final String Alarme = "Alarme";
    public static final String idAlarme = "_idalarme";
    public static final String dataAlarme = "dataAlarme";
    public static final String toque = "toque";
    public static final String Evento_idEvento = "Evento_idEvento";
    public static final int VERSAO = 1;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sql = "CREATE TABLE " + Materia + "(" + idMateria + " integer primary key autoincrement, " + nomeMateria + " text, " + meta +
                " integer )";
        sqLiteDatabase.execSQL(sql);
        sql = " CREATE TABLE " + Nota + "(" + idNota + " integer primary key autoincrement, " + nota + " integer, " + Materia_idMateria +
                " integer, foreign key( " + Materia_idMateria + " ) references " + Materia + "( " + idMateria + " ) on update cascade)";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE " + Evento + "(" + idEvento + " integer primary key autoincrement, " + titulo + " text, " + descricao + " text, " +
                date + " date, " + concluido + " tinyint, " + cor + " text, " + notaTotal + "integer, " + Materia_idMateria + " integer, foreign" +
                " key(" + Materia_idMateria + ") references " + Materia + "( " + idMateria + " ) on update cascade)";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE " + Alarme + "(" + idAlarme + " integer primary key autoincrement, " + dataAlarme + " date, " + toque +
                " text, " + Evento_idEvento + " integer, foreign key( " + Evento_idEvento + " ) references " + Evento + "( " + idEvento + " ) on update cascade)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO Materia (idMateria, nomeMateria, meta) VALUES (0, 'Sem matéria', 0)";
        sqLiteDatabase.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Materia);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Nota);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Evento);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Alarme);
        onCreate(sqLiteDatabase);
    }

    public CriaBanco(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

}