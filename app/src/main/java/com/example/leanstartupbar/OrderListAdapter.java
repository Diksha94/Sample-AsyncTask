package com.example.leanstartupbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by diksha on 12/6/15.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder>{


        private LayoutInflater inflater;
        public List<OrderList> mydata = Collections.emptyList();
        private Context con;
        View view;


        public OrderListAdapter(Context context, List<OrderList> data) {
            con = context;
            inflater = LayoutInflater.from(context);
            this.mydata = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            view = inflater.inflate(R.layout.listcell, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            OrderList current = mydata.get(position);
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            try{
                holder.id.setText("Order no. is : "+current.orderId);
                holder.name.setText("Ordered for: "+current.orderName);
                holder.orderTime.setText("Ordered at: "+dateFormat.format(current.ordertime));
                holder.finishTime.setText("Finished at : "+dateFormat.format(current.finishtime));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        @Override
        public int getItemCount() {
            return mydata.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView id,name,orderTime,finishTime;
            LinearLayout unreadOrderList;

            public MyViewHolder(View itemView) {
                super(itemView);
                unreadOrderList = (LinearLayout) itemView.findViewById(R.id.ll);
                id=(TextView) itemView.findViewById(R.id.tv_itemid);
                name = (TextView) itemView.findViewById(R.id.tv_itemname);
                orderTime=(TextView) itemView.findViewById(R.id.tv_orderTime);
                finishTime=(TextView) itemView.findViewById(R.id.tv_finishTime);

            }
        }


    
}
