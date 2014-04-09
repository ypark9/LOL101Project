package com.example.lolproject;

public class AllStaticValues {

	/*
	 * bottom URL are for tracking recent games of "specific" user.
	 */
	public final static String URL_GAME_PART1 = "https://prod.api.pvp.net/api/lol/na/v1.3/game/by-summoner/";
	public final static String URL_GAME_PART2 = "/recent?api_key=82968033-b737-453c-aff5-b2b8c81fd7d3";
	public final static String API_KEY_IS = "?api_key=";
	public final static String USING_LEAGUE_API_VERSION_1_3 = "https://prod.api.pvp.net/api/lol/na/v1.3/";
	public final static String NAME = "name";
	public final static String ID = "id";
	public final static String CHAMPIONS = "champions";
	
	/***************************************************************************
	 * Develop KEYS of League API
	 ****************************************************************************/
	public final static String DEVELOP_KEY_INU = "82968033-b737-453c-aff5-b2b8c81fd7d3";
	public final static String DEVELOP_KEY_ROCKET = "feef8114-1b45-4b48-8e58-56847d1b5fc6";
	public final static String DEVELOP_KEY_RANTOL = "2a7807f1-3d3d-42bf-acab-970da47031a7";
	// Paul, we need your develop key, too. I made 2 more league ID for this
	// project LOL.
	public final static String DEVELOP_KEY_RYBINK = "";
	
	
	// How many games are covered?
	public static int GAME_CAN_BE_HANDLED = 1;
	/*
	 * URL for tracking champion name by champion ID
	 */
	public static String url_Champion_code = "https://prod.api.pvp.net/api/lol/na/v1.1/champion"
			+ API_KEY_IS + DEVELOP_KEY_INU;
	// // Array of integers points to images stored in /res/drawable-ldpi/
		// int[] flags = new int[] { R.drawable.aatrox, R.drawable.ahri,
		// R.drawable.akali, R.drawable.alistar, R.drawable.amumu,
		// R.drawable.anivia, R.drawable.annie, R.drawable.ashe,
		// R.drawable.blitzcrank, R.drawable.brand, R.drawable.caitlyn,
		// R.drawable.cassiopeia, R.drawable.chogath, R.drawable.corki,
		// R.drawable.darius, R.drawable.diana, R.drawable.draven,
		// R.drawable.drmundo, R.drawable.elise, R.drawable.evelynn,
		// R.drawable.ezreal, R.drawable.fiddlesticks, R.drawable.fiora,
		// R.drawable.fizz, R.drawable.galio, R.drawable.gangplank,
		// R.drawable.garen, R.drawable.gragas, R.drawable.graves,
		// R.drawable.hecarim, R.drawable.heimerdinger,
		// R.drawable.ic_launcher, R.drawable.irelia, R.drawable.janna,
		// R.drawable.jarvaniv, R.drawable.jax, R.drawable.jayce,
		// R.drawable.jinx, R.drawable.karma, R.drawable.karthus,
		// R.drawable.kassadin, R.drawable.katarina, R.drawable.kayle,
		// R.drawable.kennen, R.drawable.khazix, R.drawable.kogmaw,
		// R.drawable.leblanc, R.drawable.leesin, R.drawable.leona,
		// R.drawable.lissandra, R.drawable.lol101logo, R.drawable.lucian,
		// R.drawable.lulu, R.drawable.lux, R.drawable.malphite,
		// R.drawable.malzahar, R.drawable.maokai, R.drawable.masteryi,
		// R.drawable.missfortune, R.drawable.monkeyking,
		// R.drawable.mordekaiser, R.drawable.morgana, R.drawable.nami,
		// R.drawable.nasus, R.drawable.nautilus, R.drawable.nidalee,
		// R.drawable.nocturne, R.drawable.nunu, R.drawable.olaf,
		// R.drawable.orianna, R.drawable.pantheon, R.drawable.poppy,
		// R.drawable.quinn, R.drawable.rammus, R.drawable.renekton,
		// R.drawable.rengar, R.drawable.riven, R.drawable.rumble,
		// R.drawable.ryze, R.drawable.sejuani, R.drawable.shaco,
		// R.drawable.shen, R.drawable.shyvana, R.drawable.singed,
		// R.drawable.sion, R.drawable.sivir, R.drawable.skarner,
		// R.drawable.sona, R.drawable.soraka, R.drawable.swain,
		// R.drawable.syndra, R.drawable.talon, R.drawable.taric,
		// R.drawable.teemo, R.drawable.thresh, R.drawable.tristana,
		// R.drawable.trundle, R.drawable.tryndamere, R.drawable.twistedfate,
		// R.drawable.twitch, R.drawable.udyr, R.drawable.urgot,
		// R.drawable.varus, R.drawable.vayne, R.drawable.veigar,
		// R.drawable.vi, R.drawable.viktor, R.drawable.vladimir,
		// R.drawable.volibear, R.drawable.warwick, R.drawable.xerath,
		// R.drawable.xinzhao, R.drawable.yasuo, R.drawable.yorick,
		// R.drawable.zac, R.drawable.zed, R.drawable.ziggs,
		// R.drawable.zilean, R.drawable.zyra };
}
