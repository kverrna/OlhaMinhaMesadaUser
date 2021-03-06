package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.test.AndroidTestCase;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarUserControllerTest extends AndroidTestCase {

	private Context context;
	private ResponseHandler<String> response;
	private Parlamentar parlamentar;
	private ParlamentarUserController controller;
	private ParlamentarUserDao dao;

	public void setUp() throws Exception {
		super.setUp();
		response = HttpConnection.getResponseHandler();
		context = getContext();
		controller = ParlamentarUserController.getInstance(context);
		dao = ParlamentarUserDao.getInstance(context);
		parlamentar = new Parlamentar();
		parlamentar.setNome("TIRIRICA");
		parlamentar.setId(0);
		parlamentar.setSeguido(0);
		dao.insertParlamentar(parlamentar);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		dao.deleteParlamentar(parlamentar);
	}

	public void testGetInstance() {

		ParlamentarUserController controller2 = ParlamentarUserController
				.getInstance(context);
		assertSame(controller, controller2);
	}

	public void testGetParlametares() {
		List<Parlamentar> list = new ArrayList<Parlamentar>();
		Parlamentar parlamentar2 = new Parlamentar();
		parlamentar2.setId(0);
		list.add(parlamentar2);
		controller.setParlamentares(list);
		assertEquals(list.get(0).getId(), controller.getParlamentares().get(0)
				.getId());
	}

	public void testGetParlamentar() {
		parlamentar = new Parlamentar();
		parlamentar.setId(1);
		controller.setParlamentar(parlamentar);
		assertEquals(parlamentar.getId(), controller.getParlamentar().getId());
	}

	public void testGetByName() {

		String nameParlamentar = "TIRIRICA";
		List<Parlamentar> listParlamentar = controller
				.getByName(nameParlamentar);

		assertEquals(nameParlamentar, listParlamentar.get(0).getNome());

	}

	public void testGetAll() {

		List<Parlamentar> listParlamentar = controller.getAll();

		assertNotNull(listParlamentar);

	}

	public void testFollowedParlamentar() throws NullCotaParlamentarException,
			NullParlamentarException {

		Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(parlamentar.getId());
		parlamentar.setSeguido(1);
		parlamentar.setCotas(list);
		controller.setParlamentar(parlamentar);

		assertTrue(controller.followedParlamentar());
	}

	public void testUnFollowedParlamentar()
			throws NullCotaParlamentarException, NullParlamentarException {

		Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(parlamentar.getId());
		list.add(cota);
		parlamentar.setCotas(list);
		parlamentar.setSeguido(1);
		controller.setParlamentar(parlamentar);
		controller.followedParlamentar();
		parlamentar.setSeguido(0);
		controller.setParlamentar(parlamentar);
		assertTrue(controller.unFollowedParlamentar());
	}

	public void testFollowedParlamentarNullParlamentrarException()
			throws NullCotaParlamentarException, NullParlamentarException {

		try {
			controller.setParlamentar(null);
			controller.followedParlamentar();

			fail("Exceptions not launched");

		} catch (NullParlamentarException npe) {
		}
	}

	public void testFollowedParlamentarNullCotaParlamentrarException()
			throws NullCotaParlamentarException, NullParlamentarException {

		try {
			Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
			List<CotaParlamentar> list = null;
			parlamentar.setSeguido(1);
			parlamentar.setCotas(list);
			controller.setParlamentar(parlamentar);

			controller.followedParlamentar();
			fail("Exceptions not launched");

		} catch (NullCotaParlamentarException npe) {
		}
	}

	public void testUnFollowedNullParlamentarException()
			throws NullParlamentarException, NullCotaParlamentarException {
		try {
			controller.setParlamentar(null);
			controller.unFollowedParlamentar();

			fail("Exceptions no launched");

		} catch (NullParlamentarException npe) {

		}
	}

	public void testUnFollowedParlamentarNullCotaParlamentarException()
			throws NullCotaParlamentarException, NullParlamentarException {

		try {
			Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
			List<CotaParlamentar> list = null;
			parlamentar.setSeguido(0);
			parlamentar.setCotas(list);
			controller.setParlamentar(parlamentar);

			controller.unFollowedParlamentar();
			fail("Exceptions not launched");

		} catch (NullCotaParlamentarException npe) {

		}
	}

	public void testDoRequest() throws ConnectionFailedException,
			RequestFailedException, TransmissionException,
			NullParlamentarException, NullCotaParlamentarException {
		Parlamentar p = new Parlamentar();
		p.setId(373);
		controller.setParlamentar(p);
		Parlamentar pJson = controller.doRequest(response);
		String result = "[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]";
		Parlamentar pResult = JSONHelper.listParlamentarFromJSON(result).get(0);

		assertEquals(pResult.getId(), pJson.getId());
		assertEquals(pResult.getNome(), pJson.getNome());
	}

	public void testDoRequestTransmissionException()
			throws NullParlamentarException, NullCotaParlamentarException,
			TransmissionException, ConnectionFailedException,
			RequestFailedException {

		try {
			controller.setParlamentar(parlamentar);
			Parlamentar pJson = controller.doRequest(null);

			fail("Exception not launched");
		} catch (TransmissionException npe) {

		}
	}

	public void testGetSelected() {

		assertNotNull(controller.getSelected());
	}

	public void testInsertAllFalse() throws NullParlamentarException,
			ConnectionFailedException, RequestFailedException,
			TransmissionException {

		ResponseHandler<String> response = HttpConnection.getResponseHandler();
		assertFalse(controller.insertAll(response));

	}

	public void testInsertAllFalseTransmissionException()
			throws NullParlamentarException, TransmissionException,
			ConnectionFailedException, RequestFailedException {

		try {
			controller.insertAll(null);

			fail("Exception not launched");
		} catch (TransmissionException npe) {

		}
	}

	public void testInsertAllTrue() throws NullParlamentarException,
			ConnectionFailedException, RequestFailedException,
			TransmissionException {

		controller.getAll();
		Iterator<Parlamentar> iterator = controller.getAll().iterator();

		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			dao.deleteParlamentar(p);

		}

		ResponseHandler<String> response = HttpConnection.getResponseHandler();
		assertTrue(controller.insertAll(response));

	}

	public void testInsertAllTrueTransmissionException()
			throws NullParlamentarException, TransmissionException,
			ConnectionFailedException, RequestFailedException {

		try {
			controller.insertAll(null);

			fail("Exception not launched");
		} catch (TransmissionException npe) {

		}
	}

	public void testGetAllSelected() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = controller.getAllSelected();
		assertNotNull(lista);

	}

	public void testDoRequestMajorRanking() throws NullParlamentarException,
			ConnectionFailedException, RequestFailedException,
			TransmissionException {
		Parlamentar p = new Parlamentar();
		p.setId(373);
		controller.setParlamentar(p);
		List<Parlamentar> pJson = controller.doRequestMajorRanking(response);
		String result = "[{\"id\":49,\"valor\":369922.75,\"nome\":\"MOREIRA MENDES\",\"partido\":\"PSD\",\"uf\":\"RO\"}]";
		Parlamentar pResult = JSONHelper.listParlamentarFromJSON(result).get(0);

		assertEquals(pResult.getId(), pJson.get(0).getId());
		assertEquals(pResult.getNome(), pJson.get(0).getNome());
	}

	public void testDorequestMajorRankingTransmissionException()
			throws NullParlamentarException, ConnectionFailedException,
			RequestFailedException, TransmissionException {
		try {
			controller.doRequestMajorRanking(null);

			fail("Exception not launched");
		} catch (TransmissionException npe) {

		}
	}

	public void testCheckEmptyDBFalse() {

		assertFalse(controller.checkEmptyDB());

	}

	public void testCheckEmptyDBTrue() {

		controller.getAll();
		Iterator<Parlamentar> iterator = controller.getAll().iterator();

		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			dao.deleteParlamentar(p);
		}

		assertTrue(controller.checkEmptyDB());
	}
}
