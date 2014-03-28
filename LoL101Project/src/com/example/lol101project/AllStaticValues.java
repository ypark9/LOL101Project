package com.example.lol101project;

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
	
}
