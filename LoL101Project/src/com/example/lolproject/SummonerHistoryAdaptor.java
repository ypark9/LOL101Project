//package com.example.lolproject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import com.example.lolproject.R;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///** An array adapter that knows how to render views when given CustomData classes */
//public class SummonerHistoryAdaptor extends ArrayAdapter<HashMap<String, String>> {
//    private Context context;
//    private ArrayList<HashMap<String, String>> GameList;
//    private int viewId;
//
//    private LayoutInflater mInflater;
//
//    public SummonerHistoryAdaptor(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> GameList) {
//        super(context, textViewResourceId, GameList);
//
//        this.context = context;
//        this.viewId = textViewResourceId;
//        this.GameList = GameList; 
//    }
//
//     @Override
//     public int getCount() {
//         return GameList.size();
//     }
//
//    /*
//     * We are overriding the getView method here - this is what defines how each
//     * list item will look.
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {         
//        // Assign the view we are converting to a local variable
//        View vi = convertView;
//        Holder holder;      
//
//        if (convertView == null) {
//
//            // Inflate the view since it does not exist
//            if (vi == null) {
//                mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                vi = mInflater.inflate(R.layout.summoner_history, null);
//            }
//
//            /*
//             * Recall that the variable position is sent in as an argument to this method.
//             * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
//             * iterates through the list we sent it)
//             * 
//             * Therefore, i refers to the current Item object.
//             */
//
//            // This is how you obtain a reference to the TextViews.
//            // These TextViews are created in the XML files we defined.
//
//            // Create and save off the holder in the tag so we get quick access to inner fields
//            // This must be done for performance reasons
//
//            holder = new Holder();
//            holder.textView = (TextView) vi.findViewById(R.id.champ_name);
//            holder.imageView = (ImageView) vi.findViewById(R.id.champ_icon);
//
//            vi.setTag(holder);                                  
//
//        } else {
//            holder = (Holder) vi.getTag();
//        }
//
//        // check to see if each individual textview is null.
//        // if not, assign some text!
//        // Populate the text 
//
//        HashMap<String, String> currentData = new HashMap<String, String>();
//        currentData = GameList.get(position);
//
//        if (currentData != null) {
//            holder.textView.setText(currentData.get(key));
//            holder.imageLoader = new ImageLoader(context.getApplicationContext());    
//            holder.imageLoader.DisplayImage(currentData.get(MainActivity.KEY_THUMBNAIL), holder.imageView);
//        }       
//
//        // Set the color
//        vi.setBackgroundColor(Color.DKGRAY);
//        return vi;
//    }
//
//    /** View holder for the views we need access to */
//    private static class Holder {
//        public TextView textView;
//        public ImageView imageView;
//        public ImageLoader imageLoader;
//    }
//}