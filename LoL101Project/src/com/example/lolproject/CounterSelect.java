package com.example.lolproject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class CounterSelect extends DialogFragment {

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String options[] = {"Good Against","Bad Against","Good With"};
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Champion Info")
	           .setItems(options, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               // The 'which' argument contains the index position
	               // of the selected item
	            	 Log.d("Chosen index","Index: "+which); 
	            	 if(which == 0)
	            	 {
	            		Intent intent = new Intent(getActivity(), GoodAgainst.class);
	         			startActivity(intent);

	            	 } else if(which ==1){
	            		 Intent intent = new Intent(getActivity(), BadAgainst.class);
		         		 startActivity(intent);

	            	 }else if(which ==2){
	            		 Intent intent = new Intent(getActivity(), GoodWith.class);
		         		 startActivity(intent);

	            	 }

	           }
	    });
	   	    
	    return builder.create();
	}

}
