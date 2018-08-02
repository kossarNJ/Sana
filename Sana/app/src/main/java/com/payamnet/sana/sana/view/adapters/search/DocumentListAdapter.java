package com.payamnet.sana.sana.view.adapters.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.payamnet.sana.sana.R;
import com.payamnet.sana.sana.constants.Constants;
import com.payamnet.sana.sana.model.Document;

import java.util.ArrayList;

/**
 * Created by kosar on 8/2/18.
 */

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<Document> docs = new ArrayList<>();

    public DocumentListAdapter(Context context, ArrayList<Document> docs) {
        this.context = context;
        this.docs = docs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        return new MyViewHolder(v);
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

        if (docs.get(position).getTitle() != null && !docs.get(position).getTitle().equals("")) {
            holder.docTitle.setText(docs.get(position).getTitle());
        } else {
            holder.docTitle.setVisibility(View.INVISIBLE);
        }
        if (docs.get(position).getAuthor() != null && !docs.get(position).getAuthor().equals("")) {
            holder.docAuthor.setText(docs.get(position).getAuthor());
        } else {
            holder.authorBar.setVisibility(View.INVISIBLE);
        }
        if (docs.get(position).getPublisher() != null && !docs.get(position).getPublisher().equals("")) {
            holder.docPublisher.setText(docs.get(position).getPublisher());
        } else {
            holder.publisherBar.setVisibility(View.INVISIBLE);
        }
        if (docs.get(position).getSubject() != null && !docs.get(position).getSubject().equals("")) {
            holder.docSubject.setText(docs.get(position).getSubject());
        } else {
            holder.subjectBar.setVisibility(View.INVISIBLE);
        }

        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Constants.TAG, "onClick: should go to detail page.");
                // TODO: 8/2/18 implement later.
            }
        });

    }

    @Override
    public int getItemCount() {
        return docs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView docTitle;
        TextView docAuthor;
        TextView docPublisher;
        TextView docSubject;
        Button detailButton;
        RelativeLayout authorBar;
        RelativeLayout publisherBar;
        RelativeLayout subjectBar;


        MyViewHolder(View itemView) {
            super(itemView);

            docTitle = (TextView) itemView.findViewById(R.id.doc_title);
            docAuthor = (TextView) itemView.findViewById(R.id.doc_author);
            docPublisher = (TextView) itemView.findViewById(R.id.doc_publisher);
            docSubject = (TextView) itemView.findViewById(R.id.doc_subject);
            detailButton = (Button) itemView.findViewById(R.id.doc_detail_button);
            authorBar = (RelativeLayout) itemView.findViewById(R.id.author_bar);
            publisherBar = (RelativeLayout) itemView.findViewById(R.id.publisher_bar);
            subjectBar = (RelativeLayout) itemView.findViewById(R.id.subject_bar);
        }
    }

    public ArrayList<Document> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<Document> docs) {
        this.docs = docs;
    }
}

