package com.example.andoirdlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
   int numObjects;
    private TextView txtInput;
    private ArrayList<Message> arrayList;
    private Message message;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        if(arrayList==null){
        arrayList=new ArrayList<>();}

//        ListAdapter adt = new MyOwnAdapter();

        ListView theList = (ListView)findViewById(R.id.the_list);

//       String[] list={""};Arrays.asList(list)

        TextView chat=findViewById(R.id.Editxt3);
//        adapter_send= new ArrayAdapter<String>(this,R.layout.chatroom,R.id.Line_send,arrayList);
//        adapter_receive= new ArrayAdapter<String>(this,R.layout.chatroom,R.id.Line_receive,arrayList);



        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        String [] columns = {MyDatabaseOpenHelper.COL_MSG,MyDatabaseOpenHelper.COL_DIR, MyDatabaseOpenHelper.COL_ID};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int msgColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MSG);
        int dirColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_DIR);
//        int sendColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_SENT);
//        int recColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_REC);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        while(results.moveToNext())
        {
            String msg= results.getString(msgColumnIndex);
            int dir=results.getInt(dirColIndex);
//            String sent = results.getString(sendColIndex);
//            String rec = results.getString(recColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            arrayList.add(new Message(msg,dir, id));

        }

        printCursor(results);
        ListAdapter adt= new MyArrayAdapter(arrayList);
        theList.setAdapter(adt);

        Button send =(Button)findViewById(R.id.buttonsend) ;
        send.setOnClickListener(a ->{
//        message=new Message(chat.getText().toString(),1,message.getMsg_id());
           // arrayList.add(message);

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_MSG,chat.getText().toString());
            newRowValues.put(MyDatabaseOpenHelper.COL_DIR,1);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
            Message newMessage=new Message(chat.getText().toString(),1,newId);
            arrayList.add(newMessage);
            ((MyArrayAdapter)adt).notifyDataSetChanged();
           // adapter= new MyArrayAdapter(arrayList);
           chat.setText(null);

//            adapter.getView(adapter.getCount()+1,null,findViewById(R.id.Line_send));

          //  adapter.notifyDataSetChanged();
        });

        Button receive =(Button)findViewById(R.id.buttonreceive) ;
        receive.setOnClickListener(b->{
            message=new Message(chat.getText().toString(),2,message.getMsg_id());
//            theList.setAdapter(adapter_receive);
//            arrayList.add(message);
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_MSG,chat.getText().toString());
            newRowValues.put(MyDatabaseOpenHelper.COL_DIR,2);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
            Message newMessage=new Message(chat.getText().toString(),2,newId);
            arrayList.add(newMessage);

            ((MyArrayAdapter)adt).notifyDataSetChanged();
            //MyArrayAdapter adapter= new MyArrayAdapter(arrayList);
            chat.setText(null);
//            adapter.getView(adapter.getCount()+1,null,findViewById(R.id.Line_receive));
//            adapter_receive.notifyDataSetChanged();
           //  adapter.notifyDataSetChanged();
        });

        SwipeRefreshLayout refresher = (SwipeRefreshLayout)findViewById(R.id.refresher) ;
        refresher.setOnRefreshListener(()-> {
            ListAdapter adapter= new MyArrayAdapter(arrayList);
            numObjects =adapter.getCount();
            theList.setSelection(numObjects - 1);
            ((MyArrayAdapter)adt).notifyDataSetChanged();
            refresher.setRefreshing( false );
        });


   //     theList.setAdapter(adt);



        //This listens for items being clicked in the list view
//        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
//            Log.e("you clicked on :" , "item "+ position);
//
//            numObjects = 20;
//            ((MyOwnAdapter) adt).notifyDataSetChanged();
//        });
    }

//    This class needs 4 functions to work properly:
//    protected class MyOwnAdapter extends BaseAdapter
//    {
//        private List dataCopy = null;
//
//        //Keep a reference to the data:
//        public MyOwnAdapter(List originalData)
//        {
//            dataCopy = originalData;
//        }
//
//        //You can give it an array
//        public MyOwnAdapter( array)
//        {
//            dataCopy = Arrays.asList(array);
//
//            @Override
//        public int getCount() {
//            return numObjects;
//        }
//
//        public Object getItem(int position){
//            return "SHow this in row "+ position;
//        }
//
//        public View getView(int position, View old, ViewGroup parent)
//        {
//            LayoutInflater inflater = getLayoutInflater();
//
//            View newView = inflater.inflate(R.layout.single_row_send, parent, false );
//
//
//            TextView rowText = (TextView)newView.findViewById(R.id.Line);
//            String stringToShow = getItem(position).toString();
//            rowText.setText( stringToShow );
//            //return the row:
//            return newView;
//        }
//
//        public long getItemId(int position)
//        {
//            return position;
//        }
//    }

    //A copy of ArrayAdapter. You just give it an array and it will do the rest of the work.
     protected class MyArrayAdapter<E> extends BaseAdapter
    {
        private List<E> dataCopy = null;

        //Keep a reference to the data:
        public MyArrayAdapter(List<E> originalData)
        {
            dataCopy = originalData;
        }

        //You can give it an array
        public MyArrayAdapter(E [] array)
        {
            dataCopy = Arrays.asList(array);
        }
        public MyArrayAdapter(){};

        //Tells the list how many elements to display:
        public int getCount()
        {
            return dataCopy.size();
        }


        public E getItem(int position){
            return dataCopy.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            View rowView = null;

            //get an object to load a layout:
            LayoutInflater inflater = getLayoutInflater();
            message=(Message)getItem(position);
            //View rowView=inflater.inflate(R.layout.single_row_send, parent, false);
            if(message.getMessageDirection()==1){
                rowView=inflater.inflate(R.layout.single_row_send,parent,false);
                txtInput=(TextView)rowView.findViewById(R.id.Line_send);
            }

            if(message.getMessageDirection()==2){
                rowView=inflater.inflate(R.layout.single_row_receive,parent,false);
                txtInput=(TextView)rowView.findViewById(R.id.Line_receive);
            }


            //Get the string to go in row: position
            String toDisplay = getItem(position).toString();
            //Set the text of the text view
            txtInput.setText(toDisplay);


            //Return the text view:
            return rowView;
        }


        //Return 0 for now. We will change this when using databases
        @Override
        public long getItemId(int position)
        {
            return 0;
        }
    }

    public void printCursor( Cursor c){

        Log.e("number of columns","Total_cols: "+c.getColumnCount());
        c.moveToFirst();
        for(int i=0;i<c.getColumnCount();i++) {
            Log.e("name of columns", "column_names: " + c.getColumnName(i));
            c.moveToNext();
        }

        Log.e("number of results","numOfResults"+c.getCount());
        c.moveToFirst();
        while(!c.isAfterLast()){
            Log.e("rows of results", "text: " + c.getString(c.getColumnIndex(MyDatabaseOpenHelper.COL_MSG)));
            c.moveToNext();
        }
    }
}
