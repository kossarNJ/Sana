package com.payamnet.sana.sana.view.adapters.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.model.Document;

import java.util.ArrayList;

/**
 * Created by kosar on 8/2/18.
 */

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.MyViewHolder>  {

    Context context;
    private ArrayList<Document> docs = new ArrayList<>();

    public DocumentListAdapter(Context context, ArrayList<Document> docs) {
        this.context = context;
        this.docs = docs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("groupId", Group.getGroupID(groups).get(position));
//                //might be problematic
//                Fragment groupFragment = new GroupFragment();
//                groupFragment.setArguments(bundle);
//                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, groupFragment).commit();
//            }
//        });

        holder.docName.setText(docs.get(position).getTitle());
        holder.docAuthor.setText(docs.get(position).getAuthor());
        holder.docPublisher.setText(docs.get(position).getPublisher());
        holder.docSubject.setText(docs.get(position).getSubject());

    }

    @Override
    public int getItemCount() {
        return docs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView docName;
        TextView docAuthor;
        TextView docPublisher;
        TextView docSubject;
        Button detailButton;


        public MyViewHolder(View itemView) {
            super(itemView);

            docName = (TextView) itemView.findViewById(R.id.doc_name);
            docAuthor = (TextView) itemView.findViewById(R.id.doc_author);
            docPublisher = (TextView) itemView.findViewById(R.id.doc_publisher);
            docSubject = (TextView) itemView.findViewById(R.id.doc_subject);
            detailButton = (Button) itemView.findViewById(R.id.doc_detail_button);
        }
    }

    public ArrayList<Document> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<Document> docs) {
        this.docs = docs;
    }
}

