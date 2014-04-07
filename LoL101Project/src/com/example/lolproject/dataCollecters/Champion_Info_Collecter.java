package com.example.lolproject.dataCollecters;

import java.util.HashMap;

import org.apache.http.client.CircularRedirectException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lolproject.R;
import com.example.lolproject.R.drawable;

import android.util.Log;

public class Champion_Info_Collecter {

	public static HashMap<String, String> champion_Name_ID;
	private final static String url_Champion_code = "https://prod.api.pvp.net/api/lol/na/v1.1/champion?api_key=82968033-b737-453c-aff5-b2b8c81fd7d3";

	public HashMap<String, String> champ_info() {
		/*
		 * Creating service handler class instance
		 */
		ServiceHandler sh = new ServiceHandler();
		String JSON_Champion_Name = sh.makeServiceCall(url_Champion_code,
				ServiceHandler.GET);
		/*
		 * YP: initialize Hash map for champion's name using RIOT API to find
		 * current available champions from JSON result and save those into Hash
		 * map for using it later to output its name instead of just champion id
		 * since people cannot understand just champion ID. (ex: 1 for Annie,
		 * but 1 means nothing to people)
		 */
		champion_Name_ID = new HashMap<String, String>();
		String champ_Name = "";
		String champ_ID = "";
		try {
			JSONObject json_Champion_code = new JSONObject(JSON_Champion_Name);
			JSONArray SummonerID = json_Champion_code.getJSONArray("champions");

			for (int i = 0; i < SummonerID.length(); i++) {
				JSONObject champ = SummonerID.getJSONObject(i);
				champ_Name = champ.getString("name");
				champ_ID = champ.getString("id");

				champion_Name_ID.put(champ_ID, champ_Name);
				Log.d("champ name ", champ_Name);
			}
			// String name = SummonerID.getString("name");
			// String profileIconId = SummonerID.getString("profileIconId");
			// String summonerLevel = SummonerID.getString("summonerLevel");
			// String revisionDate = SummonerID.getString("revisionDate");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return champion_Name_ID;
	}

	public int champIconCollector(String champname) {
		String cname = champname.toLowerCase();
		Log.d(champname, cname);
		HashMap<String, Integer> cIcon = new HashMap<String, Integer>();

		cIcon.put("aatrox", R.drawable.aatrox);
		cIcon.put("ahri", R.drawable.ahri);
		cIcon.put("akali", R.drawable.akali);
		cIcon.put("alistar", R.drawable.alistar);
		cIcon.put("amumu", R.drawable.amumu);
		cIcon.put("anivia", R.drawable.anivia);
		cIcon.put("annie", R.drawable.annie);
		cIcon.put("ashe", R.drawable.ashe);
		cIcon.put("blitzcrank", R.drawable.blitzcrank);
		cIcon.put("brand", R.drawable.brand);
		cIcon.put("caitlyn", R.drawable.caitlyn);
		cIcon.put("cassiopeia", R.drawable.cassiopeia);
		cIcon.put("chogath", R.drawable.chogath);
		cIcon.put("corki", R.drawable.corki);
		cIcon.put("darius", R.drawable.darius);
		cIcon.put("diana", R.drawable.diana);
		cIcon.put("draven", R.drawable.draven);
		cIcon.put("drmundo", R.drawable.drmundo);
		cIcon.put("elise", R.drawable.elise);
		cIcon.put("evelynn", R.drawable.evelynn);
		cIcon.put("ezreal", R.drawable.ezreal);
		cIcon.put("fiddlesticks", R.drawable.fiddlesticks);
		cIcon.put("fiora", R.drawable.fiora);
		cIcon.put("fizz", R.drawable.fizz);
		cIcon.put("galio", R.drawable.galio);
		cIcon.put("gangplank", R.drawable.gangplank);
		cIcon.put("garen", R.drawable.garen);
		cIcon.put("gragas", R.drawable.gragas);
		cIcon.put("graves", R.drawable.graves);
		cIcon.put("hecarim", R.drawable.hecarim);
		cIcon.put("heimerdinger", R.drawable.heimerdinger);
		cIcon.put("irelia", R.drawable.irelia);
		cIcon.put("janna", R.drawable.janna);
		cIcon.put("jarvaniv", R.drawable.jarvaniv);
		cIcon.put("jax", R.drawable.jax);
		cIcon.put("jayce", R.drawable.jayce);
		cIcon.put("jinx", R.drawable.jinx);
		cIcon.put("karma", R.drawable.karma);
		cIcon.put("karthus", R.drawable.karthus);
		cIcon.put("kassadin", R.drawable.kassadin);
		cIcon.put("katarina", R.drawable.katarina);
		cIcon.put("kayle", R.drawable.kayle);
		cIcon.put("kennen", R.drawable.kennen);
		cIcon.put("khazix", R.drawable.khazix);
		cIcon.put("kogmaw", R.drawable.kogmaw);
		cIcon.put("leblanc", R.drawable.leblanc);
		cIcon.put("leesin", R.drawable.leesin);
		cIcon.put("leona", R.drawable.leona);
		cIcon.put("lissandra", R.drawable.lissandra);
		cIcon.put("lucian", R.drawable.lucian);
		cIcon.put("lulu", R.drawable.lulu);
		cIcon.put("lux", R.drawable.lux);
		cIcon.put("malphite", R.drawable.malphite);
		cIcon.put("malzahar", R.drawable.malzahar);
		cIcon.put("maokai", R.drawable.maokai);
		cIcon.put("masteryi", R.drawable.masteryi);
		cIcon.put("missfortune", R.drawable.missfortune);
		cIcon.put("monkeyking", R.drawable.monkeyking);
		cIcon.put("mordekaiser", R.drawable.mordekaiser);
		cIcon.put("morgana", R.drawable.morgana);
		cIcon.put("nami", R.drawable.nami);
		cIcon.put("nasus", R.drawable.nasus);
		cIcon.put("nautilus", R.drawable.nautilus);
		cIcon.put("nidalee", R.drawable.nidalee);
		cIcon.put("nocturne", R.drawable.nocturne);
		cIcon.put("nunu", R.drawable.nunu);
		cIcon.put("olaf", R.drawable.olaf);
		cIcon.put("orianna", R.drawable.orianna);
		cIcon.put("pantheon", R.drawable.pantheon);
		cIcon.put("poppy", R.drawable.poppy);
		cIcon.put("quinn", R.drawable.quinn);
		cIcon.put("rammus", R.drawable.rammus);
		cIcon.put("renekton", R.drawable.renekton);
		cIcon.put("rengar", R.drawable.rengar);
		cIcon.put("riven", R.drawable.riven);
		cIcon.put("rumble", R.drawable.rumble);
		cIcon.put("ryze", R.drawable.ryze);
		cIcon.put("sejuani", R.drawable.sejuani);
		cIcon.put("shaco", R.drawable.shaco);
		cIcon.put("shen", R.drawable.shen);
		cIcon.put("shyvana", R.drawable.shyvana);
		cIcon.put("singed", R.drawable.singed);
		cIcon.put("sion", R.drawable.sion);
		cIcon.put("sivir", R.drawable.sivir);
		cIcon.put("skarner", R.drawable.skarner);
		cIcon.put("sona", R.drawable.sona);
		cIcon.put("soraka", R.drawable.soraka);
		cIcon.put("swain", R.drawable.swain);
		cIcon.put("syndra", R.drawable.syndra);
		cIcon.put("talon", R.drawable.talon);
		cIcon.put("taric", R.drawable.taric);
		cIcon.put("teemo", R.drawable.teemo);
		cIcon.put("thresh", R.drawable.thresh);
		cIcon.put("tristana", R.drawable.tristana);
		cIcon.put("trundle", R.drawable.trundle);
		cIcon.put("tryndamere", R.drawable.tryndamere);
		cIcon.put("twistedfate", R.drawable.twistedfate);
		cIcon.put("twitch", R.drawable.twitch);
		cIcon.put("udyr", R.drawable.udyr);
		cIcon.put("urgot", R.drawable.urgot);
		cIcon.put("varus", R.drawable.varus);
		cIcon.put("vayne", R.drawable.vayne);
		cIcon.put("veigar", R.drawable.veigar);
		cIcon.put("vi", R.drawable.vi);
		cIcon.put("viktor", R.drawable.viktor);
		cIcon.put("vladimir", R.drawable.vladimir);
		cIcon.put("volibear", R.drawable.volibear);
		cIcon.put("warwick", R.drawable.warwick);
		cIcon.put("xerath", R.drawable.xerath);
		cIcon.put("xinzhao", R.drawable.xinzhao);
		cIcon.put("yasuo", R.drawable.yasuo);
		cIcon.put("yorick", R.drawable.yorick);
		cIcon.put("zac", R.drawable.zac);
		cIcon.put("zed", R.drawable.zed);
		cIcon.put("ziggs", R.drawable.ziggs);
		cIcon.put("zilean", R.drawable.zilean);
		cIcon.put("zyra", R.drawable.zyra);

		return cIcon.get(cname);

	}

}
