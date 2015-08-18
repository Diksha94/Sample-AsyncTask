package com.example.leanstartupbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    LinkedList<Order> data= new LinkedList<>();
    LinkedList<Date> finishTime = new LinkedList<>();
    List<OrderList> mydata = new ArrayList<>();
    EditText et_itemId;
    Button submit;
    OrderListAdapter adapter;
    RecyclerView rv_orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_itemId = (EditText)findViewById(R.id.et_itemid);
        submit = (Button)findViewById(R.id.bt_submit);
        adapter = new OrderListAdapter(this, mydata);

        rv_orderList= (RecyclerView) findViewById(R.id.rv_orders);
        rv_orderList.setAdapter(adapter);
        rv_orderList.setLayoutManager(new LinearLayoutManager(this));

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        Log.d("my time",dateFormat.format(date)+"");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int n = data.size()==0?1:data.getLast().orderId+1;
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date orderTime = new Date();
                Log.d("my time", dateFormat.format(orderTime) + "");
                data.add(new Order(n, Integer.parseInt(et_itemId.getText().toString()), orderTime));
                Toast.makeText(getApplicationContext(),"Order placed. Wait for " + MyMenu.mytime[Integer.parseInt(et_itemId.getText().toString())-1]+" seconds",Toast.LENGTH_SHORT).show();
                Log.d("our data",n+", "+Integer.parseInt(et_itemId.getText().toString())+", "+orderTime);
                new Chef().execute(data.get(n-1));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Chef extends AsyncTask<Order, String, Order> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Order doInBackground(Order... arg) {
            long t;
            if(arg[0].orderId==1  ) {
                 t = arg[0].ordertime.getTime();
            }
            else if(arg[0].ordertime.compareTo(finishTime.getLast())>0)
            {
                t = arg[0].ordertime.getTime();
            }
            else {
                t = finishTime.getLast().getTime();
            }
            Log.d("time got",t+"");
            long addT= MyMenu.itemTime[arg[0].orItemId-1];
            Log.d("add time",addT+"");
            finishTime.add(new Date(t + (addT)));

            Log.d("finish time",finishTime.getLast()+"");
            try{
                Log.d("sleeping","sleeping");
                Thread.sleep(addT, 0);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Log.d("mythread error",ex.toString());
            }
            return arg[0];
        }


        @Override
        protected void onPostExecute(Order result) {
            Log.d("order id",result.orderId+"");
            Log.d("Item", MyMenu.itemName[result.orItemId - 1]);
            Log.d("order placed at",result.ordertime+"");
            Log.d("order completed at",finishTime.get(result.orderId-1)+"");
            writeFile(result);
        }

        void writeFile(Order result)
        {
            try{
                File file = new File(getApplicationContext().getFilesDir().getPath().toString()+"leanbar.txt");
                file.createNewFile();
                Log.d("file created", file.getAbsolutePath() + file.exists());
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                String mystring=result.orderId + "  "+MyMenu.itemName[result.orItemId - 1]+"  "+dateFormat.format(result.ordertime)+ " "+finishTime.get(result.orderId-1)+"\n";
                bw.write(mystring);
                bw.flush();
                bw.close();

                mydata.add(new OrderList(result.orderId,MyMenu.itemName[result.orItemId - 1],result.ordertime,finishTime.get(result.orderId-1)));
                adapter.notifyDataSetChanged();

            }catch(IOException e){
                e.printStackTrace();
                Log.e("Write failed",e.toString());
            }
        }
    }

}
