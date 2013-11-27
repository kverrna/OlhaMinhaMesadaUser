package com.OMM.application.user.view;

import java.text.DecimalFormat;
import java.util.Iterator;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.CotaParlamentar;

public class ParlamentarDetailFragment extends Fragment {

	ParlamentarUserController parlamentarController;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());

		View view = inflater.inflate(R.layout.gui_detalhe, container, false);

		final Button btn_detalhe_seguir = (Button) view
				.findViewById(R.id.btn_detalhe_seguir);
		final Button btn_detalhe_desseguir = (Button) view
				.findViewById(R.id.btn_detalhe_desseguir);
		btn_detalhe_desseguir.setVisibility(View.INVISIBLE);

		// if(parlamentarController.getParlamentar().isSeguido() == 1) {
		btn_detalhe_desseguir.setVisibility(View.INVISIBLE);

		// } else {
		// btn_detalhe_seguir.setVisibility(View.INVISIBLE);
		// }

		createButtons(view);

		btn_detalhe_desseguir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					parlamentarController.getParlamentar().setSeguido(0);
					parlamentarController.unFollowedParlamentar();
					Toast.makeText(getActivity(), "Parlamentar DesSeguido",
							Toast.LENGTH_SHORT).show();
					// btn_detalhe_desseguir.setVisibility(View.GONE);
					// btn_detalhe_seguir.setVisibility(View.VISIBLE);

				} catch (NullParlamentarException nullEx) {
					Toast.makeText(getActivity(),
							"Deu pau,compre outro celular", Toast.LENGTH_SHORT)
							.show();

				}
			}
		});

		btn_detalhe_seguir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					parlamentarController.getParlamentar().setSeguido(1);
					parlamentarController.followedParlamentar();
					Toast.makeText(getActivity(), "Parlamentar Seguido",
							Toast.LENGTH_SHORT).show();
					btn_detalhe_seguir.setVisibility(View.GONE);
					btn_detalhe_desseguir.setVisibility(View.VISIBLE);

				} catch (NullParlamentarException nullEx) {
					Toast.makeText(getActivity(),
							"Deu pau,compre outro celular", Toast.LENGTH_SHORT)
							.show();

				}
			}
		});
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	public void setBarras() {
		
		parlamentarController.getParlamentar();

		TextView view = (TextView) getView().findViewById(R.id.nome);	
		view.setText(parlamentarController.getParlamentar().getNome());		
		Iterator<CotaParlamentar> iterator = parlamentarController.getParlamentar().getCotas().iterator();

		while (iterator.hasNext()) {

			CotaParlamentar cota = iterator.next();
//TODO: Corrigir m�s solicitado
			if (cota.getMes() == 6) {
				

				double valorCota = cota.getValor();
							
				int numeroSubCota = cota.getNumeroSubCota();
				
				Log.i("ParlamentarDetailFragment","Valor da cota " + valorCota + " da nCota " + numeroSubCota);

				DecimalFormat valorCotaDecimal = new DecimalFormat("#,###.00"); 
				
				switch (numeroSubCota) {

				case 1:

					ImageView barEscritorio = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_escritorio);
					TextView textViewEscritorio = (TextView) getActivity().findViewById(R.id.valor_cota_escritorio);
					textViewEscritorio.setText("R$ " + valorCotaDecimal.format(valorCota));
				
					sizeBar(barEscritorio, valorCota);
					
					break;

				case 3:

					ImageView barCombustivel = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_gasolina);
					TextView textViewCombustivel = (TextView) getActivity().findViewById(R.id.valor_cota_gasolina);
					textViewCombustivel.setText("R$ " + valorCotaDecimal.format(valorCota));
				
					sizeBar(barCombustivel, valorCota);

					break;

				case 4:

					ImageView barTrabalhoTecnico = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_trabalho_tecnico);
					TextView textViewTrabalhoTecnico = (TextView) getActivity().findViewById(R.id.valor_cota_trabalho_tecnico);
					textViewTrabalhoTecnico.setText("R$ " + valorCotaDecimal.format(valorCota));
					
					sizeBar(barTrabalhoTecnico, valorCota);

					break;

				case 5:

					ImageView barDivulgacao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_divulgacao);
					TextView textViewDivulgacao = (TextView) getActivity().findViewById(R.id.valor_cota_divulgacao);
					textViewDivulgacao.setText("R$ " + valorCotaDecimal.format(valorCota));
			
					sizeBar(barDivulgacao, valorCota);

					break;

				case 8:

					ImageView barSeguranca = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_seguranca);
					TextView textViewSeguranca = (TextView) getActivity().findViewById(R.id.valor_cota_seguranca);
					textViewSeguranca.setText("R$ " + valorCotaDecimal.format(valorCota));

					sizeBar(barSeguranca, valorCota);

					break;

				case 9:

					ImageView barAluguelAviao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_aluguel_aviao);
					TextView textViewAluguelAviao = (TextView) getActivity().findViewById(R.id.valor_cota_aluguel_aviao);
					textViewAluguelAviao.setText("R$ " + valorCotaDecimal.format(valorCota));

					sizeBar(barAluguelAviao, valorCota);

					break;

				case 10:

					ImageView barTelefonia = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_telefonia);
					TextView textViewTelefonia = (TextView) getActivity().findViewById(R.id.valor_cota_telefonia);
					textViewTelefonia.setText("R$ " + valorCotaDecimal.format(valorCota));

					sizeBar(barTelefonia, valorCota);

					break;

				case 11:

					ImageView barCorreios = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_correios);
					TextView textViewCorreios = (TextView) getActivity().findViewById(R.id.valor_cota_correios);
					textViewCorreios.setText("R$ " + valorCotaDecimal.format(valorCota));


					sizeBar(barCorreios, valorCota);

					break;

				case 13:

					ImageView barAlimentacao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_alimentacao);
					TextView textViewAlimentacao = (TextView) getActivity().findViewById(R.id.valor_cota_alimentacao);
					textViewAlimentacao.setText("R$ " + valorCotaDecimal.format(valorCota));

					sizeBar(barAlimentacao, valorCota);

					break;

				case 14:

					ImageView barHospedagem = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_hoespedagem);
					TextView textViewHospedagam = (TextView) getActivity().findViewById(R.id.valor_cota_hospedagem);
					textViewHospedagam.setText("R$ " + valorCotaDecimal.format(valorCota));
					
					sizeBar(barHospedagem, valorCota);

					break;

				case 999:

					ImageView barBilhetesAereos = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_bilhetes_aereos);
					TextView textViewBilhetesAereos = (TextView) getActivity().findViewById(R.id.valor_cota_bilhetes_aereos);
					textViewBilhetesAereos.setText("R$ " + valorCotaDecimal.format(valorCota));

					sizeBar(barBilhetesAereos, valorCota);
					
					
					break;
					
				default: 
						Log.i("Parlamentar detail Fragment",
								"Cod estranho");

				}

			}

		}

	}

	public void sizeBar(ImageView bar, double valorCota) {

		if (valorCota <= 500) {

			// Nothing should be done

		} else if (valorCota <= 1500) {

			bar.setImageResource(R.drawable.barra_verde);

		} else if (valorCota <= 3000) {

			bar.setImageResource(R.drawable.barra_amarela);

		} else if (valorCota <= 5000) {

			bar.setImageResource(R.drawable.barra_laranja);

		} else if (valorCota > 5000) {

			bar.setImageResource(R.drawable.barra_vermelha);

		} else {
			Log.i("Parlamentar detail Fragment", "Valor Mensal Negativo");
		}

	}

	public void createButtons(View view) {

		final TextView btn_cota_alimentacao = (TextView) view
				.findViewById(R.id.btn_cota_alimentacao);
		final Button btn_cota_aluguel_aviao = (Button) view
				.findViewById(R.id.btn_cota_aluguel_aviao);
		final Button btn_cota_bilhetes_aereos = (Button) view
				.findViewById(R.id.btn_cota_bilhetes_aereos);
		final Button btn_cota_correios = (Button) view
				.findViewById(R.id.btn_cota_correios);
		final Button btn_cota_divulgacao = (Button) view
				.findViewById(R.id.btn_cota_divulgacao);
		final Button btn_cota_escritorio = (Button) view
				.findViewById(R.id.btn_cota_escritorio);
		final Button btn_cota_gasolina = (Button) view
				.findViewById(R.id.btn_cota_gasolina);
		final Button btn_cota_hospedagem = (Button) view
				.findViewById(R.id.btn_cota_hospedagem);
		final Button btn_cota_seguranca = (Button) view
				.findViewById(R.id.btn_cota_seguranca);
		final Button btn_cota_telefone = (Button) view
				.findViewById(R.id.btn_cota_telefone);
		final Button btn_cota_trabalho_tecnico = (Button) view
				.findViewById(R.id.btn_cota_trabalho_tecnico);

		btn_cota_alimentacao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Alimenta��o", Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_cota_aluguel_aviao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Fretamento de Aeronaves",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_bilhetes_aereos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Bilhetes A�reos",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_correios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Servi�os Postais",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_correios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Servi�os Postais",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_divulgacao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),
						"Divulga��o da Atividade Parlamentar",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_escritorio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),
						"Manuten��o de Escrit�rio de Apoio", Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_cota_gasolina.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Combust�veis e Lubrificantes",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_hospedagem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Hospedagem", Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_cota_seguranca.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Servi�o de Seguran�a",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_telefone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Telefonia", Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_cota_trabalho_tecnico
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(),
								"Consultorias, Pesquisas e Trabalhos T�cnicos",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

}