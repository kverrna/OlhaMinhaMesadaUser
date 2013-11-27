package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MontaURL;

public class CeapUserController {

	private static CeapUserController instance;

	private CotaParlamentarUserDao cotaDao;
	private CeapUserController(Context context) {
		this.cotaDao=CotaParlamentarUserDao.getInstance(context);
		
	}

	//TODO: Verificar a necessidade do Context no construtor
	public static CeapUserController getInstance(Context context) {

		if (instance == null) {

			instance = new CeapUserController(context);
		}

		return instance;
	}

	private List<CotaParlamentar> convertJsonToCotaParlamentar(String jsonCota)
			throws NullParlamentarException {
		try {

			List<CotaParlamentar> cotas = JSONHelper
					.listCotaParlamentarFromJSON(jsonCota);

			return cotas;
			
		} catch (NullPointerException npe) {

			throw new NullParlamentarException();
		}
	}

	public boolean persistCotaDB(Parlamentar parlamentar) throws NullParlamentarException {

		boolean result = true;
		
		List<CotaParlamentar> cotas=parlamentar.getCotas();
		Iterator<CotaParlamentar> iterator = cotas.iterator();

		while (iterator.hasNext()) {

			boolean temporary = cotaDao.insertFollowed(parlamentar,
					iterator.next());
			
			result = result & temporary;
		}

		return result;
	}
}
