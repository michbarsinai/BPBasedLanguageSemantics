package aspects;

import java.util.ArrayList;
import java.util.HashMap;

public class MSDSPOStaticInfo {

	private static HashMap<String,HashMap<Integer,String>> interactionsLifelines=null;
	private static HashMap<String,HashMap<Integer,String>> interactionsVariables=null;


	public static HashMap<String,HashMap<Integer,String>>
		getInteractionLifelines()
	{
		if(interactionsLifelines==null){
			buildInteractionsLifelines();
		}
		return interactionsLifelines;
	}

	private static void buildInteractionsLifelines()
	{
		interactionsLifelines=new HashMap<String,HashMap<Integer,String>>();
		HashMap<Integer,String> lifelines = null;

		lifelines = new HashMap<Integer,String>();
		lifelines.put(0,"User");
		lifelines.put(1,"Alice");
		lifelines.put(2,"Bob");
		interactionsLifelines.put("main", lifelines);
	}


	public static HashMap<String,HashMap<Integer,String>>
		getInteractionVariables()
	{
		if(interactionsVariables==null){
			buildInteractionsVariables();
		}
		return interactionsVariables;
	}

	private static void buildInteractionsVariables()
	{
		interactionsVariables=new HashMap<String,HashMap<Integer,String>>();
		HashMap<Integer,String> variables = null;
	}

}

