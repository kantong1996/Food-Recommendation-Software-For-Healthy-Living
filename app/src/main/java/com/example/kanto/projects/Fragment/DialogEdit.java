package com.example.kanto.projects.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.kanto.projects.model.MyDBClass;
import com.example.kanto.projects.R;

import java.util.ArrayList;

public class DialogEdit extends DialogFragment {
    private MyDBClass mDBClass = new MyDBClass(getContext());
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private Cursor mCurupdate;
    public int edit = 0;
    ArrayList <String> Foodedit = new ArrayList<String>();
    ArrayList <String> clear = new ArrayList<String>();
    ArrayList <Integer> Foodid = new ArrayList<Integer>();
    ListView listView;
    Bundle mArgs;
    String data;
    Double Protien;
    Double Fat;
    Double Carb;
    int fid;
    int mid;

    ArrayAdapter<String> adapterDir;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public DialogEdit(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_edit,container,false);

        listView = (ListView) rootView.findViewById(R.id.listedit);
        mArgs = getArguments();
        data = mArgs.getString("KCAL");
        fid = mArgs.getInt("FID");
        mid = mArgs.getInt("MID");
        Protien = mArgs.getDouble("Protien");
        Fat = mArgs.getDouble("Fat");
        Carb = mArgs.getDouble("Carb");
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM Food Where KCAL <= "+data+" AND KCAL >="+(Double.parseDouble(data)-50)+" AND PROTEIN <= "+Protien+"",null);
        mCursor.moveToFirst();
        if(adapterDir!=null){
            adapterDir.clear();
        }
        while (!mCursor.isAfterLast()){
            Foodedit.add(mCursor.getString(mCursor.getColumnIndex("FName"))+"\t\t"+mCursor.getString(mCursor.getColumnIndex("KCAL")));
            Foodid.add(mCursor.getInt(mCursor.getColumnIndex("FID")));
            mCursor.moveToNext();
        }
        final Bundle args = new Bundle();
        adapterDir = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Foodedit);
        listView.setAdapter(adapterDir);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + Foodid.get(position), Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
                mCurupdate = mDb.rawQuery("update Management set FID = "+Foodid.get(position)+" where FID = "+fid+" AND MID = "+mid+"",null);
                mCurupdate.moveToFirst();
                adapterDir.clear();
//                HomeFragment nextFrag= new HomeFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, nextFrag,"Home")
//                        .commit();
            }
        });


        return rootView;
    }


}

