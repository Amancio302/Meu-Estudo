package meuestudo.wave.br.meuestudo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }

    public boolean insereMateria(String nome, int meta) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.nomeMateria, nome);
        valores.put(CriaBanco.meta, meta);

        resultado = db.insert(CriaBanco.Materia, null, valores);
        db.close();

        if (resultado ==-1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insereNota(int nota, int materia) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.nota, nota);
        valores.put(CriaBanco.Materia_idMateria, materia);

        resultado = db.insert(CriaBanco.Nota, null, valores);
        db.close();

        if (resultado ==-1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insereEvento(String titulo, String descricao, String data, boolean concluido, String cor, int notaTotal, int materia) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.titulo, titulo);
        valores.put(CriaBanco.descricao, descricao);
        valores.put(CriaBanco.date, data);
        valores.put(CriaBanco.concluido, concluido);
        valores.put(CriaBanco.cor, cor);
        valores.put(CriaBanco.notaTotal, notaTotal);
        valores.put(CriaBanco.Materia_idMateria, materia);

        resultado = db.insert(CriaBanco.Evento, null, valores);
        db.close();

        if (resultado ==-1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insereAlarme(String data, String toque, int evento) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.dataAlarme, data);
        valores.put(CriaBanco.toque, toque);
        valores.put(CriaBanco.Evento_idEvento, evento);

        resultado = db.insert(CriaBanco.Materia, null, valores);
        db.close();

        if (resultado ==-1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor carregaMateria() {
        Cursor cursor;
        String[] campos = {banco.idMateria, banco.nomeMateria, banco.meta};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.Materia, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaNota() {
        Cursor cursor;
        String[] campos = {banco.idNota, banco.nota, banco.Materia_idMateria};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.Nota, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaEvento() {
        Cursor cursor;
        String[] campos = {banco.idEvento, banco.titulo, banco.descricao, banco.date, banco.concluido, banco.cor, banco.notaTotal, banco.Materia_idMateria};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.Evento, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaAlarme() {
        Cursor cursor;
        String[] campos = {banco.idAlarme, banco.dataAlarme, banco.toque, banco.Evento_idEvento};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.Alarme, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void apagaMateria (int id) {
        String where = CriaBanco.idMateria + "=" + id;
        db = banco.getReadableDatabase();
        Cursor cursor = carregaEventoByMateria(id);
        for(int i = 0; i < cursor.getCount(); i++){

            //Move para a posição i;

            cursor.moveToPosition(i);

            //Pega o index da coluna

            int iIdEvento = cursor.getColumnIndex("idEvento");
            int iTitulo = cursor.getColumnIndex("titulo");
            int iDescricao = cursor.getColumnIndex("descricao");
            int iDate = cursor.getColumnIndex("date");
            int iConcluido = cursor.getColumnIndex("concluido");
            int iCor = cursor.getColumnIndex("cor");
            int iNotaTotal = cursor.getColumnIndex("notaTotal");

            //Resgata o valor da coluna

            int idEvento = cursor.getInt(iIdEvento);
            String titulo = cursor.getString(iTitulo);
            String descricao = cursor.getString(iDescricao);
            String date = cursor.getString(iDate);
            int conc = cursor.getInt(iConcluido);
            boolean concluido = (conc != 0);
            String cor = cursor.getString(iCor);
            int notaTotal = cursor.getInt(iNotaTotal);

            //Altera materia para 0

            alteraEvento(idEvento, titulo, descricao, date, concluido, cor, notaTotal, 0);

        }

        db.delete(CriaBanco.Materia, where, null);
    }

    public void apagaNota (int id) {
        String where = CriaBanco.idNota + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.Nota, where, null);
    }

    public void apagaEvento (int id) {
        String where = CriaBanco.idEvento + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.Evento, where, null);
    }

    public void apagaAlarme (int id) {
        String where = CriaBanco.idAlarme + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.Alarme, where, null);
    }

    public Cursor carregaMateriaById(int id){
        Cursor cursor;
        String[] campos = {banco.idMateria, banco.nomeMateria, banco.meta};
        String where = CriaBanco.idMateria + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Materia, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public Cursor carregaNotaById(int id){
        Cursor cursor;
        String[] campos = {banco.idNota, banco.nota, banco.Materia_idMateria};
        String where = CriaBanco.idNota + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Nota, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public Cursor carregaEventoById(int id){
        Cursor cursor;
        String[] campos = {banco.idEvento, banco.titulo, banco.descricao, banco.date, banco.concluido, banco.cor, banco.notaTotal, banco.Materia_idMateria};
        String where = CriaBanco.idEvento + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Evento, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public Cursor carregaAlarmeById(int id){
        Cursor cursor;
        String[] campos = {banco.idAlarme, banco.dataAlarme, banco.toque, banco.Evento_idEvento};
        String where = CriaBanco.idAlarme + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Alarme, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public void alteraMateria(int id, String nome, int meta){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.idMateria + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBanco.nomeMateria, nome);
        valores.put(CriaBanco.meta, meta);

        db.update(CriaBanco.Materia, valores, where, null);
    }

    public void alteraNota(int id, int nota, int materia){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.idNota + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBanco.nota, nota);
        valores.put(CriaBanco.Materia_idMateria, materia);

        db.update(CriaBanco.Nota, valores, where, null);
    }

    public void alteraEvento(int id, String titulo, String descricao, String data, boolean concluido, String cor, int notaTotal, int materia){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.idEvento + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBanco.titulo, titulo);
        valores.put(CriaBanco.descricao, descricao);
        valores.put(CriaBanco.date, data);
        valores.put(CriaBanco.concluido, concluido);
        valores.put(CriaBanco.cor, cor);
        valores.put(CriaBanco.notaTotal, notaTotal);
        valores.put(CriaBanco.Materia_idMateria, materia);

        db.update(CriaBanco.Evento, valores, where, null);
    }

    public void alteraAlarme(int id, String data, String toque, int evento){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.idAlarme + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBanco.dataAlarme, data);
        valores.put(CriaBanco.toque, toque);
        valores.put(CriaBanco.Evento_idEvento, evento);

        db.update(CriaBanco.Nota, valores, where, null);
    }

    public Cursor carregaNotaByMateria(int id){
        Cursor cursor;
        String[] campos = {banco.idNota, banco.nota};
        String where = CriaBanco.idMateria + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Nota, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    };

    public Cursor carregaEventoByMateria(int id){
        Cursor cursor;
        String[] campos = {banco.idEvento, banco.titulo, banco.descricao, banco.date, banco.concluido, banco.cor, banco.notaTotal, banco.Materia_idMateria};
        String where = CriaBanco.idMateria + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Evento, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    };

    public Cursor carregaAlarmeByEvento(int id){
        Cursor cursor;
        String[] campos = {banco.idAlarme, banco.dataAlarme, banco.toque, banco.Evento_idEvento};
        String where = CriaBanco.idEvento + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.Alarme, campos, where, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    };
}