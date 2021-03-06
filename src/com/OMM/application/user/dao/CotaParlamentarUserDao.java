package com.OMM.application.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.helper.LocalDatabase;
import com.OMM.application.user.model.CotaParlamentar;

public class CotaParlamentarUserDao {

	private static String nome_tabela = "COTA";
	private static CotaParlamentarUserDao instance;
	private LocalDatabase database;
	private SQLiteDatabase sqliteDatabase;

	private CotaParlamentarUserDao(Context context) {
		database = new LocalDatabase(context);
	}

	public static CotaParlamentarUserDao getInstance(Context context) {

		if (instance == null) {
			instance = new CotaParlamentarUserDao(context);
		}
		return instance;
	}

	public boolean insertCotasOnFollowedParlamentar(List<CotaParlamentar> cotas) {
		sqliteDatabase = database.getWritableDatabase();
		ContentValues content;
		CotaParlamentar cota;
		boolean result = true;
		Iterator<CotaParlamentar> iterator = cotas.iterator();
		while (iterator.hasNext() && result) {
			content = new ContentValues();
			cota = iterator.next();
			content.put("ID_COTA", cota.getCod());
			content.put("ID_PARLAMENTAR", cota.getIdParlamentar());
			content.put("DESCRICAO", cota.getDescricao());
			content.put("MES", cota.getMes());
			content.put("ANO", cota.getAno());
			content.put("VALOR", cota.getValor());
			content.put("NUM_SUBCOTA", cota.getNumeroSubCota());
			result = (sqliteDatabase.insert(nome_tabela, null, content) > 0);
		}
		sqliteDatabase.close();
		return result;
	}

	public boolean deleteCotasFromParlamentar(int idParlamentar) {
		sqliteDatabase = database.getWritableDatabase();
		boolean result = (sqliteDatabase.delete(nome_tabela,
				"ID_PARLAMENTAR=?", new String[] { idParlamentar + "" }) > 0);
		sqliteDatabase.close();
		return result;
	}

	public List<CotaParlamentar> getCotasByIdParlamentar(int idParlamentar) {
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
				"SELECT * FROM COTA WHERE ID_PARLAMENTAR=" + idParlamentar,
				null);
		List<CotaParlamentar> listCotas = new ArrayList<CotaParlamentar>();
		while (cursor.moveToNext()) {
			CotaParlamentar cota = new CotaParlamentar();
			cota.setCod(cursor.getInt(0));
			cota.setIdParlamentar(idParlamentar);
			cota.setNumeroSubCota(cursor.getInt(2));
			cota.setDescricao(cursor.getString(3));
			cota.setMes(cursor.getInt(4));
			cota.setAno(cursor.getInt(5));
			cota.setValor(cursor.getDouble(6));
			listCotas.add(cota);
		}
		sqliteDatabase.close();
		return listCotas;
	}

}