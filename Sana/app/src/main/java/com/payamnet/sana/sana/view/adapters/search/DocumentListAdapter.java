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
        holder.docLine.setVisibility(View.VISIBLE);

        if (docs.get(position).getTitle() != null && !docs.get(position).getTitle().equals("")) {
            holder.docTitle.setText(docs.get(position).getTitle());
        } else {
            ((ViewGroup) holder.docTitle.getParent()).removeView(holder.docTitle);
            ((RelativeLayout.LayoutParams) holder.authorBar.getLayoutParams()).removeRule(RelativeLayout.BELOW);
            ((RelativeLayout.LayoutParams) holder.authorBar.getLayoutParams()).setMargins(((RelativeLayout.LayoutParams) holder.authorBar.getLayoutParams()).leftMargin, 20, 0, 10);
        }


        if (docs.get(position).getAuthor() != null && !docs.get(position).getAuthor().equals("")) {
            holder.docAuthor.setText(docs.get(position).getAuthor());
        } else {
            ((ViewGroup) holder.authorBar.getParent()).removeView(holder.authorBar);
            if (docs.get(position).getTitle() != null && !docs.get(position).getTitle().equals("")) {
                ((RelativeLayout.LayoutParams) holder.publisherBar.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.doc_title);
            } else {
                ((RelativeLayout.LayoutParams) holder.publisherBar.getLayoutParams()).removeRule(RelativeLayout.BELOW);
                ((RelativeLayout.LayoutParams) holder.publisherBar.getLayoutParams()).setMargins(((RelativeLayout.LayoutParams) holder.publisherBar.getLayoutParams()).leftMargin, 20, 0, 10);
            }
        }


        if (docs.get(position).getPublisher() != null && !docs.get(position).getPublisher().equals("")) {
            holder.docPublisher.setText(docs.get(position).getPublisher());
        } else {
            ((ViewGroup) holder.publisherBar.getParent()).removeView(holder.publisherBar);
            if (docs.get(position).getAuthor() != null && !docs.get(position).getAuthor().equals("")) {
                ((RelativeLayout.LayoutParams) holder.subjectBar.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.author_bar);
            } else if (docs.get(position).getTitle() != null && !docs.get(position).getTitle().equals("")) {
                ((RelativeLayout.LayoutParams) holder.subjectBar.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.doc_title);
            } else {
                ((RelativeLayout.LayoutParams) holder.subjectBar.getLayoutParams()).removeRule(RelativeLayout.BELOW);
                ((RelativeLayout.LayoutParams) holder.subjectBar.getLayoutParams()).setMargins(((RelativeLayout.LayoutParams) holder.subjectBar.getLayoutParams()).leftMargin, 20, 0, 10);
            }
        }


        if (docs.get(position).getSubject() != null && !docs.get(position).getSubject().equals("")) {
            holder.docSubject.setText(docs.get(position).getSubject());
        } else {
            ((ViewGroup) holder.subjectBar.getParent()).removeView(holder.subjectBar);
            if (docs.get(position).getPublisher() != null && !docs.get(position).getPublisher().equals("")) {
                ((RelativeLayout.LayoutParams) holder.docLine.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.publisher_bar);
            } else if (docs.get(position).getAuthor() != null && !docs.get(position).getAuthor().equals("")) {
                ((RelativeLayout.LayoutParams) holder.docLine.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.author_bar);
            } else if (docs.get(position).getTitle() != null && !docs.get(position).getTitle().equals("")) {
                ((RelativeLayout.LayoutParams) holder.docLine.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.doc_title);
            } else {
                holder.docLine.setVisibility(View.INVISIBLE);
            }
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
        RelativeLayout docLine;


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
            docLine = (RelativeLayout) itemView.findViewById(R.id.doc_line);
        }
    }

    public ArrayList<Document> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<Document> docs) {
        this.docs = docs;
    }
}

