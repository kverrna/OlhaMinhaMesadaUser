package com.OMM.application.user.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.OMM.application.user.helper.DB;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserDao {
	
	//TODO:Fazer try catch do banco

	private static String nome_tabela = "PARLAMENTAR";
	private static Context context;
	private static String[] colunas = { "ID_PARLAMENTAR,NOME_PARLAMENTAR" };
	private static ParlamentarUserDao instance;

	@SuppressWarnings("static-access")
	private ParlamentarUserDao(Context context) {
		this.context = context;
	}

	public static ParlamentarUserDao getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserDao(context);
		}

		return instance;
	}

	public boolean checkEmptyDB() {

		boolean result = false;

		List<Parlamentar> parlamentares = new ArrayList<Parlamentar>();

		parlamentares = getAll();

		if (parlamentares.isEmpty()) {
			result = true;
		}

		Log.i("ParlamentarUserDao", "Result : " + result);

		return result;
	}

	public boolean insertParlamentar(Parlamentar parlamentar) {
		
		SQLiteDatabase database = new DB(context).getWritableDatabase();
		ContentValues content = new ContentValues();

		content.put("ID_PARLAMENTAR", parlamentar.getId());
		content.put("NOME_PARLAMENTAR", parlamentar.getNome());

		return (database.insert(nome_tabela, null, content) > 0);
	}

	public boolean deleteParlamentar(Parlamentar parlamentar) {
		
		SQLiteDatabase database = new DB(context).getWritableDatabase();
		
		return (database.delete(nome_tabela, "ID_PARLAMENTAR=?",
				new String[] { parlamentar.getId() + "" }) > 0);
	}

	public boolean updateParlamentar(Parlamentar parlamentar) {
		
		SQLiteDatabase database = new DB(context).getWritableDatabase();
		ContentValues content = new ContentValues();

		content.put("SEGUIDO", parlamentar.isSeguido());

		return (database.update(nome_tabela, content, "ID_PARLAMENTAR=?",
				new String[] { parlamentar.getId() + "" }) > 0);
	}

	public Parlamentar getById(Integer ID_PARLAMENTAR) {
		
		SQLiteDatabase database = new DB(context).getReadableDatabase();

		Cursor cursor = database.query(nome_tabela, colunas, "ID_PARLAMENTAR=?",
				new String[] { ID_PARLAMENTAR.toString() }, null, null, null);

		Parlamentar parlamentar = null;
		
		if (cursor.moveToFirst()) {
			
			parlamentar = new Parlamentar();
			parlamentar.setId(Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("ID_CLIENTE"))));
			
			parlamentar.setNome(cursor.getString(cursor.getColumnIndex("NOME_PARLAMENTAR")));

		}
		
		return parlamentar;
	}

	public List<Parlamentar> getAll() {
		
		SQLiteDatabase database = new DB(context).getReadableDatabase();
		Cursor cursor = database.rawQuery("SELECT ID_PARLAMENTAR,NOME_PARLAMENTAR FROM PARLAMENTAR", null);
		List<Parlamentar> listParlamentares = new ArrayList<Parlamentar>();
		
		while (cursor.moveToNext()) {
			
			Parlamentar parlamentar = new Parlamentar();
			parlamentar.setId(cursor.getInt(0));
			parlamentar.setNome(cursor.getString(1));

			listParlamentares.add(parlamentar);
		}
		
		return listParlamentares;
	}

	/*
	 * TODO: Metodo utilizado para realizar o filtro de parlamentares ele deve ser
	 * 		 trabalhado melhor para condi��o de nao encontrar um parlamentar
	 */
	public List<Parlamentar> getSelected(String nameParlamentar) {

		SQLiteDatabase database = new DB(context).getReadableDatabase();
		Cursor cursor = database.rawQuery(
						"SELECT ID_PARLAMENTAR,NOME_PARLAMENTAR FROM PARLAMENTAR WHERE NOME_PARLAMENTAR LIKE '%"
								+ nameParlamentar + "%'", null);
		
		List<Parlamentar> listParlamentar = new ArrayList<Parlamentar>();
		
		while (cursor.moveToNext()) {
			
			Parlamentar parlamentar = new Parlamentar();
			parlamentar.setId(cursor.getInt(0));
			parlamentar.setNome(cursor.getString(1));

			listParlamentar.add(parlamentar);
		}

		return listParlamentar;
	}

	public List<Parlamentar> getAllSelected() {
		
		SQLiteDatabase database = new DB(context).getReadableDatabase();
		Cursor cursor = database.rawQuery(
						"SELECT ID_PARLAMENTAR,NOME_PARLAMENTAR FROM PARLAMENTAR WHERE SEGUIDO IN(1)",
						null);
		
		List<Parlamentar> listParlamentar = new ArrayList<Parlamentar>();
		
		while (cursor.moveToNext()) {
			
			Parlamentar parlamentar = new Parlamentar();
			parlamentar.setId(cursor.getInt(0));
			parlamentar.setNome(cursor.getString(1));

			listParlamentar.add(parlamentar);
		}
		
		return listParlamentar;
	}
}
