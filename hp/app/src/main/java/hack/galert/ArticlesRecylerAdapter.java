package hack.galert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.xml.transform.Templates;

/**
 * Created by Ankit on 9/10/2016.
 */
public class ArticlesRecylerAdapter extends RecyclerView.Adapter<ArticlesRecylerAdapter.ArticleHolder> {


    private static Context context;
    private ArrayList<ArticlesModel> articles;
    private static ArticlesRecylerAdapter mInstance;

    public ArticlesRecylerAdapter(Context context) {
        this.context = context;
        this.articles = new ArrayList<>();
    }

    public static ArticlesRecylerAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ArticlesRecylerAdapter(context);
        }
        return mInstance;
    }

    public void setArticles(ArrayList<ArticlesModel> list) {
        this.articles = list;
        notifyDataSetChanged();
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_item_card, null, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleHolder viewHolder, int position) {

        Typeface materialTypeFace = FontManager.getInstance(context).getTypeFace(FontManager.FONT_MATERIAL);
        Typeface robotoMedium = FontManager.getInstance(context).getTypeFace(FontManager.FONT_ROBOTO_MEDIUM);
        Typeface robotoRegular = FontManager.getInstance(context).getTypeFace(FontManager.FONT_ROBOTO_REGULAR);

        viewHolder.refrence.setText(articles.get(position).refrence);
        viewHolder.readTime.setText(articles.get(position).readTime+" Minute read");
        viewHolder.articleHeader.setText(Html.fromHtml(articles.get(position).articleHeader));
        String newAbs = articles.get(position).articleAbstract.replaceAll("(\\n|\\t)", "").replaceAll("<.*?>","");
        Log.d("ArticlesAdapter",newAbs);
        viewHolder.articleAbstract.setText(Html.fromHtml(newAbs));
        //viewHolder.likes.setText(articles.get(position).likes);

//        // disable like button
//        if (articles.get(position).isLiked) {
//            viewHolder.likesIcon.setTextColor(context.getResources().getColor(R.color.PrimaryColor));
//        }



        if (SharedPreferenceManager.getInstance(context).getChoiceOnImageLoad()) {
            if (ConnectionUtils.getInstance(context).isConnected()) {
                //request picasso to load image
                Picasso.with(context)
                        .load(articles.get(position).imageUrl)
                        .into(viewHolder.articleImage);
            }

        }


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder {

        TextView refrence;
        TextView readTime;
        TextView articleHeader;
        TextView articleAbstract;
        LinearLayout articleLayout;
        ImageView articleImage;

        public ArticleHolder(View itemView) {
            super(itemView);

            articleLayout = (LinearLayout) itemView.findViewById(R.id.article_item_layout);
            refrence = (TextView) itemView.findViewById(R.id.articleRefrence);
            readTime = (TextView) itemView.findViewById(R.id.readLength);
            articleHeader = (TextView) itemView.findViewById(R.id.articlesHeader);
            articleAbstract = (TextView) itemView.findViewById(R.id.articlesAbstract);
//            likesIcon = (TextView) itemView.findViewById(R.id.likesIcon);
//            likes = (TextView) itemView.findViewById(R.id.likesCount);
            articleImage = (ImageView) itemView.findViewById(R.id.articleImage);

            articleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Adapter", "tapped");
                    int position = getAdapterPosition();
                    ArticlesRecylerAdapter adapter = ArticlesRecylerAdapter.getInstance(context);
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    ArticlesModel item = adapter.articles.get(position);

                    bundle.putString("ref", item.refrence);
                    bundle.putString("read", item.readTime);
                    bundle.putString("head", item.articleHeader);
                    bundle.putString("abs", item.articleAbstract);
                    bundle.putString("url", item.imageUrl);
                    //bundle.putParcelable(Constants.EXTRAA_DETAILS, adapter.articles.get(position));
                    detailsIntent.putExtras(bundle);
                    context.startActivity(detailsIntent);

                }
            });

        }
    }

}
